package com.horen.service.mvp.contract;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.service.bean.BillDetailBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/13:59
 * @description :账单中心详情
 * @github :https://github.com/chenyy0708
 */
public interface BillDetailContract {
    interface Model extends BaseModel {
        /**
         * 账单中心详情
         */
        Observable<BillDetailBean> getBillDetail(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 耗材服务费、产品出入费
         */
        void setSuppliesCharge(BillDetailBean mData);
        /**
         * 维修费
         */
        void setRepairCharge(BillDetailBean mData);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 账单中心首页
         *
         * @param cost_type     账单类型
         * @param relativeMonth 账单时间
         */
        public abstract void getBillDetail(String cost_type, String relativeMonth);
    }
}
