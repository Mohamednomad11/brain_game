package com.nomad.mybrainmemory.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.google.firebase.Timestamp;
import com.google.type.DateTime;
import com.nomad.mybrainmemory.model.ScoreModel;
import com.nomad.mybrainmemory.util.FireStoreUtils;
import com.nomad.mybrainmemory.util.StaticConstants;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ScoreDB";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_SCORES = "scores";
    private static final String COLUMN_ID = "id";

    private static final String COLUMN_F_ID = "fid";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";

    private static final String COLUMN_TIME = "time";

    private static final String COLUMN_GAME = "game";

    private static final String COLUMN_TIMESTAMP = "timestamp";

    public ScoreDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SCORES +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_F_ID + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SCORE + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_GAME + " TEXT," +
                COLUMN_TIMESTAMP + " TEXT" +
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void addScore(String name, String score, String time, String game) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_TIME,time);
        values.put(COLUMN_GAME,game);

        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    public long addScore(String name, String score, String time, String game, Timestamp timestamp) {
        long rowId;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_TIME,time);
        values.put(COLUMN_GAME,game);
        String timeStampText = FireStoreUtils.timestampToString(timestamp);
        values.put(COLUMN_TIMESTAMP, timeStampText);
        rowId = db.insert(TABLE_SCORES, null, values);
        db.close();
        return rowId;
    }

    public long addScore(String uid, String name, String score, String time, String game, Timestamp timestamp) {
        long rowId;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_F_ID,uid);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_TIME,time);
        values.put(COLUMN_GAME,game);
        String timeStampText = FireStoreUtils.timestampToString(timestamp);
        values.put(COLUMN_TIMESTAMP, timeStampText);
        rowId = db.insert(TABLE_SCORES, null, values);
        db.close();
        return rowId;
    }

    public  ScoreModel getScore(long rowID){
        ScoreModel scoreModel = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SCORES + " WHERE rowid = " + rowID ,null);
        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int fIdIndex = cursor.getColumnIndex(COLUMN_F_ID);
        int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
        int scoreIndex = cursor.getColumnIndex(COLUMN_SCORE);
        int timeIndex = cursor.getColumnIndex(COLUMN_TIME);
        int gameIndex = cursor.getColumnIndex(COLUMN_GAME);
        int timeStampIndex = cursor.getColumnIndex(COLUMN_TIMESTAMP);


        while (cursor.moveToNext()) {
            if (idIndex >= 0 && nameIndex >= 0 && scoreIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String uid = cursor.getString(fIdIndex);
                String score = cursor.getString(scoreIndex);
                String time = cursor.getString(timeIndex);
                String game = cursor.getString(gameIndex);
                String timeStampText = cursor.getString(timeStampIndex);
                Timestamp timeStamp = FireStoreUtils.stringToTimestamp(timeStampText);
                scoreModel = new ScoreModel(id, Integer.parseInt(score), name, time, game,timeStamp,uid);
            }
        }

        return scoreModel;
    }

    public ArrayList<ScoreModel> viewAllScores(){
        ArrayList<ScoreModel> scores = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SCORES, null);

        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
        int fIdIndex = cursor.getColumnIndex(COLUMN_F_ID);
        int scoreIndex = cursor.getColumnIndex(COLUMN_SCORE);
        int timeIndex = cursor.getColumnIndex(COLUMN_TIME);
        int gameIndex = cursor.getColumnIndex(COLUMN_GAME);
        int timeStampIndex = cursor.getColumnIndex(COLUMN_TIMESTAMP);


        while (cursor.moveToNext()) {
            if (idIndex >= 0 && nameIndex >= 0 && scoreIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String uid = cursor.getString(fIdIndex);
                String score = cursor.getString(scoreIndex);
                String time = cursor.getString(timeIndex);
                String game = cursor.getString(gameIndex);
                String timeStampText = cursor.getString(timeStampIndex);
                Timestamp timeStamp = FireStoreUtils.stringToTimestamp(timeStampText);
                ScoreModel scoreModel = new ScoreModel(id, Integer.parseInt(score), name,time,game,timeStamp,uid);
                scores.add(scoreModel);
            }
        }

        cursor.close();
        db.close();

        // Sort the scores in descending order based on the score value
        // If the scores are the same, compare the IDs in descending order
        Collections.sort(scores, new Comparator<ScoreModel>() {
            @Override
            public int compare(ScoreModel score1, ScoreModel score2) {
                int scoreComparison = Integer.compare(score2.getScore(), score1.getScore());
                if (scoreComparison == 0) {
                    return Integer.compare(score2.getId(), score1.getId());
                }
                return scoreComparison;
            }
        });
        Log.e("Score"," "+scores.size());
        return scores;
    }

}
