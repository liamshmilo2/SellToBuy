package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Buyproduct extends AppCompatActivity implements IFirebaseCallback , AdapterView.OnItemSelectedListener, Serializable {
    private ArrayList<Product> products;
    private View.OnClickListener onItemClickListener;
    private FirebaseController firebaseController;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    Spinner type;
    String text;
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyproduct);
        type = findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);
        text="General product";
        firebaseController = new FirebaseController(this);



        products = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview_product);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        text = adapterView.getItemAtPosition(i).toString();
        if(text.equals("General product"))
        {
            firebaseController.retrieveData(this);
        }
        if(text.equals("Tech product"))
        {
            firebaseController.readTechProducts(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //הפעולה מציגה את התפריט
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    //הפעוןלה בודקת איזה כפתור בתפריט נלחץ ומעבירה לעמוד המתאים
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


    //הפעולה מציגה את רשימת המוצרים הרגילים
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


                Product product = new Product(productItem.getPrice(),productItem.getName(),productItem.getInfo(),productItem.getStratDate(),productItem.getFinalDate());
                product.setPid(productItem.getPid());
                Intent intent = new Intent(Buyproduct.this, BuyOneProduct.class);
                intent.putExtra("product" , product);
                intent.putExtra("type" , "general");
                bitmap=productItem.getImage();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("bitmap", byteArray);
                startActivity(intent);
            }
        };
        productAdapter.setmOnItemClickListener(onItemClickListener);
    }



    //הפעולה מציגה את רשימת המוצרים הטכנולוגיים
    @Override
    public void onCallbackTechList(ArrayList<TechProduct> techProducts) {
        products.clear();
        for (int i=0; i<techProducts.size(); i++)
        {
            products.add(new TechProduct(techProducts.get(i).getPrice(),techProducts.get(i).getName(),techProducts.get(i).getInfo(),techProducts.get(i).getStratDate(),techProducts.get(i).getFinalDate(),techProducts.get(i).getImage(),techProducts.get(i).getSociety()));
            products.get(i).setPid(techProducts.get(i).getPid());
        }

        productAdapter=new ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);

        onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                int position = viewHolder.getAdapterPosition();
                TechProduct productItem = (TechProduct) products.get(position);

                TechProduct techProduct = new TechProduct(productItem.getPrice(),productItem.getName(),productItem.getInfo(),productItem.getStratDate(),productItem.getFinalDate(),productItem.getSociety());
                techProduct.setPid(productItem.getPid());
                Intent intent = new Intent(Buyproduct.this, BuyOneProduct.class);
                intent.putExtra("techProduct" , techProduct);
                intent.putExtra("type" , "tech");
                bitmap=productItem.getImage();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("bitmap", byteArray);
                startActivity(intent);
            }
        };
        productAdapter.setmOnItemClickListener(onItemClickListener);
    }


}