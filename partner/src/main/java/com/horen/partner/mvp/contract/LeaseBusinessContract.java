package com.horen.partner.mvp.contract;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.TypeBean;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.chart.linechart.ILineChartData;
import com.horen.partner.bean.BusinessLineBean;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.OrderBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/17 14:19
 * Description:This isBusinessCenterContract
 */
public interface LeaseBusinessContract {
    interface Model extends BaseModel {
        Observable<List<CompanyBean>> getCompanyData();

        Observable<BusinessLineBean> getLineChartData(RequestBody body);

        Observable<OrderBean> getRTpOrderInfo(RequestBody body);

        Observable<OrderBean> getSellOrderInfo(RequestBody body);

        Observable<OrderBean> getRTpSellOrderData(RequestBody body);
    }

    interface View extends BaseView {
        void setSelectCompanyInfo(TypeBean typeBean);

        void setOrderData(List<OrderBean.Order> orderBeans, int pageNum, int pageTotal);

        void setLineChartData(List<List<ILineChartData>> lineChartData, List<String> names, List<String> months, int maxY);

        void showDefaultCompanyInfo(CompanyBean defaultCompany,boolean isFirst);

        void showEmptyView();

        void showNoCompany();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void showCompanyDialog(FragmentActivity context, String selectId);

        public abstract void getCompanyData();

        public abstract void getLineChartData(String order_companyid);

        public abstract void getRTpOrderInfo(String order_companyid, String month, String type, int pageNum);

        public abstract void getSellOrderInfo(String order_companyid, String month, String type, int pageNum);

        public abstract void getRTpSellOrderData(String order_companyid, String month, int pageNum);

    }
}
