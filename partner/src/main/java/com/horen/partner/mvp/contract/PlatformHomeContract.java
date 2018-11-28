package com.horen.partner.mvp.contract;

import android.support.v7.widget.RecyclerView;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.partner.adapter.HomeTabAdapter;
import com.horen.partner.bean.ApiResultHomeData;
import com.horen.partner.bean.ApiResultPartnerList;
import com.horen.partner.bean.HomeBanner;
import com.horen.partner.bean.Partner;
import com.horen.partner.bean.PlanTypeList;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/27 15:40
 * Description:This isPlatformHomeContract
 */
public interface PlatformHomeContract {
    interface Model extends BaseModel {
        Observable<ApiResultHomeData> getData();
    }

    interface View extends BaseView {
        void setData(List<Object> datas);

        void setBanner(List<HomeBanner> solution_homebanners);

        void stopBanner();

        void startBanner();

        void setHomeTab(List<PlanTypeList> planTypeLists);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getData();

        public abstract void changeTabLayout(RecyclerView recyclerView, int firstItemPosition, HomeTabAdapter homeTabAdapter);
    }
}
