package com.example.pm1e1201810110093;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CallActivity extends AppCompatActivity {

    EditText txtnombre, txttelefono, txtnota;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }

    public void irMainActivity (View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        id  = getIntent().getExtras().getLong("ID");
        String nombre  = getIntent().getExtras().getString("Nombre");
        String telefono  = getIntent().getExtras().getString("Telefono");
        String nota  = getIntent().getExtras().getString("Nota");

        txtnombre = findViewById(R.id.txtNombre);
        txttelefono = findViewById(R.id.txtNumero);
        txtnota = findViewById(R.id.txtNota);

        txtnombre.setText(nombre);
        txttelefono.setText(telefono);
        txtnota.setText(nota);
    }
}