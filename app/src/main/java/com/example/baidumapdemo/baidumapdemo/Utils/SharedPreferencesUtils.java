package com.example.baidumapdemo.baidumapdemo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.baidumapdemo.baidumapdemo.activity.BaseApplication;

/**
 * Created by ${Apollo} on 2016/6/4 22:08.
 */
public class SharedPreferencesUtils {
    public static boolean putString(String name, String value) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(BaseApplication.getContext().getApplicationInfo().packageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (!TextUtils.isEmpty(name)) {
            editor.putString(name, value);
        }
        return editor.commit();
    }

    public static String getString(String name) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(BaseApplication.getContext().getApplicationInfo().packageName, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(name)){
            return sp.getString(name, "");
        }else {
            return null;
        }

    }

    public static boolean putBoolean(String name, boolean value) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(BaseApplication.getContext().getApplicationInfo().packageName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (!TextUtils.isEmpty(name)) {
            editor.putBoolean(name, value);
        }
        return editor.commit();
    }

    public static boolean getBoolean(String name) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(BaseApplication.getContext().getApplicationInfo().packageName, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(name)) {
            return sp.getBoolean(name, false);
        } else {
            return false;
        }
    }
}
