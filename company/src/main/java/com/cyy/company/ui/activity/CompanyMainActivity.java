package com.cyy.company.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cyy.company.R;
import com.cyy.company.ui.activity.me.MessageActivity;
import com.cyy.company.ui.fragment.main.CompanyMapFragment;
import com.cyy.company.ui.fragment.main.EyeFragment;
import com.cyy.company.ui.fragment.main.MeFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.app.HRConstant;
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
 * @description :用户端首页
 * @github :https://github.com/chenyy0708
 */
@Route(path = ARouterPath.COMPANY_MAIN_ACTIVITY)
public class CompanyMainActivity extends SupportActivity implements OnTabSelectListener {
    private CommonTabLayout tabLayout;

    private String[] mTitles;

    public static final int FOUR = 3;

    private int[] mIconUnselectIds = {R.drawable.ic_wanxiang_normal,
            R.drawable.ic_baiwang_normal, R.drawable.ic_eye_normal, R.drawable.ic_me_normal};
    private int[] mIconSelectIds = {R.drawable.ic_wanxiang_select, R.drawable.ic_baiwang_select,
            R.drawable.ic_eye_select, R.drawable.ic_me_select};

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
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
        // 是否是点击通知栏，打开app
        if (getIntent().getBooleanExtra(HRConstant.IS_OPEN_PUSH, false)) { // 点击通知栏打开app，跳转消息中心
            MessageActivity.startAction(this, getIntent().getStringExtra(HRConstant.PUSH_EXTRA));
        }
        // 隐藏系统状态栏
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        // 标题
        mTitles = new String[]{"万箱", "百网",
                "天眼", "我"};
        // 初始化Fragment
        if (savedInstanceState == null) {
            mFragments.add(PlatformFragment.newInstance());
            mFragments.add(CompanyMapFragment.newInstance());
            mFragments.add(EyeFragment.newInstance());
            mFragments.add(MeFragment.newInstance());
            loadMultipleRootFragment(
                    R.id.fl_container,
                    FIRST,
                    mFragments.get(FIRST),
                    mFragments.get(SECOND),
                    mFragments.get(THIRD),
                    mFragments.get(FOUR)
            );
        } else {
            // 从内存中读取Fragment
            mFragments.clear();
            mFragments.add(findFragment(PlatformFragment.class));
            mFragments.add(findFragment(CompanyMapFragment.class));
            mFragments.add(findFragment(EyeFragment.class));
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (position == 1 || position == 2)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
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
            super.onBackPressedSupport();
        }
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ScanActivity.REQUEST && data != null) {
//            //判断是否是来自扫码页面>获取扫码信息
//            //处理扫描结果
//            Bundle bundle = data.getExtras();
//            if (null == bundle) return;
//            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                String result = bundle.getString(CodeUtils.RESULT_STRING);
//                LogUtils.e("二维码扫描结果:" + result);
//                if (result.contains("codeUuid")) {
//                    try {
//                        GsonUtil.getGson().fromJson(result, ScanLogin.class);
//                    } catch (Exception e) {
//                        ToastUitl.showShort("此二维码无效");
//                        return;
//                    }
//                    ScanLoginActivity.startAction(this, result);
//                } else {
//                    ToastUitl.showShort("此二维码无效");
//                }
//            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                ToastUitl.showShort("解析二维码失败");
//            }
//        }
    }
}
