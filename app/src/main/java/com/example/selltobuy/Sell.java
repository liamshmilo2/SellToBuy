package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Sell extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {

    ImageView imageView;
    FloatingActionButton button;
    Button btnSell;
    EditText editTextName,editTextPrice,editTextInfo;
    Check check;


    FirebaseController firebaseController;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.black)));

        imageView = findViewById(R.id.imageView);
        button=findViewById(R.id.floatingActionButton);
        btnSell=findViewById(R.id.buttonsell);
        btnSell.setOnClickListener(this);
        editTextName=findViewById(R.id.editTextName);
        editTextInfo=findViewById(R.id.editTextInfo);
        editTextPrice=findViewById(R.id.editTextPrice);
        check=new Check();
        firebaseController=new FirebaseController(this);
        Spinner chooseType = findViewById(R.id.chooseType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseType.setAdapter(adapter);
        chooseType.setOnItemSelectedListener(this);



//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImagePicker.with(this)
//                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                        .start()
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.setting){
            Intent intent1 = new Intent(Sell.this , SettingActivity.class);
            startActivity(intent1);
        }
        if (item.getItemId()==R.id.user){
            Intent intent2 = new Intent(Sell.this , Userdetails.class);
            startActivity(intent2);
        }
        if (item.getItemId()==R.id.buy){
            Intent intent3 = new Intent(Sell.this , Buyproduct.class);
            startActivity(intent3);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view==btnSell)
        {
            String name = editTextName.getText().toString();
            String info = editTextInfo.getText().toString();
            String price =editTextPrice.getText().toString();
            int price2;

            if(check.checkName(name)==false)
            {
                Toast.makeText(this, "please write a real name", Toast.LENGTH_SHORT).show();
            }
            else if(check.chekInpo(info)==false)
            {
                Toast.makeText(this, "please write any info about the product", Toast.LENGTH_SHORT).show();
            } else if (check.chekPrice(price)==false)
            {
                Toast.makeText(this, "please write price", Toast.LENGTH_SHORT).show();
            } else
            {
                Product product;
                price2 = Integer.parseInt(price);
                Calendar calendar = Calendar.getInstance();
                int day  = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                MyDate date1 = new MyDate(year,month+1,day);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.DAY_OF_YEAR,7);
                MyDate date2 = new MyDate(calendar1.get(Calendar.YEAR) , calendar1.get(Calendar.MONTH)+1,calendar1.get(Calendar.DAY_OF_MONTH));
                product = new Product(price2,name,info,date1,date2);
                firebaseController.saveProduct(product);
                Intent intent = new Intent(Sell.this,Buyproduct.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}