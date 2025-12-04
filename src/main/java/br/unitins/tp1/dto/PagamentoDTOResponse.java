package br.unitins.tp1.dto;

import br.unitins.tp1.model.FormaPagamento;
import br.unitins.tp1.model.Pagamento;

public record PagamentoDTOResponse(
    Long id,
    FormaPagamento formaPagamento,
    Double valorTotal,
    String dataPagamento,
    Boolean confirmado
) {

    public static PagamentoDTOResponse valueOf(Pagamento pagamento) {
        return new PagamentoDTOResponse(
            pagamento.getId(),
            pagamento.getFormaPagamento(),
            pagamento.getValorTotal(),
            pagamento.getDataPagamento().toString(),
            pagamento.getConfirmado()
        );
    }
}
