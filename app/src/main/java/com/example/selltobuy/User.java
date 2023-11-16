package com.example.selltobuy;

import java.util.List;

public class User {
    private  String name;
    private String userName;
    private  String id;
    private  int coin;
    private String email;
    private String phone;
    //private List<Product> buyList;
    //private  List<Product> sellList;


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
}
