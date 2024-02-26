package com.example.selltobuy.classes;

import java.io.Serializable;
import java.util.List;

/**
 * The type User.
 */
public class User implements Serializable {
    private  String name; //שם
    private String userName; //שם משתמש
    private  String id; //מזהה אישי
    private  int coin; //מספר מטבעות
    private String email; //אימייל
    private String phone; // מספר טלפון

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param name     the name
     * @param userName the user name
     * @param id       the id
     * @param coin     the coin
     * @param email    the email
     * @param phone    the phone
     */
    public User(String name, String userName, String id, int coin, String email, String phone) {
        this.name = name;
        this.userName = userName;
        this.id = id;
        this.coin = coin;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Instantiates a new User.
     *
     * @param name     the name
     * @param userName the user name
     * @param coin     the coin
     * @param email    the email
     * @param phone    the phone
     */
    public User(String name, String userName, int coin, String email, String phone) {
        this.name = name;
        this.userName = userName;
        this.coin = coin;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets coin.
     *
     * @return the coin
     */
    public int getCoin() {
        return coin;
    }

    /**
     * Sets coin.
     *
     * @param coin the coin
     */
    public void setCoin(int coin) {
        this.coin = coin;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
