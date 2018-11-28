package com.cyy.company.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cyy.company.R;
import com.cyy.company.ui.fragment.MapFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.base.AppManager;
import com.horen.base.bean.TabEntity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.util.ToastUitl;
import com.horen.partner.ui.fragment.PlatformFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/08/06/09:27
 * @description :未登陆用户端首页
 * @github :https://github.com/chenyy0708
 */
@Route(path = ARouterPath.UN_LOGIN_MAIN_ACTIVITY)
public class UnLoginMainActivity extends SupportActivity implements OnTabSelectListener {
    private CommonTabLayout tabLayout;

    private String[] mTitles;

    private int[] mIconUnselectIds = {
            R.drawable.ic_wanxiang_normal, R.drawable.ic_baiwang_normal, R.drawable.ic_eye_normal};
    private int[] mIconSelectIds = {
            R.drawable.ic_wanxiang_select, R.drawable.ic_baiwang_select,
            R.drawable.ic_eye_select};

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private int prePosition = 0;
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
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        // 标题
        mTitles = new String[]{"万箱", "百网",
                "天眼"};
        // 初始化Fragment
        if (savedInstanceState == null) {
            mFragments.add(PlatformFragment.newInstance());
            mFragments.add(MapFragment.newInstance());
            mFragments.add(PlatformFragment.newInstance());
            loadMultipleRootFragment(
                    R.id.fl_container,
                    FIRST,
                    mFragments.get(FIRST),
                    mFragments.get(SECOND),
                    mFragments.get(THIRD)
            );
        } else {
            // 从内存中读取Fragment
            mFragments.clear();
            mFragments.add(findFragment(PlatformFragment.class));
            mFragments.add(findFragment(MapFragment.class));
            mFragments.add(findFragment(PlatformFragment.class));
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
        if (position == 1 || position == 2) {
            // 未登录跳转登陆页面
            ARouter.getInstance()
                    .build(ARouterPath.USER_LOGIN).navigation();
            tabLayout.setCurrentTab(FIRST);
            tabLayout.setCurrentTab(prePosition);
            return;
        }
        prePosition = position;
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
