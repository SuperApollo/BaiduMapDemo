package com.example.baidumapdemo.baidumapdemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.baidumapdemo.baidumapdemo.R;
import com.example.baidumapdemo.baidumapdemo.fragment.ShowMapFragment;
import com.example.baidumapdemo.baidumapdemo.fragment.LayerMapFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static MainActivity instance;
    private Button btn_base_map;
    private Button btn_layer_map;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
        creaateInstance();
    }

    private synchronized void  creaateInstance() {
        instance = this;
    }

    public static MainActivity shareInstance(){
        return instance;
    }

    private void initFragment() {
        setFragment("基本地图展示",new ShowMapFragment());
    }

    private void initView() {
        btn_base_map = (Button) findViewById(R.id.btn_base_map);
        btn_layer_map = (Button) findViewById(R.id.btn_layer_map);

        btn_base_map.setOnClickListener(this);
        btn_layer_map.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_base_map:
                setFragment("基本地图展示",new ShowMapFragment());
                break;
            case R.id.btn_layer_map:
                setFragment("地图图层展示",new LayerMapFragment());
                break;
            default:
                break;
        }
    }

    private void setFragment(String str,Fragment fragment) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_container,fragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_HOME||keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
