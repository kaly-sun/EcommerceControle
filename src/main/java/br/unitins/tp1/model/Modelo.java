package br.unitins.tp1.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Modelo extends DefaultEntity {

    private String nome;
    private String versao;
    private Integer anoLancamento;
    private String descricao;
    private String codigoReferencia;
    private Boolean ativo;

    @OneToMany(mappedBy = "modelo")
    private List<Controle> controles;

    @ManyToOne
@JoinColumn(name = "id_marca")
private Marca marca;

@ManyToOne
@JoinColumn(name = "id_plataforma")
private Plataforma plataforma;


    // Getters e Setters
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVersao() {
        return versao;
    }
    public void setVersao(String versao) {
        this.versao = versao;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }
    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoReferencia() {
        return codigoReferencia;
    }
    public void setCodigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Controle> getControles() {
        return controles;
    }
    public void setControles(List<Controle> controles) {
        this.controles = controles;
    }
    
}
