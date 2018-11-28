package com.horen.partner.mvp.model;

import com.horen.base.rx.RxHelper;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.BusinessLineBean;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.OrderBean;
import com.horen.partner.mvp.contract.LeaseBusinessContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/17 14:39
 * Description:This isLeaseBusinessModel
 */
public class LeaseBusinessModel implements LeaseBusinessContract.Model {
    @Override
    public Observable<List<CompanyBean>> getCompanyData() {
        return ApiPartner.getInstance().getCompanyList(ApiRequest.getCompanyList()).compose(RxHelper.<List<CompanyBean>>getResult());
    }

    @Override
    public Observable<BusinessLineBean> getLineChartData(RequestBody body) {
        return ApiPartner.getInstance().getBusinessLineData(body).compose(RxHelper.<BusinessLineBean>getResult());
    }

    @Override
    public Observable<OrderBean> getRTpOrderInfo(RequestBody body) {
        return ApiPartner.getInstance().getRTpOrderInfoByMonth(body).compose(RxHelper.<OrderBean>getResult());
    }

    @Override
    public Observable<OrderBean> getSellOrderInfo(RequestBody body) {
        return ApiPartner.getInstance().getSellOrderInfoByMonth(body).compose(RxHelper.<OrderBean>getResult());
    }

    @Override
    public Observable<OrderBean> getRTpSellOrderData(RequestBody body) {
        return ApiPartner.getInstance().getRTpSellOrderData(body).compose(RxHelper.<OrderBean>getResult());
    }
}
