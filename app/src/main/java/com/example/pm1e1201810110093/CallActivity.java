package com.example.pm1e1201810110093;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }

    public void irMainActivity (View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}