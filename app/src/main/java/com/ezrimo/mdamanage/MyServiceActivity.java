package com.ezrimo.mdamanage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyServiceActivity extends Service {
    MediaPlayer player;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(player==null)
            player = MediaPlayer.create(getApplicationContext(), R.raw.beep);
        player.setLooping(true);
        player.start();

        final String CHANNEL_ID = "Metronome Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentText("Metronome Running")
                .setContentTitle("MDA Manage")
                .setSmallIcon(R.drawable.mdaman);
        startForeground(1001, notification.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
