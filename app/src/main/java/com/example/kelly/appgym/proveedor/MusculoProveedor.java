package com.example.kelly.appgym.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.kelly.appgym.constantes.Utilidades;
import com.example.kelly.appgym.pojos.Musculo;

import java.io.IOException;


public class MusculoProveedor {
    static public void insert(ContentResolver resolvedor, Musculo musculo, Context contexto){
        Uri uri = Contrato.Musculo.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Musculo.NOMBRE, musculo.getNombre());

//        resolvedor.insert(uri, values);
        Uri returnUri = resolvedor.insert(uri, values);

    }

    static public void delete(ContentResolver resolver, int musculoId){
        Uri uri = Uri.parse(Contrato.Musculo.CONTENT_URI + "/" + musculoId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Musculo musculo, Context contexto){
        Uri uri = Uri.parse(Contrato.Musculo.CONTENT_URI + "/" + musculo.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Musculo.NOMBRE, musculo.getNombre());

        resolver.update(uri, values, null, null);

    }

    static public Musculo read(ContentResolver resolver, int musculoId) {
        Uri uri = Uri.parse(Contrato.Musculo.CONTENT_URI + "/" + musculoId);

        String[] projection = {Contrato.Musculo._ID,
                Contrato.Ejercicio.NOMBRE};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Musculo musculo = new com.example.kelly.appgym.pojos.Musculo();
            musculo.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Musculo._ID)));
            musculo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Musculo.NOMBRE)));

            return musculo;
        }

        return null;

    }
}
