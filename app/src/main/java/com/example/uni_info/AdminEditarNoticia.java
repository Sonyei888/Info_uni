package com.example.uni_info;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class AdminEditarNoticia extends AppCompatActivity implements View.OnClickListener {

    EditText titulo_edit;
    EditText resumen_edit;
    /*EditText fecha_edit;
    EditText hora_edit;*/
    private TextView fechatv;
    private Calendar calendar;
    private TextView horatv;
    Metodos metodos;
    Noticias noticias;
    private String fecha;
    private String horaSeleccionada;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_editar_noticia);
        titulo_edit = findViewById(R.id.edit_editar_titulo_noticia);
        resumen_edit = findViewById(R.id.edit_editar_resumen_noticia);
        fechatv = findViewById(R.id.tv_fecha_edit);
        horatv = findViewById(R.id.tv_hora_edit);
        calendar = Calendar.getInstance();
        findViewById(R.id.btn_editar_aceptar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_cancelar_editar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_aliminar_noticia).setOnClickListener(this);
        findViewById(R.id.btn_seleccionar_fecha_edit).setOnClickListener(this);
        findViewById(R.id.btn_seleccionar_hora_edit).setOnClickListener(this);
        noticias = new Noticias();
        metodos = new Metodos(this);

        Bundle extras = getIntent().getExtras();
        noticias = (Noticias) extras.getSerializable("ID");

        /*dbInfo dbInfo = new dbInfo(AdminEditarNoticia.this);
        noticias = metodos.ver(id);*/

        if(noticias != null){
            titulo_edit.setText(noticias.getNombre());
            resumen_edit.setText(noticias.getResumen());
            fechatv.setText(noticias.getFecha());
            horatv.setText(noticias.getHora());
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
            case R.id.btn_seleccionar_fecha_edit:
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdminEditarNoticia.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Actualizar el TextView con la fecha seleccionada
                                fecha = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                                fechatv.setText(fecha);


                            }
                        },
                        year, month, dayOfMonth);

                // Establecer la fecha mínima seleccionable en un día después de la fecha actual
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, -1);

                datePickerDialog.show();

                break;
            case R.id.btn_seleccionar_hora_edit:
                // Obtener hora actual
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minuto = calendar.get(Calendar.MINUTE);

                // Abrir selector de hora
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                                horatv.setText(horaSeleccionada);

                            }
                        },
                        hora, minuto, false);
                timePickerDialog.show();
                break;
            //boton editar noticias
            case R.id.btn_editar_aceptar_noticia:
                boolean correcto;
                //sconvertir editext a string
                String nombre = titulo_edit.getText().toString();
                //setear la clase noticias
                noticias.setNombre(titulo_edit.getText().toString());
                String resumen = resumen_edit.getText().toString();
                noticias.setResumen(resumen);
                String fecha1 = fechatv.getText().toString();
                noticias.setFecha(fecha1);
                String hora1 = horatv.getText().toString();
                noticias.setHora(hora1);
                //if para comprobar que los campos no esten vacios
                if(!nombre.isEmpty() && !resumen.isEmpty() && !fecha1.isEmpty() && !hora1.isEmpty()){
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
                                metodos.eliminarNoticia(noticias.getId());
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