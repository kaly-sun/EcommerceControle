package br.unitins.tp1.service;

import java.time.Duration;
import java.util.Set;

import br.unitins.tp1.dto.LoginDTO;
import br.unitins.tp1.dto.TokenResponseDTO;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import at.favre.lib.crypto.bcrypt.BCrypt;


@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UsuarioRepository repository;

    @Override
    @Transactional
    public TokenResponseDTO login(LoginDTO dto) {

        Usuario user = repository.findByLogin(dto.login());

        if (user == null) {
            throw new RuntimeException("Login ou senha inválidos");
        }

        var result = BCrypt.verifyer().verify(dto.senha().toCharArray(), user.getSenha());
        if (!result.verified) {
            throw new RuntimeException("Login ou senha inválidos");
        }

        String token = Jwt.issuer("unitins-jwt")
                .subject(user.getLogin())
                .groups(Set.of(user.getPerfil().name())) // Perfil vindo do Enum
                .expiresIn(Duration.ofHours(2))
                .sign();

        return new TokenResponseDTO(token, "Bearer");
    }
}
