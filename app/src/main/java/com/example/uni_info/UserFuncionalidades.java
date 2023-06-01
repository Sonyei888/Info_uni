package com.example.uni_info;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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

import com.example.uni_info.NotificationHelper;

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

    private NotificationHelper notificationHelper;

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

        notificationHelper = new NotificationHelper(this);

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
                @RequiresApi(api = Build.VERSION_CODES.O)
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


                    }
                    // Obtén la hora y la fecha actual
                    Calendar currentTime = Calendar.getInstance();

                    for (Noticias noticias : listanoticias) {
                        // Obtén la hora y la fecha del evento desde la base de datos
                        String eventDateStr = noticias.getFecha();
                        String eventTimeStr = noticias.getHora();

                        // Parsea la fecha y la hora del evento a objetos de tipo Date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        Date eventDate = null;
                        Date eventTime = null;
                        try {
                            eventDate = dateFormat.parse(eventDateStr);
                            eventTime = timeFormat.parse(eventTimeStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (eventDate != null && eventTime != null) {
                            // Combina la fecha y la hora del evento en un solo objeto de tipo Calendar
                            Calendar eventDateTime = Calendar.getInstance();
                            eventDateTime.setTime(eventDate);
                            eventDateTime.set(Calendar.HOUR_OF_DAY, eventTime.getHours());
                            eventDateTime.set(Calendar.MINUTE, eventTime.getMinutes());

                            // Calcula la diferencia de tiempo en milisegundos
                            long timeDifferenceMillis = eventDateTime.getTimeInMillis() - currentTime.getTimeInMillis();

                            // Convierte la diferencia de tiempo a minutos
                            int timeDifferenceMinutes = (int) (timeDifferenceMillis / (1000 * 60));

                            // Si la diferencia de tiempo es igual o menor a 5 minutos, muestra la notificación
                            if (timeDifferenceMinutes <= 5) {
                                String notificationTitle = "Evento próximo";
                                String notificationMessage = "Estás a 5 minutos del evento: " + noticias.getNombre();
                                notificationHelper.showNotification(notificationTitle, notificationMessage);
                                //eliminar la noticia 10 minutos despues de suceder
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //se elimina el registro de la noticia desde la base de datos
                                        databaseReference.child("Noticias").child(noticias.getId()).removeValue();
                                        metodos.eliminarNoticia(noticias.getId());
                                    }
                                }, 5 * 60 * 1000); // 5 minutos en milisegundos
                            }
                        }
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