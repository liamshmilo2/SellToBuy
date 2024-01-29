package com.example.selltobuy.classes;

import android.graphics.Bitmap;
import android.util.Patterns;

public class Check {


    //הפעולה בודקת אם השם שהוכנס תקין
    public boolean checkName(String name)
    {
        if(!name.isEmpty())
            return true;
        return false;
    }


    //הפעולה בודקת אם האימייל שהוכנס תקין
    public boolean checkEmail (String email)
    {
        if(!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        return false;
    }

    //הפעולה בודקת אם הסיסמה שהוכנסה תקינה
    public boolean checkPass (String password)
    {
        if(!password.isEmpty() && password.length()>=6)
        {
            return true;
        }
        return false;
    }


    //הפעולה בודקת אם מספר הטלפון שהוכנס תקין
    public boolean checkPhone (String phone)
    {
        if(!phone.isEmpty() && Patterns.PHONE.matcher(phone).matches())
        {
            return  true;
        }
        return false;
    }


    //הפעולה בודקת אם הטקסט שהוכנס לא ריק
    public boolean chekInpo(String inpo)
    {
        if(!inpo.isEmpty())
            return true;
        return false;
    }

    //הפעולה בודקת אם המחיר שהוכנס תקין
    public boolean chekPrice(String price)
    {
        if(!price.isEmpty())
            return true;
        return false;
    }


    //הפעולה בודקת אם התמונה שהוכנסה תקינה
    public boolean chekBitmap(Bitmap bitmap)
    {
        if(bitmap!=null)
            return true;
        return false;
    }


}
