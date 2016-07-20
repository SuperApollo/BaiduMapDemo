package com.example.myapplication.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by ${Apollo} on 2016/6/21 20:50.
 */
public class FragmentUtils {
    public static void replace(FragmentManager fm, int container, Fragment toFragment){
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container,toFragment);
        transaction.commit();
    }
}
