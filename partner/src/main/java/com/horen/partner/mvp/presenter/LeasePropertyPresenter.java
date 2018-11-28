package com.horen.partner.mvp.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.horen.base.bean.TypeBean;
import com.horen.base.listener.TypeSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.PropertyBean;
import com.horen.partner.bean.PropertySingleBean;
import com.horen.partner.mvp.contract.LeasePropertyContract;
import com.horen.partner.ui.widget.BottomSheetDialog;
import com.horen.partner.ui.widget.CustomDialog;
import com.horen.partner.ui.widget.SelectCustomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/22 11:10
 * Description:This isLeasePropertyPresenter
 */
public class LeasePropertyPresenter extends LeasePropertyContract.Presenter {
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
                    mView.showEmptyCompany();
                    return;
                }
                getDialogContent();
                //默认显示第一个公司的所有数据
                mView.setDefaultCompanyInfo(companyBeans.get(0), isFirst);
                isFirst = false;
            }

            @Override
            protected void onError(String message) {
                mView.showError(message);
            }
        }));
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
    public void getSingleData(String company_id) {
        mRxManager.add(mModel.getSingleData(ApiRequest.getPropertySingleData(company_id)).subscribeWith(new BaseObserver<PropertySingleBean>(mContext, false) {
            @Override
            protected void onSuccess(PropertySingleBean propertySingleBeans) {
                mView.clearMapMarker();
                if (propertySingleBeans.getList().size() != 0) {
                    mView.setMapData(propertySingleBeans.getList());
                } else {
                    mView.onError("暂无数据");
                }
            }

            @Override
            protected void onError(String message) {
                mView.showError(message);
            }
        }));
    }

    @Override
    public void getPropertyData(String company_id) {
        mRxManager.add(mModel.getPropertyData(ApiRequest.getPropertyData(company_id)).subscribeWith(new BaseObserver<List<PropertyBean>>(mContext, false) {
            @Override
            protected void onSuccess(List<PropertyBean> propertyBeans) {
                if (propertyBeans.size() == 0) {
                    mView.onError("暂无数据");
                    mView.showEmptyData();
                } else {
                    mView.setBottomSheetData(propertyBeans);
                }
            }

            @Override
            protected void onError(String message) {
                mView.showError(message);
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
