package com.example.kelly.appgym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        Intent intent = this.getIntent();
        String nombreUsuario = intent.getExtras().getString("usuario");


        TextView nombrePerfil = (TextView) findViewById(R.id.textUsuario);
        nombrePerfil.setText(nombreUsuario);
    }
}
