package com.cyy.company.mvp.presenter;

import android.graphics.Color;

import com.baidu.mapapi.model.LatLng;
import com.cyy.company.R;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.HomeCoor;
import com.cyy.company.bean.HomeOkuraDetail;
import com.cyy.company.bean.HomeOrgDetail;
import com.cyy.company.bean.MapOkuraBean;
import com.cyy.company.enums.OrgStatus;
import com.cyy.company.mvp.contract.HomeMapContract;
import com.cyy.company.utils.DistanceHelper;
import com.horen.base.rx.BaseObserver;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.LogUtils;
import com.horen.maplib.MapHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/12:46
 * @description :百网千驿
 * @github :https://github.com/chenyy0708
 */
public class HomeMapPresenter extends HomeMapContract.Presenter {

    private MapHelper mapHelper;
    /**
     * 网点数据
     */
    private List<HomeCoor.PdListBean> mData;
    /**
     * 默认网点位置
     */
    private int defaultOutlet = 0;


    /**
     * 设置地图帮助类
     */
    @Override
    public void setMapHelper(MapHelper mapHelper) {
        this.mapHelper = mapHelper;
    }

    /**
     * 首页:百网千驿坐标查询
     */
    @Override
    public void getHomeCoorList() {
        // 清空地图数据
        mapHelper.clearMap();
        mRxManager.add(mModel.getHomeCoorList(CompanyParams.getHomeCoorList())
                .subscribeWith(new BaseObserver<HomeCoor>() {
                    @Override
                    protected void onSuccess(HomeCoor homeCoor) {
                        // 添加markerView
                        addMarker(homeCoor.getPdList());
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 添加Marker点
     */
    private void addMarker(List<HomeCoor.PdListBean> pdList) {
        mData = pdList;
        // 大仓位置
        for (int i = 0; i < pdList.size(); i++) {
            HomeCoor.PdListBean pdListBean = pdList.get(i);
            if (pdListBean.getOrg_status().equals(OrgStatus.ONE.getPosition())) { // 大仓
                mapHelper.addMarker(pdListBean.getOrg_latitude(), pdListBean.getOrg_longitude(), R.drawable.icon_warehouse_normal, i, MapHelper.NORMAL_ZINDEX);
            } else if (pdListBean.getOrg_status().equals(OrgStatus.TWO.getPosition())) { // 默认网点
                mapHelper.addLocation(pdListBean.getOrg_latitude(), pdListBean.getOrg_longitude(), R.drawable.ic_location);
                defaultOutlet = i;
            } else if (pdListBean.getOrg_status().equals(OrgStatus.THREE.getPosition())) { // 下游网点
                mapHelper.addMarker(pdListBean.getOrg_latitude(), pdListBean.getOrg_longitude(), R.drawable.icon_org_normal, i, MapHelper.NORMAL_ZINDEX);
            }
        }
        List<LatLng> latLngs = new ArrayList<>();
        for (HomeCoor.PdListBean pdListBean : pdList) {
            latLngs.add(new LatLng(pdListBean.getOrg_latitude(), pdListBean.getOrg_longitude()));
        }
        // 刷新层级
        mapHelper.setZoom(latLngs);
        // 计算和大仓的最短距离
        if (pdList.size() > 2) {
            int radius = (int) DistanceHelper.getMinDistance(pdList, defaultOutlet);
            LogUtils.d(radius + "");
            mapHelper.drawCircle(pdList.get(defaultOutlet).getOrg_latitude(), pdList.get(defaultOutlet).getOrg_longitude(), Color.parseColor("#386fba2c"),
                    Color.parseColor("#6b6fba2c"), radius, DisplayUtil.dip2px(1));
        }
    }

    /**
     * 返回点击对应位置的数据
     */
    @Override
    public HomeCoor.PdListBean getPositionData(int position) {
        return mData.get(position);
    }

    /**
     * 根据百网ID,产新信息查询
     *
     * @param position
     */
    @Override
    public void getOrgByIdsProType(final int position) {
        mRxManager.add(mModel.getOrgByIdsProType(CompanyParams.getOrderProNumber(mData.get(position).getOrg_id()))
                .subscribeWith(new BaseObserver<HomeOrgDetail>() {
                    @Override
                    protected void onSuccess(HomeOrgDetail coorDetail) {
                        mView.getOrgSuccess(coorDetail.getPdList(), getPositionData(position));
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    @Override
    public void getHomeOkuraList(final int position) {
        mRxManager.add(mModel.getHomeOkuraList(CompanyParams.getOrderProNumber(mData.get(position).getOrg_id()))
                .subscribeWith(new BaseObserver<HomeOkuraDetail>() {
                    @Override
                    protected void onSuccess(HomeOkuraDetail okuraDetail) {
                        // 分类液体包装
                        LinkedHashMap<String, List<HomeOkuraDetail.PdListBean>> map = new LinkedHashMap();
                        for (HomeOkuraDetail.PdListBean pdListBean : okuraDetail.getPdList()) {
                            // 没有此类产品
                            if (!map.containsKey(pdListBean.getProduct_industry())) {
                                ArrayList<HomeOkuraDetail.PdListBean> pdListBeans = new ArrayList<>();
                                pdListBeans.add(pdListBean);
                                map.put(pdListBean.getProduct_industry(), pdListBeans);
                            } else { // map已经包含此产品类型
                                List<HomeOkuraDetail.PdListBean> pdListBeans = map.get(pdListBean.getProduct_industry());
                                // 添加产品信息
                                pdListBeans.add(pdListBean);
                            }
                        }
                        // 最终整合数据
                        List<MapOkuraBean> mData = new ArrayList<>();
                        Iterator it = map.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            String key = (String) entry.getKey();
                            List<HomeOkuraDetail.PdListBean> value = (List<HomeOkuraDetail.PdListBean>) entry.getValue();
                            mData.add(new MapOkuraBean(key, value));
                        }
                        // 大仓物品数据
                        mView.getOkuraSuccess(mData, getPositionData(position));
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    public List<HomeCoor.PdListBean> getmData() {
        return mData;
    }

    /**
     * 默认网点集合位置
     */
    public int getDefaultOutlet() {
        return defaultOutlet;
    }
}
