package com.horen.partner.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.app.BaseApp;
import com.horen.base.base.AppManager;
import com.horen.base.net.Url;
import com.horen.base.widget.HRToolbar;
import com.horen.partner.R;
import com.horen.partner.event.EventConstans;
import com.horen.base.constant.MsgEvent;
import com.horen.partner.ui.fragment.PartnerFragmentOne;
import com.jaeger.library.StatusBarUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;

public class PartnerActivity extends SupportActivity implements View.OnClickListener {

    private AppBarLayout mAppbar;
    private ViewPager mViewPager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private HRToolbar tool_bar;
    private String[] mTitles = {"申请流程", "条件及要求", "合作规则"};
    private SlidingTabLayout mSTabLayout;
    private ImageView mIvBanner;

    /**
     * 申请流程
     */
    public static final int APPLICATION_PROCESS = 0;
    /**
     * 条件及要求
     */
    public static final int CONDITIONS_REQUIREMENTS = 1;
    /**
     * 合作规则
     */
    public static final int COOPERATION_RULES = 2;
    private SuperButton mSbtJoin;

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PartnerActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        initView();
    }

    /**
     * 白色状态栏
     * 6.0以上状态栏修改为白色，状态栏字体为黑色
     * 6.0以下状态栏为黑色
     */
    protected void setWhiteStatusBar(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(this, BaseApp.getAppResources().getColor(color), 0);
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(com.horen.base.R.color.black), 0);
        }
    }

    /**
     * 白色背景toolbar
     *
     * @param toolbar
     * @param isWhite 是否白色状态栏
     */
    protected void initToolbar(@NonNull Toolbar toolbar, boolean isWhite) {
        // 白色状态栏
        if (isWhite) {
            setWhiteStatusBar(com.horen.base.R.color.color_f5);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void initView() {
        EventBus.getDefault().register(this);
        tool_bar = (HRToolbar) findViewById(R.id.tl_white);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tool_bar.setElevation(0.0f);
        }
        initToolbar(tool_bar.getToolbar(), true);
        tool_bar.setTitle("箱箱合伙人简介");
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mIvBanner = (ImageView) findViewById(R.id.iv_banner);
        mSbtJoin = (SuperButton) findViewById(R.id.sbt_join);
        mSTabLayout = (SlidingTabLayout) findViewById(R.id.stab_layout);
        mSbtJoin.setOnClickListener(this);
        mIvBanner.setOnClickListener(this);
        mFragments.add(PartnerFragmentOne.newInstance(APPLICATION_PROCESS));
        mFragments.add(PartnerFragmentOne.newInstance(CONDITIONS_REQUIREMENTS));
        mFragments.add(PartnerFragmentOne.newInstance(COOPERATION_RULES));
        mSTabLayout.setViewPager(mViewPager, mTitles, this, mFragments);
        mSTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mAppbar.setExpanded(false); // 点击关闭AppBarlayout
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_banner || v.getId() == R.id.sbt_join) {
            if (AppManager.getAppManager().isOpenActivity(PartnerWebViewActivity.class)) {
                finish();
            } else {
                PartnerWebViewActivity.startAction(this, Url.FREND);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber
    public void onMessageEvent(MsgEvent event) {
        switch (event.getEvent()) {
            case EventConstans.FINISH_WEBVIEW://万箱通知关闭webview
                finish();
                break;
        }
    }
}
