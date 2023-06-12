package com.example.uni_info;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class Fcm extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.e("token", "mi token es:" + token);
        guardartoken(token);
    }

    private void guardartoken(String token) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        ref.child("id").setValue(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from  = remoteMessage.getFrom();


        if(remoteMessage.getData().size() > 0){

            String titulo = remoteMessage.getData().get("Titulo");
            String mensaje = remoteMessage.getData().get("Mensaje");

            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                mayorqueoreo(titulo, mensaje);
            }
            if(android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.O){
                menorqueoreo(titulo, mensaje);
            }

        }

    }

    private void menorqueoreo(String titulo, String mensaje) {
        String id = "mensaje";

        NotificationManager nm =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id, "Nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);

        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.icono_info_grande_round)
                .setContentText(mensaje)
                .setContentIntent(clicknoti())
                .setContentInfo("nuevo");

        Random random = new Random();
        int idNotify = random.nextInt(8000);

        nm.notify(idNotify, builder.build());

    }

    private void mayorqueoreo(String titulo, String mensaje) {

        String id = "mensaje";

        NotificationManager nm =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id, "Nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);

        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.icono_info_grande_round)
                .setContentText(mensaje)
                .setContentIntent(clicknoti())
                .setContentInfo("nuevo");

        Random random = new Random();
        int idNotify = random.nextInt(8000);

        nm.notify(idNotify, builder.build());

    }
    public PendingIntent clicknoti() {
        Intent nf = new Intent(getApplicationContext(), UserFuncionalidades.class);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int requestCode = 0;
        int flags = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            flags = PendingIntent.FLAG_IMMUTABLE;
        } else {
            flags = PendingIntent.FLAG_UPDATE_CURRENT;
        }

        return PendingIntent.getActivity(this, requestCode, nf, flags);
    }

}
