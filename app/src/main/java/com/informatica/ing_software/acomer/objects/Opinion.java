package com.informatica.ing_software.acomer.objects;

/**
 * Created by Diego on 20/05/2017.
 */

public class Opinion {

    private int restaurant_id;
    private String username;
    private String date;
    private String opinion;
    private String valoracion;

    public Opinion(int id, String us, String da, String op, String va) {
        setValues(id, us, da, op, va);
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public void setValues(int id, String us, String da, String op, String va) {
        this.restaurant_id = id;
        this.username = us;
        this.date = da;
        this.opinion = op;
        this.valoracion = va;
    }
}