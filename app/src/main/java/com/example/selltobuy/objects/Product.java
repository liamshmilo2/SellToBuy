package com.example.selltobuy.objects;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * The type Product.
 */
public class Product implements Serializable {

    protected int price; //מחיר התחלתי של המוצר

    protected String name; //שם המוצר

    protected String info; // הסבר על המוצר

    protected MyDate stratDate; // תאריך התחלת העלאת המוצר

    protected MyDate finalDate; // תאריך בו המוצר ימכר

    protected Bitmap image;
    protected String pid;
    protected String sellId; // המזהה של המשתמש שמכר את המוצר
    protected String buyId; // המזהה של המשתמש האחרון שהעלה את מחיר המוצר


    /**
     * Instantiates a new Product.
     */
    public Product() {
    }

    /**
     * הפעולה יוצרת מוצר חדש
     */

    public Product(int price, String name, String info, MyDate stratDate, MyDate finalDate , Bitmap image) {
        this.price = price;
        this.name = name;
        this.info = info;
        this.stratDate = stratDate;
        this.finalDate = finalDate;
        this.image = image;
    }


    /**
     * הפעולה מחזירה את ה id של המוכר
     */
    public String getSellId() {
        return sellId;
    }

    /**
     * הפעולה מקבל את ה id של המוכר וומשנה בהתאם
     */
    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    /**
     * הפועלה מחזירה את ה id של הקונה
     */
    public String getBuyId() {
        return buyId;
    }

    /**
     * הפעולה מקבל את ה id של הקונה ומשנה בהתאם
     */
    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    /**
     * הפעולה יוצרת מוצר חדש
     */
    public Product(int price, String name, String info, MyDate stratDate, MyDate finalDate) {
        this.price = price;
        this.name = name;
        this.info = info;
        this.stratDate = stratDate;
        this.finalDate = finalDate;
    }

    /**
     * הפעולה מחזירה את מחיר המוצר
     */
    public int getPrice() {
        return price;
    }

    /**
     * הפעולה מחזירה את שם המוצר
     */
    public String getName() {
        return name;
    }

    /**
     * הפעולה מחזירה את ההסבר על המוצר
     */
    public String getInfo() {
        return info;
    }

    /**
     * הפעולה מחזירה את התאריך שהמוצר עלה
     */
    public MyDate getStratDate() {
        return stratDate;
    }

    /**
     * הפעולה מחזירה את התאריך שהמוצר ימכר בו
     */
    public MyDate getFinalDate() {
        return finalDate;
    }

    /**
     * הפעולה מחזירה את התמונה של המוצר
     */
    public Bitmap getImage(){
        return  image;
    }

    /**
     * הפעולה מקבלת מחיר של מוצר ומשנה אותו בהתאם
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * הפעולה מחזירה את ה id של המוצר
     */
    public String getPid() {
        return pid;
    }

    /**
     * הפעולה מקבלת id של מוצר ומשנה אותו בהתאם
     */
    public void setPid(String pid) {
        this.pid = pid;
    }
}
