package com.example.selltobuy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selltobuy.FirebaseController;
import com.example.selltobuy.IFirebaseCallback;
import com.example.selltobuy.classes.Product;
import com.example.selltobuy.R;
import com.example.selltobuy.classes.TechProduct;
import com.example.selltobuy.classes.User;

import java.util.ArrayList;

/**
 * The type Buy one product.
 */
public class BuyOneProduct extends AppCompatActivity implements View.OnClickListener , IFirebaseCallback {
    /**
     * The Name product.
     */
    TextView nameProduct,
    /**
     * The Final date.
     */
    finalDate,
    /**
     * The Current price.
     */
    currentPrice,
    /**
     * The Info text.
     */
    infoText,
    /**
     * The Society.
     */
    society;
    /**
     * The Product image.
     */
    ImageView productImage;
    /**
     * The Bitmap.
     */
    Bitmap bitmap;
    /**
     * The Product.
     */
    Product product=null;
    /**
     * The Tech product.
     */
    TechProduct techProduct=null;
    /**
     * The Back btn.
     */
    ImageButton backBtn;
    /**
     * The Price.
     */
    Button price,
    /**
     * The Btn save.
     */
    btnSave;
    /**
     * The Edit price.
     */
    int editPrice;
    /**
     * The Firebase controller.
     */
    FirebaseController firebaseController;
    /**
     * The Buy id.
     */
    String buyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_one_product);
        nameProduct=findViewById(R.id.nameProduct);
        finalDate=findViewById(R.id.finalDate);
        currentPrice=findViewById(R.id.currentPrice);
        infoText=findViewById(R.id.TextInfo);
        society=findViewById(R.id.society);
        productImage=findViewById(R.id.productImage);
        if(getIntent().getExtras()!=null)
        {
            if(getIntent().getExtras().get("type").equals("general"))
            {
                product=(Product) getIntent().getSerializableExtra("product");
                nameProduct.setText("Product name: " + product.getName());
                finalDate.setText("Final date to buy: " + product.getFinalDate().toString());
                currentPrice.setText("current price: " + product.getPrice());
                infoText.setText("Info: " + product.getInfo());
                byte[] byteArray = getIntent().getByteArrayExtra("bitmap");
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                productImage.setImageBitmap(bitmap);

            }
            else
            {
                techProduct=(TechProduct) getIntent().getSerializableExtra("techProduct");
                nameProduct.setText("Product name: " + techProduct.getName());
                finalDate.setText("Final date to buy: " + techProduct.getFinalDate().toString());
                currentPrice.setText("current price: " + techProduct.getPrice());
                infoText.setText("Info: " + techProduct.getInfo());
                society.setText("Society: " + techProduct.getSociety());
                byte[] byteArray = getIntent().getByteArrayExtra("bitmap");
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                productImage.setImageBitmap(bitmap);

            }

        }


        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
        price=findViewById(R.id.price);
        price.setOnClickListener(this);
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        firebaseController=new FirebaseController(this);
        firebaseController.read(this);

    }

    @Override
    public void onClick(View view) {
        if(view==backBtn)
        {
            Intent intent = new Intent(BuyOneProduct.this, Buyproduct.class);
            startActivity(intent);
        }
        if(view==price)
        {
            if(product!=null)
            {
                editPrice = product.getPrice();
                editPrice=editPrice+10;
                currentPrice.setText("current price: " +editPrice);
                product.setPrice(editPrice);
            }
            else {
                editPrice = techProduct.getPrice();
                editPrice=editPrice+10;
                currentPrice.setText("current price: " +editPrice);
                techProduct.setPrice(editPrice);
            }

        }
        if(view==btnSave)
        {
            if(product!=null)
            {
                firebaseController.updateProduct(product.getPid(),editPrice,buyId);
                Intent intent = new Intent(BuyOneProduct.this,Buyproduct.class);
                startActivity(intent);
            }
            else
            {
                firebaseController.updateTechProduct(techProduct.getPid(),editPrice,buyId);
                Intent intent = new Intent(BuyOneProduct.this,Buyproduct.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onCallbackUser(User user) {
        buyId=user.getId();
    }

    @Override
    public void onCallbackList(ArrayList<Product> products) {

    }

    @Override
    public void onCallbackTechList(ArrayList<TechProduct> techProducts) {

    }
}