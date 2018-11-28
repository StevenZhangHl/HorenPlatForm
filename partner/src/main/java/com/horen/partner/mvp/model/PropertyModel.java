package com.horen.partner.mvp.model;

import com.horen.base.rx.RxHelper;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.BusinessLineBean;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.PropertyBean;
import com.horen.partner.bean.PropertySingleBean;
import com.horen.partner.mvp.contract.LeasePropertyContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/17 14:56
 * Description:This isPropertyModel
 */
public class PropertyModel implements LeasePropertyContract.Model {
    @Override
    public Observable<List<CompanyBean>> getCompanyData() {
        return ApiPartner.getInstance().getCompanyList(ApiRequest.getCompanyList()).compose(RxHelper.<List<CompanyBean>>getResult());
    }

    @Override
    public Observable<PropertySingleBean> getSingleData(RequestBody body) {
        return ApiPartner.getInstance().getPropertySingleData(body).compose(RxHelper.<PropertySingleBean>getResult());
    }

    @Override
    public Observable<List<PropertyBean>> getPropertyData(RequestBody body) {
        return ApiPartner.getInstance().getPropertyData(body).compose(RxHelper.<List<PropertyBean>>getResult());
    }
}
