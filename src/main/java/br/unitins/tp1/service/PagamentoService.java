package br.unitins.tp1.service;

import br.unitins.tp1.dto.PagamentoDTO;
import br.unitins.tp1.dto.PagamentoDTOResponse;
import br.unitins.tp1.model.Pagamento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PagamentoService {

    @Transactional
    public PagamentoDTOResponse pagar(PagamentoDTO dto) {

        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(dto.formaPagamento());
        pagamento.setValorTotal(dto.valorTotal());
        pagamento.setConfirmado(true); // simulação de pagamento processado


        return PagamentoDTOResponse.valueOf(pagamento);
    }
}
