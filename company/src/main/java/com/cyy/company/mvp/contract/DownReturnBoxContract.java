package com.cyy.company.mvp.contract;

import android.content.Intent;

import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.bean.RenewalAddress;
import com.cyy.company.bean.ReturnOrderPD;
import com.cyy.company.bean.ReturnProFreight;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.ui.adapter.OrderReturnPDAdapter;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:46
 * @description :下游还箱
 * @github :https://github.com/chenyy0708
 */
public interface DownReturnBoxContract {
    interface Model extends BaseModel {
        /**
         * 下游地址
         */
        Observable<RenewalAddress> getDownStreamOrgsList(RequestBody body);
        /**
         * 下游还箱产品信息查询
         */
        Observable<ReturnOrderPD> getUnderOrderProNumber(RequestBody body);

        Observable<SubmitOrder> saveOrderInfo(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 选择运输方式
         */
        void onSelectTransportMode(String flag_send);

        /**
         * 提交订单成功
         */
        void onSubmitSuccess(SubmitOrder submitOrder);

        /**
         * 还箱物品
         */
        void getOrderProdouctList(List<ReturnOrderPD.PdListBean> mData);

        /**
         * 物品Adapter，用于提交订单
         */
        OrderReturnPDAdapter getAdapter();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 选择配送方式
         */
        public abstract void showTransportMode();
        /**
         * 检查提交按钮状态  （地址、租赁天数、运输方式、预计使用时间）
         */
        public abstract boolean checkStatus();
        /**
         * 上游还箱产品信息查询
         */
        public abstract void getOrderProNumber(String org_id);

        /**
         * 提交订单
         */
        public abstract void submitOrder(String order_note);

        /**
         * 默认下游网点大仓地址
         */
        public abstract void getDownStreamOrgsList();
    }
}
