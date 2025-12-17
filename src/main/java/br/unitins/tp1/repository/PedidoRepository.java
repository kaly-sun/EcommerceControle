package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findByUsuario(Long idUsuario) {
        return find("usuario.id", idUsuario).list();
    }

    public List<Pedido> findByStatus(String status) {
        return find("status", status).list();
    }

    public List<Pedido> findByUsuarioAndStatus(Long idUsuario, String status) {
        return find("usuario.id = ?1 and status = ?2", idUsuario, status).list();
    }
}
