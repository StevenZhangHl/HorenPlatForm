package com.cyy.company.widget;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import com.flyco.animation.BaseAnimatorSet;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/13:37
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BottomEnter extends BaseAnimatorSet {
    @Override
    public void setAnimation(View view) {
        DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
        animatorSet.playTogether(//
                ObjectAnimator.ofFloat(view, "translationY", 250 * dm.density, 0)
        );
    }
}
