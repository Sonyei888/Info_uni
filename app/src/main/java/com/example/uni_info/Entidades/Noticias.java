package com.example.uni_info.Entidades;

public class Noticias {
    int id;
    String nombre;
    String resumen;
    String informacion;
    String fecha;
    String hora;

    public Noticias() {
    }

    public Noticias(String nombre, String resumen, String informacion, String fecha, String hora) {
        this.nombre = nombre;
        this.resumen = resumen;
        this.informacion = informacion;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
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
}
