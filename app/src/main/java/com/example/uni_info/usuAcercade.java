package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class usuAcercade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usu_acercade);
    }
    public void regresarinformacion(View v){
        switch (v.getId()){
            case R.id.regresar_informacion:
                Toast.makeText(usuAcercade.this, "Noticias", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(usuAcercade.this, UserFuncionalidades.class);
                startActivity(i3);
                finish();
        }
    }
}