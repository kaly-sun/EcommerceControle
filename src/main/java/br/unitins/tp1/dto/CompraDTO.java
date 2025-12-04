package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.FormaPagamento;

public record CompraDTO(
    Long idUsuario,
    List<ItemCompraDTO> itens,
    FormaPagamento formaPagamento,
    EnderecoEntregaDTO enderecoEntrega
) {}
