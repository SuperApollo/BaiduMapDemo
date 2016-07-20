package com.example.baidumapdemo.baidumapdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.example.baidumapdemo.baidumapdemo.R;
import com.example.baidumapdemo.baidumapdemo.Utils.SharedPreferencesUtils;
import com.example.baidumapdemo.baidumapdemo.Utils.ToastUtils;
import com.example.baidumapdemo.baidumapdemo.activity.BaseApplication;
import com.example.baidumapdemo.baidumapdemo.activity.MainActivity;
import com.example.baidumapdemo.baidumapdemo.activity.PlayActivity;
import com.example.baidumapdemo.baidumapdemo.common.AppConfig;
import com.example.baidumapdemo.baidumapdemo.constant.Constant;

/**
 * Created by ${Apollo} on 2016/4/8 21:44.
 */
public class ShowMapFragment extends BaseFragment {
    private static final String TAG = "ShowMapFragment";
    private boolean is_show;
    private TextView tvShame;
    private TextView tvSurprise;
    private TextView tvMore;
    private PopupWindow pop;
    private int displayWidth;
    private int displayHeight;
    private Camera camera;
    private Camera.Parameters p;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        setToogleView();
    }

    private void setToogleView() {
        is_show = SharedPreferencesUtils.getBoolean(AppConfig.IS_LAMP_ON);
        if (is_show) {
            btn_light.setBackgroundResource(R.drawable.icon_toogle_on);
        } else {
            btn_light.setBackgroundResource(R.drawable.icon_toogle_close);
        }
        setLamp(is_show);
    }

    private void setLamp(boolean isLampOn) {

        if (camera == null) {
            camera = Camera.open();
        }
        p = camera.getParameters();
        if (isLampOn) {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                }
            });
        } else {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_zoom:
                Constant.showZomm = !Constant.showZomm;
                //隐藏缩放按钮
                mapView.showZoomControls(Constant.showZomm);
//                Toast.makeText(v.getContext(), "是否显示缩放按钮" + Constant.showZomm, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_show_scale:
                Constant.showScale = !Constant.showScale;
                //隐藏缩放标尺
                mapView.showScaleControl(Constant.showScale);
//                Toast.makeText(v.getContext(), "是否显示缩放标尺" + Constant.showScale, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_location:
                BDLocation bdLocation = locationClient.getLastKnownLocation();
                SetCenter(bdLocation);
                break;
            case R.id.btn_showmap:
                if (!is_show) {
                    ll_search.setVisibility(View.VISIBLE);
                    is_show = true;
                } else {
                    ll_search.setVisibility(View.GONE);
                    is_show = false;
                }
                break;
            case R.id.btn_play:
                showPop();
                break;
            case R.id.tv_pop_shame:
                Intent intent = new Intent(getContext(), PlayActivity.class);
                startActivity(intent);
                pop.dismiss();
                backAlpha(1.0f);
                break;
            case R.id.tv_pop_surprise:
                showSurprise();
                pop.dismiss();
                backAlpha(1.0f);
                break;
            case R.id.tv_pop_more:
                ToastUtils.show(getContext(), "更多功能,敬请期待!");
                pop.dismiss();
                backAlpha(1.0f);
                break;
            case R.id.iv_surprize_show:
                dissmissSurprize();
                break;
            case R.id.btn_light:
                toogle();
                setToogleView();
                break;
            case R.id.btn_search:
                ToastUtils.show(getContext(),"还没做好哦!");
                break;
        }
    }

    private void toogle() {
        boolean isOn = SharedPreferencesUtils.getBoolean(AppConfig.IS_LAMP_ON);
        SharedPreferencesUtils.putBoolean(AppConfig.IS_LAMP_ON, !isOn);
    }

    private void showSurprise() {
        playVoice(R.raw.voice_only_you);
        ivSurprize.setVisibility(View.VISIBLE);
        WindowManager wm = (WindowManager) BaseApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        displayWidth = wm.getDefaultDisplay().getWidth();
        displayHeight = wm.getDefaultDisplay().getHeight();
        ScaleAnimation sa = new ScaleAnimation(0f, 1f, 0f, 1f, displayWidth / 2, displayHeight / 2);
        sa.setDuration(3000);
        sa.setRepeatCount(1);
        sa.setRepeatMode(Animation.REVERSE);
        sa.setFillAfter(true);
        RotateAnimation ra = new RotateAnimation(0, 720, displayWidth / 2, displayHeight / 2);
        ra.setDuration(3000);
        ra.setRepeatCount(1);
        ra.setRepeatMode(Animation.REVERSE);
        ra.setFillAfter(true);
        AlphaAnimation aa = new AlphaAnimation(0f, 1f);
        aa.setDuration(3000);
        aa.setRepeatCount(1);
        aa.setRepeatMode(Animation.REVERSE);
        aa.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(sa);
        set.addAnimation(ra);
        set.addAnimation(aa);
        ivSurprize.startAnimation(set);
    }

    private void dissmissSurprize() {
        ScaleAnimation sa = new ScaleAnimation(1f, 0f, 1f, 0f, displayWidth / 2, displayHeight / 2);
        sa.setDuration(4000);
        sa.setRepeatCount(1);
        sa.setRepeatMode(Animation.REVERSE);
        sa.setFillAfter(true);
        RotateAnimation ra = new RotateAnimation(0, 360, displayWidth / 2, displayHeight / 2);
        ra.setDuration(4000);
        ra.setRepeatCount(1);
        ra.setRepeatMode(Animation.REVERSE);
        ra.setFillAfter(true);
        AlphaAnimation aa = new AlphaAnimation(1f, 0f);
        aa.setDuration(4000);
//        aa.setRepeatCount(1);
        aa.setRepeatMode(Animation.REVERSE);
        aa.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(sa);
        set.addAnimation(ra);
        set.addAnimation(aa);
        ivSurprize.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivSurprize.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void showPop() {

        View content = LayoutInflater.from(getContext()).inflate(R.layout.layout_my_pop, null);
        tvShame = (TextView) content.findViewById(R.id.tv_pop_shame);
        tvSurprise = (TextView) content.findViewById(R.id.tv_pop_surprise);
        tvMore = (TextView) content.findViewById(R.id.tv_pop_more);
        tvShame.setOnClickListener(this);
        tvSurprise.setOnClickListener(this);
        tvMore.setOnClickListener(this);
        pop = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(false);
//        pop.setBackgroundDrawable(new BitmapDrawable());
//        pop.showAtLocation(parentView, Gravity.CENTER,0,-100);

//        WindowManager wm = (WindowManager) BaseApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
//        int displayWidth = wm.getDefaultDisplay().getWidth();
//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        content.measure(w, h);
//        int popWidth = content.getMeasuredWidth();
//        int popHeight = content.getMeasuredHeight();
//        int offset = (popWidth - displayWidth) / 2;
//        Log.i(TAG, "width: " + displayWidth + "pop: " + popWidth);
        pop.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        backAlpha(0.5f);
    }

    private void backAlpha(float alpha) {

        WindowManager.LayoutParams lp = MainActivity.shareInstance().getWindow().getAttributes();
        lp.alpha = alpha;
        MainActivity.shareInstance().getWindow().setAttributes(lp);
    }

}
