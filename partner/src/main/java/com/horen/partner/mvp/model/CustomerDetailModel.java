package com.horen.partner.mvp.model;

import com.horen.base.rx.RxHelper;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.CustomerBean;
import com.horen.partner.bean.VisiteNoteBaseBean;
import com.horen.partner.mvp.contract.CustomerDetailContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/9 10:24
 * Description:This isCustomerDetailModel
 */
public class CustomerDetailModel implements CustomerDetailContract.Model {

    @Override
    public Observable<CustomerBean> getCustomerDetail(RequestBody body) {
        return ApiPartner.getInstance().getCustomerDetail(body).compose(RxHelper.<CustomerBean>getResult());
    }

    @Override
    public Observable<List<VisiteNoteBaseBean>> getVisiteNote(RequestBody body) {
        return ApiPartner.getInstance().getVisiteNoteData(body).compose(RxHelper.<List<VisiteNoteBaseBean>>getResult());
    }
}
