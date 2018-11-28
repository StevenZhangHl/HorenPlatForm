package com.horen.service.mvp.presenter;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.mvp.contract.RepairDealWithContract;

import java.util.Arrays;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:06
 * @description :维修编辑
 * @github :https://github.com/chenyy0708
 */
public class RepairDealWithPresenter extends RepairDealWithContract.Presenter {

    @Override
    public void getRepairDetail(String service_id) {
        mRxManager.add(mModel.getRepairDetail(ServiceParams.getRepairList(Arrays.asList(service_id)))
                .subscribeWith(new BaseObserver<RepairDetailBean>(mContext, true) {
                    @Override
                    protected void onSuccess(RepairDetailBean detailBean) {
                        mView.getRepairDetailSuccess(detailBean);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    @Override
    public void repairComplete(String service_id, String remake) {
        mRxManager.add(mModel.repairComplete(ServiceParams.repairComplete(service_id, remake))
                .subscribeWith(new BaseObserver<BaseEntry>(mContext, true) {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        mView.repairCompleteSuccess();
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }
}
