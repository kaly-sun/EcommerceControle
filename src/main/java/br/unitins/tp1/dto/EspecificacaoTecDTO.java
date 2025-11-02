package br.unitins.tp1.dto;

public record EspecificacaoTecDTO(
    Double peso,
    String material,
    Integer autonomiaBateria,
    String conectividade,
    String dimensoes,
    String sensoresExtras
) {}
