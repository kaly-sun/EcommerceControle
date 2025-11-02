package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Plataforma;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlataformaRepository implements PanacheRepository<Plataforma> {

    public List<Plataforma> findByNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return List.of();
        }
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
