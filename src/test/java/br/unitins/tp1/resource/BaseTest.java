package br.unitins.tp1.resource;

import org.junit.jupiter.api.BeforeEach;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@QuarkusTest
public abstract class BaseTest {

    @Inject
    EntityManager em;

    protected String gerarTokenAdmin() {
        return Jwt.issuer("unitins")
                .subject("test-admin")
                .groups("ADMIN")
                .sign(); 
    }

    @BeforeEach
    @TestTransaction
    public void setUp() {
        resetDatabase();
        seedDatabase();
    }

    @Transactional
    protected void resetDatabase() {
        em.createQuery("DELETE FROM Controle").executeUpdate();
        em.createQuery("DELETE FROM Modelo").executeUpdate();
        em.createQuery("DELETE FROM Cor").executeUpdate();
        em.createQuery("DELETE FROM Categoria").executeUpdate();
    }

    @Transactional
    protected void seedDatabase() {
    }
}
