package com.example.selltobuy;

import com.example.selltobuy.classes.Product;
import com.example.selltobuy.classes.TechProduct;
import com.example.selltobuy.classes.User;

import java.util.ArrayList;

/**
 * The interface Firebase callback.
 */
public interface IFirebaseCallback {
    /**
     * On callback user.
     *
     * @param user the user
     */
    void onCallbackUser(User user);

    /**
     * On callback list.
     *
     * @param products the products
     */
    void onCallbackList(ArrayList<Product> products);

    /**
     * On callback tech list.
     *
     * @param techProducts the tech products
     */
    void onCallbackTechList(ArrayList<TechProduct> techProducts);

}
