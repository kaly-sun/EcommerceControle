package br.unitins.tp1.dto;

public record CartaoSalvoResponseDTO(
    Long id,
    String nomeTitular,
    String validade,
    String ultimos4
) {}
