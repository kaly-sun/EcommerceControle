package br.unitins.tp1.service;


import br.unitins.tp1.dto.PagamentoResponseDTO;

public interface PagamentoService {

    PagamentoResponseDTO findByIdSeguro(Long idPagamento, Long idUsuarioToken, boolean isAdmin);

    PagamentoResponseDTO gerarPixParaPedidoSeguro(Long idPedido, Long idUsuarioToken, boolean isAdmin);

    void solicitarConfirmacao(Long idPagamento, Long idUsuarioToken);

}
