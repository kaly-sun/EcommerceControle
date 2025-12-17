package br.unitins.tp1.resource;

import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import static io.restassured.RestAssured.given;

@QuarkusTest
@TestSecurity(user = "admin", roles = {"ADMIN"})
public class ControleResourceCategoriaTest {

    @Test
    public void buscarControlePorCategoria() {

        String nomeCategoria = "Edição Limitada";

        given()
            .when()
                .get("/controles/search/categoria/" + nomeCategoria)
            .then()
                .statusCode(200)
                .body("$", notNullValue());
    }
}
