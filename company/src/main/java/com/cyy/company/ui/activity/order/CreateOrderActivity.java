package com.cyy.company.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.cyy.company.R;
import com.cyy.company.ui.fragment.order.RenewalBoxFragment;
import com.cyy.company.ui.fragment.order.RentBoxFragment;
import com.cyy.company.ui.fragment.order.ReturnBoxFragment;
import com.cyy.company.widget.HRNavigatorAdapter;
import com.horen.base.app.BaseApp;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.widget.HRViewPager;
import com.jaeger.library.StatusBarUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/10/19/15:11
 * @description :创建订单
 * @github :https://github.com/chenyy0708
 */
public class CreateOrderActivity extends SupportActivity implements View.OnClickListener {

    private MagicIndicator mMagicIndicator;
    private HRViewPager mViewPager;

    public static int ORDER_TYPE_RENT = 0;
    public static int ORDER_TYPE_RETURN = 1;
    public static int ORDER_TYPE_RENEWAL = 2;

    public static void startAction(Context context) {
        startAction(context, 0);
    }

    public static void startAction(Context context, int position) {
        startAction(context, position, HRConstant.HUNDRED_KILOMETERS);
    }

    public static void startAction(Context context, int position, String type) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("type", type);
        intent.setClass(context, CreateOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        initView(savedInstanceState);
    }

    public void initView(Bundle savedInstanceState) {
        setWhiteStatusBar(R.color.white);
        mMagicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        mViewPager = (HRViewPager) findViewById(R.id.view_pager);
        findViewById(R.id.iv_back).setOnClickListener(this);
        List<SupportFragment> mFragments = new ArrayList<>();
        mFragments.add(RentBoxFragment.newInstance());
        mFragments.add(ReturnBoxFragment.newInstance(getIntent().getStringExtra("type")));
        mFragments.add(RenewalBoxFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(),
                mFragments, new String[]{"租箱", "还箱", "续租"}));
        final List<String> mTitle = Arrays.asList("租箱", "还箱", "续租");
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new HRNavigatorAdapter(mTitle, mViewPager));
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        mViewPager.setCurrentItem(getIntent().getIntExtra("position", ORDER_TYPE_RENT));
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        }
    }
}
