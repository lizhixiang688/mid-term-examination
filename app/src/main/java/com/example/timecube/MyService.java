package com.example.timecube;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private NotificationCompat.Builder builder;
    private NotificationChannel channel;
    private NotificationManager manager;
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter=new IntentFilter("123");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                builder=new NotificationCompat.Builder(getApplicationContext(),"1")
                        .setSmallIcon(R.drawable.icon_notifi)
                        .setContentTitle(intent.getStringExtra("message"))
                        .setVibrate(new long[]{0,1000,1000,1000})

                        .setPriority(NotificationCompat.PRIORITY_HIGH);
                manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
                    channel = new NotificationChannel("1", "MusicChannel", NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(channel);
                }
               manager.notify(1,builder.build());
            }
        },intentFilter);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}