package com.horen.user.ui.activity.accout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.LoginBean;
import com.horen.base.constant.EventBusCode;
import com.horen.base.constant.MsgEvent;
import com.horen.base.util.EditTextHelper;
import com.horen.base.util.EditTextUtils;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.KeybordS;
import com.horen.base.util.MatcherUtils;
import com.horen.base.util.TimeCountUtil;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.PWEditText;
import com.horen.base.widget.PinEntryEditText;
import com.horen.base.widget.RippleButton;
import com.horen.base.widget.VerificationAction;
import com.horen.user.R;
import com.horen.user.mvp.contract.ModifyPhoneContract;
import com.horen.user.mvp.presenter.ModifyPhonePresenter;

import org.simple.eventbus.EventBus;

/**
 * Created by Chen
 * 更换手机号页面
 */
public class ModifyPhoneActivity extends BaseActivity<ModifyPhonePresenter, ModifyPhoneContract.Model> implements ModifyPhoneContract.View, View.OnClickListener, EditTextUtils.EdittextInputLinstener, EditTextHelper.EditTextInputListener {

    private LinearLayout mLlRegisterAuthPhone;
    private PWEditText mEtPhone;
    private RippleButton mBtRegisterNext;
    private LinearLayout mLlResetpsdCode;
    private TextView tvCodeNotice;
    private PinEntryEditText mTxtPinEntry;
    private TextView tvCodeMsg;
    private TextView tvCodeTime;
    private TextView tvError;

    private TimeCountUtil mTimeCountUtil;
    private int type;
    private String org_id;

    public static void startAction(Context context, int type) {
        startAction(context, type, "");
    }


    public static void startAction(Context context, int type, String org_id) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("org_id", org_id);
        intent.setClass(context, ModifyPhoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_modify_phone;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("更换手机号");
        type = getIntent().getIntExtra("type", ChangePhoneActivity.PHONE_NUMBER);
        org_id = getIntent().getStringExtra("org_id");
        mLlRegisterAuthPhone = (LinearLayout) findViewById(R.id.ll_register_auth_phone);
        mEtPhone = (PWEditText) findViewById(R.id.et_phone);
        mBtRegisterNext = (RippleButton) findViewById(R.id.bt_register_next);
        mLlResetpsdCode = (LinearLayout) findViewById(R.id.ll_resetpsd_code);
        tvCodeNotice = (TextView) findViewById(R.id.tv_resetpsd_phone_code);
        mTxtPinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        tvCodeMsg = (TextView) findViewById(R.id.tv_resetpsd_msg);
        tvCodeTime = (TextView) findViewById(R.id.tv_resetpsd_resend);
        tvError = (TextView) findViewById(R.id.tv_resetpsd_error);

        findViewById(R.id.tv_resetpsd_resend).setOnClickListener(this);
        mTxtPinEntry.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void onInputCompleted(CharSequence s) {
                mPresenter.verifyCode(s.toString(), type, org_id);
            }
        });
        new EditTextHelper.Builder().addEditTexts(mEtPhone).addInputLimit(11).addOnInputListener(this).build();
        mTimeCountUtil = new TimeCountUtil(tvCodeTime, 60 * 1000, 1000);
        mTimeCountUtil.setOnTimeOutListener(new TimeCountUtil.OnTimeOutListener() {
            @Override
            public void timeOut() {
                tvCodeMsg.setVisibility(View.GONE);
                tvCodeTime.setText("重新发送验证码");
            }
        });
        mBtRegisterNext.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.sendCode(mEtPhone);
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_resetpsd_resend) {
            mPresenter.reSendCode();
        }
    }

    /**
     * 显示输入验证码布局
     */
    @Override
    public void showInputCodeView() {
        tvCodeNotice.setText(FormatUtil.phoneSetMiddleGone(mEtPhone.getText().toString()));
        mBtRegisterNext.setEnabled(false);
        mEtPhone.setEnabled(false);
        mLlRegisterAuthPhone.setVisibility(View.GONE);
        mLlRegisterAuthPhone.removeAllViews();
        mLlResetpsdCode.setVisibility(View.VISIBLE);
        mLlResetpsdCode.invalidate();
        mTimeCountUtil.start();
        tvCodeMsg.setVisibility(View.VISIBLE);
        mTxtPinEntry.requestFocus();
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(mTxtPinEntry, 0);
        }
    }

    /**
     * 手机格式错误
     *
     * @param message
     */
    @Override
    public void showPhoneError(String message) {
        mBtRegisterNext.showRedButton(message);
    }

    /**
     * 验证码校验失败
     *
     * @param message
     */
    @Override
    public void verifyCodeError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
        tvError.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tvError != null) {
                    tvError.setVisibility(View.GONE);
                }
            }
        }, 2000);
    }

    /**
     * 发送验证码--加载中
     */
    @Override
    public void showLoadingSendCode() {
        mBtRegisterNext.showLoadingButton();
    }

    /**
     * 发送验证码成功
     */
    @Override
    public void stopLoadingSendCode() {
        mBtRegisterNext.showGreenButton();
    }

    @Override
    public void submitSuccess() {
        if (type == ChangePhoneActivity.PHONE_NUMBER) {
            KeybordS.closeKeybord(mTxtPinEntry, mContext);
            finish();
            showToast("修改手机号成功");
            LoginBean userInfo = UserHelper.getUserInfo();
            userInfo.getLoginInfo().setUser_mobile(mEtPhone.getText().toString());
            // 更新本地绑定号码
            UserHelper.saveUserInfo(userInfo);
            EventBus.getDefault().post(new MsgEvent(EventBusCode.REFERSH_USER_INFO));
        } else if (type == ChangePhoneActivity.PHONE_CONTACT) {
            KeybordS.closeKeybord(mTxtPinEntry, mContext);
            finish();
            // 通知业务范围更新联系电话
            EventBus.getDefault().post(mEtPhone.getText().toString().trim(), EventBusCode.CHANGE_ORG_PHONE);
        }
    }

    @Override
    public void onSuccess() {
        mBtRegisterNext.showGreenButton();
    }

    @Override
    public void onError() {
        mBtRegisterNext.showGrayButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimeCountUtil.cancel();
    }

}