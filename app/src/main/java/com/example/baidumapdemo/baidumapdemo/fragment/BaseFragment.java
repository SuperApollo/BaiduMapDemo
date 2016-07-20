package com.example.baidumapdemo.baidumapdemo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.baidumapdemo.baidumapdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Apollo} on 2016/4/8 21:39.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "BaseFragment";
    protected MapView mapView;
    protected Button btn_showmap;
    protected Button btn_show_zoom;
    protected Button btn_show_scale;
    protected BaiduReceiver baiduReceiver;
    protected BaiduMap baiduMap;
    protected LatLng latLngDefault = new LatLng(40.063397, 116.347177);
    protected LocationClient locationClient;
    private boolean isFirst = true;
    protected LinearLayout ll_search;
    protected Button btn_search;
    protected Button btn_play;
    protected View parentView;
    protected ImageView ivSurprize;
    private BitmapDescriptor mCurrentIcon;
    protected Button btn_location;
    protected boolean isLightOn = false;
    protected Button btn_light;


    private LatLng latLngCurrent;
    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (isFirst) {
                if (bdLocation == null || mapView == null) {
                    return;
                }
                String address = bdLocation.getAddress().address;
                Log.i(TAG, "地址=====" + address);
                MyLocationData myLocationData = new MyLocationData.Builder()
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude()).build();
                baiduMap.setMyLocationData(myLocationData);//设置定位 数据
                //设置中心点
                SetCenter(bdLocation);
                isFirst = false;

            }


        }

    };


    private class DoInBack extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Thread.sleep(2000);
                playVoice(R.raw.voice_niuniu_meme);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private MediaPlayer player;

    /**
     * 播放音频哈哈
     */
    protected void playVoice(int resouce) {
        Log.i(TAG,"hahahahahahahhah======");
        player = new MediaPlayer().create(getContext(), resouce);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.stop();
            }
        });
        player.start();

//        //音效播放池
//        SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//        //存放音效的HashMap
//        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//        map.put(1,soundPool.load(getContext(),R.raw.voice_hahaha,1));
//        AudioManager mgr = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
//        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
//        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        float volume = streamVolumeCurrent/streamVolumeMax;
//        soundPool.play(map.get(1), volume, volume, 1, 0, 1f);
//        //参数：1、Map中取值   2、当前音量     3、最大音量  4、优先级   5、重播次数   6、播放速度

    }

    protected void SetCenter(BDLocation bdLocation) {
        latLngCurrent = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        MapStatusUpdate center = MapStatusUpdateFactory.newLatLng(latLngCurrent);
        baiduMap.setMapStatus(center);

        MarkerOptions options = new MarkerOptions();
        BitmapDescriptor bd1 = BitmapDescriptorFactory.fromResource(R.drawable.icon_location_db32_on);
        BitmapDescriptor bd2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_location_db32_off);
        List<BitmapDescriptor> icons = new ArrayList();
        icons.add(bd1);
        icons.add(bd2);
        options.position(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()))
                .icons((ArrayList<BitmapDescriptor>) icons).period(20).title("妞妞在这里");
        baiduMap.clear();
        baiduMap.addOverlay(options);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initMapSDK();//初始化sdk,校验key
        parentView = inflater.inflate(R.layout.fragment_base, container, false);
        parentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mapView = (MapView) parentView.findViewById(R.id.mapview);
        btn_showmap = (Button) parentView.findViewById(R.id.btn_showmap);
        btn_show_zoom = (Button) parentView.findViewById(R.id.btn_show_zoom);
        btn_show_scale = (Button) parentView.findViewById(R.id.btn_show_scale);
        btn_location = (Button) parentView.findViewById(R.id.btn_location);
        btn_search = (Button) parentView.findViewById(R.id.btn_search);
        ll_search = (LinearLayout) parentView.findViewById(R.id.ll_search);
        btn_play = (Button) parentView.findViewById(R.id.btn_play);
        ivSurprize = (ImageView) parentView.findViewById(R.id.iv_surprize_show);
        btn_light = (Button) parentView.findViewById(R.id.btn_light);

        btn_show_zoom.setOnClickListener(this);
        btn_show_scale.setOnClickListener(this);
        btn_showmap.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        ivSurprize.setOnClickListener(this);
        btn_light.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        init();
        return parentView;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 移动中心点
     *
     * @param ll
     */
    private void moveToCenter(LatLng ll) {
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.setMapStatus(u);
    }

    protected void init() {
        baiduMap = mapView.getMap();
        //设置中心点
        MapStatusUpdate center = MapStatusUpdateFactory.newLatLng(latLngDefault);
        baiduMap.setMapStatus(center);
        //设置默认缩放级别
        MapStatusUpdate zoom = MapStatusUpdateFactory.zoomTo(15);
        baiduMap.setMapStatus(zoom);
        //除去百度logo
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        mapView.showScaleControl(false);
        mapView.showZoomControls(false);
        //更改定位图标
        mCurrentIcon = BitmapDescriptorFactory.fromResource(R.drawable.icon_trasparent);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentIcon));

        new DoInBack().execute();
    }

    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setScanSpan(5000);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setNeedDeviceDirect(true);

        locationClient.setLocOption(locationClientOption);
    }

    @Override
    public void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);//打开定位图层
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationClient.registerLocationListener(myListener);//注册定位监听
        setLocationOption();
        locationClient.start();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        getContext().unregisterReceiver(baiduReceiver);
        if (player!=null){
            player.stop();
            player.release();
            player = null;
        }
        super.onDestroy();
    }


    private void initMapSDK() {
        if (baiduReceiver == null) {
            baiduReceiver = new BaiduReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        SDKInitializer.initialize(getActivity().getApplicationContext());
        getContext().registerReceiver(baiduReceiver, filter);
    }

    class BaiduReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR:
                    Toast.makeText(getActivity().getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
                    break;
                case SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR:
                    Toast.makeText(getActivity().getApplicationContext(), "key校验错误", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

}
