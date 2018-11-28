package com.cyy.company.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cyy.company.R;
import com.cyy.company.enums.OrgType;
import com.cyy.company.ui.fragment.me.AddressBookFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.horen.base.base.AppManager;
import com.horen.base.widget.HRTitle;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 订单选择地址
 */
public class OrderAddressActivity extends SupportActivity {
    private HRTitle mToolBar;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    /**
     * 上游
     */
    public static final int UP_STREAM = 0;
    /**
     * 下游
     */
    public static final int DOWN_STREAM = 1;

    public final static int RESULT_SUCCESS = 66;

    private String[] mTitlesUp = {"上游企业"};
    private String[] mTitlesDown = {"下游企业"};
    private int type;

    public static void startAction(Context context, int type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(context, OrderAddressActivity.class);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Fragment fragment, int type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(fragment.getContext(), OrderAddressActivity.class);
        fragment.startActivityForResult(intent, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        type = getIntent().getIntExtra("type", UP_STREAM);
        AppManager.getAppManager().addActivity(this);
        mFragments.add(AddressBookFragment.newInstance(type == UP_STREAM ?
                OrgType.ONE.getPosition() : OrgType.TWO.getPosition(), AddressBookFragment.ORDER));
        initView();
    }

    public void initView() {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mToolBar.bindActivity(this, R.color.white);
        mToolBar.setIsClose(true);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout.setViewPager(mViewPager, type == UP_STREAM ? mTitlesUp : mTitlesDown, this, mFragments);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }
}
