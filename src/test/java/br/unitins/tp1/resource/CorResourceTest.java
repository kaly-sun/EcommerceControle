package br.unitins.tp1.resource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.CorDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;

@QuarkusTest
@TestSecurity(user = "admin", roles = {"ADMIN"})
public class CorResourceTest extends BaseTest {

    private static final String COR_PATH = "/cor";
    private static final String COR_ID_PATH = COR_PATH + "/{id}";

    @Test
    public void testFindAll() {
        given()
            .when().get(COR_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", greaterThanOrEqualTo(10));
    }

    @Test
    public void testFindById() {
        given()
            .pathParam("id", 2)
        .when()
            .get(COR_ID_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("id", equalTo(2))
            .body("nome", is("Cosmic Red"))
            .body("descricao", is("Versão especial do DualSense PS5 em vermelho vibrante"));
    }

    @Test
    public void testFindByIdNotFound() {
        given()
            .pathParam("id", 9999)
        .when()
            .get(COR_ID_PATH)
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    public void testInsertCor() {
        CorDTO dto = new CorDTO(
                "Midnight Black Matte",
                "Versão fosca da cor Midnight Black"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post(COR_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("id", notNullValue())
            .body("nome", is("Midnight Black Matte"))
            .body("descricao", is("Versão fosca da cor Midnight Black"));
    }

    @Test
@TestTransaction
public void testUpdateCor() {

    CorDTO novaCor = new CorDTO(
            "Cosmic Red Test",
            "Cor criada apenas para teste de update"
    );

    Long idCriado =
        given()
            .contentType(ContentType.JSON)
            .body(novaCor)
        .when()
            .post(COR_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract().jsonPath().getLong("id");

    CorDTO dtoUpdate = new CorDTO(
            "Cosmic Red Test Dark",
            "Versão escura da cor de teste"
    );

    given()
        .contentType(ContentType.JSON)
        .body(dtoUpdate)
        .pathParam("id", idCriado)
    .when()
        .put(COR_ID_PATH)
    .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("id", equalTo(idCriado.intValue()))
        .body("nome", is("Cosmic Red Test Dark"))
        .body("descricao", is("Versão escura da cor de teste"));
}

    @Test
    @TestTransaction
    public void testDeleteCor() {
        // Cria a cor para fazer o delete
        CorDTO dto = new CorDTO(
                "Test Delete",
                "Cor usada apenas para teste de exclusão"
        );

        Long idCriado =
            given()
                .contentType(ContentType.JSON)
                .body(dto)
            .when()
                .post(COR_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", idCriado)
        .when()
            .delete(COR_ID_PATH)
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given()
            .pathParam("id", idCriado)
        .when()
            .get(COR_ID_PATH)
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
