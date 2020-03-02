package com.murach.russvocab;

import android.R.layout;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.layout.simple_expandable_list_item_1;
import static android.R.layout.simple_list_item_1;

public class WordList extends Activity {


    private SQLiteDatabase db;
    private RussVocabDB rv;
    private ListView userList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        userList = (ListView)findViewById(R.id.user_list);

        listItem =  new ArrayList<>();
        rv = new RussVocabDB(WordList.this, "RV.db", null, 1);
        db = rv.getReadableDatabase();
        final Cursor l = db.query("NOUNS", null, null, null, null, null, null);

        for(int x = 0; x < getIdCount(); x++){
            String word;
            l.moveToPosition(x);
            word = l.getString(l.getColumnIndex("Noun"));
            listItem.add(word);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, simple_list_item_1, listItem);
        userList.setAdapter(adapter);


    }

    public int getIdCount(){
        int idCount = 0;

        String sql = "SELECT COUNT(*) FROM NOUNS ";
        Cursor d = rv.getReadableDatabase().rawQuery(sql, null);
        if(d.getCount() > 0){
            d.moveToFirst();
            idCount = d.getInt(0);
        }
        d.close();
        return idCount;
    }



}
