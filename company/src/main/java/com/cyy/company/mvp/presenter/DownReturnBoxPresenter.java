package com.cyy.company.mvp.presenter;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.cyy.company.R;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.ChargeDetail;
import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.bean.RenewalAddress;
import com.cyy.company.bean.ReturnOrderPD;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.enums.OrderType;
import com.cyy.company.mvp.contract.DownReturnBoxContract;
import com.cyy.company.utils.DateUtils;
import com.cyy.company.utils.OrderUtils;
import com.horen.base.bean.RadioSelectBean;
import com.horen.base.listener.RadioSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.SpanUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.RadioSelectDialog;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:45
 * @description :下游还箱
 * @github :https://github.com/chenyy0708
 */
public class DownReturnBoxPresenter extends DownReturnBoxContract.Presenter {

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

    public DownReturnBoxPresenter setText(@IdRes int viewId, CharSequence value) {
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
    public void showTransportMode() {
        mTransportModeDialog.show();
    }

    /**
     * 默认下游网点大仓地址
     */
    @Override
    public void getDownStreamOrgsList() {
        mRxManager.add(mModel.getDownStreamOrgsList(CompanyParams.getDefaultOrgsList())
                .subscribeWith(new BaseObserver<RenewalAddress>() {
                    @Override
                    protected void onSuccess(RenewalAddress address) {
                        // 下游无需选择网点，新建一个网点大仓信息，后续将默认网点大仓信息设置给当前数据orgAddress
                        orgAddress = new DefaultOrgBean();
                        ArrayList<DefaultOrgBean.PdListBean> pdList = new ArrayList<>();
                        pdList.add(new DefaultOrgBean.PdListBean());
                        orgAddress.setPdList(pdList);
                        // 默认地址和大仓地址的数据返回结构不同，不合理
                        DefaultOrgBean.PdListBean orgAddressBean = orgAddress.getPdList().get(0);
                        RenewalAddress.PdListBean defaultAddressBean = address.getPdList().get(0);
                        orgAddressBean.setKm(defaultAddressBean.getKm()); // km
                        orgAddressBean.setOrg_address(defaultAddressBean.getOrg_address1());
                        orgAddressBean.setOrg_consignee(defaultAddressBean.getOrg_consignee1());
                        orgAddressBean.setOrg_consigneetel(defaultAddressBean.getOrg_consigneetel1());
                        orgAddressBean.setOrg_contact(defaultAddressBean.getOrg_consignee());
                        orgAddressBean.setOrg_id(defaultAddressBean.getOrg_id1());
                        orgAddressBean.setOrg_name(defaultAddressBean.getOrg_name1());
                        orgAddressBean.setOrg_tel(defaultAddressBean.getOrg_consigneetel1());
                        orgAddressBean.setPro_org_address(defaultAddressBean.getOrg_address());
                        orgAddressBean.setPro_org_consignee(defaultAddressBean.getOrg_consignee());
                        orgAddressBean.setPro_org_consigneetel(defaultAddressBean.getOrg_consigneetel());
                        orgAddressBean.setPro_org_id(defaultAddressBean.getOrg_id());
                        orgAddressBean.setPro_org_name(defaultAddressBean.getOrg_name());
                        setAddress(orgAddress);
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
     * 上游还箱产品信息查询
     */
    @Override
    public void getOrderProNumber(String org_id) {
        mRxManager.add(mModel.getUnderOrderProNumber(CompanyParams.getOrderProNumber(org_id))
                .subscribeWith(new BaseObserver<ReturnOrderPD>() {
                    @Override
                    protected void onSuccess(ReturnOrderPD orderPD) {
                        // 初始化页面物品列表
                        mView.getOrderProdouctList(orderPD.getPdList());
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
                        mView.onSubmitSuccess(order);
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
