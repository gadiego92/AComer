package com.informatica.ing_software.acomer.objects;

/**
 * Created by Diego on 09/01/2017.
 */

public class Restaurante {

    private int id;
    private String nombre;
    private String ciudad;
    private String telefono;
    private String tipo_cocina;
    private String valoracion;

    public Restaurante() {
        this.id = 0;
        this.nombre = "";
        this.ciudad = "";
        this.telefono = "";
        this.tipo_cocina = "";
        this.valoracion = "";
    }

    public Restaurante(int id, String nm, String cd, String tl, String cn, String vl) {
        setValues(id, nm, cd, tl, cn, vl);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo_cocina() {
        return tipo_cocina;
    }

    public void setTipo_cocina(String tipo_cocina) {
        this.tipo_cocina = tipo_cocina;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public void setValues(int id, String nm, String cd, String tl, String cn, String vl) {
        this.id = id;
        this.nombre = nm;
        this.ciudad = cd;
        this.telefono = tl;
        this.tipo_cocina = cn;
        this.valoracion = vl;
    }
}