package com.example.selltobuy;

import android.util.Patterns;

public class Check {

    public boolean checkName(String name)
    {
        if(!name.isEmpty())
            return true;
        return false;
    }

    public boolean checkEmail (String email)
    {
        if(!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        return false;
    }

    public boolean checkPass (String password)
    {
        if(!password.isEmpty() && password.length()>=6)
        {
            return true;
        }
        return false;
    }

    public boolean checkPhone (String phone)
    {
        if(!phone.isEmpty() && Patterns.PHONE.matcher(phone).matches())
        {
            return  true;
        }
        return false;
    }


}
