package com.example.uni_info.Metodos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.uni_info.BD.dbInfo;
import com.example.uni_info.Entidades.Noticias;

import java.util.ArrayList;

public class Metodos {
    Context c;
    ArrayList<Noticias> listanoticia;

    public Metodos(Context c) {
        this.c = c;
    }

    public boolean insertnoticia(Noticias n){
        boolean id = false;
        try{
            dbInfo dbInfo = new dbInfo(c);
            SQLiteDatabase db = dbInfo.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("titulo", n.getNombre());
            values.put("resumen", n.getResumen());
            values.put("informacion", n.getInformacion());
            values.put("fecha", n.getFecha());
            values.put("hora", n.getHora());

            id = (db.insert("noticias", null, values)>0);

        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Noticias> vernoticias(){
        Cursor noticia;
        dbInfo dbInfo = new dbInfo(c);
        SQLiteDatabase db = dbInfo.getWritableDatabase();
        ArrayList<Noticias> listanoticia = new ArrayList<>();

        noticia = db.rawQuery("select * from noticias", null );

        if(noticia != null && noticia.moveToFirst()){
            do{
                Noticias noticias = new Noticias();
                noticias.setId(noticia.getInt(0));
                noticias.setNombre(noticia.getString(1));
                noticias.setResumen(noticia.getString(2));
                noticias.setInformacion(noticia.getString(3));
                noticias.setFecha(noticia.getString(4));
                noticias.setHora(noticia.getString(5));
                listanoticia.add(noticias);
            }while (noticia.moveToNext());
        }
        return listanoticia;
    }
}
