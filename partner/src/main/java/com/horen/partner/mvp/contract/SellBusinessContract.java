package com.horen.partner.mvp.contract;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.horen.base.bean.TypeBean;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.partner.bean.BusinessLineBean;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.OrderBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/17 14:50
 * Description:This isSellBusinessContract
 */
public interface SellBusinessContract {
    interface Model extends BaseModel {
        Observable<List<CompanyBean>> getCompanyData();

        Observable<OrderBean> getSellOrderInfo(RequestBody body);
    }

    interface View extends BaseView {
        void setSelectCompanyInfo(TypeBean typeBean);

        void setOrderData(List<OrderBean.Order> orderBeans, int pageNum, int pageTotal);

        void setSelectTime(String selectTime);

        void showDefaultCompanyInfo(CompanyBean companyBean, boolean isFirst);

        void showEmptyView();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getCompanyData();

        public abstract void showCompanyDialog(FragmentActivity fragmentActivity, String selectId);

        public abstract void showSelectTimeDialog(FragmentActivity fragmentActivity);

        public abstract void getSellOrderInfo(String order_companyid, String month, String type, int pageNum);

    }
}
