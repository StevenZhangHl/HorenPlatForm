package com.horen.service.mvp.presenter;

import com.horen.base.rx.BaseObserver;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.BillDetailBean;
import com.horen.service.mvp.contract.BillDetailContract;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:06
 * @description :账单中心
 * @github :https://github.com/chenyy0708
 */
public class BillDetailPresenter extends BillDetailContract.Presenter {

    @Override
    public void getBillDetail(final String cost_type, String relativeMonth) {
        mRxManager.add(mModel.getBillDetail(ServiceParams.getBillDetail(cost_type, relativeMonth))
                .subscribeWith(new BaseObserver<BillDetailBean>(mContext, true) {
                    @Override
                    protected void onSuccess(BillDetailBean bean) {
                        switch (cost_type) {
                            case "3": // 耗材服务费
                                mView.setSuppliesCharge(bean);
                                break;
                            case "2": // 产品服务费
                                mView.setSuppliesCharge(bean);
                                break;
                            case "4": // 维修费
                                mView.setRepairCharge(bean);
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }
}
