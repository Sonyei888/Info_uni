package com.example.uni_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni_info.BD.dbInfo;
import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;
import com.example.uni_info.adaptadores.ListaNoticiasAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserFuncionalidades extends AppCompatActivity implements View.OnClickListener {

    ListaNoticiasAdapter adapter;
    ArrayList<Noticias> listanoticiasArray;
    private List<Noticias> listanoticias = new ArrayList<Noticias>();
    RecyclerView listalibro;
    private TextView tvNoConnection;
    Metodos metodos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private dbInfo dbInfo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_funcionalidades);
        listalibro = findViewById(R.id.listanoticias);
        tvNoConnection = findViewById(R.id.tv_no_connection);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_acerca).setOnClickListener(this);
        findViewById(R.id.btn_noticias).setOnClickListener(this);

        listalibro.setLayoutManager(new LinearLayoutManager(this));
        dbInfo = new dbInfo(this); //instanciar la base de datos
        metodos = new Metodos(this);
        listanoticiasArray = new ArrayList<>();
        inicializarFirebase(); // metodo incializarfirebase
        listarDatos();
        Comprobar(); //metodo para comprobar internet



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
                if (!isNetworkAvailable()){
                    Comprobar(); //invoca el metodo comprobar
                }else { //si esta conectado a una red internet cambia de activity.
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btn_acerca:
                Intent intent1 = new Intent(this, usuAcercade.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_noticias:
                if (!isNetworkAvailable()){
                    Comprobar();
                    listarDatos();
                }else {
                    Toast.makeText(this, "Noticias Actualizadas", Toast.LENGTH_SHORT).show();
                    listarDatos();
                    Comprobar();
                }

                break;
        }
    }
    private void Comprobar(){
        if (!isNetworkAvailable()) {
            tvNoConnection.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvNoConnection.setVisibility(View.GONE);
                }
            }, 20000); // 20000 milisegundos = 20 segundos
        }else {
            tvNoConnection.setVisibility(View.GONE);
        }
    }
    //metodo para comprobar conexion a internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void listarDatos(){
        if(isNetworkAvailable()){
            databaseReference.child("Noticias").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listanoticias.clear();
                    dbInfo.clearData(); //Limpiar la base de datos
                    for(DataSnapshot objSnapshot : snapshot.getChildren()){
                        Noticias noticias = objSnapshot.getValue(Noticias.class);
                        listanoticias.add(noticias);
                        metodos.insertnoticia(noticias);
                        adapter = new ListaNoticiasAdapter(listanoticias);
                        listalibro.setAdapter(adapter);
                        String fecha = noticias.getFecha().toString();
                        String hora = noticias.getHora().toString();
                        String FechaHora = fecha + hora;


                        /*//Notificaciones

                        Calendar currentTime = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        Calendar activityTime = Calendar.getInstance();
                        try {
                            Date activityDateTime = dateFormat.parse(FechaHora);
                            activityTime.setTime(activityDateTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // Calcula la diferencia de tiempo en milisegundos
                        long timeDifference = activityTime.getTimeInMillis() - currentTime.getTimeInMillis();
                        // Resta 5 minutos (300,000 milisegundos) a la diferencia de tiempo
                        timeDifference -= 300000;

                        new CountDownTimer(timeDifference, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Aquí puedes actualizar una interfaz de usuario con el tiempo restante si es necesario
                            }

                            @Override
                            public void onFinish() {
                                // Cuando se complete el temporizador, muestra la notificación
                                NotificationHelper notificationHelper = new NotificationHelper(context);
                                notificationHelper.showNotification("Recordatorio de actividad", "La actividad comenzará en 5 minutos", 1);
                            }
                        }.start();*/
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Error al descargar lso datos: " );
                }
            });
        }else{
            ListaNoticiasAdapter adapter = new ListaNoticiasAdapter(metodos.vernoticias());
            listalibro.setAdapter(adapter);
        }
    }
}