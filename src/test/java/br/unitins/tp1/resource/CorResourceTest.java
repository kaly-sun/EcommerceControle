package br.unitins.tp1.resource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.CorDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class CorResourceTest extends BaseTest {

    private static final String COR_PATH = "/cor";
    private static final String COR_ID_PATH = COR_PATH + "/{id}";

    @Test
    public void testFindAll() {
        given()
            .when().get(COR_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testFindByIdNotFound() {
        given()
            .when().get(COR_ID_PATH, 9999)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    public void testInsertCor() {
        CorDTO dto = new CorDTO("Cosmic Red", "Cor vibrante inspirada nos controles do PS5.");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(COR_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", notNullValue())
                .body("nome", is("Cosmic Red"))
                .body("descricao", is("Cor vibrante inspirada nos controles do PS5."));
    }

    @Test
    @TestTransaction
    public void testUpdateCor() {

        CorDTO dto = new CorDTO("Midnight Black", "Cor clássica e elegante.");

        Long idCriado = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post(COR_PATH)
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .extract().jsonPath().getLong("id");

        CorDTO dtoUpdate = new CorDTO("Midnight Black Matte", "Versão fosca da clássica cor preta.");

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .pathParam("id", idCriado)
            .when().put(COR_ID_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", equalTo(idCriado.intValue()))
                .body("nome", is("Midnight Black Matte"))
                .body("descricao", is("Versão fosca da clássica cor preta."));
    }

    @Test
    @TestTransaction
    public void testDeleteCor() {

        CorDTO dto = new CorDTO("Pulse Blue", "Cor azul brilhante para controles especiais.");

        Long idCriado = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post(COR_PATH)
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", idCriado)
            .when().delete(COR_ID_PATH)
            .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given()
            .pathParam("id", idCriado)
            .when().get(COR_ID_PATH)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
