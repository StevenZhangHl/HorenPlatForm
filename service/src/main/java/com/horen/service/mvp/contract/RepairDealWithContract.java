package com.horen.service.mvp.contract;

import com.horen.base.bean.BaseEntry;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.service.bean.RepairDetailBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/13:59
 * @description :维修处理
 * @github :https://github.com/chenyy0708
 */
public interface RepairDealWithContract {

    interface Model extends BaseModel {
        Observable<RepairDetailBean> getRepairDetail(RequestBody body);

        Observable<BaseEntry> repairComplete(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 获取详情成功
         */
        void getRepairDetailSuccess(RepairDetailBean detailBean);

        /**
         * 维修完成成功
         */
        void repairCompleteSuccess();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 维修详情
         */
        public abstract void getRepairDetail(String service_id);

        /**
         * 维修完成
         */
        public abstract void repairComplete(String service_id, String remake);
    }
}
