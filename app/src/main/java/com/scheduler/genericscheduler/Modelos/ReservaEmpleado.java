package com.scheduler.genericscheduler.Modelos;

public class ReservaEmpleado {



    private String idReserva;
    private String cliente;
    private String empleado;
    private String clienteid;
    private String empleadoid;

    public ReservaEmpleado(){}

    public ReservaEmpleado(String id, String c, String e, String cid,String empid){
        this.idReserva=id;
        this.cliente=c;
        this.empleado=e;
        this.clienteid=cid;
        this.empleadoid=empid;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getClienteid() {
        return clienteid;
    }

    public void setClienteid(String clienteid) {
        this.clienteid = clienteid;
    }

    public String getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(String empleadoid) {
        this.empleadoid = empleadoid;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getId() {
        return idReserva;
    }

    public void setId(String id) {
        this.idReserva = id;
    }
}
