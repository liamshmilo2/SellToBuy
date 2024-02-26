package com.example.selltobuy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.selltobuy.FirebaseController;
import com.example.selltobuy.IFirebaseCallback;
import com.example.selltobuy.ProductAdapter;
import com.example.selltobuy.classes.Product;
import com.example.selltobuy.R;
import com.example.selltobuy.classes.TechProduct;
import com.example.selltobuy.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Userdetails.
 */
public class Userdetails extends AppCompatActivity implements IFirebaseCallback, View.OnClickListener, AdapterView.OnItemSelectedListener , Serializable {

    /**
     * The Name details.
     */
    TextView nameDetails,
    /**
     * The User name details.
     */
    usernameDetails,
    /**
     * The Email details.
     */
    emailDetails,
    /**
     * The Coins details.
     */
    coinsDetails;
    /**
     * The Firebase controller.
     */
    FirebaseController firebaseController;

    private View.OnClickListener onItemClickListener;

    /**
     * רשימת מוצרים שהמשתמש מכר
     */
    private ArrayList<Product> products;

    /**
     * the Recycler View of sold products
     */
    private RecyclerView recyclerviewProduct2;

    /**
     * The Product Adapter
     */
    private ProductAdapter productAdapter;

    private Bitmap bitmap;


    /**
     * The Update btn.
     */
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        nameDetails=findViewById(R.id.nameDetails);
        usernameDetails =findViewById(R.id.usernamedetails);
        emailDetails =findViewById(R.id.emaildetails);
        coinsDetails=findViewById(R.id.coinsDetails);
        firebaseController=new FirebaseController(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        firebaseController.myProducts(this,firebaseUser);
        firebaseController.read(this);

        updateBtn=findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);

        products = new ArrayList<>();

        recyclerviewProduct2 = findViewById(R.id.recyclerview_product2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerviewProduct2.setLayoutManager(layoutManager);
    }

    //הפולה יוצרת את ה menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //פעולת ההצגה של ה menu שבודקת מה המשתמש בחר
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.setting){
            Intent intent1 = new Intent(Userdetails.this , SettingActivity.class);
            startActivity(intent1);
        }
        if (item.getItemId()==R.id.buy){
            Intent intent3 = new Intent(Userdetails.this , Buyproduct.class);
            startActivity(intent3);
        }
        if (item.getItemId()==R.id.sell){
            Intent intent4 = new Intent(Userdetails.this , Sell.class);
            startActivity(intent4);
        }
        return true;
    }


    //פעולה הקוראת את פרטי המשתמש הנוכחי
    @Override
    public void onCallbackUser(User user) {
        nameDetails.setText("Name: " + user.getName());
        usernameDetails.setText("User name: " + user.getUserName());
        emailDetails.setText("Email: " + user.getEmail());
        coinsDetails.setText("Coin number: " + user.getCoin());
    }


    //הפעולה קוראת את רשימת המוצרים
    @Override
    public void onCallbackList(ArrayList<Product> products1) {
        products= products1;
        productAdapter=new ProductAdapter(products);
        recyclerviewProduct2.setAdapter(productAdapter);
    }

    //הפעולה קוראת את רשימת המוצרים הטכנולוגיים
    @Override
    public void onCallbackTechList(ArrayList<TechProduct> techProducts) {

    }


    @Override
    public void onClick(View view) {
        if (view==updateBtn){
            Dialog dialog = new Dialog(Userdetails.this);
            dialog.setContentView(R.layout.updatedialog);
            Button btnclose = dialog.findViewById(R.id.btnclose);
            btnclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}