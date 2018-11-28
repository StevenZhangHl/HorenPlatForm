package com.cyy.company.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/23/14:01
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class HRNavigatorAdapter extends CommonNavigatorAdapter {

    private List<String> mTitle;
    private ViewPager mViewPager;

    public HRNavigatorAdapter(List<String> mTitle, ViewPager mViewPager) {
        this.mTitle = mTitle;
        this.mViewPager = mViewPager;
    }

    @Override
    public int getCount() {
        return mTitle == null ? 0 : mTitle.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
        simplePagerTitleView.setText(mTitle.get(index));
        simplePagerTitleView.setNormalColor(Color.parseColor("#666666"));
        simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight(UIUtil.dip2px(context, 2));
        indicator.setLineWidth(UIUtil.dip2px(context, 15));
        indicator.setRoundRadius(UIUtil.dip2px(context, 1));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        indicator.setColors(Color.parseColor("#333333"));
        return indicator;
    }
}
