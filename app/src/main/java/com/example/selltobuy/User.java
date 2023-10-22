package com.example.selltobuy;

import java.util.List;

public class User {
    private  String name;
    private  String id;
    private  Date birthDate;
    private  int coin;
    //private List<Product> buyList;
    //private  List<Product> sellList;


    public User(String name, String id, Date birthDate) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;
        this.coin = 100;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getCoin() {
        return coin;
    }


}
