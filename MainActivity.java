package com.murach.russvocab;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends Activity {

    private TextView lbl_count;
    private TextView txt_showWord;
    private TextView txt_showWord2;
    private TextView txt_count;
    private Button btn_showWord;
    private Button btn_showEnglish;
    private RussVocabDB rv;
    private SQLiteDatabase nDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lbl_count = (TextView)findViewById(R.id.lbl_count);
        txt_showWord = (TextView)findViewById(R.id.showWord);
        txt_showWord2 = (TextView)findViewById(R.id.showWord2);
        txt_count= (TextView)findViewById(R.id.txt_count);
        btn_showWord = (Button)findViewById(R.id.shuffle);
//        btn_showEnglish = (Button)findViewById(R.id.showEnglish);
        rv = new RussVocabDB(MainActivity.this, "RV.db", null, 1);
        nDB = rv.getWritableDatabase();
        final Cursor c = nDB.query("NOUNS", null, null, null, null, null, null);

        txt_count.setText(String.valueOf(getIdCount()));
        btn_showWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                while(c.moveToPosition(generateId())){
                    txt_showWord.setText(c.getString(c.getColumnIndex("Noun")));
                    txt_showWord2.setText(c.getString(c.getColumnIndex("English")));

                }
            }
        });

        lbl_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_count.setText(String.valueOf(getIdCount()));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.russvocab_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_add:
                startActivity(new Intent(getApplicationContext(), NewWord.class));
                return true;
            case R.id.menu_words:
                startActivity(new Intent(getApplicationContext(), WordList.class));
                return true;
            case R.id.menu_wordlevel:
                startActivity(new Intent(getApplicationContext(), RussVocabLevel.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getRandomNumber(int min, int max){
        return(new Random().nextInt((max - min) +1) + min);
    }

    public int generateId(){
        int randId = getRandomNumber(0, getIdCount());

        return randId;
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
