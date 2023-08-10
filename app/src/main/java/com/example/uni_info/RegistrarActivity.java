package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText UserNombre;
    private RadioGroup UserDocumento;
    private EditText UserEmail;
    private EditText UserContraseña;
    private EditText UserConfirmarContraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        findViewById(R.id.googleButton).setOnClickListener(this);
        findViewById(R.id.registerButton).setOnClickListener(this);
        UserNombre = findViewById(R.id.editTextFullName);
        UserEmail = findViewById(R.id.editTextEmail);
        UserContraseña = findViewById(R.id.editTextPassword);
        UserConfirmarContraseña = findViewById(R.id.editTextRepeatPassword);

    }
    public void regresarregistro(View v){
        switch (v.getId()){
            case R.id.regresar_informacion:
                Intent i3 = new Intent(RegistrarActivity.this, LoginActivity.class);
                startActivity(i3);
                finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.googleButton:
                break;

            case R.id.registerButton:
                break;
        }
    }
}