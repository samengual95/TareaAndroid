package com.scheduler.genericscheduler.Modelos;

import java.io.Serializable;

public class TokenRequest implements Serializable{

    private String facebooktoken;
    private String nombre;
    private String apellido;
    private String correo;
    private String facebookid;

    public TokenRequest() {
    }

    public String getToken() {
        return facebooktoken;
    }

    public void setToken(String facebooktoken) {
        this.facebooktoken = facebooktoken;
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

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }
}
