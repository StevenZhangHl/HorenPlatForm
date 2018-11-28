package com.horen.partner.mvp.presenter;

import com.horen.base.rx.BaseObserver;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.PotentialBean;
import com.horen.partner.mvp.contract.PotentialCustomerContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/8 14:14
 * Description:This isPotentialCustomerPresenter
 */
public class PotentialCustomerPresenter extends PotentialCustomerContract.Presenter {
    @Override
    public void getPotentialList(String pageNum, String pageSize) {
        mRxManager.add(mModel.getPotentialData(ApiRequest.getPotentialData(pageNum, pageSize)).subscribeWith(new BaseObserver<PotentialBean>(mContext, false) {

            @Override
            protected void onSuccess(PotentialBean listBean) {
                getCaculateWaringPosition(listBean.getList());
                mView.setPotentialData(listBean.getList(), listBean.getPages());
                mView.setBarData(listBean.getList());
                if (listBean.getTotal() == 0) {
                    mView.showDataEmpty();
                }
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }

        }));
    }

    /**
     * 取出保护期小于等于15的天数
     *
     * @param list
     */
    private void getCaculateWaringPosition(List<PotentialBean.ListBean> list) {
        List<Integer> waringPositions = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDays() <= 15) {
                waringPositions.add(i);
            }
        }
        mView.setWaringPosition(waringPositions);
    }

}
