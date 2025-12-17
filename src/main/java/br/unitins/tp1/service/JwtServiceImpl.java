package br.unitins.tp1.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo ao gerar JWT");
        }

        String login = usuario.getLogin();
        Perfil perfil = usuario.getPerfil();

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login não pode ser vazio ao gerar JWT");
        }

        if (perfil == null) {
            throw new IllegalArgumentException("Perfil não pode ser nulo ao gerar JWT");
        }

        Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);

        Set<String> roles = new HashSet<>();
        roles.add(perfil.name()); 

        return Jwt.issuer("albuns-jwt")
                .subject(login)
                .upn(login)
                .groups(roles)
                .expiresAt(expiryDate)
                .claim("perfil", perfil.name())
                .claim("idUsuario", usuario.getId())  
                .sign();
    }
}

