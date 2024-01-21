package com.example.selltobuy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SaleReceiver extends BroadcastReceiver {
    FirebaseController firebaseController;
    String id;
    Intent intent2;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "saleReceiver", Toast.LENGTH_SHORT).show();


        firebaseController=new FirebaseController(context);
         if(!intent.getExtras().get("productId").equals(null))
         {
             if(!intent.getExtras().get("productId").equals(""))
             {
                 id = intent.getStringExtra("productId");
                 firebaseController.removeProduct(id);
             }
         }
         else if(intent.getExtras().get("techProductId")!=null)
         {
             if(!intent.getExtras().get("techProductId").equals(""))
             {
                 id=intent.getStringExtra("techProductId");
                 firebaseController.removeProduct(id);
             }
         }
    }
}