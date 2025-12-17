package br.unitins.tp1.dto;

public record ModeloDTO(
    String nome,
    String versao,
    Integer anoLancamento,
    String descricao,
    String codigoReferencia,
    Boolean ativo,
    Long idMarca,
    Long idPlataforma
){}
