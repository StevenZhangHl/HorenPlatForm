package com.horen.partner.mvp.model;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.RxHelper;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.bean.IndustryBean;
import com.horen.partner.mvp.contract.AddPotentialCustomerContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/7 17:42
 * Description:This isCustomerModel
 */
public class AddPotentialCustomerModel implements AddPotentialCustomerContract.Model {

    @Override
    public Observable<List<IndustryBean>> getIndustryData(RequestBody body) {
        return ApiPartner.getInstance().getIndustryData(body).compose(RxHelper.<List<IndustryBean>>getResult());
    }

    @Override
    public Observable<BaseEntry> editCustomerInfo(RequestBody body) {
        return ApiPartner.getInstance().editCustomerInfo(body).compose(RxHelper.handleResult());
    }

}
