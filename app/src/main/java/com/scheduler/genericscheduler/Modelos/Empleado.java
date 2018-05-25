package com.scheduler.genericscheduler.Modelos;

import java.io.Serializable;
import java.util.ArrayList;



public class Empleado implements Serializable{
    private String id;
    private String token;
    private String nombre;
    private String apellido;
    private String correo;
    private String imagen;
    private ArrayList<HorarioEmpleado> horarios;
    private ArrayList<Servicio> servicios;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<HorarioEmpleado> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<HorarioEmpleado> horarios) {
        this.horarios = horarios;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }
}
