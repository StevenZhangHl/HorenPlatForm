package com.cyy.company.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cyy.company.R;
import com.cyy.company.mvp.contract.ScanLoginContract;
import com.cyy.company.mvp.presenter.ScanLoginPresenter;
import com.horen.base.base.BaseActivity;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.HRToolbar;
import com.horen.base.widget.RippleButton;


/**
 * Created by HOREN on 2018/2/24.
 */
public class ScanLoginActivity extends BaseActivity<ScanLoginPresenter, ScanLoginContract.Model> implements ScanLoginContract.View {
    private RippleButton btnCommit;
    private static String code;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle bundle) {
        HRToolbar customTitleBar = (HRToolbar) findViewById(R.id.custom_bar);
        initToolbar(customTitleBar.getToolbar(), true, R.color.white);
        btnCommit = (RippleButton) findViewById(R.id.bt_login_commit);
        mPresenter.checkCode(code);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_login_cancel) {
            finish();
        }
    }

    public static void startAction(Context context, String result) {
        code = result;
        Intent intent = new Intent();
        intent.setClass(context, ScanLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onError(String msg) {
        btnCommit.showRedButton(msg);
    }

    @Override
    public void success() {
        ToastUitl.showShort("登录成功");
        finish();
    }

    @Override
    public void codeIsValid() {
        btnCommit.showGreenButton();
        btnCommit.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCommit.showLoadingButton();
                mPresenter.login();
            }
        });
    }

    @Override
    public void codeIsNoValid(String msg) {
        btnCommit.showRedButton(msg);
    }
}