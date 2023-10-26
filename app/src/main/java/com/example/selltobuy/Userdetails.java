package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Userdetails extends AppCompatActivity {

    TextView nameDetails,usernamedetails,emaildetails,coinsDetails;
    FirebaseController firebaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        nameDetails=findViewById(R.id.nameDetails);
        usernamedetails=findViewById(R.id.usernamedetails);
        emaildetails=findViewById(R.id.emaildetails);
        coinsDetails=findViewById(R.id.coinsDetails);
        firebaseController=new FirebaseController(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.setting){
            Intent intent1 = new Intent(Userdetails.this , SettingActivity.class);
            startActivity(intent1);
        }
//        if (item.getItemId()==R.id.user){
//            Intent intent2 = new Intent(Userdetails.this , Userdetails.class);
//            startActivity(intent2);
//        }
        if (item.getItemId()==R.id.buy){
            Intent intent3 = new Intent(Userdetails.this , Buyproduct.class);
            startActivity(intent3);
        }
        if (item.getItemId()==R.id.sell){
            Intent intent4 = new Intent(Userdetails.this , Sell.class);
            startActivity(intent4);
        }
        return true;
    }
}