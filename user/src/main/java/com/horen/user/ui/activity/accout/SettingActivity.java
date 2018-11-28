package com.horen.user.ui.activity.accout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.SuperButton;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.horen.base.base.AppManager;
import com.horen.base.base.BaseActivity;
import com.horen.base.base.BaseWebViewActivity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.net.Url;
import com.horen.base.util.AppUtil;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;
import com.horen.base.widget.SheetDialog;
import com.horen.user.R;

/**
 * @author :ChenYangYi
 * @date :2018/10/15/13:25
 * @description :
 * @github :https://github.com/chenyy0708
 */
@Route(path = ARouterPath.USER_SETTING)
public class SettingActivity extends BaseActivity implements View.OnClickListener, OnOperItemClickL {

    private HRTitle mToolBar;
    private RelativeLayout mRlSuggestionClick;
    private RelativeLayout mRlServiceClick;
    private RelativeLayout mRlAboutClick;
    private SuperButton mSbtLogout;
    private TextView mTvVersion;

    private SheetDialog logOutDilaog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mToolBar.bindActivity(this);
        mRlSuggestionClick = (RelativeLayout) findViewById(R.id.rl_suggestion_click);
        mRlServiceClick = (RelativeLayout) findViewById(R.id.rl_service_click);
        mRlAboutClick = (RelativeLayout) findViewById(R.id.rl_about_click);
        mSbtLogout = (SuperButton) findViewById(R.id.sbt_logout);
        mTvVersion = (TextView) findViewById(R.id.tv_version);

        mRlSuggestionClick.setOnClickListener(this);
        mRlServiceClick.setOnClickListener(this);
        mRlAboutClick.setOnClickListener(this);
        mSbtLogout.setOnClickListener(this);

        // 退出登陆弹框
        logOutDilaog = new SheetDialog(mContext, new String[]{"退出登录"}, null)
                .isTitleShow(false)
                .cancelText("取消");
        logOutDilaog.setOnOperItemClickL(this);

        mTvVersion.setText("V" + AppUtil.getVerName(this));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sbt_logout) { // 退出登陆
            logOutDilaog.show();
        } else if (view.getId() == R.id.rl_suggestion_click) { // 投诉建议
            ComplaintsActivity.startActivity(mContext);
        } else if (view.getId() == R.id.rl_service_click) { // 服务协议
            BaseWebViewActivity.startAction(mContext, Url.SERVICE_AGREEMENT);
        } else if (view.getId() == R.id.rl_about_click) { // 关于箱箱
            BaseWebViewActivity.startAction(mContext, Url.ABOUT_WEB);
        }
    }

    @Override
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: // 退出登录
                // 退出登陆
                logOutDilaog.dismiss();
                UserHelper.logOut();
                AppManager.getAppManager().finishAllActivity();
                // 打开登陆页面
                ARouter.getInstance().build(ARouterPath.USER_LOGIN).navigation();
                break;
        }
    }
}
