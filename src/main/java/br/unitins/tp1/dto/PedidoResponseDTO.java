package br.unitins.tp1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(

    Long id,
    LocalDateTime dataCriacao,
    BigDecimal total,
    String status,
    String observacao,

    /*Long idUsuario,*/

    EnderecoEntregaResponseDTO endereco,

    List<ItemPedidoResponseDTO> itens,

    PagamentoResponseDTO pagamento
) {}
