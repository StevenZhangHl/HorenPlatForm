package com.cyy.company.mvp.presenter;

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
import com.cyy.company.bean.ChargeDetail;
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.bean.RenewalAddress;
import com.cyy.company.bean.RenewalRtp;
import com.cyy.company.bean.RentDays;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.listener.SubmitListener;
import com.cyy.company.mvp.contract.RenewalBoxContract;
import com.cyy.company.utils.OrderUtils;
import com.cyy.company.widget.OrderAmountDialog;
import com.horen.base.bean.RadioSelectBean;
import com.horen.base.listener.RadioSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.CollectionUtils;
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
 * @description :租箱
 * @github :https://github.com/chenyy0708
 */
public class RenewalBoxPresenter extends RenewalBoxContract.Presenter {

    private SparseArray<View> views;
    /**
     * 根布局
     */
    private View mRootView;
    /**
     * 网点地址
     */
    private RenewalAddress orgAddress;
    /**
     * 租赁物品
     */
    private OrderProducts products;
    /**
     * 租赁天数
     */
    private RadioSelectDialog mRentalDayDialog;
    private RadioSelectDialog mTransportModeDialog;
    private OrderAmountDialog amountDialog;

    /**
     * 租赁天数
     */
    private String set_rentdays;
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
        mTransportModeDialog = new RadioSelectDialog(mContext, OrderUtils.getTransModeBean(), "运输方式");
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

    public RenewalBoxPresenter setText(@IdRes int viewId, CharSequence value) {
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

    /**
     * 选择租赁天数
     */
    @Override
    public void showRentalDays() {
        mRentalDayDialog.show();
    }

    @Override
    public void showTransportMode() {
        mTransportModeDialog.show();
    }

    /**
     * 上游续租产品信息查询
     */
    @Override
    public void getProductsList(String set_rentdays) {
        if(CollectionUtils.isNullOrEmpty(orgAddress.getPdList())) return;
        mRxManager.add(mModel.getProductsList(CompanyParams.getContProductsList(orgAddress.getPdList().get(0).getOrg_id(), set_rentdays))
                .subscribeWith(new BaseObserver<RenewalRtp>() {
                    @Override
                    protected void onSuccess(RenewalRtp renewalRtp) {
                        mView.getProductsList(renewalRtp.getPdList());
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
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
     * 获取租赁天数
     */
    private void getRentaDays() {
        if (mRentalDayDialog == null) {
            mRxManager.add(mModel.getRentdaysList(CompanyParams.getCompanyId())
                    .subscribeWith(new BaseObserver<RentDays>() {
                        @Override
                        protected void onSuccess(RentDays rentDays) {
                            List<RadioSelectBean> mData = new ArrayList<>();
                            for (RentDays.PdListBean pdListBean : rentDays.getPdList()) {
                                mData.add(new RadioSelectBean(pdListBean.getSet_rentdays() + "天", pdListBean.getSet_rentdays() + ""));
                            }
                            mRentalDayDialog = new RadioSelectDialog(mContext, mData, "租赁天数", 0);
                            mRentalDayDialog.setRadioSelectListener(new RadioSelectListener() {
                                @Override
                                public void onSelected(RadioSelectBean bean, int position) {
                                    // 重新选择了租赁天数
                                    if (bean.getId() != set_rentdays) {
                                        mView.onSelectRentalDays(set_rentdays);
                                    }
                                    set_rentdays = bean.getId();
                                    setText(R.id.tv_rental_days, bean.getTabName());
                                }
                            });
                            // 默认选择第一个
                            if (!CollectionUtils.isNullOrEmpty(rentDays.getPdList()))
                                set_rentdays = rentDays.getPdList().get(0).getSet_rentdays() + "";
                            // 设置天数
                            setText(R.id.tv_rental_days, set_rentdays + "天");
                            // 获取续租物品
                            getProductsList(set_rentdays);
                        }

                        @Override
                        protected void onError(String message) {
                            mView.onError(message);
                        }
                    }));
        } else {
            mRentalDayDialog.show();
        }
    }

    /**
     * 设置上下游地址
     */
    private void setAddress(RenewalAddress orgBean) {
        if (CollectionUtils.isNullOrEmpty(orgBean.getPdList())) return;
        RenewalAddress.PdListBean bean = orgBean.getPdList().get(0);
        mRootView.findViewById(R.id.fl_select_address).setVisibility(View.GONE);
        // 动画显示View
        if (getView(R.id.ll_address_from_to).getVisibility() != View.VISIBLE) {
            AnimationUtils.foldingShowView(getView(R.id.ll_address_from_to), 300);
        }
        // 网点地址
        setText(R.id.tv_from_name, bean.getOrg_consignee());
        setText(R.id.tv_from_phone, FormatUtil.phoneSetMiddleEmpty(bean.getOrg_consigneetel()));
        TextView mTvFromOrgAddress = getView(R.id.tv_from_org_address);
        mTvFromOrgAddress.setText(new SpanUtils()
                .append(" " + bean.getOrg_name() + " ")
                .setFontSize(12, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.base_text_color_light))
                .setBackgroundColor(Color.parseColor("#336fba2c"))
                .append("  " + bean.getOrg_address())
                .setFontSize(13, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333)).create());
        // 百网千翌地址
        setText(R.id.tv_to_name, bean.getOrg_consignee1());
        setText(R.id.tv_to_phone, FormatUtil.phoneSetMiddleEmpty(bean.getOrg_consigneetel1()));
        TextView mTvToOrgAddress = getView(R.id.tv_to_org_address);
        mTvToOrgAddress.setText(new SpanUtils()
                .append(" " + bean.getOrg_name1() + " ")
                .setFontSize(12, true)
                .setForegroundColor(Color.parseColor("#F15B02"))
                .setBackgroundColor(Color.parseColor("#33f15b02"))
                .append("  " + bean.getOrg_address1())
                .setFontSize(13, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333)).create());
    }

    /**
     * 检查提交按钮状态  （地址、租赁天数、运输方式、预计使用时间）
     */
    @Override
    public boolean checkStatus() {
        return orgAddress != null && !TextUtils.isEmpty(set_rentdays)
                && !TextUtils.isEmpty(flag_send);
    }

    /**
     * 计算金额价格
     *
     * @param mData 选择物品
     */
    @Override
    public void calculate(List<RenewalRtp.PdListBean> mData) {
        // 租箱费 = 箱子单价 * 个数
        double rentalFee = 0;
        // 耗材费 = 耗材单价 * 个数
        double consumablesFee = 0;
        // 运费 =  箱子运费单价 * 个数
        double freight = 0;
        // 清洗费
        double cleaningFee = 0;
        // 优惠
        double Offer = 0;
        for (RenewalRtp.PdListBean mDatum : mData) {
            rentalFee += mDatum.getRent_price() * mDatum.getOrder_qty();
            if (flag_send.equals("0")) { // 只有配送才需要计算运费
                freight += Double.valueOf(mDatum.getTms_price()) * mDatum.getOrder_qty();
            }
        }
        // 总费用 = 所有费用相加
        double totalCost = rentalFee + consumablesFee + freight;
        // 实付金额 = 总费用 - 优惠
        double payAmount = totalCost - Offer;
        // 设置数据 总费用
        setText(R.id.tv_order_amount, "¥ " + NumberUtil.formitNumberTwoPoint(totalCost));
        // 优惠
        setText(R.id.tv_preferential, "优惠: ¥ 0.00");
        chargeDetail = new ChargeDetail(rentalFee,
                consumablesFee, freight, cleaningFee, Offer, payAmount, totalCost);
    }

    /**
     * 续租提交订单
     */
    @Override
    public void submitOrder(String order_note) {
        if(CollectionUtils.isNullOrEmpty(orgAddress.getPdList())) return;
        RenewalAddress.PdListBean address = orgAddress.getPdList().get(0);
        mRxManager.add(mModel.saveOrderInfo(CompanyParams.saveContLeaOrderInfo(address.getOrg_id()
                , flag_send, mView.getAdapter().getData(), UserHelper.getUserInfo().getLoginInfo().getCompany_id()
                , UserHelper.getUserInfo().getLoginInfo().getCompany_name(), order_note, set_rentdays, address.getOrg_id1()))
                .subscribeWith(new BaseObserver<SubmitOrder>() {
                    @Override
                    protected void onSuccess(SubmitOrder submitOrder) {
                        mView.onSubmitSuccess(submitOrder);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 默认地址
     */
    @Override
    public void getDefaultOrgsList() {
        mRxManager.add(mModel.getDefaultOrgsList(CompanyParams.getDefaultOrgsList())
                .subscribeWith(new BaseObserver<RenewalAddress>() {
                    @Override
                    protected void onSuccess(RenewalAddress address) {
                        orgAddress = address;
                        setAddress(orgAddress);
                        // 获取租赁天数
                        getRentaDays();
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
    public RenewalAddress.PdListBean getAddress() {
        return orgAddress.getPdList().get(0);
    }
}
