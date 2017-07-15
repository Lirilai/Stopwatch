package com.lirilai.stopwatch;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lirilai.stopwatch.utils.ExerciseDB;
import com.lirilai.stopwatch.utils.InputFilterMinMax;

/**
 * Created by Lirilai on 27.06.2017.
 */

public class AddExerciseActivity extends Activity {

    private EditText editName;
    private EditText editGongTime;
    private Button addButton;

    private String name;
    private int gongTime;

    private SQLiteOpenHelper exerciseDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exerciseDB= new ExerciseDB(this);

        editName = (EditText) findViewById(R.id.edit_name);
        editGongTime = (EditText) findViewById(R.id.edit_gong_time);
        addButton = (Button) findViewById(R.id.add_button);

        editGongTime.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "120")});

        addButton.setEnabled(false);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != ""){
                    name = s.toString();
                } else {
                    editName.setError("Enter exercise name");
                }
            }
        });

        editGongTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString() != ""){
                    gongTime = Integer.parseInt(s.toString());
                } else {
                    editName.setError("Enter time for gong");
                }

            }
        });

        if (name != "" && gongTime != 0){
            addButton.setEnabled(true);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = exerciseDB.getWritableDatabase();

                ContentValues exerciseValues = new ContentValues();
                exerciseValues.put("NAME", name);
                exerciseValues.put("RING", gongTime);
                db.insert("EXERCISE", null, exerciseValues);

                Intent intent = new Intent(AddExerciseActivity.this, StopwatchActivity.class);
                startActivity(intent);

            }
        });


    }
}
