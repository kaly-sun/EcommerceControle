package br.unitins.tp1.dto;

import br.unitins.tp1.model.Marca;

public class MarcaDTOResponse {

    private Long id;
    private String nome;
    private String paisOrigem;
    private Integer anoFundacao;
    private String siteOficial;
    private String logo;

    public MarcaDTOResponse() {}

    public MarcaDTOResponse(Marca marca) {
        this.id = marca.getId();
        this.nome = marca.getNome();
        this.paisOrigem = marca.getPaisOrigem();
        this.anoFundacao = marca.getAnoFundacao();
        this.siteOficial = marca.getSiteOficial();
        this.logo = marca.getLogo();
    }

    // ✅ Método auxiliar estático
    public static MarcaDTOResponse valueOf(Marca marca) {
        return new MarcaDTOResponse(marca);
    }

    // ✅ Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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
}
