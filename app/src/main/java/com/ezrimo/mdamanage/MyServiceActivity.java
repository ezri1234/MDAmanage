package com.ezrimo.mdamanage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyServiceActivity extends Service {
    MediaPlayer player;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = MediaPlayer.create(getApplicationContext(), R.raw.beep);
        player.setLooping(true);
        player.start();

        final String CHANNEL_ID = "Metronome Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
        );
        //creating the Notification
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentText("Metronome Is Running")
                .setContentTitle("MDA Manage")
                .setSmallIcon(R.drawable.mdaman)
                .build();
        startForeground(1001, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        //deleting the Media player
        if(player.isPlaying()){
            player.stop();
            player.release();
            player = null;
        }
        super.onDestroy();
    }
}
