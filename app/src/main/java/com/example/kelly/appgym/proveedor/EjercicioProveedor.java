package com.example.kelly.appgym.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


public class EjercicioProveedor {
    static public void insert(ContentResolver resolvedor, com.example.kelly.appgym.pojos.Ejercicio ejercicio){
        Uri uri = Contrato.Ejercicio.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Ejercicio.NOMBRE, ejercicio.getNombre());
        values.put(Contrato.Ejercicio.REPETICIONES, ejercicio.getRepeticiones());

        resolvedor.insert(uri, values);
    }

    static public void delete(ContentResolver resolver, int ejercicioId){
        Uri uri = Uri.parse(Contrato.Ejercicio.CONTENT_URI + "/" + ejercicioId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, com.example.kelly.appgym.pojos.Ejercicio ejercicio){
        Uri uri = Uri.parse(Contrato.Ejercicio.CONTENT_URI + "/" + ejercicio.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Ejercicio.NOMBRE, ejercicio.getNombre());
        values.put(Contrato.Ejercicio.REPETICIONES, ejercicio.getRepeticiones());

        resolver.update(uri, values, null, null);
    }

    static public com.example.kelly.appgym.pojos.Ejercicio read(ContentResolver resolver, int ejercicioId) {
        Uri uri = Uri.parse(Contrato.Ejercicio.CONTENT_URI + "/" + ejercicioId);

        String[] projection = {Contrato.Ejercicio._ID,
                Contrato.Ejercicio.NOMBRE,
                Contrato.Ejercicio.REPETICIONES};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            com.example.kelly.appgym.pojos.Ejercicio ejercicio = new com.example.kelly.appgym.pojos.Ejercicio();
            ejercicio.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Ejercicio._ID)));
            ejercicio.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Ejercicio.NOMBRE)));
            ejercicio.setRepeticiones(cursor.getInt(cursor.getColumnIndex(Contrato.Ejercicio.REPETICIONES)));
            return ejercicio;
        }

        return null;

    }
}
