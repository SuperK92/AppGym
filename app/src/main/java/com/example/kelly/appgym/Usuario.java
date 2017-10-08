package com.example.kelly.appgym;

import android.os.Parcel;
import android.os.Parcelable;


public class Usuario implements Parcelable{
    String correo;
    String nombre;
    String pass;
    String sexo;

    public Usuario(String correo, String nombre, String pass, String sexo) {
        this.correo = correo;
        this.nombre = nombre;
        this.pass = pass;
        this.sexo = sexo;
    }

    public Usuario() {
        super();
    }

    protected Usuario(Parcel in) {
        correo = in.readString();
        nombre = in.readString();
        pass = in.readString();
        sexo = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(correo);
        dest.writeString(nombre);
        dest.writeString(pass);
        dest.writeString(sexo);
    }
}
