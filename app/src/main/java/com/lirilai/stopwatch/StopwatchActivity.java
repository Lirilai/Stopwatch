package com.lirilai.stopwatch;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dgmltn.multiseekbar.ArcSeekBar;
import com.dgmltn.multiseekbar.internal.AbsMultiSeekBar;


public class StopwatchActivity extends AppCompatActivity implements View.OnClickListener{

    private ArcSeekBar seekBar;
    private TextView timeView;
    private Button startStopButton;


    private Integer seekBarProgress = 1000;

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    private MediaPlayer mediaPlayer;

    final static String LOG_TAG = "foo";


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
        outState.putInt("seekBarProgress", seekBarProgress);
        Log.e(LOG_TAG, "onCreate");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");

        Log.e(LOG_TAG, "onCreate");

        if (savedInstanceState != null) {
            seekBarProgress = savedInstanceState.getInt("seekBarProgress");
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            if (wasRunning) {
                running = true;
            }

        }


        timeView = (TextView) findViewById(R.id.chronometer);

        startStopButton = (Button) findViewById(R.id.button1);
        startStopButton.setText("Start");
        startStopButton.setOnClickListener(this);


        seekBar = (ArcSeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(120);

        seekBar.setOnSliderChangeListener(new AbsMultiSeekBar.OnSliderChangeListener() {
            @Override
            public void onStartTrackingTouch(AbsMultiSeekBar slider) {

            }

            @Override
            public void onStopTrackingTouch(AbsMultiSeekBar slider) {



            }
        });

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });



        runTime();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button1:
                if (running) {
                    onClickStop();

                } else {
                    onClickStart();

                } break;
        }
    }

    public void runTime () {

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d",
                        hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    timeView.setTextColor(ContextCompat.getColor(timeView.getContext(), R.color.colorAccent));
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void onClickStart() {
        startStopButton.setText("Stop");
        running = true;
    }
    public void onClickStop() {
        startStopButton.setText("Start");
        running = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {

            case R.id.add_exercise_item:

                intent = new Intent(this, AddExerciseActivity.class);
                startActivity(intent);

            case R.id.choose_exercise_item:
                intent = new Intent(this, ChooseExerciseActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
