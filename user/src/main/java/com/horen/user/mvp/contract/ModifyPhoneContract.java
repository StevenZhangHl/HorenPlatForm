package com.horen.user.mvp.contract;

import android.widget.EditText;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;


/**
 * Created by Chen on 2017/4/17.
 */
public interface ModifyPhoneContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView {
        void showInputCodeView();

        void showPhoneError(String error);

        void verifyCodeError(String message);

        void showLoadingSendCode();

        void stopLoadingSendCode();

        void submitSuccess();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void sendCode(EditText etPhone);

        public abstract void reSendCode();

        /**
         * 更换手机号
         *
         * @param s      验证码
         * @param type   判断是更换手机号还是网点联系电话
         * @param org_id 网点id
         */
        public abstract void verifyCode(String s, int type, String org_id);

    }
}