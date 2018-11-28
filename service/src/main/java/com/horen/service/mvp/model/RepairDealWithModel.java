package com.horen.service.mvp.model;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.RxHelper;
import com.horen.service.api.Api;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.mvp.contract.RepairDealWithContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:05
 * @description :维修处理
 * @github :https://github.com/chenyy0708
 */
public class RepairDealWithModel implements RepairDealWithContract.Model {

    @Override
    public Observable<RepairDetailBean> getRepairDetail(RequestBody body) {
        return Api.getInstance()
                .getRepairList(body)
                .compose(RxHelper.<RepairDetailBean>getResult());
    }

    @Override
    public Observable<BaseEntry> repairComplete(RequestBody body) {
        return Api.getInstance()
                .repairComplete(body)
                .compose(RxHelper.handleResult());
    }
}
