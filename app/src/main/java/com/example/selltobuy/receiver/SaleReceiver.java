package com.example.selltobuy.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.selltobuy.helpers.FirebaseController;
import com.example.selltobuy.R;

/**
 * The type Sale receiver.
 */
public class SaleReceiver extends BroadcastReceiver {
    /**
     * The Firebase controller.
     */
    FirebaseController firebaseController;
    /**
     * The Id.
     */
    String id;
    /**
     * The Intent 2.
     */
    Intent intent2;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "saleReceiver", Toast.LENGTH_SHORT).show();


        firebaseController=new FirebaseController(context);
         if(intent.getExtras()!=null)
         {
             if(!intent.getExtras().getString("productId").equals(""))
             {
                 id = intent.getExtras().getString("productId");
                 firebaseController.removeProduct(id,"products");

                 sendNotification(context);
             }
             if(!intent.getExtras().getString("techProductId").equals(""))
             {
                 id=intent.getExtras().getString("techProductId");
                 firebaseController.removeProduct(id,"techProducts");

                 sendNotification(context);
             }
         }
    }

    private void sendNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("channel", "channel 2", NotificationManager.IMPORTANCE_HIGH);
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
             //   ((Activity) context).requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.FOREGROUND_SERVICE}, 100);
                return;
            }
        }
        notificationManager.notify(1, notification);
    }


}

