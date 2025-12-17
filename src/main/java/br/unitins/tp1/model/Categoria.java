package br.unitins.tp1.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class Categoria extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @JsonIgnore
    @ManyToMany(mappedBy = "categorias")
    private List<Controle> controles;

    public Categoria() {}

    public Categoria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Controle> getControles() {
        return controles;
    }

    public void setControles(List<Controle> controles) {
        this.controles = controles;
    }
}
