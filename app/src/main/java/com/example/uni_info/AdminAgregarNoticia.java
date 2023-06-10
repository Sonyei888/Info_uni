package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uni_info.BD.dbInfo;
import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class AdminAgregarNoticia extends AppCompatActivity implements View.OnClickListener {
    EditText titulo;
    EditText resumen;
    private TextView tvFecha;
    private Calendar calendar;
    private TextView tvHora;
    Metodos metodos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String fecha;
    private String horaSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agregar_noticia);
        titulo = findViewById(R.id.edit_titulo_noticia);
        resumen = findViewById(R.id.edit_resumen_noticia);
        tvFecha = findViewById(R.id.tv_fecha);
        tvHora = findViewById(R.id.tv_hora);
        calendar = Calendar.getInstance();
/*      informacion = findViewById(R.id.edit_info_noticia);*/
        findViewById(R.id.btn_agregar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_cancelar_agregar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_seleccionar_fecha).setOnClickListener(this);
        findViewById(R.id.btn_seleccionar_hora).setOnClickListener(this);
        metodos = new Metodos(this);
        Noticias noticias = new Noticias();
        inicializarFirebase();
    }
    /*Inicializar base de datos global - firebase*/
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_seleccionar_fecha:
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdminAgregarNoticia.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Actualizar el TextView con la fecha seleccionada
                                fecha = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                                tvFecha.setText(fecha);


                            }
                        },
                        year, month, dayOfMonth);

                // Establecer la fecha mínima seleccionable en un día después de la fecha actual
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -1);


                datePickerDialog.show();

                break;
            case R.id.btn_seleccionar_hora:
                // Obtener hora actual
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);

                // Abrir selector de hora
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                                tvHora.setText(horaSeleccionada);

                            }
                        },
                        hora, minuto, false);
                timePickerDialog.show();
                break;
            case R.id.btn_agregar_noticia:
                //se declara e instancia la clase noticias
                Noticias noticias = new Noticias();
                //se setea noticias
                noticias.setId(UUID.randomUUID().toString());
                noticias.setNombre(titulo.getText().toString());
                noticias.setResumen(resumen.getText().toString());
                noticias.setFecha(tvFecha.getText().toString());
                noticias.setHora(tvHora.getText().toString());
                noticias.setDatabase("Bases de datos online");

                //se comprueba que los campos no esten vacios
                if(!noticias.getNombre().isEmpty() && !noticias.getResumen().isEmpty() && !noticias.getFecha().isEmpty() && !noticias.getHora().isEmpty()){
                   //si es verdad se agrega la noticia a la base de datos
                    databaseReference.child("Noticias").child(noticias.getId()).setValue(noticias);
                    /*metodos.insertnoticia(noticias);*/ //se invoca el metodo insertar noticia, el cual inserta la noticia en una base de datos local
                    //se muestra un mensaje con toast
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    //se envia a otra vista
                    Intent intent = new Intent(this, AdminVerNoticias.class);
                    startActivity(intent);
                    finish();

                }else {
                    //metodo validacion
                    validacion();
                }
                break;
                //boton de cancelar
            case R.id.btn_cancelar_agregar_noticia:
                Intent intent = new Intent(this, AdminVerNoticias.class);
                startActivity(intent);
                finish();
                break;
        }

    }
    private void validacion(){
        String nombre = titulo.getText().toString();
        String resumen1 = resumen.getText().toString();
        String fecha1 = tvFecha.getText().toString();
        String hora1 = tvHora.getText().toString();
        if(nombre.isEmpty()){
            titulo.setError("Requerido");
        }
        if(resumen1.isEmpty()){
            resumen.setError("Requerido");
        }
        if(fecha1.isEmpty()){
            tvFecha.setError("Requerido");
        }
        if(hora1.isEmpty()){
            tvHora.setError("Requerido");
        }
    }


}