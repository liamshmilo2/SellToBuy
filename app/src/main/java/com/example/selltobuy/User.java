package com.example.selltobuy;

import java.util.List;

public class User {
    private  String name; //שם
    private String userName; //שם משתמש
    private  String id; //מזהה אישי
    private  int coin; //מספר מטבעות
    private String email; //אימייל
    private String phone; // מספר טלפון
    private List<Product> buyList; //מוצרים שהמשתמש קנה
    private  List<Product> sellList; // מוצרים שהמשתמש מכר


    public User() {
    }

    public User(String name, String userName, String id, int coin, String email, String phone) {
        this.name = name;
        this.userName = userName;
        this.id = id;
        this.coin = coin;
        this.email = email;
        this.phone = phone;
    }

    //הפעולה יוצרת משתמש
    public User(String name, String userName, int coin, String email, String phone) {
        this.name = name;
        this.userName = userName;
        this.coin = coin;
        this.email = email;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Product> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<Product> buyList) {
        this.buyList = buyList;
    }

    public List<Product> getSellList() {
        return sellList;
    }

    public void setSellList(List<Product> sellList) {
        this.sellList = sellList;
    }
}
