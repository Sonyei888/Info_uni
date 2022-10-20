package com.example.uni_info.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbInfo extends SQLiteOpenHelper {
    public dbInfo(@Nullable Context context) {
        super(context, "info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table noticias(" +
                "id INTEGER primary key autoincrement, " +
                "titulo TEXT," +
                "resumen TEXT," +
                "informacion TEXT," +
                "fecha TEXT," +
                "hora Text)");

        sqLiteDatabase.execSQL("create table admin(" +
                "id INTEGER primary key autoincrement, " +
                "usuario TEXT," +
                "contraseña Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists noticias");
        sqLiteDatabase.execSQL("drop table if exists admin");
    }
}
