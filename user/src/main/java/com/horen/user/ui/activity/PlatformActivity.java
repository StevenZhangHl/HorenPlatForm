package com.horen.user.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.SuperButton;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.constant.Constants;
import com.horen.base.util.SPUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRToolbar;
import com.horen.user.R;

/**
 * @author ChenYangYi
 */
@Route(path = ARouterPath.USER_PLATFORM)
public class PlatformActivity extends BaseActivity {

    private HRToolbar mToolBar;
    private SuperButton mSbtPartner;
    private SuperButton mSbtService;

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_platform;
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

    public void service(View view) {
        SPUtils.setSharedBooleanData(this, HRConstant.USER_SELET_PARTNER, false);
        ARouter.getInstance().build(ARouterPath.SERVICE_MAIN_ACTIVITY).navigation();
        finish();
    }

    public void partner(View view) {
        SPUtils.setSharedBooleanData(this, HRConstant.USER_SELET_PARTNER, true);
        ARouter.getInstance().build(ARouterPath.PARNNER_MAIN_ACTIVITY).navigation();
        finish();
    }
}
