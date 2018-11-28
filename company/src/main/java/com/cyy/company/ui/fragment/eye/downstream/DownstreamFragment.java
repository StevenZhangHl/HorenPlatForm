package com.cyy.company.ui.fragment.eye.downstream;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.allen.library.SuperButton;
import com.cyy.company.R;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragment;
import com.horen.base.base.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/15:53
 * @description :下游概况
 * @github :https://github.com/chenyy0708
 */
public class DownstreamFragment extends BaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private SuperButton mSbtBoxNumber;
    private SuperButton mSbtBreakageRate;
    private ViewPager mViewPager;

    private String[] mTitles = {"资产在租量统计", "资产损坏数统计"};

    private List<SupportFragment> mFragments = new ArrayList<>();

    /**
     * 当前状态，使用箱数、破损率排名
     */
    private int currentPosition = 0;

    public static DownstreamFragment newInstance() {
        Bundle args = new Bundle();
        DownstreamFragment fragment = new DownstreamFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_down_steam;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mSbtBoxNumber = (SuperButton) rootView.findViewById(R.id.sbt_box_number);
        mSbtBreakageRate = (SuperButton) rootView.findViewById(R.id.sbt_breakage_rate);
        mSbtBoxNumber.setOnClickListener(this);
        mSbtBreakageRate.setOnClickListener(this);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mFragments.add(StreamBoxFragment.newInstance(HRConstant.BOX_USED));
        mFragments.add(StreamBoxFragment.newInstance(HRConstant.BREAKAGE_RATE));
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles));
        mViewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void changeTab(int position) {
        if (position == 0) {
            currentPosition = 0;
            mSbtBoxNumber.setTextColor(ContextCompat.getColor(_mActivity, R.color.base_text_color_light));
            mSbtBoxNumber.setShapeStrokeColor(ContextCompat.getColor(_mActivity, R.color.base_text_color_light))
                    .setUseShape();
            mSbtBreakageRate.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999));
            mSbtBreakageRate.setShapeStrokeColor(ContextCompat.getColor(_mActivity, R.color.DCDCDC))
                    .setUseShape();
        } else if (position == 1) {
            currentPosition = 1;
            mSbtBreakageRate.setTextColor(ContextCompat.getColor(_mActivity, R.color.base_text_color_light));
            mSbtBreakageRate.setShapeStrokeColor(ContextCompat.getColor(_mActivity, R.color.base_text_color_light))
                    .setUseShape();
            mSbtBoxNumber.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999));
            mSbtBoxNumber.setShapeStrokeColor(ContextCompat.getColor(_mActivity, R.color.DCDCDC))
                    .setUseShape();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sbt_box_number) { // 使用箱数
            if (currentPosition == 0) return;
            currentPosition = 0;
            changeTab(currentPosition);
            mViewPager.setCurrentItem(currentPosition);
        } else if (view.getId() == R.id.sbt_breakage_rate) { // 破损率排名
            if (currentPosition == 1) return;
            currentPosition = 1;
            changeTab(currentPosition);
            mViewPager.setCurrentItem(currentPosition);
        }
    }
}
