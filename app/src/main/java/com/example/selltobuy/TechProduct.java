package com.example.selltobuy;

public class TechProduct extends Product{
    private String society;

    public TechProduct(int price, String name, String info, MyDate stratDate, MyDate finalDate, String image, String society) {
        super(price, name, info, stratDate, finalDate, image);
        this.society = society;
    }

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
