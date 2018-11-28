package com.cyy.company.mvp.model;

import com.cyy.company.api.ApiCompany;
import com.cyy.company.bean.RenewalAddress;
import com.cyy.company.bean.RenewalRtp;
import com.cyy.company.bean.RentDays;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.mvp.contract.RenewalBoxContract;
import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.RxHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:46
 * @description :租箱
 * @github :https://github.com/chenyy0708
 */
public class RenewalBoxModel implements RenewalBoxContract.Model {
    @Override
    public Observable<RenewalAddress> getDefaultOrgsList(RequestBody body) {
        return ApiCompany.getInstance().getDefaultOrgsList(body)
                .compose(RxHelper.<RenewalAddress>getResult());
    }

    @Override
    public Observable<RenewalRtp> getProductsList(RequestBody body) {
        return ApiCompany.getInstance().getContProductsList(body)
                .compose(RxHelper.<RenewalRtp>getResult());
    }

    @Override
    public Observable<RentDays> getRentdaysList(RequestBody body) {
        return ApiCompany.getInstance().getRentdaysList(body)
                .compose(RxHelper.<RentDays>getResult());
    }

    @Override
    public Observable<SubmitOrder> saveOrderInfo(RequestBody body) {
        return ApiCompany.getInstance().saveContLeaOrderInfo(body)
                .compose(RxHelper.<SubmitOrder>getResult());
    }
}
