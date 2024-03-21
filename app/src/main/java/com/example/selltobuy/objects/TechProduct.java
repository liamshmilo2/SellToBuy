package com.example.selltobuy.objects;

import android.graphics.Bitmap;

/**
 * The type Tech product.
 */
public class TechProduct extends Product {
    private String society; //חברה של המוצר

    /**
     * Instantiates a new Tech product.
     */
    public TechProduct() {
    }

    /**
     * הפעולה יוצרת מוצר טכנולוגי
     */
    public TechProduct(int price, String name, String info, MyDate stratDate, MyDate finalDate, Bitmap image, String society) {
        super(price, name, info, stratDate, finalDate, image);
        this.society = society;
    }

    /**
     * הפעולה יוצרת מוצר טכנולוגי
     */
    public TechProduct(int price, String name, String info, MyDate stratDate, MyDate finalDate ,String society) {
        super(price, name, info, stratDate, finalDate);
        this.society = society;
    }


    /**
     * הפעולה מחזירה את החברה של המוצר
     */
    public String getSociety() {
        return society;
    }

    /**
     * הפעולה מקבלת שם של חברה ומשנה אותו בהתאם
     */
    public void setSociety(String society) {
        this.society = society;
    }
}
