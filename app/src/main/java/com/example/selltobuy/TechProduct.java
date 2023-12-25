package com.example.selltobuy;

import android.graphics.Bitmap;

public class TechProduct extends Product{
    private String society; //חברה של המוצר

    public TechProduct() {
    }

    //הפעולה יוצרת מוצר טכנולוגי
    public TechProduct(int price, String name, String info, MyDate stratDate, MyDate finalDate, Bitmap image, String society) {
        super(price, name, info, stratDate, finalDate, image);
        this.society = society;
    }

    //הפעולה יוצרת מוצר טכנולוגי
    public TechProduct(int price, String name, String info, MyDate stratDate, MyDate finalDate ,String society) {
        super(price, name, info, stratDate, finalDate);
        this.society = society;
    }



    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }
}
