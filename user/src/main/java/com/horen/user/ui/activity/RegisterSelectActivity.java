package com.horen.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allen.library.SuperButton;
import com.horen.base.base.BaseActivity;
import com.horen.base.widget.HRToolbar;
import com.horen.user.R;

/**
 * @author ChenYangYi
 */
public class RegisterSelectActivity extends BaseActivity {

    private HRToolbar mToolBar;
    private SuperButton mSbtPartner;
    private SuperButton mSbtService;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterSelectActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_register_select;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setWhiteStatusBar(R.color.white);
        mToolBar = (HRToolbar) findViewById(R.id.tool_bar);
        mSbtPartner = (SuperButton) findViewById(R.id.sbt_partner);
        mSbtService = (SuperButton) findViewById(R.id.sbt_service);
    }

    public void partner(View view) {
        RegisterConsultantActivity.startRegisterActivity(this);
        finish();
    }

    public void service(View view) {
        RegisterActivity.startRegisterActivity(this);
        finish();
    }
}
