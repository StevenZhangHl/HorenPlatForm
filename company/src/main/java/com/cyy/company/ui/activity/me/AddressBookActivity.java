package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cyy.company.R;
import com.cyy.company.enums.OrgType;
import com.cyy.company.ui.fragment.me.AddressBookFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.horen.base.base.AppManager;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 地址簿
 */
public class AddressBookActivity extends SupportActivity {
    private HRTitle mToolBar;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"上游企业", "下游企业"};

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AddressBookActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        AppManager.getAppManager().addActivity(this);
        if (UserHelper.checkDownStream()) { // 下游不需要显示切换
            mFragments.add(AddressBookFragment.newInstance(OrgType.ONE.getPosition(), AddressBookFragment.NORMAL));
        } else {
            mFragments.add(AddressBookFragment.newInstance(OrgType.ONE.getPosition(), AddressBookFragment.NORMAL));
            mFragments.add(AddressBookFragment.newInstance(OrgType.TWO.getPosition(), AddressBookFragment.NORMAL));
        }
        initView();
    }

    public void initView() {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mToolBar.bindActivity(this, R.color.white);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout.setViewPager(mViewPager, mTitles, this, mFragments);
        if (UserHelper.checkDownStream()) { // 下游不需要显示切换
            mTabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }
}
