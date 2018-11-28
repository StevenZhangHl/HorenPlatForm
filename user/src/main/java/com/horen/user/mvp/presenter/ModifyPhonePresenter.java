package com.horen.user.mvp.presenter;

import android.widget.EditText;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.FormatUtil;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.mvp.contract.ModifyPhoneContract;
import com.horen.user.ui.activity.accout.ChangePhoneActivity;

/**
 * Created by HOREN on 2018/2/5.
 */
public class ModifyPhonePresenter extends ModifyPhoneContract.Presenter {
    private String phone = "";
    private String code = "";

    /**
     * 第一次发送验证码
     *
     * @param etPhone 手机号
     */
    @Override
    public void sendCode(EditText etPhone) {
        final String s = etPhone.getText().toString().trim();
        if (FormatUtil.isMobileNO(s)) {
            phone = s;
            send();
        } else {
            mView.showPhoneError("请输入正确的手机号");
        }
    }

    @Override
    public void reSendCode() {
        //处理网络请求，重新发送验证码
        send();
    }

    /**
     * 发送验证码
     */
    private void send() {
        // 发送验证码
        mRxManager.add(ApiUser.getInstance().getMobileValidCode(UserApiPram.getMobileValidCode(phone))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    public void onStart() {
                        mView.showLoadingSendCode();
                    }

                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        mView.showInputCodeView();
                    }

                    @Override
                    protected void onError(String message) {
                        mView.showPhoneError(message);
                    }
                }));
    }

    /**
     * 更换手机号
     *
     * @param s      验证码
     * @param type   判断是更换手机号还是网点联系电话
     * @param org_id 网点id
     */
    @Override
    public void verifyCode(final String s, int type, String org_id) {
        if (type == ChangePhoneActivity.PHONE_NUMBER) {
            mRxManager.add(ApiUser.getInstance().bindMobile(UserApiPram.bindMobile(phone, s))
                    .compose(RxHelper.handleResult())
                    .subscribeWith(new BaseObserver<BaseEntry>() {
                        @Override
                        protected void onSuccess(BaseEntry baseEntry) {
                            // 暂时直接验证成功
                            mView.submitSuccess();
                        }

                        @Override
                        protected void onError(String message) {
                            mView.onError(message);
                        }
                    }));
        } else if (type == ChangePhoneActivity.PHONE_CONTACT) { // 更換网点联系电话
            mRxManager.add(ApiUser.getInstance().updateOrgTel(UserApiPram.updateOrgTel(phone, org_id))
                    .compose(RxHelper.handleResult())
                    .subscribeWith(new BaseObserver<BaseEntry>() {
                        @Override
                        protected void onSuccess(BaseEntry baseEntry) {
                            // 暂时直接验证成功
                            mView.submitSuccess();
                        }

                        @Override
                        protected void onError(String message) {
                            mView.onError(message);
                        }
                    }));
        }

    }
}