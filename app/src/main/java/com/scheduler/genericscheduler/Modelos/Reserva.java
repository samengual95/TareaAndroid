package com.scheduler.genericscheduler.Modelos;

public class Reserva {

    public Reserva(){}

    public Reserva(String id){
        this.id=id;
    }


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
