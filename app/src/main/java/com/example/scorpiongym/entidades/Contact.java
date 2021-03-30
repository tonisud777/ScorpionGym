package com.example.scorpiongym.entidades;

import java.io.Serializable;

public class Contact implements Serializable {

    String Title, name, descripcion, imagen;

    public Contact(String Title,String name, String descripcion, String imagen) {
        this.Title =Title;
        this.name = name;
        this.descripcion = descripcion;
        this.imagen = imagen;

    }

    public Contact() {

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
