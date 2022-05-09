package com.ezrimo.mdamanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class Metronome extends AppCompatActivity {
    Button metronome;
    MediaPlayer player;
    int counter=0;
    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        metronome = findViewById(R.id.metronome);

        metronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter%2==0){
                    if(player==null)
                        player = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                    player.setLooping(true);
                    player.start();
                    counter++;
                }
                else if(counter%2 == 1){
                    player.pause();
                    counter=0;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.loginUp){
            Intent go = new Intent(getApplicationContext(), SignInUpActivity.class);
            startActivity(go);
            finish();
        }
        return true;
    }

}