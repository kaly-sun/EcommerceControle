package br.unitins.tp1.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PedidoDTO(



   Long idEnderecoEntrega,
   EnderecoEntregaDTO endereco,

    String observacao,

    @NotEmpty
    List<ItemPedidoDTO> itens,

    @NotNull
    PagamentoDTO pagamento
) {}
