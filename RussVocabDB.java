package com.murach.russvocab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RussVocabDB extends SQLiteOpenHelper {



    public RussVocabDB( Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table NOUNS(_id integer primary key autoincrement," +
                    " English text, Noun text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase datab, int oldVersion, int newVersion) {
        datab.execSQL("DROP TABLE IF EXISTS " + "NOUNS");
        onCreate(datab);
    }


}
