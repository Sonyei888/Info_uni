package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;
import com.example.uni_info.adaptadores.ListaVerNoticiasAdapter;

import java.util.ArrayList;

public class AdminVerNoticias extends AppCompatActivity {

    ListaVerNoticiasAdapter adapter;
    ArrayList<Noticias> listaverArray;
    RecyclerView listanoticia;
    Metodos metodos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ver_noticias);
        listanoticia = findViewById(R.id.lista_ver_noticias_admin);
        listanoticia.setLayoutManager(new LinearLayoutManager(this));
        metodos = new Metodos();
        listaverArray = new ArrayList<>();
    }
}