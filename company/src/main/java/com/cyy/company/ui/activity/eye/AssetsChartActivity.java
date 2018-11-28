package com.cyy.company.ui.activity.eye;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cyy.company.R;
import com.cyy.company.ui.fragment.eye.asset.AssetDamageChartFragment;
import com.cyy.company.ui.fragment.eye.asset.AssetRentChartFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.widget.HRViewPager;
import com.horen.chart.hrlinechart.HRChart;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/08:42
 * @description :资产分析 图表
 * @github :https://github.com/chenyy0708
 */
public class AssetsChartActivity extends SupportActivity implements OnTabSelectListener {

    private HRChart chart;

    private int type;

    private SlidingTabLayout mTabLayout;
    private ImageView mIvBack;
    private HRViewPager mViewPager;

    private List<SupportFragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"资产在租量统计", "资产损坏数统计"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_assets_chart);
        initView(savedInstanceState);
    }

    public static void startAction(Context context, int type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(context, AssetsChartActivity.class);
        context.startActivity(intent);
    }

    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", HRConstant.RENT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mViewPager = (HRViewPager) findViewById(R.id.view_pager);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mFragments.add(AssetRentChartFragment.newInstance(HRConstant.RENT));
        mFragments.add(AssetDamageChartFragment.newInstance(HRConstant.DAMAGE));
        mViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setViewPager(mViewPager, mTitles);
        mTabLayout.setOnTabSelectListener(this);
        if (type == HRConstant.RENT) {
            mViewPager.setCurrentItem(0);
        } else {
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onTabSelect(int position) {
        mTabLayout.setCurrentTab(position);
    }

    @Override
    public void onTabReselect(int position) {

    }
}
