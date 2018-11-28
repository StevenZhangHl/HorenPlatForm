package com.horen.partner.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:Steven
 * Time:2018/8/27 14:41
 * Description:This isMyToobar
 */
public class MyToobar extends FrameLayout {
    private Toolbar toolbar;
    private ImageView ivRight, ivLeft;
    private TextView tvTitle, tvRight, tvLeft;
    private Context context;
    private String title;
    private String leftTitle;
    private String rightTitle;
    /**
     * 是否是白色背景Toolbar
     */
    private boolean isWhite = false;
    /**
     * 默认2dp阴影
     */
    private int elevation = 0;
    /**
     * Toolbar背景颜色
     */
    @ColorInt
    private int backgroundColor;

    public MyToobar(Context context) {
        this(context, null);
        this.context = context;
    }

    public MyToobar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setUp(attrs);
    }

    /**
     * 设置属性
     *
     * @param attrs 自定义属性
     */
    private void setUp(AttributeSet attrs) {
        backgroundColor = ContextCompat.getColor(context, com.horen.base.R.color.transparent);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, com.horen.base.R.styleable.HRToolbar, 0, 0);
        try {
            title = a.getString(com.horen.base.R.styleable.HRToolbar_title);
            leftTitle = a.getString(com.horen.base.R.styleable.HRToolbar_left_title);
            rightTitle = a.getString(com.horen.base.R.styleable.HRToolbar_right_title);
            elevation = a.getInteger(com.horen.base.R.styleable.HRToolbar_title_elevation, 0);
            isWhite = a.getBoolean(com.horen.base.R.styleable.HRToolbar_is_white, false);
            backgroundColor = a.getColor(com.horen.base.R.styleable.HRToolbar_bg_color, ContextCompat.getColor(context, com.horen.base.R.color.transparent));
        } finally {
            a.recycle();
        }
        if (isWhite) {
            // setElevation生效必须设置背景，且不能为透明
            if (backgroundColor == ContextCompat.getColor(context, com.horen.base.R.color.transparent)) {
                setBackgroundColor(ContextCompat.getColor(context, com.horen.base.R.color.transparent));
            } else {
                setBackgroundColor(backgroundColor);
            }
            View.inflate(context, com.horen.base.R.layout.horen_toolbar_layout_white, this);
            toolbar = (Toolbar) findViewById(com.horen.base.R.id.tool_bar_balck);
            tvTitle = (TextView) findViewById(com.horen.base.R.id.tool_bar_title_tv_black);
            tvRight = (TextView) findViewById(com.horen.base.R.id.right_tv_black);
            tvLeft = (TextView) findViewById(com.horen.base.R.id.left_tv_black);
            ivRight = (ImageView) findViewById(com.horen.base.R.id.right_iv_black);
            ivLeft = (ImageView) findViewById(com.horen.base.R.id.left_iv_black);
        } else {
            // setElevation生效必须设置背景，且不能为透明
            setBackgroundColor(backgroundColor);
            View.inflate(context, com.horen.base.R.layout.horen_toolbar_layout, this);
            toolbar = (Toolbar) findViewById(com.horen.base.R.id.tool_bar_white);
            tvTitle = (TextView) findViewById(com.horen.base.R.id.tool_bar_title_tv_white);
            tvRight = (TextView) findViewById(com.horen.base.R.id.right_tv_white);
            tvLeft = (TextView) findViewById(com.horen.base.R.id.left_tv_white);
            ivRight = (ImageView) findViewById(com.horen.base.R.id.right_iv_white);
            ivLeft = (ImageView) findViewById(com.horen.base.R.id.left_iv_white);
        }
        setToolbarElevation(elevation);
        tvTitle.setText(title);
        tvRight.setText(rightTitle);
        tvLeft.setText(leftTitle);
    }

    /**
     * 设置高度阴影
     *
     * @param elevation 阴影
     */
    public void setToolbarElevation(int elevation) {
        // 高于5.0版本系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(0);
        }
    }

    public void setTitle(String string) {
        tvTitle.setText(string);
    }

    public void setRightImageRes(@DrawableRes int id) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(id);
    }

    /**
     * 获取Toolbar
     *
     * @return
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setOnRightImagListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setOnRightTextListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    public void setRightText(String rightText) {
        tvRight.setText(rightText);
    }

    //    /**
//     * 标题背景颜色
//     *
//     * @param color
//     */
//    public void setBackGroundColor(int color) {
//        toolbar.setBackgroundColor(context.getResources().getColor(color));
//    }
    public void setTvRightVistible(boolean vistible) {
        tvRight.setVisibility(vistible ? View.VISIBLE : View.GONE);
    }
}
