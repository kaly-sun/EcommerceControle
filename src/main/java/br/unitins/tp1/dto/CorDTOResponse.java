package br.unitins.tp1.dto;

import br.unitins.tp1.model.Cor;

public record CorDTOResponse(Long id, String nome, String descricao) {

    public static CorDTOResponse valueOf(Cor cor) {
        return new CorDTOResponse(cor.getId(), cor.getNome(), cor.getDescricao());
    }
}
