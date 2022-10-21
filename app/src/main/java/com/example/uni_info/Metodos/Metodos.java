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

    public boolean editar(int id,Noticias noticias) {
        boolean correcto = false;

        dbInfo dbHelper = new dbInfo(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL(" UPDATE noticias SET titulo = '"+noticias.getNombre()+"',  resumen = '"+noticias.getResumen()+"', fecha = '"+noticias.getFecha()+"', hora = '"+noticias.getHora()+"' WHERE id= '"+id+"' ");
            correcto = true;

        } catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            {
                db.close();
            }
        }
        return correcto;
    }
    public Noticias ver(int id) {

        dbInfo dbHelper = new dbInfo(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Noticias noticias = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM noticias WHERE id = " + id + " LIMIT 1 ", null);

        if (cursorContactos.moveToFirst()) {
            noticias = new Noticias();
            noticias.setId(cursorContactos.getInt(0));
            noticias.setNombre(cursorContactos.getString(1));
            noticias.setResumen(cursorContactos.getString(2));
            noticias.setFecha(cursorContactos.getString(4));
            noticias.setHora(cursorContactos.getString(4));
        }
        cursorContactos.close();
        return  noticias;

    }
    public boolean eliminarNoticia(int id) {

        boolean correcto = false;

        dbInfo dbHelper = new dbInfo(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL(" DELETE FROM noticias WHERE id ='"+id+"'");
            correcto = true;

        } catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            {
                db.close();
            }
        }
        return correcto;
    }
}
