package com.horen.service.ui.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.horen.base.base.AppManager;
import com.horen.base.base.BaseFragment;
import com.horen.base.bean.LoginBean;
import com.horen.base.constant.ARouterPath;
import com.horen.base.util.AppUtil;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.UserHelper;
import com.horen.service.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author :ChenYangYi
 * @date :2018/09/04/15:24
 * @description :个人中心
 * @github :https://github.com/chenyy0708
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mLlUser;
    private CircleImageView mCivUser;
    private TextView mTvSnack;
    private TextView mTvPhone;
    private LinearLayout mLlBusinessScope;
    private LinearLayout mLlPlatformSupport;
    private LinearLayout mLlSwitch;
    private LinearLayout mLlVersion;
    private TextView mTvVersion;
    private TextView mTvAccountType;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_me;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlUser = (LinearLayout) rootView.findViewById(R.id.ll_user);
        mCivUser = (CircleImageView) rootView.findViewById(R.id.civ_user);
        mTvSnack = (TextView) rootView.findViewById(R.id.tv_snack);
        mTvPhone = (TextView) rootView.findViewById(R.id.tv_phone);
        mTvAccountType = (TextView) rootView.findViewById(R.id.tv_account_type);
        mTvVersion = (TextView) rootView.findViewById(R.id.tv_version);
        mLlBusinessScope = (LinearLayout) rootView.findViewById(R.id.ll_business_scope);
        mLlPlatformSupport = (LinearLayout) rootView.findViewById(R.id.ll_platform_support);
        mLlSwitch = (LinearLayout) rootView.findViewById(R.id.ll_switch);
        mLlVersion = (LinearLayout) rootView.findViewById(R.id.ll_version);
        mLlUser.setOnClickListener(this);
        mLlBusinessScope.setOnClickListener(this);
        mLlPlatformSupport.setOnClickListener(this);
        mLlSwitch.setOnClickListener(this);
        initUserInfo();
    }

    private void initUserInfo() {
        LoginBean.LoginInfoBean infoBean = UserHelper.getUserInfo().getLoginInfo();
        ImageLoader.load(_mActivity, UserHelper.getUserInfo().getLoginInfo().getPhoto(), mCivUser);
        mTvSnack.setText(infoBean.getUser_nickname());
        mTvPhone.setText(infoBean.getUser_mobile());
        // 版本
        mTvVersion.setText("V" + AppUtil.getVerName(_mActivity));
        // 如果身份既是合伙人又是服务商才显示切换按钮
        if (UserHelper.isPartnerAndService()) {
            mLlSwitch.setVisibility(View.VISIBLE);
        } else {
//            rootView.findViewById(R.id.ll_type).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_user) { // 基本信息
            ARouter.getInstance().build(ARouterPath.USER_INFO).navigation();
        } else if (view.getId() == R.id.ll_business_scope) { // 业务范围
            ARouter.getInstance().build(ARouterPath.BUSINESS_SCOPE).navigation();
        } else if (view.getId() == R.id.ll_platform_support) { // 平台支援
            ARouter.getInstance().build(ARouterPath.PLATFORM_SUPPORT).navigation();
        } else if (view.getId() == R.id.ll_switch) { // 切换身份
            AppManager.getAppManager().finishAllActivity();
            // 打开切换页面
            ARouter.getInstance().build(ARouterPath.USER_PLATFORM).navigation();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCivUser != null)
            ImageLoader.load(_mActivity, UserHelper.getUserInfo().getLoginInfo().getPhoto(), mCivUser);
        // 更新昵称
        if (mTvSnack != null)
            mTvSnack.setText(UserHelper.getUserInfo().getLoginInfo().getUser_nickname());
    }
}
