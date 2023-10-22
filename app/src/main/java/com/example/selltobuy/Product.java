package com.example.selltobuy;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Product {
    private  int price; //מחיר התחלתי של המוצר
    private  String name; //שם המוצר
    private  String info; // הסבר על המוצר
    private  Date stratDate; // תאריך התחלת העלאת המוצר
    private  Date finalDate; // תאריך בו המוצר ימכר

    private String image;

    public Product(int price, String name, String info, Date stratDate, Date finalDate , String image) {
        this.price = price;
        this.name = name;
        this.info = info;
        this.stratDate = stratDate;
        this.finalDate = finalDate;
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Date getStratDate() {
        return stratDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public String getImage(){
        return  image;
    }
}
