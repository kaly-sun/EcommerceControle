package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.NovoCartaoDTO;
import br.unitins.tp1.exception.ValidationException;
import br.unitins.tp1.model.CartaoSalvo;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.CartaoSalvoRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CartaoSalvoServiceImpl implements CartaoSalvoService {

    @Inject
    CartaoSalvoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public List<CartaoSalvo> listarDoUsuario(Long idUsuario) {
        return repository.listarPorUsuario(idUsuario);
    }

    @Override
    public CartaoSalvo buscarDoUsuario(Long idCartao, Long idUsuario) {
        return repository.buscarDoUsuario(idCartao, idUsuario);
    }

    @Override
    @Transactional
    public CartaoSalvo criarParaUsuario(NovoCartaoDTO dto, Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null)
            throw ValidationException.of("usuario", "Usuário não encontrado.");

        String numero = dto.numero().replaceAll("[^0-9]", "");
        if (numero.length() < 12)
            throw ValidationException.of("cartao", "Número de cartão inválido.");

        CartaoSalvo c = new CartaoSalvo();
        c.setUsuario(usuario);
        c.setNomeTitular(dto.nomeTitular());
        c.setValidade(dto.validade());
        c.setBandeira(dto.bandeira());

        String ult4 = numero.substring(numero.length() - 4);
        c.setUltimos4(ult4);

        // mascara: **** **** **** 1234
        c.setNumeroMascarado("**** **** **** " + ult4);

        repository.persist(c);
        return c;
    }
}
