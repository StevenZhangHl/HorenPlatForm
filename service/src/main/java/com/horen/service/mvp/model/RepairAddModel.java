package com.horen.service.mvp.model;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.RxHelper;
import com.horen.service.api.Api;
import com.horen.service.bean.AddRepairBean;
import com.horen.service.bean.RtpInfo;
import com.horen.service.mvp.contract.RepairAddContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:05
 * @description :新增维修
 * @github :https://github.com/chenyy0708
 */
public class RepairAddModel implements RepairAddContract.Model {

    @Override
    public Observable<RtpInfo> getRtpInfo(RequestBody body) {
        return Api.getInstance().getRtpInfo(body)
                .compose(RxHelper.<RtpInfo>getResult());
    }

    @Override
    public Observable<AddRepairBean> addRtpRepair(RequestBody body) {
        return Api.getInstance().addRtpRepair(body)
                .compose(RxHelper.<AddRepairBean>getResult());
    }

    @Override
    public Observable<BaseEntry> delRepair(RequestBody body) {
        return Api.getInstance().delRepair(body)
                .compose(RxHelper.handleResult());
    }
}
