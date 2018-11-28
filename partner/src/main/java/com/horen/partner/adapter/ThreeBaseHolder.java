package com.horen.partner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.horen.partner.bean.Plan;

/**
 * Created by BuZhiheng on 2017/12/13.
 * <p>
 * 封装方案item viewholder
 * (布局文件大小可能不一样，需要继承此viewholder 构造方法绑定view即可)
 * <p>
 * 首页热门方案 全部方案 全部热门方案 共用此viewholder
 * <p>
 * 调用setData方法 绑定数据
 */
public class ThreeBaseHolder extends RecyclerView.ViewHolder {

    HorizontalInfiniteCycleViewPager hic_view_pager;

    public ThreeBaseHolder(View itemView) {
        super(itemView);
    }

    public void setData(final Context context, final Plan plan) {
        hic_view_pager.setAdapter(new vPagerAdapter(context,plan.getSolutions()));
        hic_view_pager.setScrollDuration(300);
        hic_view_pager.setInterpolator(new AccelerateDecelerateInterpolator());
        hic_view_pager.setMediumScaled(true);
        hic_view_pager.setMaxPageScale(1.0F);
        hic_view_pager.setMinPageScale(0.8F);
        hic_view_pager.setMinPageScaleOffset(-175F);
    }
}