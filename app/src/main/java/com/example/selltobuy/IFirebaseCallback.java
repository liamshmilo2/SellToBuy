package com.example.selltobuy;

import com.example.selltobuy.classes.Product;
import com.example.selltobuy.classes.TechProduct;
import com.example.selltobuy.classes.User;

import java.util.ArrayList;

public interface IFirebaseCallback {
    void onCallbackUser(User user);
    void onCallbackList(ArrayList<Product> products);

    void onCallbackTechList(ArrayList<TechProduct> techProducts);

}
