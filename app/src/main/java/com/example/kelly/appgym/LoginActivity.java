package com.example.kelly.appgym;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Activity contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contexto = this;

        Intent intent = this.getIntent();
        final Usuario usuario = intent.getParcelableExtra("usuario");

        final EditText nombreUser = (EditText) findViewById(R.id.editNombreLogin);
        final EditText passUser = (EditText) findViewById(R.id.editPasswordLogin);

        Button buttonLogin = (Button) findViewById(R.id.buttonIrAlPerfil);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreUser.getText().toString();

                if(TextUtils.isEmpty(nombre)) {
                    nombreUser.setError("Campo vacio");
                    nombreUser.requestFocus();
                    return;
                }
                else if(!TextUtils.equals(nombre, usuario.getCorreo())) {
                    nombreUser.setError("Nombre de Usuario no existe");
                    nombreUser.requestFocus();
                    return;
                }

                String pass = passUser.getText().toString();

                if(TextUtils.isEmpty(pass)) {
                    passUser.setError("Campo vacio");
                    passUser.requestFocus();
                    return;
                }
                else if (!TextUtils.equals(pass, usuario.getPass())) {
                    passUser.setError("Contraseña no es correcta");
                    passUser.requestFocus();
                    return;
                }

                Intent intent = new Intent(contexto, PrincipalActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }
}
