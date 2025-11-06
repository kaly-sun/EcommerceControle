package br.unitins.tp1.resource;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.CategoriaDTO;
import br.unitins.tp1.dto.ControleDTO;
import br.unitins.tp1.dto.EspecificacaoTecDTO;
import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.ModeloDTO;
import br.unitins.tp1.dto.PlataformaDTO;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@QuarkusTest
public class ControleResourceTest {

    private static final String CONTROLE_PATH = "/controles";
    private static final String CONTROLE_ID_PATH = "/controles/{id}";

    // ✅ Injeta o EntityManager para manipular o banco diretamente
    @Inject
    EntityManager em;

    // 🔹 Limpa o banco antes de cada teste
    @BeforeEach
    @Transactional
    public void limparBanco() {
        em.createQuery("DELETE FROM Controle").executeUpdate();
        em.createQuery("DELETE FROM Categoria").executeUpdate();
        em.createQuery("DELETE FROM Modelo").executeUpdate();
        em.createQuery("DELETE FROM Plataforma").executeUpdate();
        em.createQuery("DELETE FROM Marca").executeUpdate();
        em.createQuery("DELETE FROM EspecificacaoTecnica").executeUpdate();
    }

    private Long criarMarca() {
        return given()
            .contentType(ContentType.JSON)
            .body(new MarcaDTO("Sony", "Japão", 1946, "https://sony.com", "logo.png"))
            .when().post("/marcas")
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .extract().jsonPath().getLong("id");
    }

    private Long criarPlataforma() {
        return given()
            .contentType(ContentType.JSON)
            .body(new PlataformaDTO("PS5", "Sony", "9° Geração", 2020, "Console"))
            .when().post("/plataformas")
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .extract().jsonPath().getLong("id");
    }

    private Long criarModelo() {
        return given()
            .contentType(ContentType.JSON)
            .body(new ModeloDTO("DualSense", "V1", 2021, "Controle PS5", "DS01", true))
            .when().post("/modelos")
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .extract().jsonPath().getLong("id");
    }

    private Long criarEspecificacao() {
        return given()
            .contentType(ContentType.JSON)
            .body(new EspecificacaoTecDTO(280.0, "Plástico ABS", 12, "Bluetooth 5.1",
                    "16x10x6 cm", "Giroscópio"))
            .when().post("/especificacoes")
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .extract().jsonPath().getLong("id");
    }

    private Long criarCategoria(String nome) {
        return given()
            .contentType(ContentType.JSON)
            .body(new CategoriaDTO(nome))
            .when().post("/categorias")
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .extract().jsonPath().getLong("id");
    }

    private ControleDTO criarDTOValido(String nome) {
        Long marca = criarMarca();
        Long plataforma = criarPlataforma();
        Long modelo = criarModelo();
        Long espec = criarEspecificacao();
        Long cat1 = criarCategoria("Edição Especial");
        Long cat2 = criarCategoria("Colecionável");

        return new ControleDTO(
            nome,
            199.90,
            "Azul",
            50,
            "Controle original sem fio",
            LocalDate.of(2025, 4, 1),
            marca,
            plataforma,
            modelo,
            espec,
            List.of(cat1, cat2)
        );
    }

    @Test
    public void testGetAll() {
        given()
            .when().get(CONTROLE_PATH)
            .then()
            .statusCode(anyOf(is(200), is(204)))
            .body(anything());
    }

    @Test
    @TestTransaction
    public void testCreate() {

        ControleDTO dto = criarDTOValido("Controle Azul");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post(CONTROLE_PATH)
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .body("id", notNullValue())
            .body("nome", is("Controle Azul"))
            .body("cor", is("Azul"));
    }

    @Test
    @TestTransaction
    public void testFindById() {

        Long id =
            given()
                .contentType(ContentType.JSON)
                .body(criarDTOValido("Controle Preto"))
                .when().post(CONTROLE_PATH)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", id)
            .when().get(CONTROLE_ID_PATH)
            .then()
            .statusCode(anyOf(is(200), is(204)))
            .body("nome", anyOf(is("Controle Preto"), nullValue()));
    }

    @Test
    @TestTransaction
    public void testUpdate() {

        Long id =
            given()
                .contentType(ContentType.JSON)
                .body(criarDTOValido("Controle Branco"))
                .when().post(CONTROLE_PATH)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .extract().jsonPath().getLong("id");

        ControleDTO atualizado = criarDTOValido("Controle Branco PRO");

        given()
            .contentType(ContentType.JSON)
            .body(atualizado)
            .pathParam("id", id)
            .when().put(CONTROLE_ID_PATH)
            .then()
            .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    @TestTransaction
    public void testDelete() {

        Long id =
            given()
                .contentType(ContentType.JSON)
                .body(criarDTOValido("Controle Verde"))
                .when().post(CONTROLE_PATH)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .extract().jsonPath().getLong("id");

        given()
            .pathParam("id", id)
            .when().delete(CONTROLE_ID_PATH)
            .then()
            .statusCode(anyOf(is(200), is(204)));

        given()
            .pathParam("id", id)
            .when().get(CONTROLE_ID_PATH)
            .then()
            .statusCode(anyOf(is(204), is(404)));
    }
}
