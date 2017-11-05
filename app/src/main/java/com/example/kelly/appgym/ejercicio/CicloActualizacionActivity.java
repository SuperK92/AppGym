package com.example.kelly.appgym.ejercicio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kelly.appgym.R;
import com.example.kelly.appgym.constantes.G;
import com.example.kelly.appgym.pojos.Ejercicio;
import com.example.kelly.appgym.proveedor.EjercicioProveedor;

public class CicloActualizacionActivity extends AppCompatActivity {
    EditText editTextCicloNombre;
    EditText editTextCicloAbreviatura;
    int ejercicioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciclo_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ciclo_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextCicloNombre = (EditText) findViewById(R.id.editTextCicloNombre);
        editTextCicloAbreviatura = (EditText) findViewById(R.id.editTextCicloAbreviatura);


        ejercicioId = this.getIntent().getExtras().getInt("ID");
        int rep = this.getIntent().getExtras().getInt("Repeticiones");
        editTextCicloNombre.setText(this.getIntent().getExtras().getString("Nombre"));
        editTextCicloAbreviatura.setText(String.valueOf(rep));

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
        editTextCicloNombre.setError(null);
        editTextCicloAbreviatura.setError(null);

        String nombre = String.valueOf(editTextCicloNombre.getText());
        String repeticiones = String.valueOf(editTextCicloAbreviatura.getText());

        if(TextUtils.isEmpty(nombre)){
            editTextCicloNombre.setError(getString(R.string.campo_requerido));
            editTextCicloNombre.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(repeticiones)){
            editTextCicloAbreviatura.setError(getString(R.string.campo_requerido));
            editTextCicloAbreviatura.requestFocus();
            return;
        }
//        else if (nRep < 1) {
//            editTextCicloAbreviatura.setError("El número debe ser mayor de 0");
//            editTextCicloAbreviatura.requestFocus();
//            return;
//        }
        Integer nRep = Integer.parseInt(editTextCicloAbreviatura.getText().toString());
        Ejercicio ejercicio = new Ejercicio(ejercicioId, nombre, nRep);
        EjercicioProveedor.update(getContentResolver(), ejercicio);
        finish();
    }
}
