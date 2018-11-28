package com.horen.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.EditTextHelper;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.KeybordS;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.HRToolbar;
import com.horen.base.widget.PWEditText;
import com.horen.base.widget.RippleButton;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;

public class RegisterConsultantActivity extends BaseActivity implements View.OnClickListener, EditTextHelper.EditTextInputListener {

    private HRToolbar mToolBar;
    private PWEditText mEtContanctName;
    private PWEditText mEtPhone;
    private RippleButton mBtSubmit;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonOne;
    private RadioButton mRadioButtonTwo;

    /**
     * 方便时间（可选）
     * 1：9:00-13:00（工作日上午） 2:13:00-18:00 （工作日下午） 4 18:00-21:00 （工作日下班）
     */
    private String consult_worktime;

    public static void startRegisterActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterConsultantActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_consultant;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRToolbar) findViewById(R.id.tool_bar);
        mEtContanctName = (PWEditText) findViewById(R.id.et_contanct_name);
        mEtPhone = (PWEditText) findViewById(R.id.et_phone);
        mBtSubmit = (RippleButton) findViewById(R.id.bt_submit);

        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mRadioButtonOne = (RadioButton) findViewById(R.id.radio_button_one);
        mRadioButtonTwo = (RadioButton) findViewById(R.id.radio_button_two);

        initToolbar(mToolBar.getToolbar(), true, R.color.white);
        //监听
        new EditTextHelper.Builder()
                .addEditTexts(mEtContanctName, mEtPhone)
                .addOnInputListener(this)
                .build();
        mBtSubmit.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 验证手机号码是否正确
                if (!FormatUtil.isMobileNO(mEtPhone.getText().toString())) {
                    mBtSubmit.showRedButton("请输入正确的手机号码");
                    return;
                }
                KeybordS.closeAllKeybord(RegisterConsultantActivity.this);
                // 注册
                mBtSubmit.showLoadingButton();
                submit();
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!TextUtils.isEmpty(mEtPhone.getText()) && !TextUtils.isEmpty(mEtContanctName.getText())) {
                    mBtSubmit.showGreenButton();
                }
                if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_one) {
                    consult_worktime = "1";
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_two) {
                    consult_worktime = "2";
                }
            }
        });
    }

    private void submit() {
        mRxManager.add(ApiUser.getInstance().submitRegister(UserApiPram.submitRegister(mEtContanctName.getText().toString(), mEtPhone.getText().toString(), "1"
                , consult_worktime)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(this, false) {
            @Override
            protected void onSuccess(BaseEntry baseEntry) {
                ToastUitl.showShort("预约成功");
                finish();
            }

            @Override
            protected void onError(String message) {
                mBtSubmit.showRedButton(message);
            }
        }));
    }


    @Override
    public void onSuccess() {
        int checkedRadioButtonId = mRadioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId > 0)
            mBtSubmit.showGreenButton();
    }

    @Override
    public void onError() {
        mBtSubmit.showGrayButton();
    }

    @Override
    public void onClick(View view) {

    }

}
