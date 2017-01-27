package com.informatica.ing_software.acomer;

/**
 * Created by Diego on 27/01/2017.
 */

public class Usuario {

    private String nombre, apellido, telefono, email;

    public Usuario(String nm, String ap, String tl, String em) {
        this.nombre = nm;
        this.apellido = ap;
        this.telefono = tl;
        this.email = em;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
