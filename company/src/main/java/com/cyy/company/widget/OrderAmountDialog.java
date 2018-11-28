package com.cyy.company.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.cyy.company.R;
import com.cyy.company.bean.ChargeDetail;
import com.cyy.company.listener.SubmitListener;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.horen.base.util.NumberUtil;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/12:56
 * @description :金额明细
 * @github :https://github.com/chenyy0708
 */
public class OrderAmountDialog extends BottomBaseDialog<OrderAmountDialog> implements View.OnClickListener {

    private View mViewBg;
    private ImageView mIvBack;
    private LinearLayout mLlMoney;
    private TextView mTvRentMoney;
    private TextView mTvSuppliesMoney;
    private TextView mTvFreightMoney;
    private TextView mTvCleaningMoney;
    private TextView mTvAllMoney;
    private TextView mTvPreferentialMoney;
    private TextView mTvActualMoneyName;
    private TextView mTvActualMoney;
    private LinearLayout mLlBottom;
    private LinearLayout mLlBottomMoney;
    private TextView mTvPreferential;
    private LinearLayout mTvAmountDetailsClick;
    private TextView mTvOrderAmount;
    private SuperButton mTvSubmitClick;

    private ChargeDetail data;

    private SubmitListener submitListener;

    public OrderAmountDialog(Context context) {
        super(context);
        mInnerShowAnim = null;
        mInnerDismissAnim = null;
    }

    @Override
    public View onCreateView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_order_amount, null);
        mViewBg = (View) inflate.findViewById(R.id.view_bg);
        mIvBack = (ImageView) inflate.findViewById(R.id.iv_back);
        mLlMoney = (LinearLayout) inflate.findViewById(R.id.ll_money);
        mTvRentMoney = (TextView) inflate.findViewById(R.id.tv_rent_money);
        mTvSuppliesMoney = (TextView) inflate.findViewById(R.id.tv_supplies_money);
        mTvFreightMoney = (TextView) inflate.findViewById(R.id.tv_freight_money);
        mTvCleaningMoney = (TextView) inflate.findViewById(R.id.tv_cleaning_money);
        mTvAllMoney = (TextView) inflate.findViewById(R.id.tv_all_money);
        mTvPreferentialMoney = (TextView) inflate.findViewById(R.id.tv_preferential_money);
        mTvActualMoneyName = (TextView) inflate.findViewById(R.id.tv_actual_money_name);
        mTvActualMoney = (TextView) inflate.findViewById(R.id.tv_actual_money);
        mLlBottom = (LinearLayout) inflate.findViewById(R.id.ll_bottom);
        mLlBottomMoney = (LinearLayout) inflate.findViewById(R.id.ll_bottom_money);
        mTvPreferential = (TextView) inflate.findViewById(R.id.tv_preferential);
        mTvAmountDetailsClick = (LinearLayout) inflate.findViewById(R.id.tv_amount_details_click);
        mTvOrderAmount = (TextView) inflate.findViewById(R.id.tv_order_amount);
        mTvSubmitClick = (SuperButton) inflate.findViewById(R.id.tv_submit_click);
        mLlBottomMoney.setOnClickListener(this);


        return inflate;
    }

    /**
     * 设置金额
     */
    public OrderAmountDialog setData(ChargeDetail data) {
        this.data = data;
        return this;
    }

    /**
     * 提交订单监听
     */
    public OrderAmountDialog setSubmitListener(SubmitListener submitListener) {
        this.submitListener = submitListener;
        return this;
    }

    @Override
    public void setUiBeforShow() {
        findViewById(R.id.view_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mTvSubmitClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitListener != null) submitListener.onSubmit();
                dismiss();
            }
        });
        // 租箱费
        mTvRentMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(data.getRentalFee()));
        // 耗材费
        mTvSuppliesMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(data.getConsumablesFee()));
        // 运费
        mTvFreightMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(data.getFreight()));
        // 清洗费
        mTvCleaningMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(data.getCleaningFee()));
        // 总费用
        mTvAllMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(data.getTotalCost()));
        // 优惠
        mTvPreferentialMoney.setText("- ¥ " + NumberUtil.formitNumberTwoPoint(data.getOffer()));
        // 实付金额
        mTvActualMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(data.getPayAmount()));
        mTvPreferential.setText("优惠: ¥ 0.00");
        mTvOrderAmount.setText("¥ " + NumberUtil.formitNumberTwoPoint(data.getTotalCost()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_bottom_money) { // 金额明细
            dismiss();
        }
    }
}