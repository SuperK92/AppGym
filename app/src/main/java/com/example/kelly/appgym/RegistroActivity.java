package com.example.kelly.appgym;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    Activity contexto;
    String sexoSeleccionado = "Mujer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        contexto = this;

        final EditText editEmail = (EditText) findViewById(R.id.editEmail);

        final EditText editNombre = (EditText) findViewById(R.id.editNombre);

        final EditText editPassword = (EditText) findViewById(R.id.editPassword);

        RadioGroup radioSexo = (RadioGroup) findViewById(R.id.radioSexo);
        radioSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.sexoHombre:
                        sexoSeleccionado = "Hombre";
                        break;
                    case R.id.sexoMujer:
                        sexoSeleccionado = "Mujer";
                        break;
                    case R.id.sexoOtro:
                        sexoSeleccionado = "Otro";
                        break;
                }
            }
        });

        final CheckBox checkBoxCondiciones = (CheckBox) findViewById(R.id.checkboxCondiciones);

        Button buttonEnviar = (Button) findViewById(R.id.buttonEnviarFormulario);
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    editEmail.setError("Campo vacio");
                    editEmail.requestFocus();
                    return;
                }
                else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    editEmail.setError("No es un email valido");
                    editEmail.requestFocus();
                    return;
                }

                String nombre = editNombre.getText().toString();

                if(TextUtils.isEmpty(nombre)) {
                    editNombre.setError("Campo vacio");
                    editNombre.requestFocus();
                    return;
                }
                else if(!nombre.matches("[a-zA-Z ]+")) {
                    editNombre.setError("Solo puede contener letras");
                    editNombre.requestFocus();
                    return;
                }

                String pass = editPassword.getText().toString();
                int longitudPass = pass.length();

                if(TextUtils.isEmpty(pass)) {
                    editPassword.setError("Campo vacio");
                    editPassword.requestFocus();
                    return;
                }
                else if (longitudPass < 8) {
                    editPassword.setError("La longitud debe tener mínimo 8 caracteres");
                    editPassword.requestFocus();
                    return;
                }

                if(!checkBoxCondiciones.isChecked()) {
                    Toast.makeText(contexto, "Debe aceptar las condiciones", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario usuario = new Usuario(email, nombre, pass, sexoSeleccionado);
                Intent intent = new Intent(contexto, LoginActivity.class);
                intent.putExtra("usuario", usuario);

                startActivity(intent);
            }
        });


    }
}
