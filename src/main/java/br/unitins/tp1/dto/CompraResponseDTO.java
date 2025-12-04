package br.unitins.tp1.dto;

import br.unitins.tp1.model.Compra;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CompraResponseDTO(
    Long id,
    LocalDateTime dataCompra,
    Double total,
    Long idUsuario,
    List<ItemCompraResponseDTO> itens,
    String status
) {
    public static CompraResponseDTO valueOf(Compra compra) {
        if (compra == null) return null;
        List<ItemCompraResponseDTO> itens = compra.getItens().stream()
                .map(ItemCompraResponseDTO::valueOf)
                .collect(Collectors.toList());
        return new CompraResponseDTO(
                compra.getId(),
                compra.getDataCompra(),
                compra.getTotal(),
                compra.getUsuario() != null ? compra.getUsuario().getId() : null,
                itens,
                compra.getStatus() != null ? compra.getStatus().name() : null
        );
    }
}
