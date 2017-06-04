package com.lirilai.stopwatch;


import android.media.MediaPlayer;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;



public class StopwatchActivity extends AppCompatActivity implements View.OnClickListener{

    private Chronometer chronometer;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private TextView seekBarText;
    private TextView circleCounter;

    private boolean stopChronometer = true;

    private Integer seekBarProgress = 1000;
    private long pauseChronometer;

    private long restoreChronometer;

    private int counterForCircles = 0;

    private MediaPlayer mediaPlayer;

    final static String LOG_TAG = "foo";


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counterChronometer", counterForCircles);
        outState.putLong("restoreChronometer", chronometer.getBase());
        outState.putInt("seekBarProgress", seekBarProgress);
        Log.e(LOG_TAG, "onCreate");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stopwatch);

        Log.e(LOG_TAG, "onCreate");

        if (savedInstanceState != null) {
            counterForCircles = savedInstanceState.getInt("counterChronometer");
            restoreChronometer = savedInstanceState.getLong("restoreChronometer");
            seekBarProgress = savedInstanceState.getInt("seekBarProgress");
//            restoreChronometer = SystemClock.elapsedRealtime() - restoreChronometer;
        }


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarText = (TextView) findViewById(R.id.seekbar_text);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        circleCounter = (TextView) findViewById(R.id.circle_counter);

        seekBar.setMax(120);
        progressBar.setVisibility(View.INVISIBLE);

        circleCounter.setOnClickListener(this);
        chronometer.setOnClickListener(this);

        if (restoreChronometer == 0) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            circleCounter.setText(String.valueOf(counterForCircles));
        } else {
            chronometer.setBase(restoreChronometer);
            progressBar.setVisibility(View.VISIBLE);
            circleCounter.setText(String.valueOf(counterForCircles));
//            seekBarText.setText(String.valueOf(seekBarProgress));
            chronometer.start();
            restoreChronometer = 0;
        }


        chronometer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (restoreChronometer == 0) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                } else {
                    chronometer.setBase(restoreChronometer);
                    chronometer.start();
                    restoreChronometer = 0;
                }
                counterForCircles ++;
                circleCounter.setText(String.valueOf(counterForCircles));
                chronometer.start();
                progressBar.setVisibility(View.VISIBLE);
                stopChronometer = false;
                return true;
            }
        });



        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                long elapsedMillis = SystemClock.elapsedRealtime()
                        - chronometer.getBase();

                if (seekBarProgress*1000 < elapsedMillis && elapsedMillis < seekBarProgress*1000 + 1000) {
                    mediaPlayer = MediaPlayer.create(StopwatchActivity.this, R.raw.gong_music);
                    mediaPlayer.start();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int stepSize = 5;
                progress = (Math.round(progress/stepSize))*stepSize;
                seekBar.setProgress(progress);

                if(fromUser) {
                    seekBarText.setText(String.valueOf(progress));}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarText.setVisibility(View.VISIBLE);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarProgress = seekBar.getProgress();

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        chronometer.stop();
        pauseChronometer = SystemClock.elapsedRealtime()
                - chronometer.getBase();
        progressBar.setVisibility(View.INVISIBLE);
        stopChronometer = true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.circle_counter:
                counterForCircles = 0;
                circleCounter.setText(String.valueOf(counterForCircles));
                break;
            case R.id.chronometer:
                if (!stopChronometer) {
                    chronometer.stop();
                    pauseChronometer = SystemClock.elapsedRealtime()
                            - chronometer.getBase();
                    progressBar.setVisibility(View.INVISIBLE);
                    stopChronometer = true;
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseChronometer);
                    chronometer.start();
                    stopChronometer = false;

                } break;
        }
    }
}
