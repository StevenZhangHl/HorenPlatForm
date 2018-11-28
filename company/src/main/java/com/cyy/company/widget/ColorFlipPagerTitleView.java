package com.cyy.company.widget;

import android.content.Context;
import android.view.ViewGroup;

import com.horen.base.util.DisplayUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * @author :ChenYangYi
 * @date :2018/10/19/15:32
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class ColorFlipPagerTitleView extends SimplePagerTitleView {
    private float mChangePercent = 0.5f;

    private float mMinScale = 0.88f;

    public ColorFlipPagerTitleView(Context context) {
        super(context);
        setTextSize(18);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);    // 实现颜色渐变
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor);
        } else {
            setTextColor(mSelectedColor);
        }
        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);
        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor);
        } else {
            setTextColor(mNormalColor);
        }
    }

    @Override
    public void onSelected(int index, int totalCount) {
    }

    @Override
    public void onDeselected(int index, int totalCount) {
    }

    public float getChangePercent() {
        return mChangePercent;
    }

    public void setChangePercent(float changePercent) {
        mChangePercent = changePercent;
    }
}