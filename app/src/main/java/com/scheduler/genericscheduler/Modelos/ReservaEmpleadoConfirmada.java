package com.scheduler.genericscheduler.Modelos;

public class ReservaEmpleadoConfirmada {

    private String id;
    private Empleado empleado;
    private Servicio servicio;
    private String entrada;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }
}
