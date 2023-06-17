package com.example.uni_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AgendaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

    }
    public void regresaragenda(View v){
        switch (v.getId()){
            case R.id.regresaragenda:
                Intent i3 = new Intent(AgendaActivity.this, UserFuncionalidades.class);
                startActivity(i3);
                finish();
        }
    }
}