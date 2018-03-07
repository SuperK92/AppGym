package com.example.kelly.appgym.actividad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.kelly.appgym.R;
import com.example.kelly.appgym.constantes.G;
import com.example.kelly.appgym.pojos.Actividad;
import com.example.kelly.appgym.pojos.Ejercicio;
import com.example.kelly.appgym.pojos.Musculo;
import com.example.kelly.appgym.proveedor.ActividadProveedor;
import com.example.kelly.appgym.proveedor.EjercicioProveedor;
import com.example.kelly.appgym.proveedor.MusculoProveedor;

public class ActividadInsercionActivity extends AppCompatActivity {
    EditText editTextActividadSeries;
    EditText editTextActividadRepeticiones;
    EditText editTextActividadEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ciclo_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextActividadEjercicio = (EditText) findViewById(R.id.editTextActividadEjercicio);
        editTextActividadSeries = (EditText) findViewById(R.id.editTextActividadSeries);
        editTextActividadRepeticiones = (EditText) findViewById(R.id.editTextActividadRepeticiones);

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
        editTextActividadEjercicio.setError(null);
        editTextActividadSeries.setError(null);
        editTextActividadRepeticiones.setError(null);


        String textoEjercicio = String.valueOf(editTextActividadEjercicio.getText());
        String textoSeries = String.valueOf(editTextActividadSeries.getText());
        String textoRepeticiones = String.valueOf(editTextActividadRepeticiones.getText());


        if(TextUtils.isEmpty(textoEjercicio)){
            editTextActividadEjercicio.setError(getString(R.string.campo_requerido));
            editTextActividadEjercicio.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(textoSeries)){
            editTextActividadSeries.setError(getString(R.string.campo_requerido));
            editTextActividadSeries.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(textoRepeticiones)){
            editTextActividadRepeticiones.setError(getString(R.string.campo_requerido));
            editTextActividadRepeticiones.requestFocus();
            return;
        }

        Integer idEjercicio = Integer.parseInt(editTextActividadEjercicio.getText().toString());
        Integer series = Integer.parseInt(editTextActividadSeries.getText().toString());
        Integer repeticiones = Integer.parseInt(editTextActividadRepeticiones.getText().toString());
        Ejercicio ejercicio = EjercicioProveedor.read(getContentResolver(), idEjercicio);

        Actividad actividad = new Actividad(G.SIN_VALOR_INT, ejercicio, series, repeticiones);
        ActividadProveedor.insert(getContentResolver(), actividad, this);

        finish();
    }
}
