package com.horen.partner.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.horen.base.base.AppManager;
import com.horen.base.base.BaseFragment;
import com.horen.base.bean.LoginBean;
import com.horen.base.bean.UserLevelBean;
import com.horen.base.constant.ARouterPath;
import com.horen.base.constant.Constants;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.AppUtil;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.NumberUtil;
import com.horen.base.util.UserHelper;
import com.horen.partner.R;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.WalletInfo;
import com.horen.partner.event.EventConstans;
import com.horen.base.constant.MsgEvent;
import com.horen.partner.ui.activity.user.WalletActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:Steven
 * Time:2018/8/15 8:41
 * Description:This isUserCenterFragment
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView mIvUserHead;
    /**
     * sdff
     */
    private TextView mTvSnack;
    /**
     * 15921326326
     */
    private TextView mTvPhone;
    private TextView mTvWalletMoney;
    private RelativeLayout mRlWallet;
    private RelativeLayout mRlPlatformSupport;
    private TextView mTvIdentify;
    private RelativeLayout mRlSwitchIdentify;
    private TextView mTvVersion;
    private RelativeLayout mRlVersion;
    private RelativeLayout mRlUserInfo;
    private RefreshLayout refreshview_user_center;
    private TextView tv_liquid_level;
    private TextView tv_fresh_level;
    private TextView tv_parts_level;
    private ImageView iv_liquid;
    private ImageView iv_fresh;
    private ImageView iv_parts;

    public static UserCenterFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        UserCenterFragment userCenterFragment = new UserCenterFragment();
        userCenterFragment.setArguments(bundle);
        return userCenterFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partner_fragment_user_center;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mIvUserHead = (CircleImageView) rootView.findViewById(R.id.iv_user_head);
        mTvSnack = (TextView) rootView.findViewById(R.id.tv_snack);
        mTvPhone = (TextView) rootView.findViewById(R.id.tv_phone);
        mTvWalletMoney = (TextView) rootView.findViewById(R.id.tv_wallet_money);
        mRlWallet = (RelativeLayout) rootView.findViewById(R.id.rl_wallet);
        mRlPlatformSupport = (RelativeLayout) rootView.findViewById(R.id.rl_platform_support);
        mTvIdentify = (TextView) rootView.findViewById(R.id.tv_identify);
        mRlSwitchIdentify = (RelativeLayout) rootView.findViewById(R.id.rl_switch_identify);
        mTvVersion = (TextView) rootView.findViewById(R.id.tv_version);
        mRlVersion = (RelativeLayout) rootView.findViewById(R.id.rl_version);
        mRlUserInfo = (RelativeLayout) rootView.findViewById(R.id.rl_user_info);
        refreshview_user_center = (SmartRefreshLayout) rootView.findViewById(R.id.refreshview_user_center);
        tv_fresh_level = (TextView) rootView.findViewById(R.id.tv_fresh_level);
        tv_liquid_level = (TextView) rootView.findViewById(R.id.tv_liquid_level);
        tv_parts_level = (TextView) rootView.findViewById(R.id.tv_parts_level);
        iv_fresh = (ImageView) rootView.findViewById(R.id.iv_fresh);
        iv_liquid = (ImageView) rootView.findViewById(R.id.iv_liquid);
        iv_parts = (ImageView) rootView.findViewById(R.id.iv_parts);
        initClick();
        initUserInfo();
        setFrefrsh();
        EventBus.getDefault().register(this);
    }

    private void setFrefrsh() {
        refreshview_user_center.setEnableLoadMore(false);
//        refreshview_user_center.setRefreshHeader(new BezierCircleHeader(_mActivity));
//        refreshview_user_center.getLayout().setBackgroundResource(R.color.color_main);
//        refreshview_user_center.setPrimaryColorsId(R.color.color_main);
        refreshview_user_center.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initUserInfo();
                refreshLayout.finishRefresh();
            }
        });
    }

    private void initClick() {
        mRlPlatformSupport.setOnClickListener(this);
        mRlSwitchIdentify.setOnClickListener(this);
        mRlVersion.setOnClickListener(this);
        mRlWallet.setOnClickListener(this);
        mRlUserInfo.setOnClickListener(this);
    }

    private void initUserInfo() {
        getUserInfo();
        getWalletInfo();
        getLevelData();
        mTvVersion.setText("V" + AppUtil.getVerName(_mActivity));
    }

    private void getUserInfo() {
        LoginBean.LoginInfoBean infoBean = UserHelper.getUserInfo().getLoginInfo();
        if (infoBean != null) {
            ImageLoader.load(_mActivity, infoBean.getPhoto(), mIvUserHead);
            mTvSnack.setText(infoBean.getUser_nickname());
            mTvPhone.setText(infoBean.getUser_mobile());
            if ("5".equals(infoBean.getCompany_class())) {
                mTvIdentify.setText("业务发展商");
            } else {
                mRlSwitchIdentify.setVisibility(View.GONE);
            }
        }
    }

    private double money = 0.00;

    @Override
    public void onClick(View view) {
        if (view == mRlPlatformSupport) {
            ARouter.getInstance().build(ARouterPath.PLATFORM_SUPPORT).navigation();
        }
        if (view == mRlSwitchIdentify) {
            AppManager.getAppManager().finishAllActivity();
            ARouter.getInstance().build(ARouterPath.USER_PLATFORM).navigation();
        }
        if (view == mRlUserInfo) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("levelInfo", mUserLevelBean);
            ARouter.getInstance().build(ARouterPath.USER_INFO).with(bundle).navigation();
        }
        if (view == mRlVersion) {

        }
        if (view == mRlWallet) {
            Intent intent = new Intent();
            intent.setClass(_mActivity, WalletActivity.class);
            intent.putExtra(Constants.PARTNER_USER_WALLET_MONEY, money);
            startActivity(intent);
        }
    }

    @Subscriber
    public void event(MsgEvent msgEvent) {
        if (EventConstans.REFRESH_WALLET_AMOUNT.equals(msgEvent.getEvent())) {
            getWalletInfo();
        }
        if (EventBusCode.REFERSH_USER_INFO.equals(msgEvent.getEvent())) {
            refreshInfo();
        }
    }

    private void refreshInfo() {
        mRxManager.add(ApiPartner.getInstance().getMobileLoginInfoByToken(ApiRequest.getUserInfo()).compose(RxHelper.<LoginBean>getResult()).subscribeWith(new BaseObserver<LoginBean>(_mActivity, false) {
            @Override
            protected void onSuccess(LoginBean loginBean) {
                // 保存用户信息和token
                UserHelper.saveUserInfo(loginBean);
                initUserInfo();
            }

            @Override
            protected void onError(String message) {

            }
        }));
    }

    private UserLevelBean mUserLevelBean;

    /**
     * 获取等级数据
     */
    private void getLevelData() {
        mRxManager.add(ApiPartner.getInstance().getUserLevelInfo(ApiRequest.getUserLevelInfo()).compose(RxHelper.<UserLevelBean>getResult()).subscribeWith(new BaseObserver<UserLevelBean>(_mActivity, true) {
            @Override
            protected void onSuccess(UserLevelBean userLevelBean) {
                mUserLevelBean = userLevelBean;
                setLiquidData(userLevelBean.getLiquid());
                setPartsData(userLevelBean.getParts());
                setFreshData(userLevelBean.getFresh());
            }

            @Override
            protected void onError(String message) {

            }
        }));
    }

    private void setPartsData(UserLevelBean.PartsBean parts) {
        String levelStr = "";
        int level = Integer.parseInt(parts.getLevel());
        switch (level) {
            case 1:
                levelStr = "一级";
                iv_parts.setImageResource(R.mipmap.icon_small_automobiellevel_one);
                break;
            case 2:
                levelStr = "二级";
                iv_parts.setImageResource(R.mipmap.icon_small_automobiellevel_two);
                break;
            case 3:
                levelStr = "三级";
                iv_parts.setImageResource(R.mipmap.icon_small_automobiellevel_three);
                break;
            case 4:
                levelStr = "四级";
                iv_parts.setImageResource(R.mipmap.icon_small_automobiellevel_four);
                break;
        }
        tv_parts_level.setText(levelStr);
    }

    private void setLiquidData(UserLevelBean.LiquidBean liquid) {
        String levelStr = "";
        int level = Integer.parseInt(liquid.getLevel());
        switch (level) {
            case 1:
                levelStr = "一级";
                iv_liquid.setImageResource(R.mipmap.icon_small_fluid_one);
                break;
            case 2:
                levelStr = "二级";
                iv_liquid.setImageResource(R.mipmap.icon_small_fluid_two);
                break;
            case 3:
                levelStr = "三级";
                iv_liquid.setImageResource(R.mipmap.icon_small_fluid_three);
                break;
            case 4:
                levelStr = "四级";
                iv_liquid.setImageResource(R.mipmap.icon_small_fluid_four);
                break;
        }
        tv_liquid_level.setText(levelStr);
    }

    private void setFreshData(UserLevelBean.FreshBean fefresh) {
        String levelStr = "";
        int level = Integer.parseInt(fefresh.getLevel());
        switch (level) {
            case 1:
                levelStr = "一级";
                iv_fresh.setImageResource(R.mipmap.icon_small_fresh_one);
                break;
            case 2:
                levelStr = "二级";
                iv_fresh.setImageResource(R.mipmap.icon_small_fresh_two);
                break;
            case 3:
                levelStr = "三级";
                iv_fresh.setImageResource(R.mipmap.icon_small_fresh_three);
                break;
            case 4:
                levelStr = "四级";
                iv_fresh.setImageResource(R.mipmap.icon_small_fresh_four);
                break;
        }
        tv_fresh_level.setText(levelStr);
    }

    /**
     * 获取钱包数据
     */
    private void getWalletInfo() {
        mRxManager.add(ApiPartner.getInstance().getWalletInfo(ApiRequest.getWalletInfo()).compose(RxHelper.<WalletInfo>getResult()).subscribeWith(new BaseObserver<WalletInfo>(_mActivity, true) {
            @Override
            protected void onSuccess(WalletInfo walletInfo) {
                money = walletInfo.getAmount();
                mTvWalletMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(money));
            }

            @Override
            protected void onError(String message) {
                showToast(message);
            }
        }));
    }

    @Override
    protected boolean isCheckNetWork() {
        return false;
    }
}
