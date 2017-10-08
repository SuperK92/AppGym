package com.example.kelly.appgym;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Activity contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexto = this;

        Button buttonInicioSesion = (Button) findViewById(R.id.buttonInicioSesion);
        buttonInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario("kelly@correo.com", "Kelly Suarez", "12345678", "Mujer");
                Intent intent = new Intent(contexto, LoginActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        Button buttonRegistro = (Button) findViewById(R.id.buttonRegistro);
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }
}
