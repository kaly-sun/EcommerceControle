package br.unitins.tp1.dto;

import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String login,
        Perfil perfil
) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;

        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getLogin(),
            usuario.getPerfil()
        );
    }
}
