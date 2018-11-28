package com.horen.service.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.base.AppManager;
import com.horen.base.bean.TabEntity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.util.ToastUitl;
import com.horen.service.R;
import com.horen.service.ui.fragment.main.BillFragment;
import com.horen.service.ui.fragment.main.BusinessFragment;
import com.horen.service.ui.fragment.main.MeFragment;
import com.horen.service.ui.fragment.main.ServiceFragment;
import com.horen.service.ui.fragment.main.StorageFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/08/06/09:27
 * @description :综合服务商首页
 * @github :https://github.com/chenyy0708
 */
@Route(path = ARouterPath.SERVICE_MAIN_ACTIVITY)
public class ServiceMainActivity extends SupportActivity implements OnTabSelectListener {
    private CommonTabLayout tabLayout;

    private String[] mTitles;

    private int[] mIconUnselectIds = {
            R.mipmap.service_ic_business_normal, R.mipmap.service_ic_service_normal,
            R.mipmap.service_ic_warehous_normal,
            R.mipmap.service_ic_bill_normal,
            R.mipmap.service_ic_me_normal};
    private int[] mIconSelectIds = {
            R.mipmap.service_ic_business_select, R.mipmap.service_ic_service_select,
            R.mipmap.service_ic_warehous_select,
            R.mipmap.service_ic_bill_select,
            R.mipmap.service_ic_me_select};

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;
    public static final int FIVE = 4;
    /**
     * 显示Fragment集合
     */
    private List<SupportFragment> mFragments = new ArrayList<>();
    /**
     * 存储初始化TabLayout所需要的数据
     */

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        // 隐藏系统状态栏
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        setContentView(R.layout.service_activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        // 标题
        mTitles = new String[]{getString(R.string.service_business), getString(R.string.service),
                getString(R.string.service_warehouse), getString(R.string.service_billing), getString(R.string.service_me)};
        // 初始化Fragment
        if (savedInstanceState == null) {
            mFragments.add(BusinessFragment.newInstance());
            mFragments.add(ServiceFragment.newInstance());
            mFragments.add(StorageFragment.newInstance());
            mFragments.add(BillFragment.newInstance());
            mFragments.add(MeFragment.newInstance());
            loadMultipleRootFragment(
                    R.id.fl_container,
                    FIRST,
                    mFragments.get(FIRST),
                    mFragments.get(SECOND),
                    mFragments.get(THIRD),
                    mFragments.get(FOUR),
                    mFragments.get(FIVE)
            );
        } else {
            // 从内存中读取Fragment
            mFragments.clear();
            mFragments.add(findFragment(BusinessFragment.class));
            mFragments.add(findFragment(ServiceFragment.class));
            mFragments.add(findFragment(StorageFragment.class));
            mFragments.add(findFragment(BillFragment.class));
            mFragments.add(findFragment(MeFragment.class));
        }
        initTabLayout();
    }

    private void initTabLayout() {
        // 初始化数据
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        // 设置数据集到Tablayout中
        tabLayout.setTabData(mTabEntities);
        // 默认选中第一个Tab
        tabLayout.setCurrentTab(FIRST);
        tabLayout.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelect(int position) {
        showHideFragment(mFragments.get(position));
    }

    @Override
    public void onTabReselect(int position) {

    }

    private long clickTime;

    @Override
    public void onBackPressedSupport() {
        long currentTime = System.currentTimeMillis();
        long time = 2000;
        if ((currentTime - clickTime) > time) {
            ToastUitl.showShort(R.string.hint_exit);
            clickTime = System.currentTimeMillis();
        } else {
//            super.onBackPressedSupport();
            AppManager.getAppManager().finishAllActivity();
        }
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }
}
