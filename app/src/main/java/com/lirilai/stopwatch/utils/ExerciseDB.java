package com.lirilai.stopwatch.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lirilai on 27.06.2017.
 */

public class ExerciseDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "stopwatch";
    private static final int DB_VERSION = 1;

    public ExerciseDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE EXERCISE (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, " + "RING INTEGER");

        ContentValues exerciseValues = new ContentValues();
        exerciseValues.put("NAME", "Default");
        exerciseValues.put("RING", 15);
        db.insert("EXERCISE", null, exerciseValues);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void writeExercise (SQLiteDatabase db, String name, int gongTime) {

        ContentValues exerciseValues = new ContentValues();
        exerciseValues.put("NAME", name);
        exerciseValues.put("RING", gongTime);
        db.insert("EXERCISE", null, exerciseValues);

    }

    public void deleteExercise (SQLiteDatabase db, int id) {

        ContentValues exerciseValues = new ContentValues();
        db.delete("EXERCISE", "_id = " + id, null);

    }


}
