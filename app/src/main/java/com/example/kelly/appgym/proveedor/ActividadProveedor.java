package com.example.kelly.appgym.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.kelly.appgym.constantes.Utilidades;
import com.example.kelly.appgym.pojos.Actividad;
import com.example.kelly.appgym.pojos.Ejercicio;
import com.example.kelly.appgym.pojos.Musculo;

import java.io.IOException;


public class ActividadProveedor {
    static public void insert(ContentResolver resolvedor, Actividad actividad, Context contexto){
        Uri uri = Contrato.Actividad.CONTENT_URI;

        ContentValues values = new ContentValues();

        values.put(Contrato.Actividad.ID_EJERCICIO, actividad.getEjercicio().getID());
        values.put(Contrato.Actividad.SERIES, actividad.getSeries());
        values.put(Contrato.Actividad.REPETICIONES, actividad.getRepeticiones());

//        resolvedor.insert(uri, values);
        Uri returnUri = resolvedor.insert(uri, values);

    }

    static public void delete(ContentResolver resolver, int actividadId){
        Uri uri = Uri.parse(Contrato.Actividad.CONTENT_URI + "/" + actividadId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Actividad actividad, Context contexto){
        Uri uri = Uri.parse(Contrato.Actividad.CONTENT_URI + "/" + actividad.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Actividad.ID_EJERCICIO, actividad.getEjercicio().getID());
        values.put(Contrato.Actividad.SERIES, actividad.getSeries());
        values.put(Contrato.Actividad.REPETICIONES, actividad.getRepeticiones());

        resolver.update(uri, values, null, null);

    }

    static public Actividad read(ContentResolver resolver, int actividadId) {
        Uri uri = Uri.parse(Contrato.Actividad.CONTENT_URI + "/" + actividadId);

        String[] projection = {Contrato.Actividad._ID,
                Contrato.Actividad.SERIES,
                Contrato.Actividad.REPETICIONES};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Actividad actividad = new Actividad();
            actividad.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Actividad._ID)));
            int ejercicioId = cursor.getInt(cursor.getColumnIndex(Contrato.Actividad.ID_EJERCICIO));
            Ejercicio ejercicio = EjercicioProveedor.read(resolver, ejercicioId);
            actividad.setEjercicio(ejercicio);
            actividad.setSeries(cursor.getInt(cursor.getColumnIndex(Contrato.Actividad.SERIES)));
            actividad.setRepeticiones(cursor.getInt(cursor.getColumnIndex(Contrato.Actividad.REPETICIONES)));

            return actividad;
        }

        return null;

    }

    /*static public Ejercicio readAllEjercicios(ContentResolver resolver) {
        Uri uri = Uri.parse(Contrato.Ejercicio.CONTENT_URI);

        String[] projection = {Contrato.Ejercicio._ID,
                Contrato.Ejercicio.NOMBRE,
                Contrato.Ejercicio.ID_MUSCULO};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Ejercicio ejercicio = new com.example.kelly.appgym.pojos.Ejercicio();
            ejercicio.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Ejercicio._ID)));
            ejercicio.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Ejercicio.NOMBRE)));

            int musculoId = cursor.getInt(cursor.getColumnIndex(Contrato.Ejercicio.ID_MUSCULO));
            Musculo musculo = MusculoProveedor.read(resolver, musculoId);
            ejercicio.setMusculo(musculo);

            return ejercicio;
        }

        return null;

    }*/
}
