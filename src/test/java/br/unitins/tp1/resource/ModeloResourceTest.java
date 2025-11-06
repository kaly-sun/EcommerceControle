package br.unitins.tp1.resource;

import br.unitins.tp1.dto.ModeloDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ModeloResourceTest extends BaseTest {

    private static final String MODELO_PATH = "/modelos";
    private static final String MODELO_ID_PATH = MODELO_PATH + "/{id}";

    @Test
    public void testGetAll() {
        given()
            .when().get(MODELO_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testFindByIdNotFound() {
        given()
            .when().get(MODELO_ID_PATH, 9999)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestTransaction
    public void testCreateModelo() {

        ModeloDTO dto = new ModeloDTO(
                "DualSense",
                "CFI-ZCT1W",
                2020,
                "Controle do PlayStation 5",
                "DS-PS5-001",
                true
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(MODELO_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", notNullValue())
                .body("nome", is("DualSense"))
                .body("versao", is("CFI-ZCT1W"))
                .body("anoLancamento", is(2020))
                .body("descricao", is("Controle do PlayStation 5"))
                .body("codigoReferencia", is("DS-PS5-001"))
                .body("ativo", is(true));
    }

    @Test
    @TestTransaction
    public void testUpdateModelo() {

        ModeloDTO dto = new ModeloDTO(
                "DualShock 4",
                "CUH-ZCT2",
                2016,
                "Controle do PlayStation 4",
                "DS4-PS4-001",
                true
        );

        Long idCriado = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post(MODELO_PATH)
                .then()
                    .extract().jsonPath().getLong("id");

        ModeloDTO atualizado = new ModeloDTO(
                "DualShock 4 Pro",
                "CUH-ZCT2-PRO",
                2017,
                "Versão atualizada com mais precisão",
                "DS4-PS4-777",
                false
        );

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .pathParam("id", idCriado)
            .when().put(MODELO_ID_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", equalTo(idCriado.intValue()))
                .body("nome", is("DualShock 4 Pro"))
                .body("versao", is("CUH-ZCT2-PRO"))
                .body("anoLancamento", is(2017))
                .body("descricao", is("Versão atualizada com mais precisão"))
                .body("codigoReferencia", is("DS4-PS4-777"))
                .body("ativo", is(false));
    }

    @Test
    @TestTransaction
    public void testDeleteModelo() {

        ModeloDTO dto = new ModeloDTO(
                "Xbox Elite Controller",
                "Series 2",
                2019,
                "Controle premium para Xbox",
                "XELITE-2",
                true
        );

        Long idCriado = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post(MODELO_PATH)
                .then()
                    .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", idCriado)
            .when().delete(MODELO_ID_PATH)
            .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given()
            .pathParam("id", idCriado)
            .when().get(MODELO_ID_PATH)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
