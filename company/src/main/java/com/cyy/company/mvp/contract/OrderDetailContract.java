package com.cyy.company.mvp.contract;

import com.cyy.company.bean.OrderDetailBean;
import com.horen.base.bean.BaseEntry;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/13:59
 * @description :订单详情
 * @github :https://github.com/chenyy0708
 */
public interface OrderDetailContract {
    interface Model extends BaseModel {
        /**
         * 上游订单详情列表查询
         */
        Observable<OrderDetailBean> getOrderLineList(RequestBody body);

        /**
         * 订单取消
         */
        Observable<BaseEntry> cancelOrderInfo(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 收发货地址
         */
        void getAddressSuccess(OrderDetailBean.PageInfoBean bean);

        /**
         * 订单物品列表
         */
        void orderProducts(OrderDetailBean.PageInfoBean bean);

        /**
         * 租赁方式
         */
        void leaseMode(OrderDetailBean.PageInfoBean bean);

        /**
         * 订单信息
         */
        void orderInfo(OrderDetailBean.PageInfoBean bean);

        /**
         * 取消订单成功
         */
        void cancelOrderSuccess();

        void setEmptyView();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 上游订单详情列表查询
         */
        public abstract void getOrderLineList(String order_id, String order_status, String order_type, String company_id);

        /**
         * 取消订单
         */
        public abstract void cancelOrderInfo(String order_id);
    }
}
