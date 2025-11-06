package br.unitins.tp1.dto;

public record CategoriaDTO(
    String nome,
    String descricao
) {
    public CategoriaDTO(String nome) {
        this(nome, null);
    }
}
