package com.horen.service.mvp.contract;

import com.horen.base.bean.BaseEntry;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.service.bean.StorageCenterBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/13:59
 * @description :仓储中心契约类
 * @github :https://github.com/chenyy0708
 */
public interface StorageContract {
    interface Model extends BaseModel {
        /**
         * 仓储中心列表
         */
        Observable<StorageCenterBean> storageList(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 仓储中心列表成功
         */
        void getStorageListSuccess(StorageCenterBean bean);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 仓储中心列表
         *
         * @param type 1:出入库统计 ，2:产品库存 ，3:耗材库存
         */
        public abstract void storageList(String type);
    }
}
