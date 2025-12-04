package br.unitins.tp1.service;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.repository.UsuarioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioDTO dto) {

        Usuario user = new Usuario();

        // agora usa os métodos corretos do DTO
        user.setLogin(dto.login());

        // criptografa a senha
        user.setSenha(BCrypt.hashpw(dto.senha(), BCrypt.gensalt()));

        // define o perfil usando o idPerfil do DTO
        user.setPerfil(Perfil.values()[dto.idPerfil()]); 
        // ou busque de um repository, dependendo do seu projeto

        repository.persist(user);

        return new UsuarioResponseDTO(
            user.getId(),
            user.getLogin(),
            user.getPerfil()
        );
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return repository.listAll()
                .stream()
                .map(u -> new UsuarioResponseDTO(
                        u.getId(),
                        u.getLogin(),
                        u.getPerfil()
                ))
                .toList();
    }
}
