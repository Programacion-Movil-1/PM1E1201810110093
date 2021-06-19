package com.example.pm1e1201810110093;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm1e1201810110093.tablas.contactos;
import com.example.pm1e1201810110093.transacciones.Transacciones;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private EditText mEditTextNumber;
    private String Dato;
    private String Nombre;
    private String Telefono;
    private String Nota;

    SQLiteConexion conexion;
    EditText buscar;
    ListView ListaContactos;
    ArrayList<contactos> ArrayLista;
    ArrayList<String> ArrayContactos;
    private AlertDialog.Builder EliminarItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        conexion= new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        buscar = (EditText) findViewById(R.id.txtBusquedaLV);
        ListaContactos= (ListView) findViewById(R.id.ListContacts);
        mEditTextNumber = (EditText) findViewById(R.id.txtBusquedaLV);
        Button btnDelete = (Button) findViewById(R.id.btnEliminarLV);
        Button btnCall = (Button) findViewById(R.id.btnLlamarLV);
        Button btnUpdate = (Button) findViewById(R.id.btnActualizarLV);

        ObtenerListaContactosPersonas();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, ArrayContactos);
        ListaContactos.setAdapter(adp);

        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adp.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ListaContactos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ListaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Dato = ""+ArrayLista.get(position).getCp_ID();
                Nombre = ""+ArrayLista.get(position).getCp_Nombre();
                Telefono = "+"+ArrayLista.get(position).getCp_Telefono();
                Nota = ""+ArrayLista.get(position).getCp_Nota();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
                Intent i = new Intent(ListViewActivity.this, ListViewActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(this, CallActivity.class);
                i.putExtra("ID", Dato);
                i.putExtra("Nombre", Nombre);
                i.putExtra("Telefono", Telefono);
                i.putExtra("Nota", Nota);
                startActivity(i);
                finish();
            }
        });
    }

    public void irMainActivity (View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



    public void irCallActivity (View view){

    }

    private void ObtenerListaContactosPersonas() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        contactos listviewContactos = null;
        ArrayLista = new ArrayList<contactos>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tblContactosPersonas, null);

        while (cursor.moveToNext()){
            listviewContactos = new contactos();
            listviewContactos.setCp_ID(cursor.getInt(0));
            listviewContactos.setCp_Nombre(cursor.getString(2));
            listviewContactos.setCp_Telefono(cursor.getString(3));
            ArrayLista.add(listviewContactos);
        }
        cursor.close();
        FillList();
    }

    private void FillList() {

        ArrayContactos = new ArrayList<String>();

        for (int i = 0;  i < ArrayLista.size(); i++){

            ArrayContactos.add(ArrayLista.get(i).getCp_Nombre() + " | "
                    +ArrayLista.get(i).getCp_Telefono());
        }
    }

    private void eliminar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String []  params = {Dato};
        String wherecond = Transacciones.cp_ID + "=?";
        db.delete(Transacciones.tblContactosPersonas, wherecond, params);
        Toast.makeText(getApplicationContext(),"Dato eliminado", Toast.LENGTH_LONG).show();
    }

    private void makePhoneCall() {
        String number = Telefono;
        if (number.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(ListViewActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ListViewActivity.this,
                        new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(ListViewActivity.this, "Ingrese un Numero Telefonico", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }else{
                Toast.makeText(this, "Permisos DENEGADOS!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}