package com.cyy.company.mvp.presenter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cyy.company.R;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AddressBook;
import com.cyy.company.bean.ChargeDetail;
import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.bean.ReturnOrderPD;
import com.cyy.company.bean.ReturnProFreight;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.enums.OrderType;
import com.cyy.company.listener.SubmitListener;
import com.cyy.company.mvp.contract.ReturnBoxContract;
import com.cyy.company.ui.activity.order.OrderAddressActivity;
import com.cyy.company.utils.DateUtils;
import com.cyy.company.utils.OrderUtils;
import com.cyy.company.widget.OrderAmountDialog;
import com.horen.base.bean.RadioSelectBean;
import com.horen.base.listener.RadioSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.NumberUtil;
import com.horen.base.util.SpanUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.RadioSelectDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:45
 * @description :还箱
 * @github :https://github.com/chenyy0708
 */
public class ReturnBoxPresenter extends ReturnBoxContract.Presenter {

    private SparseArray<View> views;
    /**
     * 根布局
     */
    private View mRootView;
    /**
     * 网点地址
     */
    private DefaultOrgBean orgAddress;
    /**
     * 租赁天数
     */
    private RadioSelectDialog mTransportModeDialog;
    private OrderAmountDialog amountDialog;
    /**
     * 运输方式
     */
    private String flag_send;
    /**
     * 金额明细
     */
    private ChargeDetail chargeDetail;

    public void setRootView(View mRootView) {
        this.mRootView = mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        views = new SparseArray<>();
        mTransportModeDialog = new RadioSelectDialog(mContext, OrderUtils.getReturnTransModeBean(), "运输方式");
        amountDialog = new OrderAmountDialog(mContext)
                .setSubmitListener(new SubmitListener() {
                    @Override
                    public void onSubmit() { // 提交订单
                        EditText editText = getView(R.id.et_remarks);
                        submitOrder(editText.getText().toString());
                    }
                });
        initListener();
    }

    /**
     * 弹框选择点击事件监听
     */
    private void initListener() {
        mTransportModeDialog.setRadioSelectListener(new RadioSelectListener() {
            @Override
            public void onSelected(RadioSelectBean bean, int position) {
                flag_send = bean.getId();
                setText(R.id.tv_transport_mode, bean.getTabName());
                mView.onSelectTransportMode(flag_send);
            }
        });
    }

    public ReturnBoxPresenter setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public String getText(@IdRes int viewId) {
        TextView view = getView(viewId);
        return view.getText().toString();
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = mRootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == OrderAddressActivity.RESULT_SUCCESS) {
            AddressBook.PdListBean.ListBean addressBookBean = (AddressBook.PdListBean.ListBean)
                    data.getSerializableExtra("addressBean");
            refreshAddress(addressBookBean);
        }
    }


    @Override
    public void showTransportMode() {
        mTransportModeDialog.show();
    }


    /**
     * 金额明细
     */
    @Override
    public void showAmountDialog() {
        amountDialog
                .setData(chargeDetail)
                .show();
    }

    /**
     * 显示上下游网点
     *
     * @param addressBookBean 上游
     */
    private void refreshAddress(AddressBook.PdListBean.ListBean addressBookBean) {
        mRxManager.add(mModel.getDefaultOrgsList(CompanyParams.getDefaultOrgsList(addressBookBean.getOrg_id(), addressBookBean.getPartner_relation()))
                .subscribeWith(new BaseObserver<DefaultOrgBean>() {
                    @Override
                    protected void onSuccess(DefaultOrgBean orgBean) {
                        orgAddress = orgBean;
                        // 显示地址
                        setAddress(orgBean);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));

    }

    /**
     * 设置上下游地址
     */
    private void setAddress(DefaultOrgBean orgBean) {
        DefaultOrgBean.PdListBean bean = orgBean.getPdList().get(0);
        mRootView.findViewById(R.id.fl_select_address).setVisibility(View.GONE);
        // 动画显示View
        if (getView(R.id.ll_address_from_to).getVisibility() != View.VISIBLE) {
            AnimationUtils.foldingShowView(getView(R.id.ll_address_from_to), 300);
        }
        // 网点地址
        setText(R.id.tv_from_name, bean.getPro_org_consignee());
        setText(R.id.tv_from_phone, FormatUtil.phoneSetMiddleEmpty(bean.getPro_org_consigneetel()));
        TextView mTvFromOrgAddress = getView(R.id.tv_from_org_address);
        mTvFromOrgAddress.setText(new SpanUtils()
                .append(" " + bean.getPro_org_name() + " ")
                .setFontSize(12, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.base_text_color_light))
                .setBackgroundColor(Color.parseColor("#336fba2c"))
                .append("  " + bean.getPro_org_address())
                .setFontSize(13, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333)).create());
        // 百网千翌地址
        setText(R.id.tv_to_name, bean.getOrg_consignee());
        setText(R.id.tv_to_phone, FormatUtil.phoneSetMiddleEmpty(bean.getOrg_consigneetel()));
        TextView mTvToOrgAddress = getView(R.id.tv_to_org_address);
        mTvToOrgAddress.setText(new SpanUtils()
                .append(" " + bean.getOrg_name() + " ")
                .setFontSize(12, true)
                .setForegroundColor(Color.parseColor("#F15B02"))
                .setBackgroundColor(Color.parseColor("#33f15b02"))
                .append("  " + bean.getOrg_address())
                .setFontSize(13, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333)).create());
        // 请求网点还箱物品
        getOrderProNumber(bean.getPro_org_id());
    }

    /**
     * 检查提交按钮状态  （地址、租赁天数、运输方式、预计使用时间）
     */
    @Override
    public boolean checkStatus() {
        return orgAddress != null
                && !TextUtils.isEmpty(flag_send);
    }

    /**
     * 计算运费
     */
    @Override
    public void calculate(List<ReturnOrderPD.PdListBean> mData) {
        // 运费 =  箱子运费单价 * 个数
        double freight = 0;
        for (ReturnOrderPD.PdListBean mDatum : mData) {
            if (flag_send.equals("0")) { // 只有收箱才需要计算运费
                freight += mDatum.getTms_price() * mDatum.getOrder_qty();
            }
        }
        setText(R.id.tv_amount_zero, NumberUtil.formitNumberTwo(freight));
    }

    /**
     * 上游还箱产品信息查询
     */
    @Override
    public void getOrderProNumber(String org_id) {
        mRxManager.add(mModel.getOrderProNumber(CompanyParams.getOrderProNumber(org_id))
                .subscribeWith(new BaseObserver<ReturnOrderPD>() {
                    @Override
                    protected void onSuccess(ReturnOrderPD orderPD) {
                        // 获取物品对应的运费单价
                        getOrderProFreight(orgAddress.getPdList().get(0).getPro_org_id(), orderPD);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 上游还箱运费单价查询
     */
    @Override
    public void getOrderProFreight(String org_id, final ReturnOrderPD orderPD) {
        mRxManager.add(mModel.getOrderProFreight(CompanyParams.getOrderProNumber(org_id))
                .subscribeWith(new BaseObserver<ReturnProFreight>() {
                    @Override
                    protected void onSuccess(ReturnProFreight proFreight) {
                        List<ReturnOrderPD.PdListBean> mData = new ArrayList<>();
                        // 设置每一个物品的运费单价
                        for (ReturnOrderPD.PdListBean pdListBean : orderPD.getPdList()) {
                            // 得到运费单价
                            for (ReturnProFreight.PdListBean listBean : proFreight.getPdList()) {
                                // 当产品id相同 设置运费
                                if (listBean.getProduct_typeid().equals(pdListBean.getProduct_typeid()))
                                    pdListBean.setTms_price(listBean.getTrans_price());
                                // 还箱数不为0，才能还箱
                                if (pdListBean.getQty() > 0) {
                                    mData.add(pdListBean);
                                    break;
                                }
                            }
                        }
                        // 初始化页面物品列表
                        mView.getOrderProdouctList(mData);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 提交订单
     */
    @Override
    public void submitOrder(String order_note) {
        DefaultOrgBean.PdListBean address = orgAddress.getPdList().get(0);
        mRxManager.add(mModel.saveOrderInfo(CompanyParams.saveReturnOrderInfo(address.getPro_org_id(), DateUtils.getCurentData(),
                "0", flag_send, mView.getAdapter().getData(), UserHelper.getUserInfo().getLoginInfo().getCompany_id()
                , UserHelper.getUserInfo().getLoginInfo().getCompany_name(), order_note, OrderType.TWO.getPosition(), address.getOrg_id()))
                .subscribeWith(new BaseObserver<SubmitOrder>() {
                    @Override
                    protected void onSuccess(SubmitOrder order) {
                        mView.onSubmitSuccess(order.getOrder_id());
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 租箱企业网点地址
     */
    public DefaultOrgBean.PdListBean getAddress() {
        return orgAddress.getPdList().get(0);
    }
}
