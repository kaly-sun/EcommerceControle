package br.unitins.tp1.resource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.EspecificacaoTecDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;

@QuarkusTest
@TestSecurity(user = "admin", roles = {"ADMIN"})
public class EspecificacaoTecResourceTest extends BaseTest {

    private static final String ESPEC_PATH = "/especificacoes";
    private static final String ESPEC_ID_PATH = ESPEC_PATH + "/{id}";

    @Test
    public void testGetAll() {
        given()
            .when().get(ESPEC_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testFindByIdNotFound() {
        given()
            .when().get(ESPEC_ID_PATH, 9999)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    public void testCreateEspecificacao() {
        EspecificacaoTecDTO dto = new EspecificacaoTecDTO(
            280.0, "Plástico ABS", 12, "Bluetooth 5.1", "16x10x6 cm", "Giroscópio"
        );

        Long idCriado = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(ESPEC_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", notNullValue())
                .body("peso", equalTo(280.0f))
                .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", idCriado)
            .when().get(ESPEC_ID_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", equalTo(idCriado.intValue()));
    }

    @Test
    @TestTransaction
    public void testUpdateEspecificacao() {
        EspecificacaoTecDTO dto = new EspecificacaoTecDTO(
            280.0, "Plástico ABS", 12, "Bluetooth 5.1", "16x10x6 cm", "Giroscópio"
        );

        Long idCriado = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(ESPEC_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().jsonPath().getLong("id");

        EspecificacaoTecDTO dtoUpdate = new EspecificacaoTecDTO(
            300.0, "Plástico reforçado", 14, "Bluetooth 5.2", "17x11x7 cm", "Giroscópio + Acelerômetro"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .pathParam("id", idCriado)
            .when().put(ESPEC_ID_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", equalTo(idCriado.intValue()));
    }

    @Test
    @TestTransaction
    public void testDeleteEspecificacao() {
        EspecificacaoTecDTO dto = new EspecificacaoTecDTO(
            280.0, "Plástico ABS", 12, "Bluetooth 5.1", "16x10x6 cm", "Giroscópio"
        );

        Long idCriado = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(ESPEC_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", idCriado)
            .when().delete(ESPEC_ID_PATH)
            .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given()
            .pathParam("id", idCriado)
            .when().get(ESPEC_ID_PATH)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
