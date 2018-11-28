package com.horen.partner.mvp.presenter;

import com.horen.base.rx.BaseObserver;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.CustomerBean;
import com.horen.partner.bean.VisiteNoteBaseBean;
import com.horen.partner.mvp.contract.CustomerDetailContract;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/9 10:25
 * Description:This isCustomerDetailContract
 */
public class CustomerDetailPresenter extends CustomerDetailContract.Presenter {
    @Override
    public void getCustomerDetailData(String customerId) {
        mRxManager.add(mModel.getCustomerDetail(ApiRequest.getCustomerDetail(customerId)).subscribeWith(new BaseObserver<CustomerBean>(mContext, false) {
            @Override
            protected void onSuccess(CustomerBean customerDetailBean) {
                if (customerDetailBean != null) {
                    mView.setViewCustomerData(customerDetailBean);
                }
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

    @Override
    public void editCustomerInfo() {

    }

    @Override
    public void getVisiteNoteData(String customer_id) {
        mRxManager.add(mModel.getVisiteNote(ApiRequest.getVisiteNoteData(customer_id)).subscribeWith(new BaseObserver<List<VisiteNoteBaseBean>>(mContext, false) {
            @Override
            protected void onSuccess(List<VisiteNoteBaseBean> visiteNoteBaseBean) {
                mView.setViewVisiteNoteData(visiteNoteBaseBean);
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }
}
