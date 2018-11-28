package com.cyy.company.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;

/**
 * @author :ChenYangYi
 * @date :2018/11/06/13:13
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class SlideLeftAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1f)
        };
    }
}
