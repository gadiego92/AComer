package com.informatica.ing_software.acomer;

/**
 * Created by Diego on 09/01/2017.
 */

public class Restaurante {

    private String nombre;
    private String ciudad;
    private String tipo_cocina;
    private String valoracion;

    public Restaurante(String nm, String cd, String cn, String vl) {
        this.nombre = nm;
        this.ciudad = cd;
        this.tipo_cocina = cn;
        this.valoracion = vl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getTipo_cocina() {
        return tipo_cocina;
    }

    public String getValoracion() {
        return valoracion;
    }
}