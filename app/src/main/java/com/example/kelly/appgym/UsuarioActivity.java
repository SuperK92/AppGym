package com.example.kelly.appgym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class UsuarioActivity extends AppCompatActivity {


    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        Intent intent = this.getIntent();

        usuario = intent.getParcelableExtra("usuario");

        TextView nombrePerfil = (TextView) findViewById(R.id.textUsuario);
        nombrePerfil.setText(usuario.getNombre());

        TextView correoPerfil = (TextView) findViewById(R.id.textEmailUsuario);
        correoPerfil.setText(usuario.getCorreo());

        TextView sexoPerfil = (TextView) findViewById(R.id.textSexousuario);
        sexoPerfil.setText(usuario.getSexo());
    }

}
