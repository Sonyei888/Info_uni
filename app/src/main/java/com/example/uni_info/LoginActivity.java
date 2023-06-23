package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni_info.Metodos.Metodos;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Metodos metodos;
    EditText usuario;
    EditText cont;
    private TextView loginnoconnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginnoconnection = findViewById(R.id.login_no_connection);
        usuario = findViewById(R.id.edit_user_name_login);
        cont = findViewById(R.id.edit_password_login);
        findViewById(R.id.btningresar).setOnClickListener(this);
        findViewById(R.id.btnregistrar).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            //boton ingresar
            case R.id.btningresar:
                if (!isNetworkAvailable()){
                    Comprobar();
                }else { //si esta conectado a una red internet cambia de activity.
                    String user = usuario.getText().toString(); //se trae la informacion de usuario y se convierte a string
                    String contraseña = cont.getText().toString();//se trae la informacion de contraseña y se convierte a string
                    String usuarioadmin = "admin@uniinfo.com";
                    String contraseñaadmin = "1234";
                    String usuario = "user@uniinfo.com";
                    String contraseñauser = "1234";
                    if(Objects.equals(user, usuarioadmin) && Objects.equals(contraseña, contraseñaadmin)){ //se compara la informacion
                        // si es correcta a traves de un intent se envia al administrador a otra vista
                        Intent intent = new Intent(this, AdminVerNoticias.class);
                        startActivity(intent);
                        finish();
                    }else if (user.isEmpty() || contraseña.isEmpty()){
                        validacion();
                    }else if(Objects.equals(user, usuario) && Objects.equals(contraseña, contraseñauser)){
                        Intent intent = new Intent(this, UserFuncionalidades.class);
                        startActivity(intent);
                        finish();

                    }else {
                        //si es falsa se muestra un toast
                        Toast.makeText(this, "Contraseña y/o Usuario incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                //boton Registrar
            case R.id.btnregistrar:
                if (!isNetworkAvailable()){
                    Comprobar();
                }else {
                    Intent intent1 = new Intent(this, RegistrarActivity.class);
                    startActivity(intent1);
                    finish();
                }

                break;
        }
    }
    /*Metodo para validar que los campos son requeridos*/
    private void validacion(){
        String usu = usuario.getText().toString();
        String contraseña = cont.getText().toString();

        if(usu.isEmpty()){
            usuario.setError("Requerido");
        }
        if(contraseña.isEmpty()){
            cont.setError("Requerido");
        }
    }
    private void Comprobar(){
        if (!isNetworkAvailable()) {
            loginnoconnection.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loginnoconnection.setVisibility(View.GONE);
                }
            }, 20000); // 20000 milisegundos = 20 segundos
        }else {
            loginnoconnection.setVisibility(View.GONE);
        }
    }

    //metodo para comprobar conexion a internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}