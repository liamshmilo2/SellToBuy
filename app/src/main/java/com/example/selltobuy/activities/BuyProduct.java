package com.example.selltobuy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.selltobuy.MyService;
import com.example.selltobuy.helpers.FirebaseController;
import com.example.selltobuy.helpers.IFirebaseCallback;
import com.example.selltobuy.objects.Product;
import com.example.selltobuy.helpers.ProductAdapter;
import com.example.selltobuy.R;
import com.example.selltobuy.objects.TechProduct;
import com.example.selltobuy.objects.User;
import com.example.selltobuy.receiver.BatteryReciver;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Buyproduct.
 */
public class BuyProduct extends AppCompatActivity implements IFirebaseCallback, AdapterView.OnItemSelectedListener, Serializable {
    /**
     * רשימת מוצרים
     */
    private ArrayList<Product> products;

    /**
     * The OnClickListener.
     */
    private View.OnClickListener onItemClickListener;

    /**
     * The Firebase controller.
     */
    private FirebaseController firebaseController;

    /**
     * The Recycler View.
     */
    private RecyclerView recyclerView;

    /**
     * The Product Adapter.
     */
    private ProductAdapter productAdapter;

    /**
     * The Products type.
     */
    private Spinner type;
    private String text;
    private Bitmap bitmap;

    /**
     * The text coin.
     */
    private TextView coinText;
    private BatteryReciver batteryReciver;




    //הפעולה מתחילה את ה  BatteryReciver
    protected void onStart ()
    {
        super.onStart();
        registerReceiver(batteryReciver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }


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
        coinText=findViewById(R.id.coinText);
        firebaseController = new FirebaseController(this);
        firebaseController.read(this);


        products = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview_product);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        batteryReciver=new BatteryReciver();



    }

    //הפעולה בודקת איזה סוג של רשימה יש להציג ומציגה אותה בהתאם
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
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
            Intent intent1 = new Intent(BuyProduct.this , SettingActivity.class);
            startActivity(intent1);
        }
        if (item.getItemId()==R.id.user){
            Intent intent2 = new Intent(BuyProduct.this , Userdetails.class);
            startActivity(intent2);
        }
        if (item.getItemId()==R.id.sell){
            Intent intent4 = new Intent(BuyProduct.this , Sell.class);
            startActivity(intent4);
        }
        if(item.getItemId()==R.id.startmusic)
        {
            startService(new Intent(this, MyService.class));
        }
        if(item.getItemId()==R.id.stopmusic)
        {
            stopService(new Intent(this, MyService.class));
        }
        return true;
    }

    @Override
    public void onCallbackUser(User user) {
        coinText.setText("" + user.getCoin());
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
                product.setSellId(productItem.getSellId());
                product.setBuyId(productItem.getBuyId());
                Intent intent = new Intent(BuyProduct.this, BuyOneProduct.class);
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
                Intent intent = new Intent(BuyProduct.this, BuyOneProduct.class);
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


    protected void onDestroy ()
    {
        super.onDestroy();
        unregisterReceiver(batteryReciver);
    }

}