package com.example.baidumapdemo.baidumapdemo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.example.baidumapdemo.baidumapdemo.constant.Constant;
import com.example.baidumapdemo.baidumapdemo.R;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ShowMapActivity extends Activity {

    private MapView mapView;
    //116.347177,40.063397
    private LatLng latLng = new LatLng(40.063397, 116.347177);
    private BaiduReceiver baiduReceiver;
    private BaiduMap baiduMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMapSDK();//初始化sdk,校验key
        setContentView(R.layout.activity_showmap);

        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (baiduReceiver != null) {
            unregisterReceiver(baiduReceiver);
        }
    }

    private void initMapSDK() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        registerReceiver(baiduReceiver, filter);

        SDKInitializer.initialize(getApplicationContext());

    }

    class BaiduReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR:
                    Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
                    break;
                case SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR:
                    Toast.makeText(getApplicationContext(), "key校验错误", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }


    private void init() {
        if (baiduReceiver == null) {
            baiduReceiver = new BaiduReceiver();
        }

        mapView = (MapView) findViewById(R.id.mapview);
        baiduMap = mapView.getMap();

        //设置中心点
        MapStatusUpdate center = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(center);
        //设置默认缩放级别
        MapStatusUpdate zoom = MapStatusUpdateFactory.zoomTo(15);
        baiduMap.setMapStatus(zoom);
        //隐藏缩放按钮
        mapView.showZoomControls(Constant.showZomm);
        //隐藏缩放标尺
        mapView.showScaleControl(Constant.showScale);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                //放大一个级别
                MapStatusUpdate zoomIn = MapStatusUpdateFactory.zoomIn();
                baiduMap.setMapStatus(zoomIn);
                break;
            case KeyEvent.KEYCODE_1:
                //缩小一个级别
                MapStatusUpdate zoomOut = MapStatusUpdateFactory.zoomOut();
                baiduMap.setMapStatus(zoomOut);
                break;
            case KeyEvent.KEYCODE_2:
                //以地图中心点旋转
                float rotate = baiduMap.getMapStatus().rotate;
                Log.i("rotate:", rotate + "");

                MapStatus status = new MapStatus.Builder().rotate(rotate + 30).build();
                MapStatusUpdate rotateStatus = MapStatusUpdateFactory.newMapStatus(status);
                baiduMap.setMapStatus(rotateStatus);
                break;
            case KeyEvent.KEYCODE_3:
                //以一条线为中心旋转
                float overlooks = baiduMap.getMapStatus().overlook;
                Log.i("overlooks", overlooks + "");

                MapStatus statuss = new MapStatus.Builder().overlook(overlooks + 30).build();
                MapStatusUpdate overLook = MapStatusUpdateFactory.newMapStatus(statuss);
                baiduMap.setMapStatus(overLook);
                break;
            case KeyEvent.KEYCODE_4:
                //移动到点

                MapStatusUpdate pointStatus = MapStatusUpdateFactory.newLatLng(new LatLng(40.065796, 116.349868));
                baiduMap.animateMapStatus(pointStatus);
                break;


        }

        return super.onKeyDown(keyCode, event);
    }
}
