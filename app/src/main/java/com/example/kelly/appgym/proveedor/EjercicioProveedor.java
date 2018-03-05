package com.example.kelly.appgym.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.kelly.appgym.constantes.Utilidades;

import java.io.IOException;


public class EjercicioProveedor {
    static public void insert(ContentResolver resolvedor, com.example.kelly.appgym.pojos.Ejercicio ejercicio, Context contexto){
        Uri uri = Contrato.Ejercicio.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Ejercicio.NOMBRE, ejercicio.getNombre());
        //values.put(Contrato.Ejercicio.REPETICIONES, ejercicio.getRepeticiones());
        values.put(Contrato.Ejercicio.ID_MUSCULO, ejercicio.getId_musculo());

//        resolvedor.insert(uri, values);
        Uri returnUri = resolvedor.insert(uri, values);

        if(ejercicio.getImagen()!=null){
            try {
                Utilidades.storeImage(ejercicio.getImagen(), contexto, "img_" + returnUri.getLastPathSegment() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Hubo un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    static public void delete(ContentResolver resolver, int ejercicioId){
        Uri uri = Uri.parse(Contrato.Ejercicio.CONTENT_URI + "/" + ejercicioId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, com.example.kelly.appgym.pojos.Ejercicio ejercicio, Context contexto){
        Uri uri = Uri.parse(Contrato.Ejercicio.CONTENT_URI + "/" + ejercicio.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Ejercicio.NOMBRE, ejercicio.getNombre());
        values.put(Contrato.Ejercicio.ID_MUSCULO, ejercicio.getId_musculo());

        resolver.update(uri, values, null, null);

        if(ejercicio.getImagen()!=null){
            try {
                Utilidades.storeImage(ejercicio.getImagen(), contexto, "img_" + ejercicio.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Hubo un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    static public com.example.kelly.appgym.pojos.Ejercicio read(ContentResolver resolver, int ejercicioId) {
        Uri uri = Uri.parse(Contrato.Ejercicio.CONTENT_URI + "/" + ejercicioId);

        String[] projection = {Contrato.Ejercicio._ID,
                Contrato.Ejercicio.NOMBRE,
                Contrato.Ejercicio.ID_MUSCULO};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            com.example.kelly.appgym.pojos.Ejercicio ejercicio = new com.example.kelly.appgym.pojos.Ejercicio();
            ejercicio.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Ejercicio._ID)));
            ejercicio.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Ejercicio.NOMBRE)));
            ejercicio.setId_musculo(cursor.getInt(cursor.getColumnIndex(Contrato.Ejercicio.ID_MUSCULO)));
            return ejercicio;
        }

        return null;

    }

    static public com.example.kelly.appgym.pojos.Ejercicio motrar_musculo(ContentResolver resolver, int ejercicioId) {
        Uri uri = Uri.parse(Contrato.Ejercicio.CONTENT_URI + "/MUSCULO");

        String[] projection = {Contrato.Ejercicio._ID,
                Contrato.Ejercicio.NOMBRE,
                Contrato.Musculo.NOMBRE};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            com.example.kelly.appgym.pojos.Ejercicio ejercicio = new com.example.kelly.appgym.pojos.Ejercicio();
            ejercicio.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Ejercicio._ID)));
            ejercicio.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Ejercicio.NOMBRE)));
            ejercicio.setId_musculo(cursor.getInt(cursor.getColumnIndex(Contrato.Ejercicio.ID_MUSCULO)));
            return ejercicio;
        }

        return null;

    }
}
