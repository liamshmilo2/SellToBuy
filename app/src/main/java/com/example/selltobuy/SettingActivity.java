package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Button logout,change;
    FirebaseController firebaseController;
    Check check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(this);

        change=findViewById(R.id.change);
        change.setOnClickListener(this);
        firebaseController= new FirebaseController(this);
        check=new Check();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        if(view==logout)
        {
            firebaseController.logOut();
        }

        if(view==change)
        {
            Dialog dialog = new Dialog(SettingActivity.this);
            dialog.setContentView(R.layout.logindialog);
            Button btnok = dialog.findViewById(R.id.btnok);
            EditText email2 = dialog.findViewById(R.id.email2);
            EditText pass = dialog.findViewById(R.id.pass);
            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(check.checkName(email2.getText().toString())==false)
                    {
                        Toast.makeText(SettingActivity.this, "please write a real email", Toast.LENGTH_SHORT).show();
                    }
                    else if(check.checkPass(pass.getText().toString())==false)
                    {
                        Toast.makeText(SettingActivity.this, "check that your password is right", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        firebaseController.swich();
                        firebaseController.lonIn(email2.getText().toString(),pass.getText().toString());
                    }

                }
            });
            dialog.show();
        }

    }
}