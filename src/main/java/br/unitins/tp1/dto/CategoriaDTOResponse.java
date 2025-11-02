package br.unitins.tp1.dto;

import br.unitins.tp1.model.Categoria;

public record CategoriaDTOResponse(
    Long id,
    String nome,
    String descricao
) {
    public static CategoriaDTOResponse valueOf(Categoria categoria) {
        return new CategoriaDTOResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }
}
