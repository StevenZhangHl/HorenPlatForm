package com.cyy.company.mvp.presenter;

import com.baidu.mapapi.model.LatLng;
import com.cyy.company.R;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AssetMapListBean;
import com.cyy.company.bean.HomeOrgDetail;
import com.cyy.company.mvp.contract.AssetMapContract;
import com.horen.base.rx.BaseObserver;
import com.horen.base.util.UserHelper;
import com.horen.maplib.MapHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/12:46
 * @description :资产分布
 * @github :https://github.com/chenyy0708
 */
public class AssetMapPresenter extends AssetMapContract.Presenter {

    private MapHelper mapHelper;
    /**
     * 网点数据
     */
    private List<AssetMapListBean.PdListBean> mData;

    /**
     * 设置地图帮助类
     */
    @Override
    public void setMapHelper(MapHelper mapHelper) {
        this.mapHelper = mapHelper;
    }

    /**
     * 资产分布:坐标查询
     */
    @Override
    public void getOrgList() {
        // 清空地图数据
        mapHelper.clearMap();
        Observable<AssetMapListBean> orgMapList = UserHelper.checkDownStream() ?
                mModel.getOrgDownMapList(CompanyParams.getAssetMapData()) : // 下游
                mModel.getOrgMapList(CompanyParams.getAssetMapData()); // 上游
        mRxManager.add(orgMapList
                .subscribeWith(new BaseObserver<AssetMapListBean>() {
                    @Override
                    protected void onSuccess(AssetMapListBean listBean) {
                        // 添加markerView
                        addMarker(listBean.getPdList());
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
    private void addMarker(List<AssetMapListBean.PdListBean> pdList) {
        mData = pdList;
        for (int i = 0; i < pdList.size(); i++) {
            AssetMapListBean.PdListBean pdListBean = pdList.get(i);
            if (pdListBean.getFlag_road().equals("0")) { // 网点
                mapHelper.addMarker(pdListBean.getOrg_latitude(), pdListBean.getOrg_longitude(), R.drawable.icon_eye_org_normal, i, MapHelper.NORMAL_ZINDEX);
            } else if (pdListBean.getFlag_road().equals("2")) { // 疑似网点
                mapHelper.addMarker(pdListBean.getOrg_latitude(), pdListBean.getOrg_longitude(), R.drawable.icon_suspected_org_normal, i, MapHelper.NORMAL_ZINDEX);
            } else if (pdListBean.getFlag_road().equals("1")) { // 在途
            }
        }
        List<LatLng> latLngs = new ArrayList<>();
        for (AssetMapListBean.PdListBean pdListBean : pdList) {
            latLngs.add(new LatLng(pdListBean.getOrg_latitude(), pdListBean.getOrg_longitude()));
        }
        // 刷新层级
        mapHelper.setZoom(latLngs);
    }

    /**
     * 返回点击对应位置的数据
     */
    @Override
    public AssetMapListBean.PdListBean getPositionData(int position) {
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
                    protected void onSuccess(HomeOrgDetail orgDetail) {
                        mView.getOrgSuccess(orgDetail, getPositionData(position));
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    public List<AssetMapListBean.PdListBean> getmData() {
        return mData;
    }
}
