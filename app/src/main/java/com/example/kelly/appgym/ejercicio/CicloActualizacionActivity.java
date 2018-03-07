package com.example.kelly.appgym.ejercicio;

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
import android.widget.TextView;

import com.example.kelly.appgym.R;
import com.example.kelly.appgym.constantes.G;
import com.example.kelly.appgym.constantes.Utilidades;
import com.example.kelly.appgym.pojos.Ejercicio;
import com.example.kelly.appgym.pojos.Musculo;
import com.example.kelly.appgym.proveedor.EjercicioProveedor;
import com.example.kelly.appgym.proveedor.MusculoProveedor;

import java.io.FileNotFoundException;

public class CicloActualizacionActivity extends AppCompatActivity {
    EditText editTextCicloNombre;
    EditText editTextCicloAbreviatura;
    int ejercicioId;

    ImageView imageViewCiclo;

    final int PETICION_CAPTURAR_IMAGEN = 1;
    final int PETICION_ESCOGER_IMAGEN_DE_GALERIA = 2;

    Bitmap foto;

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
        int rep = this.getIntent().getExtras().getInt("Id_Musculo");

        editTextCicloNombre.setText(this.getIntent().getExtras().getString("Nombre"));
        editTextCicloAbreviatura.setText(String.valueOf(rep));

        imageViewCiclo = (ImageView) findViewById(R.id.image_view_ciclo);

        try {
            Utilidades.loadImageFromStorage(this, "img_" + ejercicioId + ".jpg", imageViewCiclo);
            foto = ((BitmapDrawable) imageViewCiclo.getDrawable()).getBitmap();
        } catch (FileNotFoundException e) {
            //El ciclo no tiene imagen asociada
            foto = null;
        }

        ImageButton imageButtonImagenDeGaleria = (ImageButton) findViewById(R.id.buttonImagenDeGaleria);
        imageButtonImagenDeGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirFotoDeGaleria();
            }
        });

        ImageButton imageButtonCamara = (ImageButton) findViewById(R.id.buttonCamara);
        imageButtonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarFoto();
            }
        });

    }

    void sacarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PETICION_CAPTURAR_IMAGEN);
    }

    void elegirFotoDeGaleria(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PETICION_ESCOGER_IMAGEN_DE_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PETICION_CAPTURAR_IMAGEN:
                if(resultCode == RESULT_OK){
                    foto = (Bitmap) data.getExtras().get("data");
                    imageViewCiclo.setImageBitmap(foto);
                } else {
                    // El usuario canceló la captura de foto
                }
                break;
            case PETICION_ESCOGER_IMAGEN_DE_GALERIA:
                if(resultCode == RESULT_OK){
                    imageViewCiclo.setImageURI(data.getData());
                    foto = ((BitmapDrawable) imageViewCiclo.getDrawable()).getBitmap();
                } else {
                    // El usuario canceló la elección de foto
                }
                break;
        }
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
//
        Integer nRep = Integer.parseInt(editTextCicloAbreviatura.getText().toString());
        Musculo musculo = MusculoProveedor.read(getContentResolver(), nRep);
        Ejercicio ejercicio = new Ejercicio(ejercicioId, nombre, musculo, foto);
        EjercicioProveedor.update(getContentResolver(), ejercicio, this);
        finish();
    }
}
