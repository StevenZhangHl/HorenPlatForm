package com.cyy.company.mvp.model;

import com.cyy.company.api.ApiCompany;
import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.bean.RentDays;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.mvp.contract.RentBoxContract;
import com.horen.base.rx.RxHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:46
 * @description :租箱
 * @github :https://github.com/chenyy0708
 */
public class RentBoxModel implements RentBoxContract.Model {
    @Override
    public Observable<DefaultOrgBean> getDefaultOrgsList(RequestBody body) {
        return ApiCompany.getInstance().getOrgsIdOkura(body)
                .compose(RxHelper.<DefaultOrgBean>getResult());
    }

    @Override
    public Observable<OrderProducts> getProductsList(RequestBody body) {
        return ApiCompany.getInstance().getProductsList(body)
                .compose(RxHelper.<OrderProducts>getResult());
    }

    @Override
    public Observable<RentDays> getRentdaysList(RequestBody body) {
        return ApiCompany.getInstance().getRentdaysList(body)
                .compose(RxHelper.<RentDays>getResult());
    }

    @Override
    public Observable<SubmitOrder> saveOrderInfo(RequestBody body) {
        return ApiCompany.getInstance().saveOrderInfo(body)
                .compose(RxHelper.<SubmitOrder>getResult());
    }
}
