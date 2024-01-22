package com.example.selltobuy;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("channel", "channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("chanel");
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel1);
        }
        Notification notification = new NotificationCompat.Builder(context, "channel").
                setSmallIcon(R.drawable.img).
                setContentTitle("delete").setContentText("the product sold").
                setPriority(NotificationCompat.PRIORITY_HIGH).build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                //((Activity) context).requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.FOREGROUND_SERVICE}, 100);
                return;
            }
        }
        notificationManager.notify(1, notification);
    }
}
