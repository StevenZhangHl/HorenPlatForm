package com.horen.service.mvp.model;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.RxHelper;
import com.horen.service.api.Api;
import com.horen.service.bean.StorageCenterBean;
import com.horen.service.mvp.contract.StorageContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:05
 * @description :仓储列表
 * @github :https://github.com/chenyy0708
 */
public class StorageModel implements StorageContract.Model {

    @Override
    public Observable<StorageCenterBean> storageList(RequestBody body) {
        return Api.getInstance().storageList(body)
                .compose(RxHelper.<StorageCenterBean>getResult());
    }
}
