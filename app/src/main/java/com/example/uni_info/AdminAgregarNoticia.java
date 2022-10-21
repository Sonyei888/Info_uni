package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;

public class AdminAgregarNoticia extends AppCompatActivity implements View.OnClickListener {
    EditText titulo;
    EditText resumen;
    EditText informacion;
    EditText fecha;
    EditText hora;
    Metodos metodos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agregar_noticia);
        titulo = findViewById(R.id.edit_titulo_noticia);
        resumen = findViewById(R.id.edit_resumen_noticia);
/*        informacion = findViewById(R.id.edit_info_noticia);*/
        fecha = findViewById(R.id.edit_fecha_noticia);
        hora = findViewById(R.id.edit_hora_noticia);
        findViewById(R.id.btn_agregar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_cancelar_agregar_noticia).setOnClickListener(this);
        metodos = new Metodos(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_agregar_noticia:
                Noticias noticias = new Noticias();
                noticias.setNombre(titulo.getText().toString());
                noticias.setResumen(resumen.getText().toString());
                noticias.setFecha(fecha.getText().toString());
                noticias.setHora(hora.getText().toString());

                if(!noticias.getNombre().isEmpty() && !noticias.getFecha().isEmpty() && !noticias.getHora().isEmpty()){
                    if (metodos.insertnoticia(noticias)) {
                        Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, AdminVerNoticias.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancelar_agregar_noticia:
                Intent intent = new Intent(this, AdminVerNoticias.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}