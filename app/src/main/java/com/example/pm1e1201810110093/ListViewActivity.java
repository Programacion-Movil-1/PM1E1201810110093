package com.example.pm1e1201810110093;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm1e1201810110093.tablas.contactos;
import com.example.pm1e1201810110093.transacciones.Transacciones;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private EditText mEditTextNumber;

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
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
            }
        });
    }

    public void irMainActivity (View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void irCallActivity (View view){

        makePhoneCall();

        /* irCallActivity es hacer la llamada
        Intent i = new Intent(this, CallActivity.class);
        startActivity(i);*/

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
    private void makePhoneCall() {
        String number = mEditTextNumber.getText().toString();
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