package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.model.ItemPedido;

public interface ItemPedidoService {

    void salvar(ItemPedido item);

    List<ItemPedido> listarPorPedido(Long idPedido);
}
