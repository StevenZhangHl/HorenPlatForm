package com.cyy.company.mvp.contract;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.bean.RentDays;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.ui.adapter.OrderRtpAdapter;
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
public interface RentBoxContract {
    interface Model extends BaseModel {
        Observable<DefaultOrgBean> getDefaultOrgsList(RequestBody body);

        Observable<OrderProducts> getProductsList(RequestBody body);

        Observable<RentDays> getRentdaysList(RequestBody body);

        Observable<SubmitOrder> saveOrderInfo(RequestBody body);
    }

    interface View extends BaseView {

        /**
         * 选择物品
         */
        void onSelectProducts(OrderProducts.PdListBean bean);

        /**
         * 选择租赁天数
         */
        void onSelectRentalDays(String rental_days);

        /**
         * 选择运输方式
         */
        void onSelectTransportMode(String flag_send);

        /**
         * 选择预计使用时间
         */
        void onSelectTime(String expect_arrivedate);

        /**
         * 选择物品Adapter，用于提交订单
         */
        OrderRtpAdapter getAdapter();

        /**
         * 提交订单成功
         */
        void onSubmitSuccess(String order_id);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 处理数据的回显
         */
        public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

        /**
         * 选择租赁天数
         */
        public abstract void showRentalDays();

        /**
         * 选择配送方式
         */
        public abstract void showTransportMode();

        /**
         * 预计送达时间
         */
        public abstract void showTimeSelect();

        /**
         * 金额明细
         */
        public abstract void showAmountDialog();

        /**
         * 选择物品
         */
        public abstract void showSelectProducts(FragmentManager fragmentManager, List<OrderProducts.PdListBean> mData);

        /**
         * 检查提交按钮状态  （地址、租赁天数、运输方式、预计使用时间）
         */
        public abstract boolean checkStatus();

        /**
         * 计算金额价格
         */
        public abstract void calculate(List<OrderProducts.PdListBean> mData);

        /**
         * 提交订单
         */
        public abstract void submitOrder(String order_note);
    }
}
