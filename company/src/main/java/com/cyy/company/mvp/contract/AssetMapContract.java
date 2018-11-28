package com.cyy.company.mvp.contract;

import com.cyy.company.bean.AssetCheckBean;
import com.cyy.company.bean.AssetMapListBean;
import com.cyy.company.bean.HomeOrgDetail;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.maplib.MapHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/12:45
 * @description :资产分布
 * @github :https://github.com/chenyy0708
 */
public interface AssetMapContract {
    interface Model extends BaseModel {
        /**
         * 分布:地图模式查询
         */
        Observable<AssetMapListBean> getOrgMapList(RequestBody body);

        /**
         * 分布:下游 地图模式查询
         */
        Observable<AssetMapListBean> getOrgDownMapList(RequestBody body);

        /**
         * 根据百网ID,网点产品信息查询
         */
        Observable<HomeOrgDetail> getOrgByIdsProType(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 网点产品信息列表
         */
        void getOrgSuccess(HomeOrgDetail orgDetail, AssetMapListBean.PdListBean org);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 设置地图帮助类
         */
        public abstract void setMapHelper(MapHelper mapHelper);

        public abstract void getOrgList();

        /**
         * 返回点击对应位置的数据
         */
        public abstract AssetMapListBean.PdListBean getPositionData(int position);

        /**
         * 根据百网ID,网点产品信息查询
         */
        public abstract void getOrgByIdsProType(int position);
    }
}
