package com.example.uni_info.Entidades;

import java.io.Serializable;

public class Noticias implements Serializable {
    private String id;
    private String nombre;
    private String resumen;
    private String database;
    private String fecha;
    private String hora;

    public Noticias() {
    }

    public Noticias(String nombre, String resumen, String fecha, String hora, String database) {
        this.nombre = nombre;
        this.resumen = resumen;
        this.fecha = fecha;
        this.hora = hora;
        this.database = database;
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

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return nombre + "\n"+ resumen ;
    }
}
