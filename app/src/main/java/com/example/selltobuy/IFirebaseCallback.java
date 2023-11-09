package com.example.selltobuy;

import java.util.ArrayList;

public interface IFirebaseCallback {
    void onCallbackUser(User user);
    void onCallbackList(ArrayList<Product> products);

}
