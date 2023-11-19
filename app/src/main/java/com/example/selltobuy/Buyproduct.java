package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class Buyproduct extends AppCompatActivity implements IFirebaseCallback , AdapterView.OnItemSelectedListener {
    private ArrayList<Product> products;
    private  ArrayList<TechProduct> techProducts;
    private View.OnClickListener onItemClickListener;
    private FirebaseController firebaseController;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    Spinner type;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyproduct);
        type = findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);

        firebaseController = new FirebaseController(this);

        if(text.equals("General product"))
        {
            firebaseController.retrieveData(this);
        }
        if(text.equals("Tech product"))
        {
            firebaseController.readTechProducts(this);
        }

        products = new ArrayList<>();
        techProducts = new  ArrayList<>();



        recyclerView = findViewById(R.id.recyclerview_product);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        text = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.setting){
            Intent intent1 = new Intent(Buyproduct.this , SettingActivity.class);
            startActivity(intent1);
        }
        if (item.getItemId()==R.id.user){
            Intent intent2 = new Intent(Buyproduct.this , Userdetails.class);
            startActivity(intent2);
        }
        if (item.getItemId()==R.id.sell){
            Intent intent4 = new Intent(Buyproduct.this , Sell.class);
            startActivity(intent4);
        }
        return true;
    }

    @Override
    public void onCallbackUser(User user) {

    }

    @Override
    public void onCallbackList(ArrayList<Product> products1) {
        products=products1;
        productAdapter=new ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);

        onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                int position = viewHolder.getAdapterPosition();
                Product productItem = products.get(position);
                Intent intent = new Intent(Buyproduct.this, BuyOneProduct.class);
                intent.putExtra("product" , productItem);
                startActivity(intent);
            }
        };
        productAdapter.setmOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onCallbackTechList(ArrayList<TechProduct> techProducts1) {
        techProducts=techProducts1;
        productAdapter=new ProductAdapter(techProducts);
        recyclerView.setAdapter(productAdapter);

        onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                int position = viewHolder.getAdapterPosition();
                Product productItem = techProducts.get(position);
                Intent intent = new Intent(Buyproduct.this, BuyOneProduct.class);
                intent.putExtra("techProduct" , productItem);
                startActivity(intent);
            }
        };
        productAdapter.setmOnItemClickListener(onItemClickListener);

    }



}