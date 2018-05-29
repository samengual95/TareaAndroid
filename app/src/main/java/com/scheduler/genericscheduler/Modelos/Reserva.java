package com.scheduler.genericscheduler.Modelos;

public class Reserva {
    private String id;
    private String entrada;
    private String nombreServicio;
    private String idServicio;
    private String duracionEnMinutosServicio;
    private String nombreEmpleado;
    private String idEmpleado;
    private String nombreCliente;
    private String idCliente;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getDuracionEnMinutosServicio() {
        return duracionEnMinutosServicio;
    }

    public void setDuracionEnMinutosServicio(String duracionEnMinutosServicio) {
        this.duracionEnMinutosServicio = duracionEnMinutosServicio;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
