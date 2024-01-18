package com.example.selltobuy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SaleReceiver extends BroadcastReceiver {
    FirebaseController firebaseController;
    String id;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "saleReceiver", Toast.LENGTH_SHORT).show();

        firebaseController=new FirebaseController(context);

       // if(!intent.getStringExtra("productId").equals(null)) {
           //if(intent.getFlags()==0) {
            id = intent.getStringExtra("productId");
            firebaseController.removeProduct(id);
       // }
//        else if(!intent.getStringExtra("techProductId").equals(null))
//        {
//            id=intent.getStringExtra("techProductId");
//            firebaseController.removeProduct(id);
//        }


    }
}