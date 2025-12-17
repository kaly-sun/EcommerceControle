package br.unitins.tp1.resource;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.ModeloDTO;
import br.unitins.tp1.dto.PlataformaDTO;
import br.unitins.tp1.model.Marca;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class ModeloResourceTest extends BaseTest {

    private static final String MARCA_PATH = "/marcas";
    private static final String PLATAFORMA_PATH = "/plataformas";
    private static final String MODELO_PATH = "/modelos";
    private static final String MODELO_ID_PATH = MODELO_PATH + "/{id}";

    Marca marca = new Marca("Sony Interactive Entertainment", "Japão", 1946,
        "https://upload.wikimedia.org/wikipedia/commons/2/21/Sony_logo.svg",
        "https://www.playstation.com");

    

    @Test
    @TestSecurity(user = "test-admin", roles = "ADMIN")
    public void testGetAll() {
        given()
            .when().get(MODELO_PATH)
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @TestSecurity(user = "test-admin", roles = "ADMIN")
    public void testFindByIdNotFound() {
        given()
            .when().get(MODELO_ID_PATH, 9999)
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

@Test
@TestTransaction
@TestSecurity(user = "test-admin", roles = "ADMIN")
public void testCreateModelo() {

    MarcaDTO marcaDTO = new MarcaDTO(
        "Sony Interactive Entertainment",
        "Japão",
        1946,
        "https://upload.wikimedia.org/wikipedia/commons/2/21/Sony_logo.svg",
        "https://www.playstation.com"
    );

    Long marcaId =
        given()
            .contentType(ContentType.JSON)
            .body(marcaDTO)
        .when()
            .post(MARCA_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .jsonPath()
            .getLong("id");

    assertNotNull(marcaId);

    PlataformaDTO plataformaDTO = new PlataformaDTO(
        "PlayStation 4",
        "Sony",
        "8ª Geração",
        2013,
        "Console"
    );

    Long plataformaId =
        given()
            .contentType(ContentType.JSON)
            .body(plataformaDTO)
        .when()
            .post(PLATAFORMA_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .jsonPath()
            .getLong("id");

    assertNotNull(plataformaId);

    ModeloDTO dto = new ModeloDTO(
        "DualSense",
        "v1",
        2020,
        "Controle oficial do PlayStation 5",
        "CFI-ZCT1W",
        true,
        marcaId,
        plataformaId
    );

    given()
        .contentType(ContentType.JSON)
        .body(dto)
    .when()
        .post(MODELO_PATH)
    .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("id", notNullValue())
        .body("nome", is("DualSense"));
}



 @Test
@TestTransaction
@TestSecurity(user = "test-admin", roles = "ADMIN")
public void testUpdateModelo() {

    Long marcaId =
        given()
            .contentType(ContentType.JSON)
            .body(new MarcaDTO(
                "Sony",
                "Japão",
                1946,
                "https://www.sony.com",
                "sony.png"
            ))
        .when()
            .post(MARCA_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .jsonPath()
            .getLong("id");

    Long plataformaId =
        given()
            .contentType(ContentType.JSON)
            .body(new PlataformaDTO(
                "PlayStation 4",
                "Sony",
                "8ª Geração",
                2013,
                "Console"
            ))
        .when()
            .post(PLATAFORMA_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .jsonPath()
            .getLong("id");

    ModeloDTO dto = new ModeloDTO(
        "DualShock 4",
        "v2",
        2013,
        "Controle oficial do PlayStation 4",
        "CUH-ZCT2",
        true,
        marcaId,
        plataformaId
    );

    Long idCriado =
        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post(MODELO_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .jsonPath()
            .getLong("id");

    ModeloDTO atualizado = new ModeloDTO(
        "DualShock 4",
        "v3",
        2014,
        "Controle atualizado do PlayStation 4",
        "CUH-ZCT2-REV",
        false,
        marcaId,
        plataformaId
    );

    given()
        .contentType(ContentType.JSON)
        .body(atualizado)
        .pathParam("id", idCriado)
    .when()
        .put(MODELO_ID_PATH)
    .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("versao", is("v3"))
        .body("ativo", is(false));
}

@Test
@TestTransaction
@TestSecurity(user = "test-admin", roles = "ADMIN")
public void testDeleteModelo() {

    Long marcaId =
        given()
            .contentType(ContentType.JSON)
            .body(new MarcaDTO(
                "Sony",
                "Japão",
                1946,
                "https://sony.com",
                "sony.png"
            ))
        .when()
            .post(MARCA_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .jsonPath()
            .getLong("id");

    Long plataformaId =
        given()
            .contentType(ContentType.JSON)
            .body(new PlataformaDTO(
                "PlayStation 4",
                "Sony",
                "8ª Geração",
                2013,
                "Console"
            ))
        .when()
            .post(PLATAFORMA_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .jsonPath()
            .getLong("id");

    ModeloDTO dto = new ModeloDTO(
        "Modelo Teste Delete",
        "v1",
        2022,
        "Modelo temporário",
        "TMP-001",
        true,
        marcaId,
        plataformaId
    );

    Long idCriado =
        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post(MODELO_PATH)
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .jsonPath()
            .getLong("id");

    given()
        .pathParam("id", idCriado)
    .when()
        .delete(MODELO_ID_PATH)
    .then()
        .statusCode(Response.Status.NO_CONTENT.getStatusCode());

    given()
        .pathParam("id", idCriado)
    .when()
        .get(MODELO_ID_PATH)
    .then()
        .statusCode(Response.Status.NOT_FOUND.getStatusCode());
}
        } 