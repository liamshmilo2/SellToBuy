package com.example.selltobuy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selltobuy.classes.Check;
import com.example.selltobuy.FirebaseController;
import com.example.selltobuy.R;
import com.example.selltobuy.classes.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSingin , btnLogin;
    TextView textHome;
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

        check = new Check();
        firebaseController=new FirebaseController(this);

        if(firebaseController.currentUser())
        {
            Intent intent = new Intent(MainActivity.this, Buyproduct.class);
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

                    }

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
                    }

                }
            });
            dialog2.show();
        }
    }





}