package com.example.selltobuy;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class MyDate implements Serializable {
    private  int yaer; //שנה
    private  int month; //חודש
    private  int day; //יום

    //הפעולה יוצרת תאריך חדש
    public MyDate(int yaer, int month, int day) {
        this.yaer = yaer;
        this.month = month;
        this.day = day;
    }

    public MyDate() {
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



    //הפעולה משווה בין התאריך הנוכחי לתאריך חדש שהתקבל
    //אם הפעולה מחזירה 0 התאריכים זהים
    //אם הפעולה מחזירה מספר גדול מ 0 אז התאריך החדש בא אחרי התאריך הנוכחי
    //אם הפעולה מחזירה מספר קטן מ 0 אז התאריך החדש בא לפני התאריך הנוכחי
    public int compareDates(MyDate date1) {
        Calendar cal1 = Calendar.getInstance();
        Date date2 = new Date(date1.getDay(),date1.getMonth(),date1.getYaer());
        cal1.setTime(date2);


        Date date3 = new Date(this.day,this.month,this.yaer);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date3);

        // Compare year, month, and day
        int yearComparison = Integer.compare(cal1.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        if (yearComparison != 0) {
            return yearComparison;
        }

        int monthComparison = Integer.compare(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        if (monthComparison != 0) {
            return monthComparison;
        }

        return Integer.compare(cal1.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
    }

}
