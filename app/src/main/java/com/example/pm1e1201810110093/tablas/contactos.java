package com.example.pm1e1201810110093.tablas;

public class contactos {
    private Integer cp_ID;
    private String cp_Pais;
    private String cp_Nombre;
    private String cp_Telefono;
    private String cp_Nota;

    public contactos(){}

    public contactos(Integer cp_id, String cp_pais, String cp_nombre, String cp_telefono, String cp_nota) {
        cp_ID = cp_id;
        cp_Pais = cp_pais;
        cp_Nombre = cp_nombre;
        cp_Telefono = cp_telefono;
        cp_Nota = cp_nota;
    }

    public Integer getCp_ID() {
        return cp_ID;
    }

    public void setCp_ID(Integer cp_ID) {
        this.cp_ID = cp_ID;
    }

    public String getCp_Pais() {
        return cp_Pais;
    }

    public void setCp_Pais(String cp_Pais) {
        this.cp_Pais = cp_Pais;
    }

    public String getCp_Nombre() {
        return cp_Nombre;
    }

    public void setCp_Nombre(String cp_Nombre) {
        this.cp_Nombre = cp_Nombre;
    }

    public String getCp_Telefono() {
        return cp_Telefono;
    }

    public void setCp_Telefono(String cp_Telefono) {
        this.cp_Telefono = cp_Telefono;
    }

    public String getCp_Nota() {
        return cp_Nota;
    }

    public void setCp_Nota(String cp_Nota) {
        this.cp_Nota = cp_Nota;
    }
}
