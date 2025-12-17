package br.unitins.tp1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Controle extends DefaultEntity {

    private String nome;
    private Double preco;

    private String cor;
    private Integer estoque;

    @Column(length = 1000)
    private String descricao;

    private LocalDate dataLancamento;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_plataforma")
    private Plataforma plataforma;

    @ManyToMany
    @JoinTable(
        name = "controle_categoria",
        joinColumns = @JoinColumn(name = "id_controle"),
        inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private List<Categoria> categorias = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_especificacao_tecnica")
    private EspecificacaoTecnica especificacaoTecnica;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        this.estoque = estoque;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public EspecificacaoTecnica getEspecificacaoTecnica() {
        return especificacaoTecnica;
    }

    public void setEspecificacaoTecnica(EspecificacaoTecnica especificacaoTecnica) {
        this.especificacaoTecnica = especificacaoTecnica;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void debitarEstoque(Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade a ser debitada deve ser maior que zero.");
        }
        if (estoque - quantidade < 0) {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }
        this.estoque -= quantidade;
    }
}
