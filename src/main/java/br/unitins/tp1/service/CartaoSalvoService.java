package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.NovoCartaoDTO;
import br.unitins.tp1.model.CartaoSalvo;

public interface CartaoSalvoService {

    List<CartaoSalvo> listarDoUsuario(Long idUsuario);

    CartaoSalvo buscarDoUsuario(Long idCartao, Long idUsuario);

    CartaoSalvo criarParaUsuario(NovoCartaoDTO dto, Long idUsuario);
}
