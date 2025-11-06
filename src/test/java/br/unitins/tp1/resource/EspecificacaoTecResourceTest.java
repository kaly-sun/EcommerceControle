package br.unitins.tp1.resource;

import br.unitins.tp1.dto.EspecificacaoTecDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
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
                250.0,
                "Plástico ABS",
                12,
                "Bluetooth 5.1",
                "15cm x 10cm x 6cm",
                "Giroscópio, Acelerômetro"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(ESPEC_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", notNullValue())
                .body("peso", is(250.0f))
                .body("material", is("Plástico ABS"))
                .body("autonomiaBateria", is(12))
                .body("conectividade", is("Bluetooth 5.1"))
                .body("dimensoes", is("15cm x 10cm x 6cm"))
                .body("sensoresExtras", is("Giroscópio, Acelerômetro"));
    }

    @Test
    @TestTransaction
    public void testUpdateEspecificacao() {

        EspecificacaoTecDTO original = new EspecificacaoTecDTO(
                300.0,
                "Plástico",
                10,
                "Bluetooth 4.2",
                "14cm x 9cm x 6cm",
                "Nenhum"
        );

        Long id = given()
                .contentType(ContentType.JSON)
                .body(original)
                .when().post(ESPEC_PATH)
                .then()
                    .extract().jsonPath().getLong("id");


        EspecificacaoTecDTO atualizado = new EspecificacaoTecDTO(
                280.0,
                "Plástico Premium",
                15,
                "Bluetooth 5.3",
                "15cm x 10cm x 6cm",
                "Acelerômetro, Gatilhos Adaptativos"
        );

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .pathParam("id", id)
            .when().put(ESPEC_ID_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", equalTo(id.intValue()))
                .body("peso", is(280.0f))
                .body("material", is("Plástico Premium"))
                .body("autonomiaBateria", is(15))
                .body("conectividade", is("Bluetooth 5.3"))
                .body("dimensoes", is("15cm x 10cm x 6cm"))
                .body("sensoresExtras", is("Acelerômetro, Gatilhos Adaptativos"));
    }

    @Test
    @TestTransaction
    public void testDeleteEspecificacao() {

        EspecificacaoTecDTO dto = new EspecificacaoTecDTO(
                200.0,
                "Plástico rígido",
                8,
                "Cabo USB-C",
                "13cm x 8cm x 5cm",
                "Nenhum"
        );

        Long id = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post(ESPEC_PATH)
                .then()
                    .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", id)
            .when().delete(ESPEC_ID_PATH)
            .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given()
            .pathParam("id", id)
            .when().get(ESPEC_ID_PATH)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
