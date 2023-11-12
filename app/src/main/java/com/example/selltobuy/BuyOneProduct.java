package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class BuyOneProduct extends AppCompatActivity implements View.OnClickListener {
    TextView nameProduct,finalDate,currentPrice,infoText;
    Product product;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_one_product);
        if(getIntent().getExtras()!=null)
        {
            product=(Product) getIntent().getSerializableExtra("product");
        }
        nameProduct=findViewById(R.id.nameProduct);
        finalDate=findViewById(R.id.finalDate);
        currentPrice=findViewById(R.id.currentPrice);
        infoText=findViewById(R.id.TextInfo);
        nameProduct.setText("Product name: " + product.getName());
        finalDate.setText("Final date to buy: " + product.getFinalDate().toString());
        currentPrice.setText("current price: " + product.getPrice());
        infoText.setText("Info: " + product.getInfo());
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==backBtn)
        {
            Intent intent = new Intent(BuyOneProduct.this,Buyproduct.class);
            startActivity(intent);
        }
    }
}