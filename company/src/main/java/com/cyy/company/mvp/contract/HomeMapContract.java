package com.cyy.company.mvp.contract;

import com.cyy.company.bean.HomeCoor;
import com.cyy.company.bean.HomeOkuraDetail;
import com.cyy.company.bean.HomeOrgDetail;
import com.cyy.company.bean.MapOkuraBean;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.maplib.MapHelper;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/12:45
 * @description :百网千驿
 * @github :https://github.com/chenyy0708
 */
public interface HomeMapContract {
    interface Model extends BaseModel {
        /**
         * 百网千驿坐标查询
         */
        Observable<HomeCoor> getHomeCoorList(RequestBody body);

        /**
         * 根据百网ID,网点产品信息查询
         */
        Observable<HomeOrgDetail> getOrgByIdsProType(RequestBody body);

        /**
         * 根据百网ID,大仓产品信息查询
         */
        Observable<HomeOkuraDetail> getHomeOkuraList(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 网点产品信息列表
         */
        void getOrgSuccess(List<HomeOrgDetail.PdListBean> mData, HomeCoor.PdListBean address);

        /**
         * 大仓产品信息列表
         */
        void getOkuraSuccess(List<MapOkuraBean> mData, HomeCoor.PdListBean address);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 设置地图帮助类
         */
        public abstract void setMapHelper(MapHelper mapHelper);

        /**
         * 首页:百网千驿坐标查询
         */
        public abstract void getHomeCoorList();

        /**
         * 返回点击对应位置的数据
         */
        public abstract HomeCoor.PdListBean getPositionData(int position);

        /**
         * 根据百网ID,网点产品信息查询
         */
        public abstract void getOrgByIdsProType(int position);

        /**
         * 根据百网ID,大仓产品信息查询
         */
        public abstract void getHomeOkuraList(int position);
    }
}
