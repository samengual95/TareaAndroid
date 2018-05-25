package com.scheduler.genericscheduler.Modelos;


import java.io.Serializable;

public class Servicio implements Serializable {
    private String id;
    private String nombre;
    private String duracionEnMinutos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(String duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }
}
