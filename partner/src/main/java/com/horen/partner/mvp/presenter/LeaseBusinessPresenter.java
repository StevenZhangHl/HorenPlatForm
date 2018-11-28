package com.horen.partner.mvp.presenter;

import android.support.v4.app.FragmentActivity;

import com.horen.base.bean.TypeBean;
import com.horen.base.listener.TypeSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.chart.linechart.ILineChartData;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.BusinessLineBean;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.LineChartBean;
import com.horen.partner.bean.OrderBean;
import com.horen.partner.mvp.contract.LeaseBusinessContract;
import com.horen.partner.ui.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/17 15:05
 * Description:This isLeaseBusinessPresenter
 */
public class LeaseBusinessPresenter extends LeaseBusinessContract.Presenter {
    private List<CompanyBean> companyBeanList = new ArrayList<>();
    private List<TypeBean> typeBeanList = new ArrayList<>();
    private boolean isFirst = true;

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

    @Override
    public void getCompanyData() {
        mRxManager.add(mModel.getCompanyData().subscribeWith(new BaseObserver<List<CompanyBean>>(mContext, true) {
            @Override
            protected void onSuccess(List<CompanyBean> companyBeans) {
                companyBeanList.clear();
                companyBeanList.addAll(companyBeans);
                if (companyBeans.size() == 0) {
                    mView.showNoCompany();
                    return;
                }
                mView.showDefaultCompanyInfo(companyBeans.get(0), isFirst);
                getDialogContent();
                isFirst = false;
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

    @Override
    public void getLineChartData(String order_companyid) {
        mRxManager.add(mModel.getLineChartData(ApiRequest.getBusinessLineData(order_companyid)).subscribeWith(new BaseObserver<BusinessLineBean>(mContext, true) {
            @Override
            protected void onSuccess(BusinessLineBean businessLineBean) {
                getTrueLineData(businessLineBean);
            }

            @Override
            protected void onError(String message) {

            }
        }));
    }

    /**
     * 将服务器的数据转换成曲线数据
     *
     * @param lineChartData
     */
    private void getTrueLineData(BusinessLineBean lineChartData) {
        //线的名字集合
        List<String> names = new ArrayList<>();
        List<String> months = new ArrayList<>();
        int maxValue1 = 0;
        int maxValue2 = 0;
        int maxValue = 0;
        names.add("收箱");
        names.add("发箱");
        List<List<ILineChartData>> lineData = new ArrayList<>();
        List<ILineChartData> incomeDatas = new ArrayList<>();
        List<ILineChartData> payDatas = new ArrayList<>();
        for (int j = 0; j < lineChartData.getRent().size(); j++) {
            if (lineChartData.getRent().get(j).getTotal_receiveqty() > maxValue1) {
                maxValue1 = lineChartData.getRent().get(j).getTotal_receiveqty();
            }
            months.add(lineChartData.getRent().get(j).getMonths());
            payDatas.add(new LineChartBean(lineChartData.getRent().get(j).getTotal_receiveqty(), lineChartData.getRent().get(j).getMonths()));
        }
        lineData.add(incomeDatas);
        for (int i = 0; i < lineChartData.getRecycle().size(); i++) {
            if (lineChartData.getRecycle().get(i).getTotal_receiveqty() > maxValue2) {
                maxValue2 = lineChartData.getRecycle().get(i).getTotal_receiveqty();
            }
            incomeDatas.add(new LineChartBean(lineChartData.getRecycle().get(i).getTotal_receiveqty(), lineChartData.getRecycle().get(i).getMonths()));
        }
        if (maxValue1 > maxValue2) {
            maxValue = maxValue1;
        } else {
            maxValue = maxValue2;
        }
        if (maxValue == 0) {
            maxValue = 300;
        }
//        if (maxValue % 3 != 0) {
//            maxValue = maxValue + 5;
//        }
        lineData.add(payDatas);
        mView.setLineChartData(lineData, names, months, maxValue);
    }

    /**
     * 发箱
     *
     * @param order_companyid
     * @param month
     * @param type
     * @param pageNum
     */
    @Override
    public void getRTpOrderInfo(String order_companyid, String month, String type, int pageNum) {
        mRxManager.add(mModel.getRTpOrderInfo(ApiRequest.getRTpOrderInfoByMonth(month, order_companyid, type, pageNum)).subscribeWith(new BaseObserver<OrderBean>(mContext, true) {
            @Override
            protected void onSuccess(OrderBean orderBeans) {
                mView.setOrderData(orderBeans.getList(), orderBeans.getPageNum(), orderBeans.getPages());
                if (orderBeans.getTotal() == 0) {
                    mView.showEmptyView();
                }
            }

            @Override
            protected void onError(String message) {
                mView.showEmptyView();
            }
        }));
    }

    /**
     * 收箱
     *
     * @param order_companyid
     * @param month
     * @param type
     * @param pageNum
     */
    @Override
    public void getSellOrderInfo(String order_companyid, String month, String type, int pageNum) {
        mRxManager.add(mModel.getSellOrderInfo(ApiRequest.getSellOrderInfoByMonth(month, order_companyid, type, pageNum)).subscribeWith(new BaseObserver<OrderBean>(mContext, true) {
            @Override
            protected void onSuccess(OrderBean orderBeans) {
                mView.setOrderData(orderBeans.getList(), orderBeans.getPageNum(), orderBeans.getPages());
                if (orderBeans.getTotal() == 0) {
                    mView.showEmptyView();
                }
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
                mView.showEmptyView();
            }
        }));
    }

    @Override
    public void getRTpSellOrderData(String order_companyid, String month, int pageNum) {
        mRxManager.add(mModel.getRTpSellOrderData(ApiRequest.getRTpSellOrderData(month, order_companyid, pageNum)).subscribeWith(new BaseObserver<OrderBean>(mContext, false) {
            @Override
            protected void onSuccess(OrderBean orderBeans) {
                mView.setOrderData(orderBeans.getList(), orderBeans.getPageNum(), orderBeans.getTotal());
                if (orderBeans.getTotal() == 0) {
                    mView.showEmptyView();
                }
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

}
