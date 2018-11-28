package com.cyy.company.mvp.contract;

import com.cyy.company.bean.RenewalAddress;
import com.cyy.company.bean.RenewalRtp;
import com.cyy.company.bean.RentDays;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.ui.adapter.RenewalRtpAdapter;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:46
 * @description :租箱
 * @github :https://github.com/chenyy0708
 */
public interface RenewalBoxContract {
    interface Model extends BaseModel {
        Observable<RenewalAddress> getDefaultOrgsList(RequestBody body);

        Observable<RenewalRtp> getProductsList(RequestBody body);

        Observable<RentDays> getRentdaysList(RequestBody body);

        Observable<SubmitOrder> saveOrderInfo(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 选择租赁天数
         */
        void onSelectRentalDays(String rental_days);

        /**
         * 获取续租物品
         */
        void getProductsList(List<RenewalRtp.PdListBean> mData);

        /**
         * 选择运输方式
         */
        void onSelectTransportMode(String flag_send);

        /**
         * 选择物品Adapter，用于提交订单
         */
        RenewalRtpAdapter getAdapter();

        /**
         * 提交订单成功
         */
        void onSubmitSuccess(SubmitOrder order);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 选择租赁天数
         */
        public abstract void showRentalDays();

        /**
         * 选择配送方式
         */
        public abstract void showTransportMode();

        /**
         * 上游续租产品信息查询
         */
        public abstract void getProductsList(String set_rentdays);

        /**
         * 金额明细
         */
        public abstract void showAmountDialog();

        /**
         * 检查提交按钮状态  （地址、租赁天数、运输方式、预计使用时间）
         */
        public abstract boolean checkStatus();

        /**
         * 计算金额价格
         */
        public abstract void calculate(List<RenewalRtp.PdListBean> mData);

        /**
         * 提交订单
         */
        public abstract void submitOrder(String order_note);

        /**
         * 默认地址
         */
        public abstract void getDefaultOrgsList();
    }
}
