package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uni_info.Metodos.Metodos;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Metodos metodos;
    EditText usuario;
    EditText cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.edit_user_name_login);
        cont = findViewById(R.id.edit_password_login);
        findViewById(R.id.btningresar).setOnClickListener(this);
        findViewById(R.id.btncancelar).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btningresar:
                String user = usuario.getText().toString();
                String contraseña = cont.getText().toString();
                String usuarioadmin = "admin@uniinfo.com";
                String contraseñaadmin = "1234";
                if(Objects.equals(user, usuarioadmin) && Objects.equals(contraseña, contraseñaadmin)){
                    Intent intent = new Intent(this, AdminVerNoticias.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this, "Contraseña y/o Usuario incorrecto", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btncancelar:
                Intent intent = new Intent(this, UserFuncionalidades.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}