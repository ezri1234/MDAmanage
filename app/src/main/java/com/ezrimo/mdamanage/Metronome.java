package com.ezrimo.mdamanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.protobuf.StringValue;

import java.util.Timer;
import java.util.TimerTask;

public class Metronome extends AppCompatActivity {
    Button metronome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        metronome = findViewById(R.id.metronome);

        Intent serviceIntent = new Intent(getApplicationContext(), MyServiceActivity.class);

        metronome.setOnClickListener(new View.OnClickListener() {
            /**
             * if the service is not running he start the service, if its running it kills it
             * @param v the button in type View
             */
            @Override
            public void onClick(View v) {
                /*if(counter%2==0){
                    if(player==null)
                        player = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                    player.setLooping(true);
                    player.start();
                    counter++;
                }
                else if(counter%2 == 1){
                    player.pause();
                    counter=0;
                }*/
                /*SharedPreferences sr = getApplicationContext().getSharedPreferences("isRunning", Context.MODE_PRIVATE);
                boolean isPlaying = sr.getBoolean("isPlaying", false);
                Log.d("TAG", String.valueOf(isPlaying));
                if(!isPlaying){
                    startService(serviceIntent);
                    sp = getApplicationContext().getSharedPreferences("isRunning", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isPlaying", true);
                    editor.commit();

                } else{
                    Log.d("TAG", "stopping");
                    stopService(serviceIntent);
                    sp = getApplicationContext().getSharedPreferences("isRunning", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isPlaying", false);
                    editor.commit();
                }*/
                if(!isMyServiceRunning(MyServiceActivity.class)){
                    startService(serviceIntent);
                    Log.d("TAG", "no service");
                } else if(isMyServiceRunning(MyServiceActivity.class))
                    stopService(serviceIntent);
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

    /**
     * checks if service is running
     * @param serviceClass type of the service you're looking for
     * @return if service is running
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}