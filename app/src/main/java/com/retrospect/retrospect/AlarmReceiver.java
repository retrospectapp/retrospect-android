package com.retrospect.retrospect;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

/**
 * Created by kunalpatel on 3/8/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("noti_title");
        int notifyID = intent.getIntExtra("identification_notify", 0);

        String CHANNEL_ID = "my_channel_01";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        Log.d("Notification ID: ", Integer.toString(notifyID));


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("You have a reminder")
                .setContentText(message)
                .setChannelId(CHANNEL_ID);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Retrospect", importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        mNotificationManager.notify(notifyID , builder.build());



    }
}
