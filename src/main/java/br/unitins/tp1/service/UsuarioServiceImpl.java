package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioResponseDTO;
import br.unitins.tp1.exception.ValidationException;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    HashService hashService;


    @Inject
    UsuarioRepository repository;

    @Override
    public List<Usuario> findAll() {
        return repository
                    .listAll();
                    
    }

    @Override
    public Usuario findByLogin(String login) {
        return repository
                    .findByLogin(login);
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return repository
                    .findByLoginSenha(login, senha);
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = repository.findById(id);
        
        if (usuario == null)
            return null;

        return usuario;
    }

    @Override
@Transactional
public UsuarioResponseDTO create(UsuarioDTO dto) {

    Usuario existente = repository.findByLogin(dto.login());
    if (existente != null)
        throw ValidationException.of("login", "Login já cadastrado.");

Usuario u = new Usuario();
u.setLogin(dto.login());
u.setSenha(hashService.getHashSenha(dto.senha()));


    u.setPerfil(Perfil.USER);

    u.setEmail(dto.email());
    u.setTelefone(dto.telefone());
    u.setNome(dto.nome());

    repository.persist(u);

    return UsuarioResponseDTO.valueOf(u);
}



}