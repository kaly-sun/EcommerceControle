package br.unitins.tp1.dto;

import br.unitins.tp1.model.Modelo;

public record ModeloDTOResponse(
    Long id,
    String nome,
    String versao,
    Integer anoLancamento,
    String descricao,
    String codigoReferencia,
    Boolean ativo
) {
    public static ModeloDTOResponse valueOf(Modelo modelo) {
        return new ModeloDTOResponse(
            modelo.getId(),
            modelo.getNome(),
            modelo.getVersao(),
            modelo.getAnoLancamento(),
            modelo.getDescricao(),
            modelo.getCodigoReferencia(),
            modelo.getAtivo()
        );
    }
}
