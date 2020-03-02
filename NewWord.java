package com.murach.russvocab;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWord extends Activity {

    private EditText edt_1;
    private EditText edt_2;
    private Button btn_1;
    private RussVocabDB russVocabDB;
    private SQLiteDatabase RVdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        edt_1 = (EditText)findViewById(R.id.edt_newWordR);
        edt_2 = (EditText)findViewById(R.id.edt_newWordE);
        btn_1 = (Button)findViewById(R.id.btn_newWord);

        russVocabDB = new RussVocabDB(NewWord.this, "RV.db", null, 1);
        RVdb = russVocabDB.getWritableDatabase();

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Exists(edt_1.getText().toString())){
                    Toast.makeText(NewWord.this, "Word already exists!", Toast.LENGTH_LONG).show();
                }else{
                    ContentValues cv = new ContentValues();
                    cv.put("Noun", edt_1.getText().toString() );
                    cv.put("English", edt_2.getText().toString());
                    long id = RVdb.insert("NOUNS", null, cv);
                    Toast.makeText(NewWord.this, String.valueOf(id), Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public boolean Exists(String searchItem) {

        String[] columns = { "Noun" };
        String selection = "Noun" + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = RVdb.query("NOUNS", columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }




}
