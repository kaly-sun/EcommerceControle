package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class EspecificacaoTecnica extends DefaultEntity {

    private Double peso; // em gramas

    @Column(length = 200)
    private String material; // Ex: plástico, metal, híbrido

    private Integer autonomiaBateria; // em horas

    @Column(length = 100)
    private String conectividade; // Bluetooth, USB, etc

    @Column(length = 100)
    private String dimensoes; // Ex: 15x10x5 cm

    @Column(length = 500)
    private String sensoresExtras; // giroscópio, vibração, acelerômetro etc

    // Getters e Setters
    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getMaterial() {
        return material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getAutonomiaBateria() {
        return autonomiaBateria;
    }
    public void setAutonomiaBateria(Integer autonomiaBateria) {
        this.autonomiaBateria = autonomiaBateria;
    }

    public String getConectividade() {
        return conectividade;
    }
    public void setConectividade(String conectividade) {
        this.conectividade = conectividade;
    }

    public String getDimensoes() {
        return dimensoes;
    }
    public void setDimensoes(String dimensoes) {
        this.dimensoes = dimensoes;
    }

    public String getSensoresExtras() {
        return sensoresExtras;
    }
    public void setSensoresExtras(String sensoresExtras) {
        this.sensoresExtras = sensoresExtras;
    }
}
