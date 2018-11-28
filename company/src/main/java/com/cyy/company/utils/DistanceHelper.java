package com.cyy.company.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.cyy.company.bean.HomeCoor;
import com.cyy.company.enums.OrgStatus;
import com.horen.base.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/09/13:55
 * @description :百度地图距离工具类
 * @github :https://github.com/chenyy0708
 */
public class DistanceHelper {

    /**
     * 计算默认网点和大仓的最短距离
     *
     * @param mData         点数据
     * @param defaultOutlet 默认网点在集合中的位置
     * @return
     */
    public static double getMinDistance(List<HomeCoor.PdListBean> mData, int defaultOutlet) {
        // 默认网点
        LatLng orgLat = new LatLng(mData.get(defaultOutlet).getOrg_latitude(), mData.get(defaultOutlet).getOrg_longitude());
        // 默认值为第一个点距离
        double minDistance = DistanceUtil.getDistance(orgLat, new LatLng(mData.get(0).getOrg_latitude(), mData.get(0).getOrg_longitude()));
        for (HomeCoor.PdListBean mDatum : mData) {
            // 计算离大仓最近的距离
            if (mDatum.getOrg_status().equals(OrgStatus.ONE.getPosition())) {
                LatLng latLng = new LatLng(mDatum.getOrg_latitude(), mDatum.getOrg_longitude());
                // 两点距离
                double distance = DistanceUtil.getDistance(orgLat, latLng);
                if (minDistance >= distance) minDistance = distance;
            }
        }
        return minDistance;
    }

    /**
     * 获取两个点之间的中心点 经纬度
     */
    public static LatLng getCenterPoint(LatLng p1, LatLng p2) {
        LatLng auxiliaryPoint = fetchThirdPointByLocations(p1, p2, 30, true);
        double bezier1x;
        double bezier1y;
        double bezier2x;
        double bezier2y;
        double bezier_x, bezier_y;
        float t = 0.5f;
        //t between 0.01 and 1
        //get the start point of a Bezier curve
        bezier1x = p1.longitude + (auxiliaryPoint.longitude - p1.longitude) * t;
        bezier1y = p1.latitude + (auxiliaryPoint.latitude - p1.latitude) * t;
        //get the end point of a Bezier curve
        bezier2x = auxiliaryPoint.longitude + (p2.longitude - auxiliaryPoint.longitude) * t;
        bezier2y = auxiliaryPoint.latitude + (p2.latitude - auxiliaryPoint.latitude) * t;
        //get the point of quadratic Bezier curve
        bezier_x = bezier1x + (bezier2x - bezier1x) * t;
        bezier_y = bezier1y + (bezier2y - bezier1y) * t;
        LatLng bezierPoint = new LatLng(bezier_y, bezier_x);
        return bezierPoint;
    }

    /**
     * 获取曲线路径点
     */
    public static List<LatLng> getBezierPaths(LatLng p1, LatLng p2) {
        LatLng auxiliaryPoint = fetchThirdPointByLocations(p1, p2, 30, false);
        double bezier1x;
        double bezier1y;
        double bezier2x;
        double bezier2y;
        double bezier_x, bezier_y;
        float t = 0f;

        List<LatLng> latLngs = new ArrayList<>();
        while (latLngs.size() < 100) {
            //t between 0.01 and 1
            //get the start point of a Bezier curve
            bezier1x = p1.longitude + (auxiliaryPoint.longitude - p1.longitude) * t;
            bezier1y = p1.latitude + (auxiliaryPoint.latitude - p1.latitude) * t;
            //get the end point of a Bezier curve
            bezier2x = auxiliaryPoint.longitude + (p2.longitude - auxiliaryPoint.longitude) * t;
            bezier2y = auxiliaryPoint.latitude + (p2.latitude - auxiliaryPoint.latitude) * t;
            //get the point of quadratic Bezier curve
            bezier_x = bezier1x + (bezier2x - bezier1x) * t;
            bezier_y = bezier1y + (bezier2y - bezier1y) * t;
            LatLng bezierPoint = new LatLng(bezier_y, bezier_x);
            latLngs.add(bezierPoint);
            t += 0.01f;
        }
        LogUtils.d(latLngs.toString());
        return latLngs;
    }

    public static LatLng fetchThirdPointByLocations(LatLng startLoc, LatLng endLoc, float angle, boolean isClockWise) {
        LatLng target;

        //angle between the two points
        double btpAngle = 0.0;
        btpAngle = Math.atan2(Math.abs(startLoc.latitude - endLoc.latitude), Math.abs(startLoc.longitude - endLoc.longitude)) * 180 / Math.PI;

        //center point
        LatLng center = new LatLng((startLoc.latitude + endLoc.latitude) / 2.0, (startLoc.longitude + endLoc.longitude) / 2.0);

        //distance between the two points
        double distance = Math.sqrt((startLoc.latitude - endLoc.latitude) * (startLoc.latitude - endLoc.latitude) + (startLoc.longitude - endLoc.longitude) * (startLoc.longitude - endLoc.longitude));

        //distance taget point between and center point
        double adis = (distance / 2.0) * Math.tan(angle * Math.PI / 180);

        //target distance  longt and lat
        double longt = adis * Math.cos((90 - btpAngle) * Math.PI / 180);

        double lat = adis * Math.sin((90 - btpAngle) * Math.PI / 180);

        if (startLoc.longitude > endLoc.longitude) {
//            isClockWise = !isClockWise;
        }
        //to get the right side of target
        if (isClockWise) {
            target = new LatLng(center.latitude + lat, center.longitude + longt);
        } else {
            target = new LatLng(center.latitude - lat, center.longitude - longt);
        }
        //avoid the target out of the map
        if (target.latitude > 90) {
            target = new LatLng(90.0f, target.longitude);
        } else if (target.latitude < -90) {
            target = new LatLng(-90.0f, target.longitude);
        }
        if (target.longitude > 180) {
            target = new LatLng(target.latitude, target.longitude - 360.0);
        } else if (target.longitude < -180) {
            target = new LatLng(target.latitude, 360.0f + target.longitude);
        }
        return target;
    }
}
