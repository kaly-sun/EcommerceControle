package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PedidoDTO;
import br.unitins.tp1.dto.PedidoResponseDTO;

public interface PedidoService {


    PedidoResponseDTO findById(Long idPedido);

    List<PedidoResponseDTO> findByUsuario(Long idUsuario);

    void cancelar(Long idPedido);

    PedidoResponseDTO createParaUsuario(PedidoDTO dto, Long idUsuarioToken);

PedidoResponseDTO findByIdSeguro(Long idPedido, Long idUsuarioToken, boolean isAdmin);

void cancelarSeguro(Long idPedido, Long idUsuarioToken);

}
