package com.horen.partner.ui.activity;

import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.app.BaseApp;
import com.horen.base.base.AppManager;
import com.horen.base.bean.TabEntity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.util.ToastUitl;
import com.horen.partner.R;
import com.horen.partner.event.EventConstans;
import com.horen.base.constant.MsgEvent;
import com.horen.partner.ui.fragment.BillCenterFragment;
import com.horen.partner.ui.fragment.CustomerCenterFragment;
import com.horen.partner.ui.fragment.PlatformFragment;
import com.horen.partner.ui.fragment.BusinessCenterFragment;
import com.horen.partner.ui.fragment.UserCenterFragment;
import com.jaeger.library.StatusBarUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = ARouterPath.PARNNER_MAIN_ACTIVITY)
public class BusinessMainActivity extends SupportActivity {
    /**
     * 导航栏数据集合
     */
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    /**
     * 导航栏标题数据源
     */
    private String[] mTabTitles = BaseApp.getAppResources().getStringArray(R.array.parter_tab_items);
    /**
     * 未选中时显示的图片集合
     */
    private int[] mIconUnselectIds = new int[mTabTitles.length];
    /**
     * 选中时显示的图片集合
     */
    private int[] mIconSelectIds = new int[mTabTitles.length];
    private TypedArray unselectArray = BaseApp.getAppResources().obtainTypedArray(R.array.parter_tab_icon_unselect);
    private TypedArray selectArray = BaseApp.getAppResources().obtainTypedArray(R.array.parter_tab_icon_select);

    /**
     * fragment集合
     */
    private SupportFragment[] mFragments = new SupportFragment[mTabTitles.length];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;
    public static final int FIVE = 4;
    /**
     * 当前选中的位置
     */
    private int currentPosition = FIRST;
    /**
     * 万箱
     */
    private PlatformFragment platformFragment;
    /**
     * 客户中心
     */
    private CustomerCenterFragment customerCenterFragment;
    /**
     * 账单中心
     */
    private BillCenterFragment billCenterFragment;
    /**
     * 业务中心
     */
    private BusinessCenterFragment assetAllocationFragment;
    /**
     * 我的
     */
    private UserCenterFragment userCenterFragment;

    /**
     * 底部导航栏
     */
    private CommonTabLayout common_tab_layout;
    /**
     * fragment容器
     */
    private FrameLayout ll_fragment_container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//解决viewpager嵌套SurfaceView时黑屏的问题
        // 隐藏系统状态栏
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        setContentView(R.layout.partner_activity_business_main);
        init();
        if (savedInstanceState == null) {
            platformFragment = new PlatformFragment();
            customerCenterFragment = CustomerCenterFragment.newInstance(mTabTitles[SECOND]);
            assetAllocationFragment = BusinessCenterFragment.newInstance(mTabTitles[THIRD]);
            billCenterFragment = BillCenterFragment.newInstance(mTabTitles[FOUR]);
            userCenterFragment = UserCenterFragment.newInstance(mTabTitles[FIVE]);
            setmFragmentsData();
            loadMultipleRootFragment(R.id.ll_fragment_container, FIRST, mFragments);
        } else {
            customerCenterFragment = findFragment(CustomerCenterFragment.class);
            billCenterFragment = findFragment(BillCenterFragment.class);
            assetAllocationFragment = findFragment(BusinessCenterFragment.class);
            platformFragment = findFragment(PlatformFragment.class);
            userCenterFragment = findFragment(UserCenterFragment.class);
            setmFragmentsData();
        }
        AppManager.getAppManager().addActivity(this);
    }

    /**
     * 设置mFragments数据
     */
    private void setmFragmentsData() {
        mFragments[FIRST] = platformFragment;
        mFragments[SECOND] = customerCenterFragment;
        mFragments[THIRD] = assetAllocationFragment;
        mFragments[FOUR] = billCenterFragment;
        mFragments[FIVE] = userCenterFragment;
    }

    public void init() {
        ll_fragment_container = (FrameLayout) findViewById(R.id.ll_fragment_container);
        common_tab_layout = findViewById(R.id.common_tab_layout);
        initTabData();
        initTabLayout();
        EventBus.getDefault().register(this);
    }

    /**
     * 转换图片资源数据
     */
    private void initTabData() {
        for (int i = 0; i < unselectArray.length(); i++) {
            mIconUnselectIds[i] = unselectArray.getResourceId(i, 0);
        }
        for (int j = 0; j < selectArray.length(); j++) {
            mIconSelectIds[j] = selectArray.getResourceId(j, 0);
        }
    }

    private void initTabLayout() {
        for (int i = 0; i < mTabTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTabTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        common_tab_layout.setTabData(mTabEntities);
        common_tab_layout.setCurrentTab(FIRST);
        common_tab_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        currentPosition = FIRST;
                        break;
                    case 1:
                        currentPosition = SECOND;
                        break;
                    case 2:
                        currentPosition = THIRD;
                        break;
                    case 3:
                        currentPosition = FOUR;
                        break;
                    case 4:
                        currentPosition = FIVE;
                        break;
                }
                showHideFragment(mFragments[position]);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Subscriber
    public void onRecieveEvent(MsgEvent event) {
        if (EventConstans.CHAGE_MAIN_TAB_SHOW_EVENT.equals(event.getEvent())) {
            if (common_tab_layout.getVisibility() == View.GONE) {
                common_tab_layout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp)
                        .duration(300)
                        .repeat(0)
                        .playOn(common_tab_layout);
            }
        }
        if (EventConstans.CHAGE_MAIN_TAB_HIDE_EVENT.equals(event.getEvent())) {
            if (common_tab_layout.getVisibility() == View.VISIBLE) {
                common_tab_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().removeActivity(this);
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
}
