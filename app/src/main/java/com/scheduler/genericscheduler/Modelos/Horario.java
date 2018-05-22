package com.scheduler.genericscheduler.Modelos;

import java.io.Serializable;

/**
 * Created by Clara on 12/05/2018.
 */

public class Horario implements Serializable {
    private String hora;
    private Reserva horaReservada;

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Reserva getHoraReservada() {
        return horaReservada;
    }

    public void setHoraReservada(Reserva horaReservada) {
        this.horaReservada = horaReservada;
    }
}
