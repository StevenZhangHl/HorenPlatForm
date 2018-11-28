package com.cyy.company.mvp.presenter;

import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.ScanLogin;
import com.cyy.company.mvp.contract.ScanLoginContract;
import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.base.util.GsonUtil;

/**
 * Created by HOREN on 2018/2/24.
 */
public class ScanLoginPresenter extends ScanLoginContract.Presenter {
    private ScanLogin login;

    @Override
    public void login() {
        if (login == null || login.codeUuid == null) {
            return;
        }
        mRxManager.add(mModel.scanloginApplyLogin(CompanyParams.checkLoginInfo(login.codeUuid)).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
            @Override
            protected void onSuccess(BaseEntry baseResponse) {
                mView.success();
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

    @Override
    public void checkCode(String qrCode) {
        login = GsonUtil.getGson().fromJson(qrCode, ScanLogin.class);
        mRxManager.add(mModel.scanloginApplyInfo(CompanyParams.checkLoginInfo(login.codeUuid)).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
            @Override
            protected void onSuccess(BaseEntry baseResponse) {
                mView.codeIsValid();
            }

            @Override
            protected void onError(String message) {
                mView.codeIsNoValid(message);
            }
        }));
    }
}