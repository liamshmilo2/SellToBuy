package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Userdetails extends AppCompatActivity implements IFirebaseCallback, View.OnClickListener {

    TextView nameDetails,usernamedetails,emaildetails,coinsDetails;
    FirebaseController firebaseController;

    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        nameDetails=findViewById(R.id.nameDetails);
        usernamedetails=findViewById(R.id.usernamedetails);
        emaildetails=findViewById(R.id.emaildetails);
        coinsDetails=findViewById(R.id.coinsDetails);
        firebaseController=new FirebaseController(this);
        firebaseController.read(this);

        updateBtn=findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);
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

    @Override
    public void onCallbackUser(User user) {
        nameDetails.setText("Name: " + user.getName());
        usernamedetails.setText("User name: " + user.getUserName());
        emaildetails.setText("Email: " + user.getEmail());
        coinsDetails.setText("Coin number: " + user.getCoin());
    }

    @Override
    public void onCallbackList(ArrayList<Product> products) {


    }

    @Override
    public void onCallbackTechList(ArrayList<TechProduct> techProducts) {

    }


    @Override
    public void onClick(View view) {
        if (view==updateBtn){
            Dialog dialog = new Dialog(Userdetails.this);
            dialog.setContentView(R.layout.updatedialog);
            Button btnclose = dialog.findViewById(R.id.btnclose);
            btnclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}