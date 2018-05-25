package com.scheduler.genericscheduler.Modelos;

import java.io.Serializable;

public class RespuestaSesion implements Serializable {

    private String token;
    private String tipo;

    public RespuestaSesion() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
