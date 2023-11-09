package com.example.selltobuy;

public class Product {
    private  int price; //מחיר התחלתי של המוצר
    private  String name; //שם המוצר
    private  String info; // הסבר על המוצר
    private MyDate stratDate; // תאריך התחלת העלאת המוצר
    private MyDate finalDate; // תאריך בו המוצר ימכר

    private String image;

    public Product(int price, String name, String info, MyDate stratDate, MyDate finalDate , String image) {
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

    public MyDate getStratDate() {
        return stratDate;
    }

    public MyDate getFinalDate() {
        return finalDate;
    }

    public String getImage(){
        return  image;
    }
}
