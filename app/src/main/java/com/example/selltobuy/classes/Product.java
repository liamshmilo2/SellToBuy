package com.example.selltobuy.classes;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Product implements Serializable {
    protected   int price; //מחיר התחלתי של המוצר
    protected   String name; //שם המוצר
    protected   String info; // הסבר על המוצר
    protected MyDate stratDate; // תאריך התחלת העלאת המוצר
    protected MyDate finalDate; // תאריך בו המוצר ימכר

    protected Bitmap image;
    protected String pid;

    protected String sellId; // המזהה של המשתמש שמכר את המוצר
    protected String buyId; // המזהה של המשתמש האחרון שהעלה את מחיר המוצר


    public Product() {
    }

    //הפעולה יוצרת מוצר חדש
    public Product(int price, String name, String info, MyDate stratDate, MyDate finalDate , Bitmap image) {
        this.price = price;
        this.name = name;
        this.info = info;
        this.stratDate = stratDate;
        this.finalDate = finalDate;
        this.image = image;
    }


    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    //הפעולה יוצרת מוצר חדש
    public Product(int price, String name, String info, MyDate stratDate, MyDate finalDate) {
        this.price = price;
        this.name = name;
        this.info = info;
        this.stratDate = stratDate;
        this.finalDate = finalDate;
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

    public MyDate getStratDate() {
        return stratDate;
    }

    public MyDate getFinalDate() {
        return finalDate;
    }

    public Bitmap getImage(){
        return  image;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
