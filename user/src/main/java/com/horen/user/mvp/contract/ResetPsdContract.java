package com.horen.user.mvp.contract;

import android.widget.EditText;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;


/**
 * Created by Chen on 2017/4/17.
 */
public interface ResetPsdContract {
    interface Model extends BaseModel {
    }
    interface View extends BaseView {
        void showInputCodeView();
        void showInputPasswordView();
        void toLogin();
        void showPhoneError(String error);
        void showPsdCommitError(String error);
        void verifyCodeError(String message);
        void showLoadingSendCode();
        void showLoadingCommit();
    }
    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void sendCode(EditText etPhone);
        public abstract void reSendCode();
        public abstract void verifyCode(String s);
        public abstract void commit(String title,EditText etPassword);
    }
}