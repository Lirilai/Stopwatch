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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdsmdg.harjot.crollerTest.Croller;


public class StopwatchActivity extends AppCompatActivity implements View.OnClickListener{

    private Croller seekBar;
    private TextView progressText;
    private TextView timeView;
    private TextView circleText;
    private Button startStopButton;
    private Button resetButton;

    private int circleCounter = 0;
    private int seekBarProgress = 0;
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    private MediaPlayer mediaPlayer;

    final static String LOG_TAG = "foo";


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putInt("seekBarProgress", seekBarProgress);
        outState.putInt("circleCounter", circleCounter);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
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
            circleCounter = savedInstanceState.getInt("circleCounter");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            if (wasRunning) {
                running = true;
            }

        }


        timeView = (TextView) findViewById(R.id.timeView);

        circleText = (TextView) findViewById(R.id.circle_counter);
        circleText.setText(String.valueOf(circleCounter) + " circle");

        progressText = (TextView) findViewById(R.id.progressText);

        startStopButton = (Button) findViewById(R.id.start_stop_button);
        startStopButton.setText("Start");
        startStopButton.setOnClickListener(this);

        resetButton = (Button) findViewById(R.id.refresh_button);
        resetButton.setEnabled(false);
        resetButton.setText("Reset");
        resetButton.setOnClickListener(this);


        seekBar = (Croller)findViewById(R.id.croller);
        seekBar.setMax(120);

       seekBar.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
           @Override
           public void onProgressChanged(int progress) {

               int stepSize = 5;
               progress = (Math.round(progress/stepSize))*stepSize;
               seekBarProgress = progress;
               progressText.setText(String.valueOf(progress) + " sec");

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

            case R.id.start_stop_button:
                if (running) {
                    onClickStop();

                } else {
                    onClickStart();

                } break;

            case R.id.refresh_button:
                onClickReset();
                break;

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
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;

                    if (seekBar.getProgress() > 5 && seconds == seekBarProgress) {
                        mediaPlayer = MediaPlayer.create(StopwatchActivity.this, R.raw.gong_music);
                        mediaPlayer.start();
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void onClickStart() {
        running = true;
        startStopButton.setText("Stop");
        resetButton.setEnabled(false);
        timeView.setTextColor(ContextCompat.getColor(timeView.getContext(), R.color.colorAccent));
    }
    public void onClickStop() {
        running = false;
        startStopButton.setText("Start");
        timeView.setTextColor(ContextCompat.getColor(timeView.getContext(), R.color.stopwatch));
        resetButton.setEnabled(true);

    }

    public void onClickReset() {
        seconds = 0;
        resetButton.setEnabled(false);
        circleCounter++;
        circleText.setText(String.valueOf(circleCounter) + " circles");

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
