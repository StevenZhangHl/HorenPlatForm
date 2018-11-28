package com.cyy.company.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.baidu.mapapi.animation.AlphaAnimation;
import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.ScaleAnimation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.cyy.company.R;
import com.cyy.company.utils.DistanceHelper;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.LogUtils;
import com.horen.maplib.LocationHelper;
import com.horen.maplib.MapHelper;
import com.horen.maplib.MapListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/07/31/13:30
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class MapFragment extends BaseFragment implements MapListener.MapClickListener, MapListener.MarkerClickListener {

    private TextureMapView mMapView;
    private MapHelper mapHelper;
    private List<LatLng> latLngs;
    private EditText editText;
    private FrameLayout flContainer;

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取布局文件
     *
     * @return 布局Id
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    @Override
    public void initPresenter() {
    }

    /**
     * 初始化view
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void initView(Bundle savedInstanceState) {
        flContainer = rootView.findViewById(R.id.fl_container);
        mMapView = rootView.findViewById(R.id.mmap);
        mapHelper = new MapHelper(_mActivity, mMapView);
        mapHelper.getBaiduMap().setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                // 定位
                LocationHelper.getLocation(_mActivity, new MapListener.OnLocationListener() {
                    @Override
                    public void location(double lat, double lng) {
                        getData(31.145775, 121.424456);
                    }
                });
            }
        });
        mapHelper.setOnMapClickListener(this);
        mapHelper.setOnMarkerClickListener(this);
    }

    private void getData(double lat, double lng) {
        // 定位
        LatLng mLocation = new LatLng(lat, lng);
        latLngs = new ArrayList<>();
        latLngs.add(new LatLng(31.339933415733693, 121.58883146145833));
        latLngs.add(new LatLng(31.224950066808844, 121.43285214770944));
        latLngs.add(new LatLng(31.015952618856446, 121.37641143887498));
        latLngs.add(new LatLng(31.163982907147894, 121.30678802929651));
        latLngs.add(new LatLng(31.011683644877664, 121.31022845867258));
        for (int i = 0; i < latLngs.size(); i++) {
            LatLng latLng = latLngs.get(i);
            mapHelper.addMarker(latLng.latitude, latLng.longitude, R.drawable.icon_org_normal, i, MapHelper.NORMAL_ZINDEX);
        }
        // 添加定位坐标
        mapHelper.addLocation(lat, lng, R.drawable.ic_location);
        // 缩放比例
        mapHelper.setZoomWithLatLngs(latLngs);
        // 计算最短距离
        // 画圆
//        mapHelper.drawCircle(lat, lng, Color.TRANSPARENT, ContextCompat.getColor(_mActivity, R.color.color_main), radius);
        // 添加marker动画
        for (int i = 0; i < mapHelper.getMarkers().size(); i++) {
            Marker marker = mapHelper.getMarkers().get(i);
            marker.setAnimation(getScaleAnimation());
            marker.startAnimation();
        }
        // 画每一个点的弧线
        for (LatLng latLng : latLngs) {
            List<LatLng> bezierPaths = DistanceHelper.getBezierPaths(mLocation, latLng);
            mapHelper.drawArc(mLocation, bezierPaths.get(bezierPaths.size() / 2), latLng,
                    ContextCompat.getColor(_mActivity, R.color.line_chart_send_color), DisplayUtil.dip2px(1.5f));
        }

    }


    /**
     * 地图点击监听
     *
     * @param lat 经度
     * @param lng 纬度
     */
    @Override
    public void onMapClickListener(double lat, double lng) {
        mapHelper.changeMarkerIcon(mapHelper.getPreviousPosition(), R.drawable.icon_org_normal, MapHelper.NORMAL_ZINDEX);
        mapHelper.setPreviousPosition(MapHelper.NORMAL);
    }

    /**
     * Marker点击监听
     *
     * @param currentPosition  当前点击Marker的索引位置
     * @param previousPosition 上一个点击Marker的索引位置
     */
    @Override
    public void onMarkerClickListener(int currentPosition, int previousPosition) {
        if (currentPosition == Integer.MAX_VALUE) return;
        // 切换上一个Marker的图标
        if (previousPosition != MapHelper.NORMAL) {
            mapHelper.changeMarkerIcon(previousPosition, R.drawable.icon_org_normal, MapHelper.NORMAL_ZINDEX);
        }
        // 运行动画
        mapHelper.changeMarkerIcon(currentPosition, R.drawable.icon_org_select, MapHelper.SELECT_ZINDEX);
        mapHelper.getMarkers().get(currentPosition).setAnimation(getScaleAnimation());
        mapHelper.getMarkers().get(currentPosition).startAnimation();
        mapHelper.moveTo(latLngs.get(currentPosition).latitude, latLngs.get(currentPosition).longitude);
    }

    /**
     * 创建缩放动画
     */
    private Animation getScaleAnimation() {
        ScaleAnimation mScale = new ScaleAnimation(0f, 1.1f, 1f);
        mScale.setInterpolator(new AnticipateOvershootInterpolator());
        mScale.setDuration(300);
        return mScale;
    }

    /**
     * 创建透明度动画
     */
    private Animation getAlphaAnimation() {
        AlphaAnimation mAlphaAnimation = new AlphaAnimation(0f, 1f);
        mAlphaAnimation.setInterpolator(new AnticipateOvershootInterpolator());
        mAlphaAnimation.setDuration(1000);
        return mAlphaAnimation;
    }

}
