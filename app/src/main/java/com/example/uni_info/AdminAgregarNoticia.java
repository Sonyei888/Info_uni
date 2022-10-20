package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminAgregarNoticia extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agregar_noticia);
        findViewById(R.id.btn_agregar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_cancelar_agregar_noticia).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_agregar_noticia:
                break;
            case R.id.btn_cancelar_agregar_noticia:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}