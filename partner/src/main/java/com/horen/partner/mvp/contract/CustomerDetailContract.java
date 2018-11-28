package com.horen.partner.mvp.contract;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.partner.bean.CustomerBean;
import com.horen.partner.bean.VisiteNoteBaseBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/9 8:38
 * Description:This isCustomerDetailContract
 */
public interface CustomerDetailContract {
    interface Model extends BaseModel {
        Observable<CustomerBean> getCustomerDetail(RequestBody body);
        Observable<List<VisiteNoteBaseBean>>getVisiteNote(RequestBody body);
    }

    interface View extends BaseView {
        void setViewCustomerData(CustomerBean bean);
        void setViewVisiteNoteData(List<VisiteNoteBaseBean> bean);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getCustomerDetailData(String customerId);

        public abstract void editCustomerInfo();

        public abstract void getVisiteNoteData(String customer_id);
    }
}
