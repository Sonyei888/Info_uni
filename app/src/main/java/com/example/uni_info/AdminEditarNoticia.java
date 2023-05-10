package com.example.uni_info;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uni_info.BD.dbInfo;
import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminEditarNoticia extends AppCompatActivity implements View.OnClickListener {

    EditText titulo_edit;
    EditText resumen_edit;
    EditText fecha_edit;
    EditText hora_edit;
    Metodos metodos;
    Noticias noticias;
    int id = 0;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_editar_noticia);
        titulo_edit = findViewById(R.id.edit_editar_titulo_noticia);
        resumen_edit = findViewById(R.id.edit_editar_resumen_noticia);
        fecha_edit = findViewById(R.id.edit_editar_fecha_noticia);
        hora_edit = findViewById(R.id.edit_editar_hora_noticia);
        findViewById(R.id.btn_editar_aceptar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_cancelar_editar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_aliminar_noticia).setOnClickListener(this);
        noticias = new Noticias();
        metodos = new Metodos(this);

        Bundle extras = getIntent().getExtras();
        noticias = (Noticias) extras.getSerializable("ID");

        /*dbInfo dbInfo = new dbInfo(AdminEditarNoticia.this);
        noticias = metodos.ver(id);*/

        if(noticias != null){
            titulo_edit.setText(noticias.getNombre());
            resumen_edit.setText(noticias.getResumen());
            fecha_edit.setText(noticias.getFecha());
            hora_edit.setText(noticias.getHora());
        }
        inicializarFirebase();
    }
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //boton editar noticias
            case R.id.btn_editar_aceptar_noticia:
                boolean correcto;
                //sconvertir editext a string
                String nombre = titulo_edit.getText().toString();
                //setear la clase noticias
                noticias.setNombre(titulo_edit.getText().toString());
                String resumen = resumen_edit.getText().toString();
                noticias.setResumen(resumen);
                String fecha = fecha_edit.getText().toString();
                noticias.setFecha(fecha);
                String hora = hora_edit.getText().toString();
                noticias.setHora(hora);
                //if para comprobar que los campos no esten vacios
                if(!nombre.isEmpty() && !resumen.isEmpty() && !fecha.isEmpty() && !hora.isEmpty()){
                    //si es verdad se modifica la base de datos
                    databaseReference.child("Noticias").child(noticias.getId()).setValue(noticias);
                    //se muestra toast
                    Toast.makeText(this, "Noticia Modificada", Toast.LENGTH_SHORT).show();
                    //con un intent se envia al administrador a otra vista
                    Intent intent = new Intent(this, AdminVerNoticias.class);
                    startActivity(intent);
                    finish();
                }else{
                    //si es falso se muestra un Toast
                    Toast.makeText(this, "Campos Vacios", Toast.LENGTH_SHORT).show();
                }
                break;

                //boton de eliminar noticias
            case R.id.btn_aliminar_noticia:

                //dialogo de alerta donde se confirma que desea elimianr la noticia
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Desea elminar esta noticia?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //se elimina el registro de la noticia desde la base de datos
                                databaseReference.child("Noticias").child(noticias.getId()).removeValue();
                                //se muestra un toast
                                    Toast.makeText(AdminEditarNoticia.this, "Noticia Eliminada", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminEditarNoticia.this, AdminVerNoticias.class);
                                    startActivity(intent);
                                    finish();

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;
                //boton de cancelar
            case R.id.btn_cancelar_editar_noticia:
                Intent intent = new Intent(this, AdminVerNoticias.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}