package com.example.selltobuy;

public class Date {
    private  int yaer;
    private  int month;
    private  int day;

    public Date(int yaer, int month, int day) {
        this.yaer = yaer;
        this.month = month;
        this.day = day;
    }

    public int getYaer() {
        return yaer;
    }

    public void setYaer(int yaer) {
        this.yaer = yaer;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return this.day+"/" + this.month +"/" + this.yaer;
    }
}
