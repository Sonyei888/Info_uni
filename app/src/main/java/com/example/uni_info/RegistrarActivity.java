package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegistrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    }
    public void regresarregistro(View v){
        switch (v.getId()){
            case R.id.regresar_informacion:
                Intent i3 = new Intent(RegistrarActivity.this, LoginActivity.class);
                startActivity(i3);
                finish();
        }
    }
}