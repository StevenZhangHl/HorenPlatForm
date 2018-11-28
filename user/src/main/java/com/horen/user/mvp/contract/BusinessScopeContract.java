package com.horen.user.mvp.contract;

import com.horen.base.bean.BaseEntry;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.user.bean.OrgInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;


/**
 * @author Chen
 * Created by Chen
 * 业务范围
 */
public interface BusinessScopeContract {
    interface Model extends BaseModel {
        Observable<OrgInfo> getOrgInfo(RequestBody body);

        Observable<BaseEntry> updateOrgAddress(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 网点信息
         *
         * @param orgInfo 网点
         */
        void getOrgInfoSuccess(OrgInfo orgInfo);

        /**
         * 网点信息
         *
         * @param org_address   网点地址
         * @param org_latitude  纬度
         * @param org_longitude 经度
         */
        void updateOrgAddressSuccess(String org_address, double org_latitude, double org_longitude);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 获取网点信息
         */
        public abstract void getOrgInfo();

        /**
         * 个人中心-修改联系地址
         *
         * @param org_address   网点地址
         * @param org_id        网点ID
         * @param org_latitude  纬度
         * @param org_longitude 经度
         */
        public abstract void updateOrgAddress(String org_address, String org_id, double org_latitude, double org_longitude);
    }
}