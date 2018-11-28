package com.cyy.company.mvp.contract;


import com.cyy.company.api.ApiCompany;
import com.horen.base.bean.BaseEntry;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.base.rx.RxHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by Chen on 2017/4/17.
 */
public interface ScanLoginContract {
    class Model implements BaseModel {
        public Observable<BaseEntry> scanloginApplyInfo(RequestBody body) {
            return ApiCompany.getInstance().scanloginApplyInfo(body)
                    .compose(RxHelper.handleResult());
        }

        public Observable<BaseEntry> scanloginApplyLogin(RequestBody body) {
            return ApiCompany.getInstance().scanloginApplyLogin(body)
                    .compose(RxHelper.handleResult());
        }
    }

    interface View extends BaseView {
        void success();

        void codeIsValid();

        void codeIsNoValid(String msg);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void login();

        public abstract void checkCode(String code);
    }
}