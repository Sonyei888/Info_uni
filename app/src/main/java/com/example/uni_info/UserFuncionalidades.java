package com.example.uni_info;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uni_info.BD.dbInfo;
import com.example.uni_info.Entidades.Noticias;
import com.example.uni_info.Metodos.Metodos;
import com.example.uni_info.adaptadores.ListaNoticiasAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


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
    private static final int PERMISSION_REQUEST_CODE = 1;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_funcionalidades);
        listalibro = findViewById(R.id.listanoticias);
        tvNoConnection = findViewById(R.id.tv_no_connection);
        findViewById(R.id.btn_agenda).setOnClickListener(this);
        findViewById(R.id.btn_acerca).setOnClickListener(this);
        findViewById(R.id.btn_noticias).setOnClickListener(this);

        listalibro.setLayoutManager(new LinearLayoutManager(this));
        dbInfo = new dbInfo(this); //instanciar la base de datos
        metodos = new Metodos(this);
        listanoticiasArray = new ArrayList<>();
        inicializarFirebase(); // metodo incializarfirebase
        listarDatos();
        Comprobar(); //metodo para comprobar internet

        verificarpermisos();

    }

    private void verificarpermisos(){

        // Verificar si el permiso está otorgado
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
                != PackageManager.PERMISSION_GRANTED) {
            // El permiso no está otorgado, solicitarlo al usuario
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                    PERMISSION_REQUEST_CODE);
        }

    }

    private void llamaratopico(String titulo, String mensaje) {
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try {
            //String token = "dqptSsLmQbyB4nDsOR9KPx:APA91bFhg8ertkgaSuZC7c415TxgWByZ0s3PjBdvOqZPu9Xh0JVioBOFFgO1bYyU0VJPplnpBejG1eipvEO8hcVlr0OVbXsCEOTopNg_XOURU0DmcTXdlcd3xyB_0BKrQPLDVUcV4tfy";

            json.put("to", "/topics/enviaratodos");
            JSONObject notificacion = new JSONObject();
            notificacion.put("Titulo", titulo);
            notificacion.put("Mensaje", mensaje);

            json.put("data", notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json, null, null) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Authorization", "key=AAAAmoHNVjM:APA91bE5vIo576s8KpbT0fUX7w33ZyF1DmzhSCfzAK_SjB7GXPXDm_I4foyvu6TUJbo2sJnbeEGEMOcelp55j3tnduq027fXtEG9XTnANDBwAp8lJY1R2HnmGD5Ud7ee4J3oXah8YVDu");
                    return header;
                }
            };

            /*Toast.makeText(this, "Notificacion creada", Toast.LENGTH_SHORT).show();*/

            myrequest.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    /*Toast.makeText(UserFuncionalidades.this, "Bienvenido", Toast.LENGTH_SHORT).show();*/
                } else {
                    Toast.makeText(UserFuncionalidades.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_agenda:
                if (!isNetworkAvailable()){
                }else { //si esta conectado a una red internet cambia de activity.
                    Intent intent = new Intent(this, AgendaActivity.class);
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
                String notificationTitle = "Evento próximo";
                String notificationMessage = "Estás a minutos del evento: ";
                llamaratopico(notificationTitle, notificationMessage);

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
                                String notificationMessage = "Estás a minutos del evento: " + noticias.getNombre();
                                llamaratopico(notificationTitle, notificationMessage);
                                /*notificationHelper.showNotification(notificationTitle, notificationMessage);*/
                                //eliminar la noticia 5 minutos despues de suceder


                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Se elimina el registro de la noticia desde la base de datos
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
    public void regresaruserfuncionalidades(View v){
        switch (v.getId()){
            case R.id.regresarUserFuncionalidades:
                Intent i3 = new Intent(UserFuncionalidades.this, LoginActivity.class);
                startActivity(i3);
                finish();
        }
    }
}
