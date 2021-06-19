package com.example.pm1e1201810110093;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm1e1201810110093.transacciones.Transacciones;

public class CallActivity extends AppCompatActivity {

    SQLiteConexion conexion;
    EditText nom, tel, not;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        Intent i = getIntent();
        id  = getIntent().getExtras().getString("ID");
        String nombre  = getIntent().getExtras().getString("Nombre");
        String telefono  = getIntent().getExtras().getString("Telefono");
        String nota  = getIntent().getExtras().getString("Nota");
        Button btnUpdate = (Button) findViewById(R.id.btnsalvar2);

        nom = (EditText) findViewById(R.id.txtNombreUP);
        tel = (EditText) findViewById(R.id.txtNumeroUP);
        not = (EditText) findViewById(R.id.txtNotaUP);

        nom.setText(nombre);
        tel.setText(telefono);
        not.setText(nota);

        Toast.makeText(getApplicationContext(),"Dato actualizados" + id, Toast.LENGTH_LONG).show();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
    }

    public void irMainActivity (View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void actualizar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String []  params = {id};

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.cp_Nombre, nom.getText().toString());
        valores.put(Transacciones.cp_Telefono, tel.getText().toString());
        valores.put(Transacciones.cp_Nota, not.getText().toString());

        db.update(Transacciones.tblContactosPersonas, valores, Transacciones.cp_ID +"=?", params);
        Toast.makeText(getApplicationContext(),"Dato actualizados", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, ListViewActivity.class);
        startActivity(i);
    }
}

