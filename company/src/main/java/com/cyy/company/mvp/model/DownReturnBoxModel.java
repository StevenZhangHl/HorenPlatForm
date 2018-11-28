package com.cyy.company.mvp.model;

import com.cyy.company.api.ApiCompany;
import com.cyy.company.bean.RenewalAddress;
import com.cyy.company.bean.ReturnOrderPD;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.mvp.contract.DownReturnBoxContract;
import com.horen.base.rx.RxHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:46
 * @description :下游还箱
 * @github :https://github.com/chenyy0708
 */
public class DownReturnBoxModel implements DownReturnBoxContract.Model {
    /**
     * 下游地址
     *
     * @param body
     */
    @Override
    public Observable<RenewalAddress> getDownStreamOrgsList(RequestBody body) {
        return ApiCompany.getInstance().getDefaultOrgsList(body)
                .compose(RxHelper.<RenewalAddress>getResult());
    }


    /**
     * 下游还箱产品信息查询
     *
     * @param body
     */
    @Override
    public Observable<ReturnOrderPD> getUnderOrderProNumber(RequestBody body) {
        return ApiCompany.getInstance().getUnderOrderProNumber(body)
                .compose(RxHelper.<ReturnOrderPD>getResult());
    }

    @Override
    public Observable<SubmitOrder> saveOrderInfo(RequestBody body) {
        return ApiCompany.getInstance().saveUnderOrderInfo(body)
                .compose(RxHelper.<SubmitOrder>getResult());
    }
}
