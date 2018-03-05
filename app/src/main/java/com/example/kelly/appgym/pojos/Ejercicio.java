package com.example.kelly.appgym.pojos;

import android.graphics.Bitmap;

import com.example.kelly.appgym.constantes.G;

/**
 * Created by kelly on 04/11/2017.
 */

public class Ejercicio {
    private int ID;
    private String nombre;
    //private int repeticiones;
    private int id_musculo;
    private Bitmap imagen;

    public Ejercicio(){
        this.ID = G.SIN_VALOR_INT;
        this.nombre = G.SIN_VALOR_STRING;
        this.id_musculo = G.SIN_VALOR_INT;
        this.setImagen(null);
    };

    public Ejercicio(int ID, String nombre, int musculo, Bitmap imagen) {
        this.ID = ID;
        this.nombre = nombre;
        this.id_musculo = musculo;
        this.imagen = imagen;
    }

    public int getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getId_musculo() {
        return id_musculo;
    }

    public void setId_musculo(int id_musculo) {
        this.id_musculo = id_musculo;
    }
}
