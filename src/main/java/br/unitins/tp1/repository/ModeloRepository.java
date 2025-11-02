package br.unitins.tp1.repository;

import br.unitins.tp1.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {

    public List<Modelo> findByNome(String nome) {
        return find("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%").list();
    }
}
