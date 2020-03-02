package com.murach.russvocab;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RussVocabLevel extends Activity {

    private SQLiteDatabase db;
    private RussVocabDB rv;
    private TextView txtProgressCount;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_russ_vocab_level);

        txtProgressCount = (TextView)findViewById(R.id.txt_progress_count);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        rv = new RussVocabDB(RussVocabLevel.this, "RV.db", null, 1);
        db = rv.getReadableDatabase();
        final Cursor l = db.query("NOUNS", null, null, null, null, null, null);
        progressBar.setProgress(getIdCount());
        txtProgressCount.setText(getIdCount() + " ");


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
