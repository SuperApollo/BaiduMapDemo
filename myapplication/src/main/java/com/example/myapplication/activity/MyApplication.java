package com.example.myapplication.activity;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ${Apollo} on 2016/6/21 20:45.
 */
public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LeakCanary.install(this);
    }
    public static Context getContext() {
        return context;
    }
}
