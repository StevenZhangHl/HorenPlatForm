package com.horen.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.LoginBean;
import com.horen.base.constant.ARouterPath;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.EditTextUtils;
import com.horen.base.util.LogUtils;
import com.horen.base.util.MatcherUtils;
import com.horen.base.util.TimeCountUtil;
import com.horen.base.util.ToastUitl;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.PWEditText;
import com.horen.base.widget.RippleButton;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.ui.activity.accout.ResetpsdActivity;

import org.simple.eventbus.EventBus;

public class VerificationCodeActivity extends BaseActivity implements TimeCountUtil.OnTimeOutListener, EditTextUtils.EdittextInputLinstener, View.OnClickListener {
    private EditTextUtils editTextUtils;
    /**
     * 请输入您的手机号
     */
    private PWEditText et_account;
    /**
     * 请输入验证码
     */
    private PWEditText et_verification_code;
    /**
     * 获取验证码
     */
    private TextView tv_register_code;
    /**
     * 后重试
     */
    private TextView tv_register_auth_msg;
    private RippleButton rbt_login;
    /**
     * 用户名登录
     */
    private TextView tv_account_login;
    /**
     * 忘记密码
     */
    private TextView tv_forget_password;
    private LinearLayout ll_content;
    private LinearLayout ll_loginView;
    private TimeCountUtil mTimeCountUtil;
    private TextView tv_register;

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, VerificationCodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verification_code;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setWhiteStatusBar(R.color.white);
        et_account = (PWEditText) findViewById(R.id.et_account);
        et_verification_code = (PWEditText) findViewById(R.id.et_verification_code);
        tv_register_code = (TextView) findViewById(R.id.tv_register_code);
        tv_register_auth_msg = (TextView) findViewById(R.id.tv_register_auth_msg);
        rbt_login = (RippleButton) findViewById(R.id.rbt_login);
        tv_account_login = (TextView) findViewById(R.id.tv_account_login);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_loginView = (LinearLayout) findViewById(R.id.ll_loginView);
        tv_register = (TextView) findViewById(R.id.tv_register);
        rbt_login.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClick();
            }
        });
        mTimeCountUtil = new TimeCountUtil(tv_register_code, 60 * 1000, 1000);
        mTimeCountUtil.setOnTimeOutListener(this);
        editTextUtils = new EditTextUtils();
        editTextUtils.addEdittexts(et_account, et_verification_code);
        editTextUtils.addEdittextInputLinstener(this);
        tv_account_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        tv_register_code.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    private void loginClick() {
        String account = et_account.getText().toString().trim();
        String code = et_verification_code.getText().toString().trim();
        if (!MatcherUtils.isMobilePhone(account)) {
            ToastUitl.showShort("请输入正确的手机号");
            return;
        }
        mRxManager.add(ApiUser.getInstance().mobileUserCodeLogin(UserApiPram.mobileUserCodeLogin(account, code))
                .compose(RxHelper.<LoginBean>getResult())
                .subscribeWith(new BaseObserver<LoginBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        rbt_login.showLoadingButton();
                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        // 保存用户信息和token
                        UserHelper.saveUserInfo(loginBean);
                        loginSuccess(loginBean);
                    }

                    @Override
                    protected void onError(String message) {
                        LoginFail(message);
                    }
                }));
    }

    /**
     * 登陆成功跳转页面
     *
     * @param loginBean 登陆
     */
    private void loginSuccess(LoginBean loginBean) {
        // 会员分类（2-客户 3-合伙人4-服务商5-合伙人+服务商）
        switch (loginBean.getLoginInfo().getCompany_class()) {
            case "3":
                ARouter.getInstance().build(ARouterPath.PARNNER_MAIN_ACTIVITY).navigation();
                break;
            case "4":
                ARouter.getInstance().build(ARouterPath.SERVICE_MAIN_ACTIVITY).navigation();
                break;
            case "5":
                startActivity(PlatformActivity.class);
                break;
            default:
                startActivity(PlatformActivity.class);
                break;
        }
        EventBus.getDefault().post("loginSuccess");
        finish();
    }

    /**
     * 登录失败之后的操作
     */
    private void LoginFail(String msg) {
        rbt_login.showRedButton(msg);
    }

    @Override
    public void timeOut() {
        // 重新发送为红色
        if (tv_register_auth_msg != null)
            tv_register_auth_msg.setVisibility(View.GONE);
        if (tv_register_code != null) {
            tv_register_code.setTextColor(ContextCompat.getColor(mContext, R.color.color_main_color));
            tv_register_code.setText("重新发送验证码");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editTextUtils = null;
    }

    @Override
    public void onSuccess() {
        rbt_login.showGreenButton("验证并登录");
    }

    @Override
    public void onError() {
        rbt_login.showGrayButton();
    }

    @Override
    public void onClick(View view) {
        if (view == tv_account_login) {
            finish();
        }
        if (view == tv_forget_password) {
            ResetpsdActivity.startAction(VerificationCodeActivity.this, "找回登录密码");
        }
        if (view == tv_register_code) {
            getCode();
        }
        if (view == tv_register) {
            RegisterActivity.startRegisterActivity(this);
        }

    }

    private void getCode() {
        String phone = et_account.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUitl.showShort("手机号不能为空");
            return;
        }
        if (!MatcherUtils.isMobilePhone(phone)) {
            ToastUitl.showShort("请输入正确的手机号");
            return;
        }
        mRxManager.add(ApiUser.getInstance().getMobileValidCode(UserApiPram.getMobileValidCode(phone)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
            @Override
            protected void onSuccess(BaseEntry baseEntry) {
                ToastUitl.showShort("验证码已发送");
                mTimeCountUtil.start();
                tv_register_code.setTextColor(ContextCompat.getColor(mContext, R.color.color_999));
                tv_register_auth_msg.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onError(String message) {
                LogUtils.e(message);
            }
        }));
    }
}
