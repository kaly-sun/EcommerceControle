package br.unitins.tp1.dto;

public record UsuarioDTO (
    String login,
    String senha,
    int idPerfil
) {

}
