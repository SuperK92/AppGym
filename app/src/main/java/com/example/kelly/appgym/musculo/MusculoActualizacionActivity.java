package com.example.kelly.appgym.musculo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.kelly.appgym.R;
import com.example.kelly.appgym.constantes.G;
import com.example.kelly.appgym.constantes.Utilidades;
import com.example.kelly.appgym.pojos.Ejercicio;
import com.example.kelly.appgym.pojos.Musculo;
import com.example.kelly.appgym.proveedor.EjercicioProveedor;
import com.example.kelly.appgym.proveedor.MusculoProveedor;

import java.io.FileNotFoundException;

public class MusculoActualizacionActivity extends AppCompatActivity {
    EditText editTextMusculoNombre;

    int musculoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musculo_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ciclo_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextMusculoNombre = (EditText) findViewById(R.id.editTextCicloNombre);


        musculoId = this.getIntent().getExtras().getInt("ID");

        editTextMusculoNombre.setText(this.getIntent().getExtras().getString("Nombre"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.add(Menu.NONE, G.GUARDAR, Menu.NONE, "Guardar");
        menuItem.setIcon(R.drawable.ic_action_confirmar);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case G.GUARDAR:
                attemptGuardar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void attemptGuardar(){
        editTextMusculoNombre.setError(null);

        String nombre = String.valueOf(editTextMusculoNombre.getText());

        if(TextUtils.isEmpty(nombre)){
            editTextMusculoNombre.setError(getString(R.string.campo_requerido));
            editTextMusculoNombre.requestFocus();
            return;
        }



        Musculo musculo = new Musculo(musculoId, nombre);
        MusculoProveedor.update(getContentResolver(), musculo, this);
        finish();
    }
}
