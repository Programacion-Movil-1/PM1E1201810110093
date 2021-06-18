package com.example.pm1e1201810110093;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm1e1201810110093.transacciones.Transacciones;

public class MainActivity extends AppCompatActivity {

    EditText nombre, telefono, nota;
    Spinner pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pais =(Spinner)findViewById(R.id.cmbPais);
        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtNumero);
        nota = (EditText) findViewById(R.id.txtNota);
        final EditText[] AllFields = {nombre, telefono, nota};

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource
                (this,R.array.cmbPais, android.R.layout.simple_spinner_item);
        pais.setAdapter(adp);


        Button btnsalvar = (Button) findViewById(R.id.btnsalvar);

        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidationFields(AllFields))
                    Toast.makeText(MainActivity.this,"Faltan campos por llenar",Toast.LENGTH_SHORT).show();
                else
                    AgregarContacto();
            }
        });
    }

    public void irAListView (View  view){
        Intent i = new Intent(this, ListViewActivity.class);
        startActivity(i);
    }

    public boolean ValidationFields(EditText[] Fields) {
        for(int i=0; i<Fields.length; i++) {
            String string = Fields[i].getText().toString();
            if(string.trim().isEmpty()) {
                Fields[i].setError("Campo vacio");
                return true;
            }
        }
        return false;
    }

    private void AgregarContacto() {

        try {
            String sPais = pais.getSelectedItem().toString();
            String sPaisText = sPais.substring(0, sPais.length() - 5);
            String sPaisNumber = sPais.substring(sPais.length() - 4, sPais.length() - 1);

            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();
            ContentValues valores = new ContentValues();

            valores.put(Transacciones.cp_Pais, sPaisText);
            valores.put(Transacciones.cp_Nombre, nombre.getText().toString());
            valores.put(Transacciones.cp_Telefono, sPaisNumber + telefono.getText().toString());
            valores.put(Transacciones.cp_Nota, nota.getText().toString());

            Long resultado = db.insert(Transacciones.tblContactosPersonas, Transacciones.cp_ID, valores);
            Toast.makeText(getApplicationContext(), "Registro Ingresado:" + resultado.toString(),Toast.LENGTH_LONG).show();
            db.close();

        } catch (Exception ex) {

            Toast.makeText(getApplicationContext(),"Se produjo un error",Toast.LENGTH_LONG).show();
        }

        ClearScreen();
    }

    private void ClearScreen() {
        pais.setSelection(0);
        nombre.setText("");
        telefono.setText("");
        nota.setText("");;
    }
}
