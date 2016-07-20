package com.example.baidumapdemo.baidumapdemo.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ${Apollo} on 2016/5/31 21:20.
 */
public class ToastUtils {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
