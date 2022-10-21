package com.example.uni_info;

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

import java.util.ArrayList;

public class UserFuncionalidades extends AppCompatActivity implements View.OnClickListener {

    ListaNoticiasAdapter adapter;
    ArrayList<Noticias> listanoticiasArray;
    RecyclerView listalibro;
    Metodos metodos;

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
                break;
            case R.id.btn_acerca:
                Intent intent1 = new Intent(this, usuAcercade.class);
                startActivity(intent1);
                break;
            case R.id.btn_noticias:
                Toast.makeText(this, "Noticias", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}