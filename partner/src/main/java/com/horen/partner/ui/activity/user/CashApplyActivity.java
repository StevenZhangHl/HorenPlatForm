package com.horen.partner.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.DecimalDigitsInputFilter;
import com.horen.base.util.NumberUtil;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.PWEditText;
import com.horen.base.widget.RippleButton;
import com.horen.partner.R;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.xw.repo.XEditText;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class CashApplyActivity extends BaseActivity implements View.OnClickListener {


    /**
     * 请输入金额
     */
    private PWEditText et_cash_money;
    /**
     * 全部
     */
    private TextView tv_all_money;
    /**
     * 申请提现
     */
    private RippleButton bt_subimit_cash_apply;
    private double cashMoney;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cash_apply;
    }

    @Override
    public void initPresenter() {

    }

    private boolean isFlag = false;

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("提现");
        getTitleBar().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        et_cash_money = (PWEditText) findViewById(R.id.et_cash_money);
        tv_all_money = (TextView) findViewById(R.id.tv_all_money);
        bt_subimit_cash_apply = (RippleButton) findViewById(R.id.bt_subimit_cash_apply);
        tv_all_money.setOnClickListener(this);
        bt_subimit_cash_apply.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
        et_cash_money.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        et_cash_money.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFlag) {
                    return;
                }
                isFlag = true;
                if (s.length() > 0) {
                    double currentMoney = Double.parseDouble(s.toString().replace(",", ""));
                    bt_subimit_cash_apply.setEnabled(true);
                    bt_subimit_cash_apply.showGreenButton();
                    if (currentMoney > cashMoney) {
                        ToastUitl.showShort("不能超过总金额");
                        et_cash_money.setText(NumberUtil.formitNumberTwoPoint(cashMoney));
                    } else {
                        et_cash_money.setText(s);
                    }
                    et_cash_money.setSelection(et_cash_money.getTrimmedString().length());//将光标移至文字末尾
                } else {
                    bt_subimit_cash_apply.setEnabled(false);
                    bt_subimit_cash_apply.showGrayButton();
                }
                isFlag = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cashMoney = getIntent().getDoubleExtra("cashMoney", 0);
        int maxLength = String.valueOf(NumberUtil.formitNumberTwoPoint(cashMoney)).length();
        et_cash_money.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
    }


    @Override
    public void onClick(View view) {
        if (view == tv_all_money) {
            et_cash_money.setText(NumberUtil.formitNumberTwoPoint(cashMoney));
        }
    }

    private void submitData() {
        String money = et_cash_money.getTrimmedString().replace(",", "");
        double m = Double.parseDouble(money);
        if (m == 0) {
            ToastUitl.showShort("金额不能为零");
            return;
        }
        bt_subimit_cash_apply.showLoadingButton();
        mRxManager.add(ApiPartner.getInstance().submitCashApply(ApiRequest.submitCashApply(money)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(this, false) {
            @Override
            protected void onSuccess(BaseEntry baseEntry) {
                ToastUitl.showShort("申请已提交");
                closeActivity();
            }

            @Override
            protected void onError(String message) {
                bt_subimit_cash_apply.showRedButton(message);
                ToastUitl.showShort(message);
            }
        }));
    }

    private void closeActivity() {
        Intent intent = new Intent();
        intent.putExtra("applyMoney", Double.parseDouble(et_cash_money.getTrimmedString().replace(",", "")));
        setResult(RESULT_OK, intent);
        finish();
    }
}
