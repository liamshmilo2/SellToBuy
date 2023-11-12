package com.example.selltobuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class Buyproduct extends AppCompatActivity implements IFirebaseCallback {
    private ArrayList<Product> products;
    private View.OnClickListener onItemClickListener;
    private FirebaseController firebaseController;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyproduct);
        firebaseController = new FirebaseController(this);
        firebaseController.retrieveData(this);



        products = new ArrayList<>();
        MyDate d1 = new MyDate(2023,9,21);
        MyDate d2 = new MyDate(2023,9,28);

//        for (int i=0; i<10; i++)
//        {
//          products.add(new Product(100, "phone2" , "samsung s10+",d1,d2));
//        }
//
//
//
//
//        onItemClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
//                int position = viewHolder.getAdapterPosition();
//                Product productItem = products.get(position);
//                Toast.makeText(Buyproduct.this , "you clicked " + productItem.getInfo() , Toast.LENGTH_SHORT).show();
//            }
//        };

        recyclerView = findViewById(R.id.recyclerview_product);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        productAdapter = new ProductAdapter(products);
//        recyclerView.setAdapter(productAdapter);
//        productAdapter.setmOnItemClickListener(onItemClickListener);
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
//        if (item.getItemId()==R.id.buy){
//            Intent intent3 = new Intent(Buyproduct.this , Buyproduct.class);
//            startActivity(intent3);
//        }
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
//                intent.putExtra("name",productItem.getName());
//                intent.putExtra("price" , productItem.getPrice());
//                intent.putExtra("info",productItem.getInfo());
//                intent.putExtra("day" , productItem.getStratDate().getDay());
//                intent.putExtra("month" , productItem.getStratDate().getMonth());
//                intent.putExtra("year" , productItem.getStratDate().getYaer());
                startActivity(intent);
                //Toast.makeText(Buyproduct.this , "you clicked " + productItem.getInfo() , Toast.LENGTH_SHORT).show();
            }
        };
        productAdapter.setmOnItemClickListener(onItemClickListener);
    }
}