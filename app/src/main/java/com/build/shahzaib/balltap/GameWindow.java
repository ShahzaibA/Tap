package com.build.shahzaib.balltap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameWindow extends Activity implements View.OnClickListener {
    static int score;
    private Timer t;
    private int TimeCounter = 29;
    private boolean canMove = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        ////Remove title screen for activty.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_window);
        moveButton();
        endonTimeOver();
        //endonScreenTap();


    }







    public void endonTimeOver(){
        ////Activity timer for 60 seconds.

        final TextView timer = (TextView) findViewById(R.id.seconds);

        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            ////Set string to timer.
            @Override
            public void run() {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {
                    public void run() {
                        timer.setText(String.valueOf(TimeCounter)); // you can set it to a textView to show it to the user to see the time passing while he is writing.
                        TimeCounter = TimeCounter - 1;
                    }
                });

            }
        }, 1000, 1000); // 1000 means start from 1 sec, and the second 1000 is do the loop each 1 sec.

        new Timer().schedule(new TimerTask(){
            public void run() {
                GameWindow.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(GameWindow.this, Finished.class));
                    }
                });
            }
        }, 30000);




    }


/*
    public void endonScreenTap(){
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.gameLayout);
        layout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameWindow.this, Finished.class));
            }

        });
    }


*/





    ////Move button.
    private void moveButton()
    {
        if(!canMove){ return; }

        runOnUiThread(
                new Runnable()
                {
                    @Override
                    public void run()
                    {

                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int width = size.x;
                        int height = size.y;

                        Button button = (Button)findViewById(R.id.button);
                        Random r = new Random();

                        int startX = width/2;
                        int startY = height/2;

                        if(score==0){
                            button.setX(startX);
                            button.setY(startY);
                        }

                        else {

                            int x = r.nextInt(width - 210);
                            int y = r.nextInt(height - 200);

                            button.setX(x);
                            button.setY(y);
                        }
                    }
                }
        );

    }




    ////Display score
    public void displayScore(int score) {
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText(String.valueOf(score));
    }








    @Override
    public void onClick(View v) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonsound);
        mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }

        });
        mp.start();

        score = score + 1;
        displayScore(score);
        switch (v.getId()) {
            case (R.id.button): {
               moveButton();
            }
        }

    }



    public static int getScore(){
        return score;
    }
}







