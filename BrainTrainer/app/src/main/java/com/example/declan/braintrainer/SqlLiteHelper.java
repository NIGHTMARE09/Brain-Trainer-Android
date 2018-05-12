package com.example.declan.braintrainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;

public class SqlLiteHelper extends SQLiteOpenHelper{

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME= "brainTrainer";
        private static final String DATABASE_TABLE = "scores";

    public SqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE scores (id INTEGER PRIMARY KEY AUTOINCREMENT, score INTEGER, captured VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS scores");
        this.onCreate(db);
    }

    //get all scores
    public List<Scores> getScores() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Scores> scores = new LinkedList<Scores>();

        Cursor c = db.rawQuery("SELECT * FROM scores", null);

        while(c.moveToNext()) {
            scores.add(new Scores(c.getInt(1), c.getString(2)));
            Log.i("Score: ", Integer.toString(c.getInt(1)));
            Log.i("Captured: ", c.getString(2));
        }

        return scores;
    }

    public void insertScore(Scores score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("score", score.getScore());
        cv.put("captured", score.getCaptured());

        db.insert("scores", null, cv);
        db.close();
    }

    public long getScoreCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, DATABASE_TABLE);
    }

    public int getTotalScore() {
        int totalScore = 0;

        List<Scores> scores = getScores();

        for(Scores s : scores) {
            totalScore = totalScore + s.getScore();
        }

        return totalScore;
    }

    public int getAverageScore() {
        long count = getScoreCount();

        //cast count to int from long
        int avgScore = getTotalScore() / (int)count;

        return avgScore;
    }


}
