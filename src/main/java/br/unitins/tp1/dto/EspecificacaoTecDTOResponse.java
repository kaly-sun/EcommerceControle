package br.unitins.tp1.dto;

import br.unitins.tp1.model.EspecificacaoTecnica;

public record EspecificacaoTecDTOResponse(
    Long id,
    Double peso,
    String material,
    Integer autonomiaBateria,
    String conectividade,
    String dimensoes,
    String sensoresExtras
) {
    public static EspecificacaoTecDTOResponse valueOf(EspecificacaoTecnica e) {
        return new EspecificacaoTecDTOResponse(
            e.getId(),
            e.getPeso(),
            e.getMaterial(),
            e.getAutonomiaBateria(),
            e.getConectividade(),
            e.getDimensoes(),
            e.getSensoresExtras()
        );
    }
}
