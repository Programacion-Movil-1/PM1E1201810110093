package com.example.pm1e1201810110093;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnlista = (Button) findViewById(R.id.btnsalvar);

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CamposVacios()) {
                    Toast.makeText(getApplicationContext(), "Registro guardado", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void irAListView (View  view){
        Intent i = new Intent(this, ListViewActivity.class);
        startActivity(i);
    }

    public boolean CamposVacios() {
        LinearLayout linearLayout = findViewById(R.id.mainLayout);
        int contador = linearLayout.getChildCount();

        boolean isAllFill = true;

        for (int i = 0; i < contador; i++) {
            EditText AllEditText = (EditText) linearLayout.getChildAt(i);

            if (AllEditText.getText().toString().isEmpty()) {
                AllEditText.setError("No puede quedar vacio");
                isAllFill = false;
                break;
            }
        }
        return isAllFill;
    }
}
