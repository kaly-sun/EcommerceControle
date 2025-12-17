package br.unitins.tp1.resource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.PlataformaDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Response.Status;

@QuarkusTest
public class PlataformaResourceTest extends BaseTest {

    private static final String PLATAFORMA_PATH = "/plataformas";
    private static final String PLATAFORMA_ID_PATH = PLATAFORMA_PATH + "/{id}";

    @Test
    @TestSecurity(user = "test-admin", roles = "ADMIN")
    public void testGetAll() {
        given()
            .when().get(PLATAFORMA_PATH)
            .then()
                .log().ifValidationFails()
                .statusCode(Status.OK.getStatusCode())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @TestSecurity(user = "test-admin", roles = "ADMIN")
    public void testFindByIdNotFound() {
        given()
            .when().get(PLATAFORMA_ID_PATH, 9999)
            .then()
                .log().ifValidationFails()
                .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    @TestSecurity(user = "test-admin", roles = "ADMIN")
    public void testCreatePlataforma() {

        PlataformaDTO dto = new PlataformaDTO(
                "PlayStation 5",
                "Sony",
                "9ª Geração",
                2020,
                "Console"
        );

        Response createResp = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(PLATAFORMA_PATH)
            .then()
                .log().ifValidationFails()
                .statusCode(Status.OK.getStatusCode())
                .extract().response();

        Long id = createResp.jsonPath().getLong("id");
        assertNotNull(id);

        createResp.then()
            .body("id", notNullValue())
            .body("nome", is("PlayStation 5"))
            .body("fabricante", is("Sony"))
            .body("geracao", is("9ª Geração"))
            .body("anoLancamento", is(2020))
            .body("tipo", is("Console"));
    }

    @Test
    @TestTransaction
    @TestSecurity(user = "test-admin", roles = "ADMIN")
    public void testUpdatePlataforma() {

        PlataformaDTO original = new PlataformaDTO(
                "Xbox One",
                "Microsoft",
                "8ª Geração",
                2013,
                "Console"
        );

        Response createResp = given()
                .contentType(ContentType.JSON)
                .body(original)
                .when().post(PLATAFORMA_PATH)
                .then()
                    .log().ifValidationFails()
                    .statusCode(Status.OK.getStatusCode())
                    .extract().response();

        Long id = createResp.jsonPath().getLong("id");
        assertNotNull(id);

        PlataformaDTO update = new PlataformaDTO(
                "Xbox One S",
                "Microsoft",
                "8ª Geração",
                2016,
                "Console Slim"
        );

        Response updateResp = given()
            .contentType(ContentType.JSON)
            .body(update)
            .pathParam("id", id)
            .when().put(PLATAFORMA_ID_PATH)
            .then()
                .log().ifValidationFails()
                .statusCode(Status.OK.getStatusCode())
                .extract().response();

        updateResp.then()
            .body("id", equalTo(id.intValue()))
            .body("nome", is("Xbox One S"))
            .body("fabricante", is("Microsoft"))
            .body("geracao", is("8ª Geração"))
            .body("anoLancamento", is(2016))
            .body("tipo", is("Console Slim"));
    }

    @Test
    @TestTransaction
    @TestSecurity(user = "test-admin", roles = "ADMIN")
    public void testDeletePlataforma() {

        PlataformaDTO dto = new PlataformaDTO(
                "Nintendo Switch",
                "Nintendo",
                "8ª Geração",
                2017,
                "Híbrido"
        );

        Response createResp = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post(PLATAFORMA_PATH)
                .then()
                    .log().ifValidationFails()
                    .statusCode(Status.OK.getStatusCode())
                    .extract().response();

        Long id = createResp.jsonPath().getLong("id");
        assertNotNull(id);

        given()
            .pathParam("id", id)
            .when().delete(PLATAFORMA_ID_PATH)
            .then()
                .log().ifValidationFails()
                .statusCode(Status.NO_CONTENT.getStatusCode());

        given()
            .pathParam("id", id)
            .when().get(PLATAFORMA_ID_PATH)
            .then()
                .log().ifValidationFails()
                .statusCode(Status.NOT_FOUND.getStatusCode());
    }
}
