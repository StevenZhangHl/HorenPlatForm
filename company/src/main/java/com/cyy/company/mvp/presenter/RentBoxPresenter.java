package com.cyy.company.mvp.presenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
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
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.bean.RentDays;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.enums.OrderType;
import com.cyy.company.listener.ProductSelectListener;
import com.cyy.company.listener.SubmitListener;
import com.cyy.company.mvp.contract.RentBoxContract;
import com.cyy.company.ui.activity.order.OrderAddressActivity;
import com.cyy.company.utils.OrderPickerViewHelper;
import com.cyy.company.utils.OrderUtils;
import com.cyy.company.widget.OrderAmountDialog;
import com.cyy.company.widget.OrderSelectRtpDialog;
import com.horen.base.bean.RadioSelectBean;
import com.horen.base.listener.RadioSelectListener;
import com.horen.base.listener.TimePickerListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.NumberUtil;
import com.horen.base.util.SpanUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.MessageDialog;
import com.horen.base.widget.RadioSelectDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:45
 * @description :租箱
 * @github :https://github.com/chenyy0708
 */
public class RentBoxPresenter extends RentBoxContract.Presenter {

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
     * 租赁物品
     */
    private OrderProducts products;
    /**
     * 租赁天数
     */
    private RadioSelectDialog mRentalDayDialog;
    private OrderSelectRtpDialog orderSelectRtpDialog;
    private RadioSelectDialog mTransportModeDialog;
    private OrderPickerViewHelper timePickerViewHelper;
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
     * 预约使用时间
     */
    private String expect_arrivedate;
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
        orderSelectRtpDialog = OrderSelectRtpDialog.newInstance();
        mTransportModeDialog = new RadioSelectDialog(mContext, OrderUtils.getTransModeBean(), "运输方式");
        timePickerViewHelper = new OrderPickerViewHelper(mContext, "预计使用时间");
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
        orderSelectRtpDialog.setListener(new ProductSelectListener() {
            @Override
            public void onSelectRtp(OrderProducts.PdListBean bean) {
                mView.onSelectProducts(bean);
            }
        });
        mTransportModeDialog.setRadioSelectListener(new RadioSelectListener() {
            @Override
            public void onSelected(RadioSelectBean bean, int position) {
                flag_send = bean.getId();
                setText(R.id.tv_transport_mode, bean.getTabName());
                mView.onSelectTransportMode(flag_send);
            }
        });
        timePickerViewHelper.setTimePickerListener(new TimePickerListener() {
            @Override
            public void onTimePicker(String time) {
                expect_arrivedate = time;
                setText(R.id.tv_order_create_time, time);
                mView.onSelectTime(expect_arrivedate);
            }
        });
    }

    public RentBoxPresenter setText(@IdRes int viewId, CharSequence value) {
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

    /**
     * 选择租赁天数
     */
    @Override
    public void showRentalDays() {
        if (!TextUtils.isEmpty(getText(R.id.tv_rental_days))) {
            new MessageDialog(mContext)
                    .showTitle("重新选择租赁天数")
                    .showContent("是否重新选择租赁天数" + "\n" + "（您需重新选择物品）")
                    .setContentTextSize(13)
                    .setContentTextColor(ContextCompat.getColor(mContext, R.color.color_666))
                    .setButtonTexts("取消", "确认")
                    .setOnClickListene(new MessageDialog.OnClickListener() {
                        @Override
                        public void onLeftClick() {

                        }

                        @Override
                        public void onRightClick() {
                            getRentaDays();
                        }
                    })
                    .show();
        } else {
            getRentaDays();
        }
    }

    @Override
    public void showTransportMode() {
        mTransportModeDialog.show();
    }

    @Override
    public void showTimeSelect() {
        timePickerViewHelper.show();
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
                            mRentalDayDialog = new RadioSelectDialog(mContext, mData, "租赁天数");
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
                            mRentalDayDialog.show();
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
     * 选择物品
     *
     * @param mData 已选择物品
     */
    @Override
    public void showSelectProducts(final FragmentManager fragmentManager, final List<OrderProducts.PdListBean> mData) {
        if (orgAddress == null) {
            mView.onError("请选择租箱网点");
            return;
        }
        if (TextUtils.isEmpty(set_rentdays)) {
            mView.onError("请选择租赁天数");
            return;
        }
        mRxManager.add(mModel.getProductsList(CompanyParams.getProductsList(orgAddress.getPdList().get(0).getPro_org_id(), set_rentdays))
                .subscribeWith(new BaseObserver<OrderProducts>() {
                    @Override
                    protected void onSuccess(OrderProducts products) {
                        // 判断是否有已选择物品
                        for (OrderProducts.PdListBean pdListBean : products.getPdList()) {
                            for (OrderProducts.PdListBean mDatum : mData) {
                                if (mDatum.getProduct_id().equals(pdListBean.getProduct_id())) {
                                    pdListBean.setSelect(true);
                                }
                            }
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("products", products);
                        orderSelectRtpDialog.setArguments(bundle);
                        orderSelectRtpDialog.show(fragmentManager, "OrderSelectRtpDialog");
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 显示上下游网点
     *
     * @param addressBookBean 上游
     */
    private void refreshAddress(AddressBook.PdListBean.ListBean addressBookBean) {
        mRxManager.add(mModel.getDefaultOrgsList(CompanyParams.getOrgId(addressBookBean.getOrg_id()))
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
    }

    /**
     * 检查提交按钮状态  （地址、租赁天数、运输方式、预计使用时间）
     */
    @Override
    public boolean checkStatus() {
        return orgAddress != null && !TextUtils.isEmpty(set_rentdays)
                && !TextUtils.isEmpty(flag_send) && !TextUtils.isEmpty(expect_arrivedate);
    }

    /**
     * 计算金额价格
     *
     * @param mData 选择物品
     */
    @Override
    public void calculate(List<OrderProducts.PdListBean> mData) {
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
        for (OrderProducts.PdListBean mDatum : mData) {
            if (mDatum.getFlag_product().equals("1")) { // 箱子
                rentalFee += mDatum.getRent_price() * mDatum.getOrder_qty();
                if (flag_send.equals("0")) { // 只有配送才需要计算运费
                    freight += Double.valueOf(mDatum.getTms_price()) * mDatum.getOrder_qty();
                }
            } else { // 耗材
                consumablesFee += mDatum.getRent_price() * mDatum.getOrder_qty();
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
     * 提交订单
     */
    @Override
    public void submitOrder(String order_note) {
        DefaultOrgBean.PdListBean address = orgAddress.getPdList().get(0);
        mRxManager.add(mModel.saveOrderInfo(CompanyParams.saveOrderInfo(address.getPro_org_id(), expect_arrivedate,
                "0", flag_send, mView.getAdapter().getData(), UserHelper.getUserInfo().getLoginInfo().getCompany_id()
                , UserHelper.getUserInfo().getLoginInfo().getCompany_name(), order_note, OrderType.ONE.getPosition(), set_rentdays, address.getOrg_id()))
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
