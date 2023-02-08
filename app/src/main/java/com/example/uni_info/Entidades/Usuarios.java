package com.example.uni_info.Entidades;

import java.io.Serializable;

public class Usuarios implements Serializable {

    private String id;
    private String nombre;
    private String email;
    private String contraseña;

    public Usuarios() {

    }

    public Usuarios(String nombre, String email, String contraseña) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return email + "\n"+ contraseña ;
    }
}
