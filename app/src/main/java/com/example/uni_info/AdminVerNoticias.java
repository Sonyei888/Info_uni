package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;
import com.example.uni_info.adaptadores.ListaVerNoticiasAdapter;

import java.util.ArrayList;

public class AdminVerNoticias extends AppCompatActivity {

    ListaVerNoticiasAdapter adapter;
    ArrayList<Noticias> listaverArray;
    RecyclerView listanoticia;
    Metodos metodos;
    Noticias noticias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ver_noticias);
        listanoticia = findViewById(R.id.lista_ver_noticias_admin);
        listanoticia.setLayoutManager(new LinearLayoutManager(this));
        metodos = new Metodos(this);
        listaverArray = new ArrayList<>();
        adapter = new ListaVerNoticiasAdapter(metodos.vernoticias());
        listanoticia.setAdapter(adapter);
    }
    public  void añadirlibro(View v){
        switch (v.getId()){
            case R.id.menu_añadir_noticia:
                Intent intent = new Intent(this, AdminAgregarNoticia.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    public void regresarveradmin(View v){
        switch (v.getId()){
            case R.id.regresarveradmin:
                Toast.makeText(AdminVerNoticias.this, "Noticias", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(AdminVerNoticias.this, UserFuncionalidades.class);
                startActivity(i3);
                finish();
        }
    }
}