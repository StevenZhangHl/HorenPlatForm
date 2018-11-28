package com.horen.partner.mvp.presenter;

import android.support.v4.app.FragmentActivity;

import com.horen.base.bean.TypeBean;
import com.horen.base.listener.TimePickerListener;
import com.horen.base.listener.TypeSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.OrderBean;
import com.horen.partner.mvp.contract.SellBusinessContract;
import com.horen.partner.ui.widget.CustomDialog;
import com.horen.partner.ui.widget.TimePickerViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/20 10:44
 * Description:This isSellBusinessPresenter
 */
public class SellBusinessPresenter extends SellBusinessContract.Presenter {
    private List<CompanyBean> companyBeanList = new ArrayList<>();
    private List<TypeBean> typeBeanList = new ArrayList<>();
    private boolean isFirst = true;

    @Override
    public void getCompanyData() {
        mRxManager.add(mModel.getCompanyData().subscribeWith(new BaseObserver<List<CompanyBean>>(mContext, true) {
            @Override
            protected void onSuccess(List<CompanyBean> companyBeans) {
                companyBeanList.clear();
                companyBeanList.addAll(companyBeans);
                if (companyBeans.size() == 0) {
                    mView.showEmptyView();
                    return;
                }
                mView.showDefaultCompanyInfo(companyBeans.get(0),isFirst);
                isFirst = false;
                getDialogContent();
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

    private void refreshCheckStatesById(String selectId) {
        for (int i = 0; i < typeBeanList.size(); i++) {
            if (typeBeanList.get(i).getTypeId().equals(selectId)) {
                typeBeanList.get(i).setChecked(true);
            } else {
                typeBeanList.get(i).setChecked(false);
            }
        }
    }

    private void refreshCheckStates(int position) {
        for (int i = 0; i < typeBeanList.size(); i++) {
            if (i == position) {
                typeBeanList.get(i).setChecked(true);
            } else {
                typeBeanList.get(i).setChecked(false);
            }
        }
    }

    @Override
    public void showCompanyDialog(FragmentActivity context, String selectId) {
        if (companyBeanList.size() == 0) {
            mView.onError("暂无公司信息");
            return;
        }
        refreshCheckStatesById(selectId);
        CustomDialog dialog = new CustomDialog(context, typeBeanList, "公司名称", selectId);
        dialog.setSelectListener(new TypeSelectListener() {
            @Override
            public void onSubmitClick(TypeBean type, int position) {
                mView.setSelectCompanyInfo(type);
                refreshCheckStates(position);
            }
        });
        dialog.show();
    }

    @Override
    public void showSelectTimeDialog(FragmentActivity fragmentActivity) {
        showTimePicker(fragmentActivity);
    }

    /**
     * 时间选择器
     *
     * @param fragmentActivity
     */
    private void showTimePicker(FragmentActivity fragmentActivity) {
        TimePickerViewHelper timePickerViewHelper = new TimePickerViewHelper(fragmentActivity);
        timePickerViewHelper.setTimePickerListener(new TimePickerListener() {
            @Override
            public void onTimePicker(String time) {
                mView.setSelectTime(time);
            }
        });
    }

    @Override
    public void getSellOrderInfo(String order_companyid, String month, String type, int pageNum) {
        mRxManager.add(mModel.getSellOrderInfo(ApiRequest.getSellOrderInfoByMonth(month, order_companyid, type, pageNum)).subscribeWith(new BaseObserver<OrderBean>(mContext, true) {
            @Override
            protected void onSuccess(OrderBean orderBean) {
                mView.setOrderData(orderBean.getList(), orderBean.getPageNum(), orderBean.getPages());
            }

            @Override
            protected void onError(String message) {
            }
        }));
    }

    /**
     * 转换数据
     *
     * @return
     */
    private void getDialogContent() {
        typeBeanList.clear();
        for (int i = 0; i < companyBeanList.size(); i++) {
            TypeBean bean = new TypeBean(companyBeanList.get(i).getCompany_id(), companyBeanList.get(i).getCompany_name(), false);
            typeBeanList.add(bean);
        }
    }
}
