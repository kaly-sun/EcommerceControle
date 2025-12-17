package br.unitins.tp1.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "modelo")
public class Modelo extends DefaultEntity {

    @Column(length = 255, nullable = false)
    private String nome;

    @Column(length = 255, nullable = false)
    private String versao;

    @Column(name = "anolancamento", nullable = false)
    private Integer anoLancamento;

    @Column(length = 255, nullable = false)
    private String descricao;

    @Column(name = "codigoreferencia", length = 255, nullable = false)
    private String codigoReferencia;

    @Column(nullable = false)
    private Boolean ativo;

    @JsonIgnore
    @OneToMany(mappedBy = "modelo")
    private List<Controle> controles;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_plataforma", nullable = false)
    private Plataforma plataforma;

    // getters e setters
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
}
