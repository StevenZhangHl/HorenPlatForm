package com.cyy.company.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.cyy.company.R;
import com.cyy.company.utils.DateUtils;
import com.horen.maplib.DrivingRouteOverlay;

public class MyDrivingRouteOverlay extends DrivingRouteOverlay {

    private Context mContext;

    private String startOrgName, endOrgName, endTime;

    /**
     * 构造函数
     *
     * @param baiduMap 该DrivingRouteOvelray引用的 BaiduMap
     */
    public MyDrivingRouteOverlay(BaiduMap baiduMap, Context mContext, String startOrgName, String endOrgName, String endTime) {
        super(baiduMap);
        this.mContext = mContext;
        this.startOrgName = startOrgName;
        this.endOrgName = endOrgName;
        this.endTime = endTime;
    }

    @Override
    public BitmapDescriptor getStartMarker() {
        View start = LayoutInflater.from(mContext).inflate(R.layout.marker_start_position, null);
        TextView mTvStartOrgName = start.findViewById(R.id.tv_org_name);
        mTvStartOrgName.setText(startOrgName);
        return BitmapDescriptorFactory.fromView(start);
    }

    @Override
    public BitmapDescriptor getTerminalMarker() {
        View end = LayoutInflater.from(mContext).inflate(R.layout.marker_end_position, null);
        TextView mTvendOrgName = end.findViewById(R.id.tv_org_name);
        TextView mTvendTvTime = end.findViewById(R.id.tv_time);
        mTvendOrgName.setText(endOrgName);
        mTvendTvTime.setText(DateUtils.formatMonthDay(endTime) + "完成");
        return BitmapDescriptorFactory.fromView(end);
    }

    @Override
    public int getLineColor() {
        return Color.parseColor("#AFE06B");
    }

}
