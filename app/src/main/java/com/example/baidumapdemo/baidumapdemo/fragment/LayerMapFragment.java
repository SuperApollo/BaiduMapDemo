package com.example.baidumapdemo.baidumapdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.example.baidumapdemo.baidumapdemo.R;
import com.example.baidumapdemo.baidumapdemo.constant.Constant;
import com.example.baidumapdemo.baidumapdemo.fragment.BaseFragment;

/**
 * Created by ${Apollo} on 2016/4/8 22:33.
 */
public class LayerMapFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected void init() {
        super.init();
        setButton();
        //隐藏缩放按钮
        mapView.showZoomControls(false);
        //隐藏缩放标尺
        mapView.showScaleControl(false);
    }

    private void setButton() {
        btn_showmap.setText("底图");
        btn_show_zoom.setText("卫星图");
        btn_show_scale.setText("交通图");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_showmap:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                baiduMap.setTrafficEnabled(false);
                break;
            case R.id.btn_show_zoom:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                baiduMap.setTrafficEnabled(false);
                break;
            case R.id.btn_show_scale:
                baiduMap.setTrafficEnabled(true);
                break;
            case R.id.btn_location:
                BDLocation bdLocation = locationClient.getLastKnownLocation();
                SetCenter(bdLocation);
                break;
        }
    }
}
