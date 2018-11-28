package com.horen.service.mvp.model;

import com.horen.base.rx.RxHelper;
import com.horen.service.api.Api;
import com.horen.service.bean.BillDetailBean;
import com.horen.service.mvp.contract.BillDetailContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:05
 * @description :账单中心
 * @github :https://github.com/chenyy0708
 */
public class BillDetailModel implements BillDetailContract.Model {

    @Override
    public Observable<BillDetailBean> getBillDetail(RequestBody body) {
        return Api.getInstance().getbillDetail(body)
                .compose(RxHelper.<BillDetailBean>getResult());
    }
}
