package br.unitins.tp1.model;

import java.time.LocalDateTime;

public class Pagamento {

    private Long id;
    private FormaPagamento formaPagamento;
    private Double valorTotal;
    private LocalDateTime dataPagamento;
    private Boolean confirmado;

    public Pagamento() {
        this.dataPagamento = LocalDateTime.now();
        this.confirmado = false;
    }

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    public Boolean getConfirmado() { return confirmado; }
    public void setConfirmado(Boolean confirmado) { this.confirmado = confirmado; }
}
