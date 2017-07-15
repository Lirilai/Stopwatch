package com.lirilai.stopwatch.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Lirilai on 28.06.2017.
 */

public class ExersiceList {

    private List<String> exersiceList;
//    ExerciseDB exerciseDB = new ExerciseDB();

    public ExersiceList(List<String> exerciseList) {
        this.exersiceList = exerciseList;
        initList();
    }

    private void initList() {
//
//        ExerciseDB exerciseDB = new ExerciseDB();
//
//        SQLiteDatabase db = exerciseDB.getReadableDatabase();

    }
}
