package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.model.ItemPedido;
import br.unitins.tp1.repository.ItemPedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Inject
    ItemPedidoRepository repository;

    @Override
    @Transactional
    public void salvar(ItemPedido item) {
        repository.persist(item);
    }

    @Override
    public List<ItemPedido> listarPorPedido(Long idPedido) {
        return repository.find("pedido.id", idPedido).list();
    }
}
