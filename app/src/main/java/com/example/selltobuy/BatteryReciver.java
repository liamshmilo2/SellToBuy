package com.example.selltobuy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryReciver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        int battery = intent.getIntExtra("level" , 0);
        if(battery<5)
        {
            //Toast.makeText(context, "low battery, charge your phone", Toast.LENGTH_SHORT).show();
        }
    }
}
