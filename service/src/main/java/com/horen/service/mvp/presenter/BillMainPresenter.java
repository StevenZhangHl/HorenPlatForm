package com.horen.service.mvp.presenter;

import com.horen.base.rx.BaseObserver;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.BillMainBean;
import com.horen.service.mvp.contract.BillMainContract;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:06
 * @description :账单中心
 * @github :https://github.com/chenyy0708
 */
public class BillMainPresenter extends BillMainContract.Presenter {


    @Override
    public void getAllBillList(String relativeMonth, String accountStatus) {
        mRxManager.add(mModel.getAllBillList(ServiceParams.getAllBillList(relativeMonth, accountStatus))
                .subscribeWith(new BaseObserver<BillMainBean>() {
                    @Override
                    protected void onSuccess(BillMainBean mainBean) {
                        // 得到总金额
                        double allMeony = 0;
                        for (BillMainBean.BillListBean billListBean : mainBean.getBillList()) {
                            allMeony += Double.valueOf(billListBean.getTotal_ar());
                        }
                        mView.setBillAllMoney(allMeony);
                        // 设置总集合
                        mView.getBillSuccess(mainBean.getBillList());
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }
}
