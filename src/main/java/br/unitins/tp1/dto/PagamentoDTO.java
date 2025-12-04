package br.unitins.tp1.dto;

import br.unitins.tp1.model.FormaPagamento;

public record PagamentoDTO(
    FormaPagamento formaPagamento,
    Double valorTotal
) {}
