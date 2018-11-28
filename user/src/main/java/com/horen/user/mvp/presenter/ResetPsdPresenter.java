package com.horen.user.mvp.presenter;

import android.widget.EditText;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.ToastUitl;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.mvp.contract.ResetPsdContract;


/**
 * Created by HOREN on 2018/2/5.
 */
public class ResetPsdPresenter extends ResetPsdContract.Presenter {
    private String phone = "";
    private String code = "";

    @Override
    public void sendCode(EditText etPhone) {
        final String s = etPhone.getText().toString().trim();
        // 获取验证码
        if (FormatUtil.isMobileNO(s)) {
            mRxManager.add(ApiUser.getInstance().getMobileValidCode(UserApiPram.getMobileValidCode(s))
                    .compose(RxHelper.handleResult())
                    .subscribeWith(new BaseObserver<BaseEntry>() {
                        @Override
                        protected void onSuccess(BaseEntry baseEntry) {
                            //处理网络请求，发送验证码
                            mView.showInputCodeView();
                            phone = s;
                        }

                        @Override
                        protected void onError(String message) {
                            mView.showPhoneError(message);
                        }
                    })
            );
        } else {
            mView.showPhoneError("请输入正确的手机号");
        }
    }

    /**
     * 重新发送验证码
     */
    @Override
    public void reSendCode() {
        mRxManager.add(ApiUser.getInstance().getMobileValidCode(UserApiPram.getMobileValidCode(phone))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        mView.showInputCodeView();
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                })
        );
        mView.showInputCodeView();
    }

    /**
     * 验证码手机号码是否正确
     */
    @Override
    public void verifyCode(final String s) {
        mRxManager.add(ApiUser.getInstance().ValidMobile(UserApiPram.validMobile(phone, s))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        mView.showInputPasswordView();
                        code = s;
                    }

                    @Override
                    protected void onError(String message) {
                        mView.verifyCodeError("验证码输入错误");
                    }
                })
        );
    }

    /**
     * 确认修改密码
     */
    @Override
    public void commit(String title, EditText etPassword) {
        String s = etPassword.getText().toString().trim();
        // 修改密码和重置密码调用不同的接口
        mRxManager.add((title.equals("修改密码") ? ApiUser.getInstance().activeMobilePwd(UserApiPram.activeMobilePwd(phone, s)) :
                ApiUser.getInstance().updateForgetPwdMobile(UserApiPram.activeMobilePwd(phone, s)))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        ToastUitl.showShort("密码设置成功");
                        mView.toLogin();
                    }

                    @Override
                    protected void onError(String message) {
                        mView.showPsdCommitError(message);
                    }
                })
        );
    }
}