package com.horen.service.mvp.contract;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.service.bean.BillMainBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/13:59
 * @description :账单中心
 * @github :https://github.com/chenyy0708
 */
public interface BillMainContract {
    interface Model extends BaseModel {
        /**
         * 账单中心首页
         */
        Observable<BillMainBean> getAllBillList(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 账单中心首页
         */
        void getBillSuccess(List<BillMainBean.BillListBean> mData);

        /**
         * 设置饼状图中总金额
         *
         * @param allMoney 总金额
         */
        void setBillAllMoney(double allMoney);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 账单中心首页
         *
         * @param relativeMonth 账单时间
         * @param accountStatus 账单状态 1未完成，3已完成
         */
        public abstract void getAllBillList(String relativeMonth, String accountStatus);
    }
}
