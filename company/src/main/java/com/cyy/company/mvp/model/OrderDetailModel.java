package com.cyy.company.mvp.model;

import com.cyy.company.api.ApiCompany;
import com.cyy.company.bean.OrderDetailBean;
import com.cyy.company.mvp.contract.OrderDetailContract;
import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.RxHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:05
 * @description :订单详情
 * @github :https://github.com/chenyy0708
 */
public class OrderDetailModel implements OrderDetailContract.Model {

    @Override
    public Observable<OrderDetailBean> getOrderLineList(RequestBody body) {
        return ApiCompany.getInstance().getOrderLineList(body)
                .compose(RxHelper.<OrderDetailBean>getResult());
    }

    @Override
    public Observable<BaseEntry> cancelOrderInfo(RequestBody body) {
        return ApiCompany.getInstance().cancelOrderInfo(body)
                .compose(RxHelper.handleResult());
    }
}
