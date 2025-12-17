package br.unitins.tp1.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoDTO(

    @NotNull
    Long idControle,

    @NotNull
    @Min(1)
    Integer quantidade
) {
}