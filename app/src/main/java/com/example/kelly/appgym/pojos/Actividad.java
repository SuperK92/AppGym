package com.example.kelly.appgym.pojos;

import android.graphics.Bitmap;

import com.example.kelly.appgym.constantes.G;

/**
 * Created by kelly on 04/11/2017.
 */

public class Actividad {
    private int ID;
    private int id_ejercicio;
    private int series;
    private int repeticiones;

    public Actividad(){
        this.ID = G.SIN_VALOR_INT;
        this.id_ejercicio = G.SIN_VALOR_INT;
        this.series = G.SIN_VALOR_INT;
        this.repeticiones = G.SIN_VALOR_INT;
    };

    public Actividad(int ID, int id_ejercicio, int series, int repeticiones) {
        this.ID = ID;
        this.id_ejercicio = id_ejercicio;
        this.series = series;
        this.repeticiones = repeticiones;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEjercicio() {
        return id_ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.id_ejercicio = ejercicio;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
}
