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
import com.horen.partner.bean.PropertyBean;
import com.horen.partner.bean.PropertySingleBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/17 14:53
 * Description:This isLeasePropertyContract
 */
public interface LeasePropertyContract {
    interface Model extends BaseModel {
        Observable<List<CompanyBean>> getCompanyData();

        Observable<PropertySingleBean> getSingleData(RequestBody body);

        Observable<List<PropertyBean>> getPropertyData(RequestBody body);
    }

    interface View extends BaseView {

        void setSelectCompanyInfo(TypeBean typeBean);

        void setMapData(List<PropertySingleBean.SingleBean> propertySingleBeans);

        void setBottomSheetData(List<PropertyBean> propertyBeans);

        void clearMapMarker();

        void setDefaultCompanyInfo(CompanyBean companyInfo, boolean isFirst);

        void showEmptyData();

        void showError(String error);

        void showEmptyCompany();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getCompanyData();

        public abstract void showCompanyDialog(FragmentActivity context, String selectId);

        public abstract void getSingleData(String company_id);

        public abstract void getPropertyData(String company_id);

    }
}
