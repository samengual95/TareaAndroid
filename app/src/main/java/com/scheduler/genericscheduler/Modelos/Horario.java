package com.scheduler.genericscheduler.Modelos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Clara on 12/05/2018.
 */

public class Horario {
    private String hora;
    private ReservaEmpleado horaReservada;

    public Horario(){}

    public Horario(String h, ReservaEmpleado hR){
        this.hora=h;
        this.horaReservada=hR;
    }


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public ReservaEmpleado getHoraReservada() {
        return horaReservada;
    }

    public void setHoraReservada(ReservaEmpleado horaReservada) {
        this.horaReservada = horaReservada;
    }
}
