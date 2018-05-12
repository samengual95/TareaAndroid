package com.scheduler.genericscheduler.Modelos;

/**
 * Created by Clara on 12/05/2018.
 */

public class Servicio {
    private int id;
    private String nombre;
    private int duracionEnMinutos;

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

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }
}
