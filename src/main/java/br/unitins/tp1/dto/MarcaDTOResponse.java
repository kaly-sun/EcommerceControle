package br.unitins.tp1.dto;

import br.unitins.tp1.model.Marca;

public record MarcaDTOResponse(
    Long id,
    String nome,
    String paisOrigem,
    Integer anoFundacao,
    String siteOficial,
    String logo
) {
    public static MarcaDTOResponse valueOf(Marca marca) {
        return new MarcaDTOResponse(
            marca.getId(),
            marca.getNome(),
            marca.getPaisOrigem(),
            marca.getAnoFundacao(),
            marca.getSiteOficial(),
            marca.getLogo()
        );
    }
}
