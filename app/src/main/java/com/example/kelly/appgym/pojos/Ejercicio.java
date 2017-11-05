package com.example.kelly.appgym.pojos;

import com.example.kelly.appgym.constantes.G;

/**
 * Created by kelly on 04/11/2017.
 */

public class Ejercicio {
    private int ID;
    private String nombre;
    private int repeticiones;

    public Ejercicio(){
        this.ID = G.SIN_VALOR_INT;
        this.nombre = G.SIN_VALOR_STRING;
        this.repeticiones = G.SIN_VALOR_INT;
    };

    public Ejercicio(int ID, String nombre, int repeticiones) {
        this.ID = ID;
        this.nombre = nombre;
        this.repeticiones = repeticiones;
    }

    public int getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
}
