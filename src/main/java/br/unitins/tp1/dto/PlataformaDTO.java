package br.unitins.tp1.dto;

public record PlataformaDTO(
    String nome,
    String fabricante,
    String geracao,
    Integer anoLancamento,
    String tipo
) {}
