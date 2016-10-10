package com.build.shahzaib.balltap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Finished extends Activity {

    public Button restart;
    public Button ResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_finished);


        final int finalScore = GameWindow.getScore();
        TextView fScore = (TextView) findViewById(R.id.textView4);
        fScore.setText(String.valueOf("Score: " + finalScore));



        ResetButton = ((Button) findViewById(R.id.button2));
        ResetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restartIntent = new Intent(Finished.this, MainActivity.class);
                GameWindow.score = 0;
                finish();
                startActivity(restartIntent);


            }

        });

        //getting preferences
        SharedPreferences pref = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int hScore = pref.getInt("finalScore", 0); //0 is the default value


        ////////SET/DISPLAY HIGHSCORE////////
        if(finalScore > hScore){
            //setting preferences
            SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("finalScore", finalScore);
            editor.commit();
            TextView highScore = (TextView) findViewById(R.id.textView7);
            highScore.setText(String.valueOf("Highscore: " + finalScore));
        }
        else{
            TextView highScore = (TextView) findViewById(R.id.textView7);
            highScore.setText(String.valueOf("Highscore: " + hScore));
        }



    }





    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}

