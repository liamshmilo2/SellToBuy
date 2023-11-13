package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Button update,logout,change;
    FirebaseController firebaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //getIntent();

//        update=findViewById(R.id.update);
//        update.setOnClickListener(this);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(this);

        change=findViewById(R.id.change);
        change.setOnClickListener(this);
        firebaseController= new FirebaseController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId()==R.id.setting){
//            Intent intent1 = new Intent(SettingActivity.this , SettingActivity.class);
//            startActivity(intent1);
//        }
        if (item.getItemId()==R.id.user){
            Intent intent2 = new Intent(SettingActivity.this , Userdetails.class);
            startActivity(intent2);
        }
        if (item.getItemId()==R.id.buy){
            Intent intent3 = new Intent(SettingActivity.this , Buyproduct.class);
            startActivity(intent3);
        }
        if (item.getItemId()==R.id.sell){
            Intent intent4 = new Intent(SettingActivity.this , Sell.class);
            startActivity(intent4);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
//        if (view==update){
//            Dialog dialog = new Dialog(SettingActivity.this);
//            dialog.setContentView(R.layout.updatedialog);
//            Button btnclose = dialog.findViewById(R.id.btnclose);
//            btnclose.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//        }

        if(view==logout)
        {
            firebaseController.logOut();
        }

    }
}