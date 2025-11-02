package br.unitins.tp1.dto;

import br.unitins.tp1.model.Plataforma;

public record PlataformaDTOResponse(
    Long id,
    String nome,
    String fabricante,
    String geracao,
    Integer anoLancamento,
    String tipo
) {
    public static PlataformaDTOResponse valueOf(Plataforma plataforma) {
        return new PlataformaDTOResponse(
            plataforma.getId(),
            plataforma.getNome(),
            plataforma.getFabricante(),
            plataforma.getGeracao(),
            plataforma.getAnoLancamento(),
            plataforma.getTipo()
        );
    }
}
