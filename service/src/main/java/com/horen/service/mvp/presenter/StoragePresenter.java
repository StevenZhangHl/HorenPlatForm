package com.horen.service.mvp.presenter;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.StorageCenterBean;
import com.horen.service.mvp.contract.StorageContract;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:06
 * @description :仓储中心
 * @github :https://github.com/chenyy0708
 */
public class StoragePresenter extends StorageContract.Presenter {

    /**
     * 仓储列表
     *
     * @param type 1:出入库统计 ，2:产品库存 ，3:耗材库存
     */
    @Override
    public void storageList(String type) {
        mRxManager.add(mModel.storageList(ServiceParams.storageList(type))
                .subscribeWith(new BaseObserver<StorageCenterBean>(mContext, false) {
                    @Override
                    protected void onSuccess(StorageCenterBean bean) {
                        mView.getStorageListSuccess(bean);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }
}
