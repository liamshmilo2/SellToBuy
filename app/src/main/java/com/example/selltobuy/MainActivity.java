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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSingin , btnLogin;
    TextView textHome;
    //Dialog dialoglogin;
    Check check;
    FirebaseController firebaseController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin=findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(this);

        btnSingin=findViewById(R.id.btnSingin);
        btnSingin.setOnClickListener(this);

        textHome= findViewById(R.id.textHome);
        //dialoglogin= new Dialog(this);
        //dialoglogin.setContentView(R.layout.logindialog);

        check = new Check();
        firebaseController=new FirebaseController(this);

        if(firebaseController.currentUser())
        {
            Intent intent = new Intent(MainActivity.this,Buyproduct.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if(view==btnLogin)
        {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.logindialog);
            Button btnok = dialog.findViewById(R.id.btnok);
            EditText email2 = dialog.findViewById(R.id.email2);
            EditText pass = dialog.findViewById(R.id.pass);
            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(check.checkName(email2.getText().toString())==false)
                    {
                        Toast.makeText(MainActivity.this, "please write a real email", Toast.LENGTH_SHORT).show();
                    }
                    else if(check.checkPass(pass.getText().toString())==false)
                    {
                        Toast.makeText(MainActivity.this, "check that your password is right", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        firebaseController.lonIn(email2.getText().toString(),pass.getText().toString());

//                        Intent intent = new Intent(MainActivity.this , Buyproduct.class);
//                        startActivity(intent);
                    }
//                    if(check.checkName(username.getText().toString()) && check.checkPass(pass.getText().toString()))
//                    {
//                      //todo  save login
//                        dialog.dismiss();
//                    }
//                    else
//                    {
//                        Toast.makeText(MainActivity.this, "check that your details is true", Toast.LENGTH_SHORT).show();
//                    }

                }
            });
            dialog.show();

        }
        if(view==btnSingin)
        {
            Dialog dialog2 = new Dialog(MainActivity.this);
            dialog2.setContentView(R.layout.singindialog);
            Button btnapproval = dialog2.findViewById(R.id.btnapproval);
            EditText name = dialog2.findViewById(R.id.name);
            EditText email = dialog2.findViewById(R.id.email);
            EditText phone = dialog2.findViewById(R.id.phone);
            EditText password = dialog2.findViewById(R.id.password);
            EditText newuser = dialog2.findViewById(R.id.newuser);
            btnapproval.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(check.checkName(name.getText().toString())==false)
                    {
                        Toast.makeText(MainActivity.this, "please write a real name", Toast.LENGTH_SHORT).show();
                    }
                    else if(check.checkPass(password.getText().toString())==false)
                    {
                        Toast.makeText(MainActivity.this, "check that your password is right", Toast.LENGTH_SHORT).show();
                    }
                    else if(check.checkEmail(email.getText().toString())==false)
                    {
                        Toast.makeText(MainActivity.this, "please write a real email", Toast.LENGTH_SHORT).show();
                    }
                    else if(check.checkPhone(phone.getText().toString())==false)
                    {
                        Toast.makeText(MainActivity.this, "please write a real phone number", Toast.LENGTH_SHORT).show();
                    }
                    else if(check.checkName(newuser.getText().toString())==false)
                    {
                        Toast.makeText(MainActivity.this, "please write a real user name", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        User user = new User(name.getText().toString(),newuser.getText().toString(),100,email.getText().toString(),phone.getText().toString());
                        firebaseController.createUser(user,password.getText().toString());
//                        Intent intent = new Intent(MainActivity.this , Buyproduct.class);
//                        startActivity(intent);
                    }


//                    if(check.checkName(name.getText().toString()) && check.checkPass(password.getText().toString()) && check.checkEmail(email.getText().toString()) && check.checkPhone(phone.getText().toString()) && check.checkName(newuser.getText().toString()))
//                    {
//                        //todo  save login
//                        dialog2.dismiss();
//                    }
//                    else
//                    {
//                        Toast.makeText(MainActivity.this, "check that your details is true", Toast.LENGTH_SHORT).show();
//                    }
                }
            });
            dialog2.show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==R.id.home)
//        {
//            Intent intent = new Intent(MainActivity.this , MainActivity.class);
//            startActivity(intent);
//        }
//        if (item.getItemId()==R.id.setting){
//            Intent intent1 = new Intent(MainActivity.this , SettingActivity.class);
//            startActivity(intent1);
//        }
//        if (item.getItemId()==R.id.user){
//            Intent intent2 = new Intent(MainActivity.this , Userdetails.class);
//            startActivity(intent2);
//        }
//        if (item.getItemId()==R.id.buy){
//            Intent intent3 = new Intent(MainActivity.this , Buyproduct.class);
//            startActivity(intent3);
//        }
//        if (item.getItemId()==R.id.sell){
//            Intent intent4 = new Intent(MainActivity.this , Sell.class);
//            startActivity(intent4);
//        }
//        return true;
//    }



}