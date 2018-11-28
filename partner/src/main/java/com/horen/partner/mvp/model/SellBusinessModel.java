package com.horen.partner.mvp.model;

import com.horen.base.rx.RxHelper;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.BusinessLineBean;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.OrderBean;
import com.horen.partner.mvp.contract.SellBusinessContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/17 14:51
 * Description:This isSellBusinessModel
 */
public class SellBusinessModel implements SellBusinessContract.Model {

    @Override
    public Observable<List<CompanyBean>> getCompanyData() {
        return ApiPartner.getInstance().getCompanyList(ApiRequest.getCompanyList()).compose(RxHelper.<List<CompanyBean>>getResult());
    }

    @Override
    public Observable<OrderBean> getSellOrderInfo(RequestBody body) {
        return ApiPartner.getInstance().getSellOrderInfoByMonth(body).compose(RxHelper.<OrderBean>getResult());
    }
}
