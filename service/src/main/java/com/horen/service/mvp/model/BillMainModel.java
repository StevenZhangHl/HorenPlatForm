package com.horen.service.mvp.model;

import com.horen.base.rx.RxHelper;
import com.horen.service.api.Api;
import com.horen.service.bean.BillMainBean;
import com.horen.service.mvp.contract.BillMainContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:05
 * @description :账单中心
 * @github :https://github.com/chenyy0708
 */
public class BillMainModel implements BillMainContract.Model {

    @Override
    public Observable<BillMainBean> getAllBillList(RequestBody body) {
        return Api.getInstance().getAllBillList(body)
                .compose(RxHelper.<BillMainBean>getResult());
    }
}
