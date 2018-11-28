package com.cyy.company.mvp.presenter;

import android.text.TextUtils;

import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.OrderDetailBean;
import com.cyy.company.mvp.contract.OrderDetailContract;
import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.base.widget.MessageDialog;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:06
 * @description :订单详情
 * @github :https://github.com/chenyy0708
 */
public class OrderDetailPresenter extends OrderDetailContract.Presenter {

    /**
     * 上游订单详情列表查询
     *
     * @param order_id
     * @param order_status
     * @param order_type
     * @param company_id
     */
    @Override
    public void getOrderLineList(String order_id, String order_status, String order_type, String company_id) {
        mRxManager.add(mModel.getOrderLineList(CompanyParams.getOrderLineList(order_id))
                .subscribeWith(new BaseObserver<OrderDetailBean>(mContext, false) {
                    @Override
                    protected void onSuccess(OrderDetailBean bean) {
                        if (TextUtils.isEmpty(bean.getPageInfo().getOrder_id())) {
                            mView.setEmptyView();
                            return;
                        }
                        // 地址
                        mView.getAddressSuccess(bean.getPageInfo());
                        // 订单物品
                        mView.orderProducts(bean.getPageInfo());
                        // 租赁方式
                        mView.leaseMode(bean.getPageInfo());
                        // 订单信息
                        mView.orderInfo(bean.getPageInfo());
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 取消订单
     *
     * @param order_id 订单id
     */
    @Override
    public void cancelOrderInfo(final String order_id) {
        new MessageDialog(mContext)
                .showTitle("取消订单")
                .showContent("是否取消订单")
                .setButtonTexts("否", "是")
                .setOnClickListene(new MessageDialog.OnClickListener() {
                    @Override
                    public void onLeftClick() {
                    }

                    @Override
                    public void onRightClick() {
                        mRxManager.add(mModel.cancelOrderInfo(CompanyParams.getOrderId(order_id))
                                .subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
                                    @Override
                                    protected void onSuccess(BaseEntry bean) {
                                        mView.cancelOrderSuccess();
                                    }

                                    @Override
                                    protected void onError(String message) {
                                        mView.onError(message);
                                    }
                                }));
                    }
                })
                .show();
    }
}
