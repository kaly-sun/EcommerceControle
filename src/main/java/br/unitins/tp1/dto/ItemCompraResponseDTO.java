package br.unitins.tp1.dto;

import br.unitins.tp1.model.ItemCompra;

public record ItemCompraResponseDTO(
        Long id,
        Long idControle,
        Integer quantidade,
        Double precoUnitario,
        Double subtotal
) {
    public static ItemCompraResponseDTO valueOf(ItemCompra item) {
        if (item == null) return null;
        return new ItemCompraResponseDTO(
                item.getId(),
                item.getControle() != null ? item.getControle().getId() : null,
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal()
        );
    }
}
