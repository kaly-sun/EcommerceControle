package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;

public record NovoCartaoDTO(
    @NotBlank String nomeTitular,
    @NotBlank String numero,
    @NotBlank String validade,
    @NotBlank String bandeira
) {}
