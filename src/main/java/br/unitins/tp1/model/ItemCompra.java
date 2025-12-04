package br.unitins.tp1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_compra")
public class ItemCompra extends DefaultEntity {

    @ManyToOne
    @JoinColumn(name = "id_compra")
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "id_controle")
    private Controle controle;

    private Integer quantidade;
    private Double precoUnitario;

    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) { this.compra = compra; }

    public Controle getControle() { return controle; }
    public void setControle(Controle controle) { this.controle = controle; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }

    public Double getSubtotal() {
        return (precoUnitario == null || quantidade == null) ? 0.0 : precoUnitario * quantidade;
    }
}
