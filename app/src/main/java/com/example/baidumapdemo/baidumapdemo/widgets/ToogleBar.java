package com.example.baidumapdemo.baidumapdemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by ${Apollo} on 2016/6/4 21:54.
 */
public class ToogleBar extends FrameLayout{
    public ToogleBar(Context context) {
        super(context);
    }

    public ToogleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToogleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {



        return super.onTouchEvent(event);
    }
}
