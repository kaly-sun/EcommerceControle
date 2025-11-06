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
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@QuarkusTest
public class CategoriaResourceTest extends BaseTest {

    private static final String CATEGORY_PATH = "/categorias";
    private static final String CATEGORY_ID_PATH = CATEGORY_PATH + "/{id}";
    private static final String CATEGORY_SEARCH_PATH = CATEGORY_PATH + "/search/{nome}";

    @Test
    public void testGetAll() {
        given()
            .when().get(CATEGORY_PATH)
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testFindByIdNotFound() {
        given()
            .when().get(CATEGORY_ID_PATH, 9999)
            .then()
                .statusCode(204); // ✅ Seu backend retorna NO_CONTENT quando não acha
    }

    @Test
    @TestTransaction
    public void testCreateCategoria() {

        CategoriaDTO dto = new CategoriaDTO("Edição Limitada PS5");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(CATEGORY_PATH)
            .then()
                .statusCode(200) // ✅ seu POST retorna OK, não CREATED
                .body("id", notNullValue())
                .body("nome", is("Edição Limitada PS5"));
    }

    @Test
    @TestTransaction
    public void testUpdateCategoria() {

        Long idCriado = 
            given()
                .contentType(ContentType.JSON)
                .body(new CategoriaDTO("Controle Xbox Série X"))
                .when().post(CATEGORY_PATH)
                .then()
                    .statusCode(200) // ✅ POST retorna 200
                    .extract().jsonPath().getLong("id");

        CategoriaDTO dtoUpdate = new CategoriaDTO("Controle Xbox Série X - Edição Elite");

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .pathParam("id", idCriado)
            .when().put(CATEGORY_ID_PATH)
            .then()
                .statusCode(200)
                .body("id", equalTo(idCriado.intValue()))
                .body("nome", is("Controle Xbox Série X - Edição Elite"));
    }

    @Test
    @TestTransaction
    public void testDeleteCategoria() {

        Long idCriado =
            given()
                .contentType(ContentType.JSON)
                .body(new CategoriaDTO("Controle Temático Star Wars"))
                .when().post(CATEGORY_PATH)
                .then()
                    .statusCode(200) // ✅ POST retorna 200
                    .extract().jsonPath().getLong("id");

        // Deletar
        given()
            .pathParam("id", idCriado)
            .when().delete(CATEGORY_ID_PATH)
            .then()
                .statusCode(204); // ✅ seu DELETE retorna NO_CONTENT

        // Validar que foi deletada
        given()
            .pathParam("id", idCriado)
            .when().get(CATEGORY_ID_PATH)
            .then()
                .statusCode(204); // ✅ conforme seu backend
    }

    @Test
    @TestTransaction
    public void testFindByNome() {

        // cria 1
        given()
            .contentType(ContentType.JSON)
            .body(new CategoriaDTO("Edição Limitada God of War"))
            .when().post(CATEGORY_PATH)
            .then()
                .statusCode(200); // ✅

        // cria 2
        given()
            .contentType(ContentType.JSON)
            .body(new CategoriaDTO("Edição Limitada Spider-Man"))
            .when().post(CATEGORY_PATH)
            .then()
                .statusCode(200); // ✅

        // pesquisa
        given()
            .pathParam("nome", "Edição Limitada")
            .when().get(CATEGORY_SEARCH_PATH)
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2))
                .body("[0].nome", containsString("Edição Limitada"));
    }
}
