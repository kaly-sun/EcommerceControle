package br.unitins.tp1.resource;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.MarcaDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class MarcaResourceTest extends BaseTest {

    private static final String MARCA_PATH = "/marcas";
    private static final String MARCA_ID_PATH = MARCA_PATH + "/{id}";
    private static final String MARCA_SEARCH_PATH = MARCA_PATH + "/search/{nome}";

    @Test
    public void testGetAll() {
        given()
            .when().get(MARCA_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testFindByIdNotFound() {
        given()
            .when().get(MARCA_ID_PATH, 9999)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    public void testCreateMarca() {

        MarcaDTO dto = new MarcaDTO(
                "Sony",
                "Japão",
                1946,
                "https://www.sony.com",
                "sony-logo.png"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(MARCA_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", notNullValue())
                .body("nome", is("Sony"))
                .body("paisOrigem", is("Japão"))
                .body("anoFundacao", is(1946))
                .body("siteOficial", is("https://www.sony.com"))
                .body("logo", is("sony-logo.png"));
    }

    @Test
    @TestTransaction
    public void testUpdateMarca() {

        MarcaDTO dto = new MarcaDTO(
                "Microsoft",
                "Estados Unidos",
                1975,
                "https://www.microsoft.com",
                "microsoft-logo.png"
        );

        Long idCriado = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post(MARCA_PATH)
                .then()
                    .extract().jsonPath().getLong("id");

        MarcaDTO atualizado = new MarcaDTO(
                "Microsoft Gaming",
                "Estados Unidos",
                1975,
                "https://gaming.microsoft.com",
                "ms-gaming-logo.png"
        );

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .pathParam("id", idCriado)
            .when().put(MARCA_ID_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", equalTo(idCriado.intValue()))
                .body("nome", is("Microsoft Gaming"))
                .body("siteOficial", is("https://gaming.microsoft.com"))
                .body("logo", is("ms-gaming-logo.png"));
    }

    @Test
    @TestTransaction
    public void testDeleteMarca() {

        MarcaDTO dto = new MarcaDTO(
                "Nintendo",
                "Japão",
                1889,
                "https://www.nintendo.com",
                "nintendo-logo.png"
        );

        Long idCriado = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post(MARCA_PATH)
                .then()
                    .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", idCriado)
            .when().delete(MARCA_ID_PATH)
            .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given()
            .pathParam("id", idCriado)
            .when().get(MARCA_ID_PATH)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    public void testFindByNome() {

        given()
            .contentType(ContentType.JSON)
            .body(new MarcaDTO("8BitDo", "China", 2013, "https://www.8bitdo.com", "8bitdo.png"))
            .when().post(MARCA_PATH);

        given()
            .contentType(ContentType.JSON)
            .body(new MarcaDTO("8BitDo Pro Series", "China", 2015, "https://www.8bitdo.com/pro", "8bitdo-pro.png"))
            .when().post(MARCA_PATH);

        given()
            .pathParam("nome", "8BitDo")
            .when().get(MARCA_SEARCH_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", greaterThanOrEqualTo(2))
                .body("[0].nome", containsString("8BitDo"));
    }
}
