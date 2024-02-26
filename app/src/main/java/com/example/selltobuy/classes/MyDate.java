package com.example.selltobuy.classes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * The type My date.
 */
public class MyDate implements Serializable {
    private  int year; //שנה
    private  int month; //חודש
    private  int day; //יום

    /**
     * הפעולה יוצרת תאריך חדש
     */
    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Instantiates a new My date.
     */
    public MyDate() {
    }

    /**
     * הפעולה מחזירה את השנה
     */
    public int getYear() {
        return year;
    }

    /**
     * הפעולה מקבל שנה ומשנה את השנה הנוכחית
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * הפעולה מחזירה את החודש
     */
    public int getMonth() {
        return month;
    }

    /**
     * הפעולה מקבל חודש ומשנה את החודש הנוחכי
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * הפעולה מחזירה את היום
     */
    public int getDay() {
        return day;
    }

    /**
     * הפעולה מקבל יום ומשנה את היום הנוכחי
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * הפעולה מדפיסה את התאריך
     */
    @Override
    public String toString() {
        return this.day+"/" + this.month +"/" + this.year;
    }


//    /**
//     * הפעולה משווה בין התאריך הנוכחי לתאריך חדש שהתקבל
//     *    אם הפעולה מחזירה 0 התאריכים זהים
//     *     אם הפעולה מחזירה מספר גדול מ 0 אז התאריך החדש בא אחרי התאריך הנוכחי
//     *     אם הפעולה מחזירה מספר קטן מ 0 אז התאריך החדש בא לפני התאריך הנוכחי
//     */
//
//    public int compareDates(MyDate date1) {
//        Calendar cal1 = Calendar.getInstance();
//        Date date2 = new Date(date1.getDay(),date1.getMonth(),date1.getYear());
//        cal1.setTime(date2);
//
//
//        Date date3 = new Date(this.day,this.month,this.year);
//        Calendar cal2 = Calendar.getInstance();
//        cal2.setTime(date3);
//
//        // Compare year, month, and day
//        int yearComparison = Integer.compare(cal1.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
//        if (yearComparison != 0) {
//            return yearComparison;
//        }
//
//        int monthComparison = Integer.compare(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
//        if (monthComparison != 0) {
//            return monthComparison;
//        }
//
//        return Integer.compare(cal1.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
//    }

}
