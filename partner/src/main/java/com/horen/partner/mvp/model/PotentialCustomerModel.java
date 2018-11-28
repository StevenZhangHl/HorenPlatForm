package com.horen.partner.mvp.model;

import com.horen.base.rx.RxHelper;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.bean.PotentialBean;
import com.horen.partner.mvp.contract.PotentialCustomerContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/8 13:48
 * Description:This isPotentialCustomerModel
 */
public class PotentialCustomerModel implements PotentialCustomerContract.Model {
    @Override
    public Observable<PotentialBean> getPotentialData(RequestBody body) {
        return ApiPartner.getInstance().getPotentialData(body).compose(RxHelper.<PotentialBean>getResult());
    }
}
