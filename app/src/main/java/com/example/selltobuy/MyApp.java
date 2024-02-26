package com.example.selltobuy;

import android.app.Activity;
import android.app.Application;

/**
 * The type My app.
 */
public class MyApp extends Application {
    public void onCreate() {
        super.onCreate();
    }

    private Activity mCurrentActivity = null;

    /**
     * Get current activity activity.
     *
     * @return the activity
     */
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    /**
     * Set current activity.
     *
     * @param mCurrentActivity the m current activity
     */
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }
}
