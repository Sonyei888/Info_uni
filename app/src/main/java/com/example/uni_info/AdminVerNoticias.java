package com.example.uni_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;
import com.example.uni_info.adaptadores.ListaVerNoticiasAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminVerNoticias extends AppCompatActivity {

    ArrayAdapter<Noticias> adapter;
    private List<Noticias> listanoticias = new ArrayList<Noticias>();
    ArrayList<Noticias> listaverArray;
    ListView listanoticia;
    Metodos metodos;
    Noticias noticias;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ver_noticias);
        listanoticia = findViewById(R.id.lista_ver_noticias_admin);

        metodos = new Metodos(this);
        listaverArray = new ArrayList<>();

        inicializarFirebase();
        listarDatos();

        listanoticia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                noticias = (Noticias) adapterView.getItemAtPosition(i);
                Toast.makeText(AdminVerNoticias.this, "Editar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminVerNoticias.this, AdminEditarNoticia.class);
                intent.putExtra("ID", noticias);
                AdminVerNoticias.this.startActivity(intent);
            }
        });
    }
    private void listarDatos(){
        databaseReference.child("Noticias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listanoticias.clear();
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    Noticias noticias = objSnapshot.getValue(Noticias.class);
                    listanoticias.add(noticias);

                    adapter = new ArrayAdapter<Noticias>(AdminVerNoticias.this, android.R.layout.simple_list_item_1, listanoticias);
                    listanoticia.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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