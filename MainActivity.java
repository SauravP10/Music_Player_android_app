package com.example.musicplayer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
/*import android.support.*;*/
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Button fwd, rev, pause, play;
    private ImageView iv;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double endTime = 0;
    private Handler myHandler = new Handler();
    private int fedTime = 5000;
    private int revTime = 5000;
    private SeekBar seekBar;
    private TextView tx1, tx2, tx3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fwd = (Button) findViewById(R.id.button_fwd);
        rev = (Button) findViewById(R.id.button_rev);
        pause = (Button) findViewById(R.id.button_pause);
        play = (Button) findViewById(R.id.button_play);

        iv = (ImageView) findViewById(R.id.imageview);

        tx1 = (TextView) findViewById(R.id.textview2);
        tx2 = (TextView) findViewById(R.id.textview3);
        tx3 = (TextView) findViewById(R.id.textview4);
        tx3.setText("uncharted.mp3");

        mediaPlayer = MediaPlayer.create(this, R.raw.uncharted);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setClickable(false);
        pause.setEnabled(false);

        final Runnable UpdateSongTime = new Runnable() {
            @Override
            public void run() {
                startTime =mediaPlayer.getCurrentPosition();
                tx1.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
                seekBar.setProgress((int)startTime);
                myHandler.postDelayed(this, 100);
            }
        };

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "PLaying", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                endTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                tx2.setText(String.format("%d min, %d sec",TimeUnit.MILLISECONDS.toMinutes((long) endTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) endTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) endTime))));

                tx1.setText(String.format("%d min, %d sec",TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));

                seekBar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime, 100);
                pause.setEnabled(true);
                play.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pausing",Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                pause.setEnabled(false);
                play.setEnabled(true);
            }
        });

        fwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int)startTime;

                if((temp+fedTime)<=endTime){
                    startTime = startTime +fedTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),">> 5 secs", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No forward", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp =(int)startTime;

                if((temp-revTime)>0){
                    startTime =startTime - revTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "<< 5 secs", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"No backword", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}