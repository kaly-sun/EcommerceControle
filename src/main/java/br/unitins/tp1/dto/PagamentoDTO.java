package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;

public record PagamentoDTO(

     @NotBlank
    String metodoPagamento,

    Long idCartaoSalvo,     
    String numeroCartao,
    String nomeImpresso,
    String validade
) {}
