package br.unitins.tp1.model;

public enum Perfil {
    ADMIN(1, "Administrador"),
    USER(2, "Usuário");

    private final Integer id;
    private final String label;

    Perfil(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
