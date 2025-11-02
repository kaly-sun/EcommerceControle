package br.unitins.tp1.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "marca")
public class Marca extends DefaultEntity {

    @Column(nullable = false, unique = true)
    private String nome;

    private String paisOrigem;

    private Integer anoFundacao;

    private String siteOficial;

    // URL ou caminho da imagem
    private String logo;

    // Relacionamento opcional (lado inverso) - apenas se quiser listar controles a partir da marca
    @OneToMany(mappedBy = "marca")
    @JsonIgnore // evita recursão infinita no JSON
    private List<Controle> controles;

    public Marca() {}

    public Marca(String nome, String paisOrigem, Integer anoFundacao, String siteOficial, String logo) {
        this.nome = nome;
        this.paisOrigem = paisOrigem;
        this.anoFundacao = anoFundacao;
        this.siteOficial = siteOficial;
        this.logo = logo;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }
    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public Integer getAnoFundacao() {
        return anoFundacao;
    }
    public void setAnoFundacao(Integer anoFundacao) {
        this.anoFundacao = anoFundacao;
    }

    public String getSiteOficial() {
        return siteOficial;
    }
    public void setSiteOficial(String siteOficial) {
        this.siteOficial = siteOficial;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Controle> getControles() {
        return controles;
    }
    public void setControles(List<Controle> controles) {
        this.controles = controles;
    }
}
