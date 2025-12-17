package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Perfil {

    ADM(1, "Adm"),
    USER(2, "User");

    private final int ID;
    private final String NAME;

    Perfil(int id, String name){
        this.ID = id;
        this.NAME = name;
    }

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public static Perfil valueOf(Integer id){

        if (id == null){
            return null;
        }

        for (Perfil r : Perfil.values()){

            if (r.getID() == id){
                return r;
            }

        }

        return null;

    }

}

