package com.horen.base.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Author:Steven
 * Time:2018/8/27 16:31
 * Description:This isAnimationUtils
 */
public class AnimationUtils {
    /**
     * 平移动画
     *
     * @param view         view
     * @param fromY        开始位置
     * @param toY          结束位置
     * @param duration     动画时间
     * @param interpolator 差值器
     */
    public static void translationView(View view, float fromY, float toY, int duration, TimeInterpolator interpolator, Animator.AnimatorListener listener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.addListener(listener);
        animator.start();
    }

    /**
     * 平移动画
     *
     * @param view         view
     * @param fromY        开始位置
     * @param toY          结束位置
     * @param duration     动画时间
     * @param interpolator 差值器
     */
    public static void translationView(View view, float fromY, float toY, int duration, TimeInterpolator interpolator) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.start();
    }

    public static void translationViewX(View view, float fromX, float toX, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", fromX, toX);
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 渐变动画
     *
     * @param view      view
     * @param fromAlpha 开始透明度
     * @param toAplha   结束透明度
     * @param duration  时间
     */
    public static void alphaView(View view, float fromAlpha, float toAplha, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAplha);
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 旋转动画
     *
     * @param view        view
     * @param from
     * @param to
     * @param duration    时间
     * @param repeatCount 选择周数
     */
    public static void rotationView(View view, float from, float to, int duration, int repeatCount) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", from, to);
        animator.setRepeatCount(repeatCount);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 放大缩小动画
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleView(View view, int duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.5f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.5f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    /**
     * @param view     view
     * @param duration 时间
     * @param fromY
     * @param toY
     */
    public static void scanLineAnim(View view, float fromY, float toY, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        animator.setDuration(duration);
        animator.start();
        animator.setRepeatCount(-1);
    }

    /**
     * 首页卡片显示动画
     *
     * @param duration 时间
     */
    public static void foldingShowView(View view, long duration) {
        // 渐变 Y轴放大
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1).setDuration(duration);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration((long) (duration));
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new OvershootInterpolator());
        view.setVisibility(View.VISIBLE);
        set.playTogether(scaleY, alpha);
        set.start();
    }


    /**
     * 首页卡片隐藏动画
     *
     * @param duration 时间
     */
    public static void foldingHideView(final View view, int duration) {
        // 渐变 Y轴放大
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0).setDuration(duration);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration((long) (duration));
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new OvershootInterpolator());
        set.playTogether(scaleY, alpha);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 普通平移动画，控件执行完动画回到原来的位置
     *
     * @param view     view
     * @param fromY    开始位置
     * @param toY      结束位置
     * @param duration 动画时间
     */
    public static void translation(View view, float fromY, float toY, int duration, Animation.AnimationListener listener) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(duration);
        animation.setAnimationListener(listener);
        view.startAnimation(animation);
    }

    /**
     * 渐变
     * 隐藏View，相当于Visibility==GONE
     */
    public static void setVisibilityGone(final View view, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }


    /**
     * 渐变
     * 显示View，相当于Visibility==VISIBLE
     */
    public static void setVisibilityVisible(final View view, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setAlpha(0f);
                view.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    /**
     * 放大View
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleBigView(View view, int duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.3f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.3f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.setInterpolator(new AnticipateOvershootInterpolator());
        set.start();
    }

    /**
     * 缩小View
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleSmallView(View view, int duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.3f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.3f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.setInterpolator(new AnticipateOvershootInterpolator());
        set.start();
    }

    /**
     * 根据传入的值 放大缩小view
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleView(View view, int duration, float... values) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", values);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", values);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.setInterpolator(new AnticipateOvershootInterpolator());
        set.start();
    }

    /**
     * 根据传入的值 放大缩小RecyclerView
     *
     * @param duration 时间
     */
    public static void scaleRecyclerView(final RecyclerView mRecyclerView, int duration, int formY, int toY) {
        ValueAnimator animator = ValueAnimator.ofInt(formY, toY);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
                lp.height = value;
                mRecyclerView.requestLayout();
            }
        });
        animator.start();
    }
}
