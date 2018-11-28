package com.horen.service.mvp.model;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.RxHelper;
import com.horen.service.api.Api;
import com.horen.service.bean.OrderDetail;
import com.horen.service.mvp.contract.BusinessContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:05
 * @description :出库单
 * @github :https://github.com/chenyy0708
 */
public class BusinessModel implements BusinessContract.Model {
    @Override
    public Observable<OrderDetail> getAllotAndTransInfo(RequestBody body) {
        return Api.getInstance().getAllotAndTransInfo(body)
                .compose(RxHelper.<OrderDetail>getResult());
    }

    @Override
    public Observable<BaseEntry> addStorage(RequestBody body) {
        return Api.getInstance().addStorage(body).compose(RxHelper.handleResult());
    }
}
