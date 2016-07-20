package com.example.baidumapdemo.baidumapdemo.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by ${Apollo} on 2016/6/3 14:49.
 */
public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
