package br.unitins.tp1.repository;

import java.util.List;
    
import br.unitins.tp1.model.Controle;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ControleRepository implements PanacheRepository<Controle> {

    public List<Controle> findByModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) {
            return List.of();
        }
        return find("UPPER(modelo.nome) LIKE ?1", "%" + modelo.toUpperCase() + "%").list();
    }

    public List<Controle> findByMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            return List.of();
        }
        return find("UPPER(marca.nome) LIKE ?1", "%" + marca.toUpperCase() + "%").list();
    }

    public List<Controle> findByPlataforma(String plataforma) {
        if (plataforma == null || plataforma.isBlank()) {
            return List.of();
        }
        return find("UPPER(plataforma.nome) LIKE ?1", "%" + plataforma.toUpperCase() + "%").list();
    }

    public List<Controle> findByNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return List.of();
        }
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
