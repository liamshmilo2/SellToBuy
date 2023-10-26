package com.example.selltobuy;

public class ChildProduct extends Product{

    private int age;

    public ChildProduct(int price, String name, String info, Date stratDate, Date finalDate, String image, int age) {
        super(price, name, info, stratDate, finalDate, image);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
