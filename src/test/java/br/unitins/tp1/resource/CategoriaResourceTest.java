package br.unitins.tp1.resource;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.CategoriaDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class CategoriaResourceTest extends BaseTest {

    private static final String CATEGORY_PATH = "/categorias";
    private static final String CATEGORY_ID_PATH = CATEGORY_PATH + "/{id}";
    private static final String CATEGORY_SEARCH_PATH = CATEGORY_PATH + "/search/{nome}";

    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testGetAll() {
        given()
            .when().get(CATEGORY_PATH)
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(4));
    }

    @Test
    @TestSecurity(user = "user", roles = {"USER"})
    public void testFindByIdNotFound() {
        given()
            .when().get(CATEGORY_ID_PATH, 9999)
            .then()
                .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"ADMIN"})
    @TestTransaction
    public void testCreateCategoria() {

        CategoriaDTO dto = new CategoriaDTO("Nova Categoria");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post(CATEGORY_PATH)
        .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("nome", is("Nova Categoria"));
    }

    @Test
    @TestSecurity(user = "admin", roles = {"ADMIN"})
    @TestTransaction
    public void testUpdateCategoria() {

        Long idCriado =
            given()
                .contentType(ContentType.JSON)
                .body(new CategoriaDTO("Categoria Teste"))
            .when()
                .post(CATEGORY_PATH)
            .then()
                .extract().jsonPath().getLong("id");

        CategoriaDTO dtoUpdate = new CategoriaDTO("Categoria Atualizada");

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .pathParam("id", idCriado)
        .when()
            .put(CATEGORY_ID_PATH)
        .then()
            .statusCode(200)
            .body("id", equalTo(idCriado.intValue()))
            .body("nome", is("Categoria Atualizada"));
    }

    @Test
    @TestSecurity(user = "admin", roles = {"ADMIN"})
    @TestTransaction
    public void testDeleteCategoria() {

        Long idCriado =
            given()
                .contentType(ContentType.JSON)
                .body(new CategoriaDTO("Categoria Remover"))
            .when()
                .post(CATEGORY_PATH)
            .then()
                .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", idCriado)
        .when()
            .delete(CATEGORY_ID_PATH)
        .then()
            .statusCode(204);

        given()
            .pathParam("id", idCriado)
        .when()
            .get(CATEGORY_ID_PATH)
        .then()
            .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin", roles = {"ADMIN"})
    @TestTransaction
    public void testFindByNome() {

        given()
            .pathParam("nome", "Edição")
        .when()
            .get(CATEGORY_SEARCH_PATH)
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(2))
            .body("[0].nome", containsString("Edição"));
    }
}
