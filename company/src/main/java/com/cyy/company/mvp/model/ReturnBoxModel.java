package com.cyy.company.mvp.model;

import com.cyy.company.api.ApiCompany;
import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.bean.ReturnOrderPD;
import com.cyy.company.bean.ReturnProFreight;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.mvp.contract.ReturnBoxContract;
import com.horen.base.rx.RxHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:46
 * @description :还箱
 * @github :https://github.com/chenyy0708
 */
public class ReturnBoxModel implements ReturnBoxContract.Model {
    @Override
    public Observable<DefaultOrgBean> getDefaultOrgsList(RequestBody body) {
        return ApiCompany.getInstance().getOrgsIdOkura(body)
                .compose(RxHelper.<DefaultOrgBean>getResult());
    }

    /**
     * 还箱产品信息查询
     */
    @Override
    public Observable<ReturnOrderPD> getOrderProNumber(RequestBody body) {
        return ApiCompany.getInstance().getOrderProNumber(body)
                .compose(RxHelper.<ReturnOrderPD>getResult());
    }

    /**
     * 上游还箱运费单价查询
     */
    @Override
    public Observable<ReturnProFreight> getOrderProFreight(RequestBody body) {
        return ApiCompany.getInstance().getOrderProFreight(body)
                .compose(RxHelper.<ReturnProFreight>getResult());
    }

    @Override
    public Observable<SubmitOrder> saveOrderInfo(RequestBody body) {
        return ApiCompany.getInstance().saveOrderInfo(body)
                .compose(RxHelper.<SubmitOrder>getResult());
    }
}
