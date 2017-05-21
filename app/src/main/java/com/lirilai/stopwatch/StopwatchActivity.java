package com.lirilai.stopwatch;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class StopwatchActivity extends Activity {

    private Chronometer mChronometer;
    private ProgressBar mProgressBar;
    private SeekBar mSeekBar;
    private TextView seekBarText;

    private boolean stopChronometer = true;

    private Integer seekBarProgress = 1000;
    private long pauseChronometer;

    private MediaPlayer mp;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stopwatch);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setMax(120);

        seekBarText = (TextView) findViewById(R.id.seekbar_text);

        mChronometer = (Chronometer) findViewById(R.id.chronometer);


        mChronometer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();

                mProgressBar.setVisibility(View.VISIBLE);return true;
            }
        });

        mChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stopChronometer) {
                    mChronometer.stop();
                    pauseChronometer = SystemClock.elapsedRealtime()
                            - mChronometer.getBase();
                    mProgressBar.setVisibility(View.INVISIBLE);
                    stopChronometer = true;
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mChronometer.setBase(SystemClock.elapsedRealtime() - pauseChronometer);
                    mChronometer.start();
                    stopChronometer = false;

                }

            }
        });

        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                long elapsedMillis = SystemClock.elapsedRealtime()
                            - mChronometer.getBase();

                if (seekBarProgress*1000 < elapsedMillis && elapsedMillis < seekBarProgress*1000 + 1000) {
                        mp = MediaPlayer.create(StopwatchActivity.this, R.raw.gong_music);
                        mp.start();
                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                seekBarProgress = mSeekBar.getProgress();

            }
        });



    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
