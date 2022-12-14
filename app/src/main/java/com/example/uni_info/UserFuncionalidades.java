package com.example.uni_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uni_info.BD.dbInfo;
import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;
import com.example.uni_info.adaptadores.ListaNoticiasAdapter;
import com.example.uni_info.adaptadores.ListaVerNoticiasAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFuncionalidades extends AppCompatActivity implements View.OnClickListener {

    ListaNoticiasAdapter adapter;
    ArrayList<Noticias> listanoticiasArray;
    private List<Noticias> listanoticias = new ArrayList<Noticias>();
    RecyclerView listalibro;
    Metodos metodos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_funcionalidades);
        listalibro = findViewById(R.id.listanoticias);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_acerca).setOnClickListener(this);
        findViewById(R.id.btn_noticias).setOnClickListener(this);
        listalibro.setLayoutManager(new LinearLayoutManager(this));
        metodos = new Metodos(this);
        listanoticiasArray = new ArrayList<>();
        inicializarFirebase();
        listarDatos();
    }
    private void listarDatos(){
        databaseReference.child("Noticias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listanoticias.clear();
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    Noticias noticias = objSnapshot.getValue(Noticias.class);
                    listanoticias.add(noticias);

                    adapter = new ListaNoticiasAdapter(listanoticias);
                    listalibro.setAdapter(adapter);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                /*dbInfo dbInfo = new dbInfo(UserFuncionalidades.this);
                SQLiteDatabase db = dbInfo.getWritableDatabase();
                if(db != null){
                    Toast.makeText(UserFuncionalidades.this, "Base de datos creeda", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(UserFuncionalidades.this, "Error", Toast.LENGTH_LONG).show();
                }*/
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_acerca:
                Intent intent1 = new Intent(this, usuAcercade.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_noticias:
                Toast.makeText(this, "Noticias Actualizadas", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}