package br.unitins.tp1.service;

import br.unitins.tp1.model.Usuario;

public interface JwtService {

    /**
     * gera um JWT com base no usuário autenticado.
     *
     * @param usuario usuário autenticado
     * @return token JWT assinado
     */
    String generateJwt(Usuario usuario);
}
