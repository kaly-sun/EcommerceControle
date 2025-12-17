package br.unitins.tp1.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.scheduler.Scheduled;
import jakarta.transaction.Transactional;
import br.unitins.tp1.model.Pagamento;
import br.unitins.tp1.repository.PagamentoRepository;


@ApplicationScoped
public class PagamentoScheduler {

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    PaymentGatewayFake gateway;

    @Transactional
    @Scheduled(every = "10s")
    void processarPagamentosPendentes() {

        List<Pagamento> pendentes = pagamentoRepository.findByStatus("PENDENTE");

        for (Pagamento p : pendentes) {

            boolean aprovado = gateway.processarPagamento(
                p.getMetodoPagamento(),
                p.getId()
            );

            if (aprovado) {
                p.setStatus("APROVADO");
                p.getPedido().setStatus("PAGO");
            } else {
                p.setStatus("REJEITADO");
                p.getPedido().setStatus("CANCELADO");
            }
        }
    }
}
