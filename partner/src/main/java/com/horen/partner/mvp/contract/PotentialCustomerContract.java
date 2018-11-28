package com.horen.partner.mvp.contract;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.partner.bean.PotentialBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/8 13:43
 * Description:This isPotentialCustomerContract
 */
public interface PotentialCustomerContract {
    interface Model extends BaseModel {
        Observable<PotentialBean> getPotentialData(RequestBody body);
    }

    interface View extends BaseView {
        void setPotentialData(List<PotentialBean.ListBean> result, int totalPages);

        void setBarData(List<PotentialBean.ListBean> result);

        void showDataEmpty();

        void setWaringPosition(List<Integer> waringPositions);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getPotentialList(String pageNum, String pageSize);

    }
}
