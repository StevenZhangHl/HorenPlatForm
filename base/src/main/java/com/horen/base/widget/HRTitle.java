package com.horen.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.R;
import com.horen.base.util.DisplayUtil;
import com.jaeger.library.StatusBarUtil;

/**
 * @author :ChenYangYi
 * @date :2018/07/30/15:08
 * @description : 通用Toolbar  左右标题、中间标题、左右图片设置
 * @github :https://github.com/chenyy0708
 */

public class HRTitle extends FrameLayout {
    private LinearLayout mLlLeftBack;
    private ImageView mIvLeft;
    private TextView mTvTitle, mTvRight, mTvLeft;
    private Context mContent;
    private String mTitle, mRightTitle, mLeftTitle;
    private boolean isWhite = true;
    private boolean isDiver = true;
    private boolean isClose;
    private FrameLayout mFlTitle;
    private TextView mTvLeftBack;
    /**
     * 左边文字id
     */
    public static final int TITLE_LEFT_TEXT = R.id.tv_left_back;
    /**
     * 右边文字id
     */
    public static final int TITLE_RIGHT_TEXT = R.id.tv_right;

    public HRTitle(Context context) {
        this(context, null);
        this.mContent = context;
    }

    public HRTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContent = context;
        setUp(attrs);
    }

    /**
     * 设置属性
     *
     * @param attrs 自定义属性
     */
    private void setUp(AttributeSet attrs) {
        TypedArray a = mContent.getTheme().obtainStyledAttributes(
                attrs, R.styleable.HRTitle, 0, 0);
        try {
            mTitle = a.getString(R.styleable.HRTitle_mTitle);
            mRightTitle = a.getString(R.styleable.HRTitle_mRightTitle);
            mLeftTitle = a.getString(R.styleable.HRTitle_mLeftTitle);
            isWhite = a.getBoolean(R.styleable.HRTitle_isWhite, true);
            isDiver = a.getBoolean(R.styleable.HRTitle_isDiver, true);
            isClose = a.getBoolean(R.styleable.HRTitle_isClose, false);
        } finally {
            a.recycle();
        }
        View.inflate(mContent, R.layout.horen_title, this);
        mLlLeftBack = (LinearLayout) findViewById(R.id.ll_left_back);
        mIvLeft = (ImageView) findViewById(R.id.iv_left);
        mTvLeft = (TextView) findViewById(R.id.tv_left);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mFlTitle = (FrameLayout) findViewById(R.id.fl_title);
        mTvLeftBack = (TextView) findViewById(R.id.tv_left_back);
        mTvTitle.setText(mTitle);
        // 设置右标题
        if (!TextUtils.isEmpty(mRightTitle)) {
            setRightText(mRightTitle);
        }
        // 根据不同的背景颜色，设置不同的返回图标
        if (isWhite) {
            mFlTitle.setBackgroundResource(R.color.white);
            mIvLeft.setImageResource(R.drawable.icon_back_black);
            mTvTitle.setTextColor(ContextCompat.getColor(mContent, R.color.color_121));
        } else {
            mFlTitle.setBackgroundResource(R.color.color_main);
            mIvLeft.setImageResource(R.drawable.icon_back_white);
            mTvTitle.setTextColor(ContextCompat.getColor(mContent, R.color.white));
            mTvRight.setTextColor(ContextCompat.getColor(mContent, R.color.white));
        }
        // 左标题
        if (!TextUtils.isEmpty(mLeftTitle)) {
            mLlLeftBack.setVisibility(GONE);
            mTvLeftBack.setVisibility(VISIBLE);
            mTvLeftBack.setText(mLeftTitle);
        }
        // 显示分割线
        if (isDiver) {
            View diver = new View(mContent);
            diver.setBackgroundResource(R.color.color_f1);
            LayoutParams lpDriver = new LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContent, 1));
            lpDriver.gravity = Gravity.BOTTOM;
            addView(diver, lpDriver);
        }
        // 返回
        if (isClose) {
            mIvLeft.setImageResource(R.drawable.icon_back_close);
        }
    }

    /**
     * 返回按钮变为 X
     *
     * @param isClose
     */
    public void setIsClose(boolean isClose) {
        isClose = isClose;
        // 返回
        if (isClose) {
            mIvLeft.setImageResource(R.drawable.icon_back_close);
        } else {
            if (isWhite) {
                mIvLeft.setImageResource(R.drawable.icon_back_black);
            } else {
                mIvLeft.setImageResource(R.drawable.icon_back_white);
            }
        }
    }

    /**
     * Toolbar标题
     *
     * @param string 标题
     */
    public void setTitle(String string) {
        mTvTitle.setText(string);
    }

    /**
     * 用于BaseActivity中统一Toolbar设置右标题
     * 布局使用可以使用属性right_title
     *
     * @param rightText 标题名
     */
    public void setRightText(String rightText) {
        mTvRight.setVisibility(VISIBLE);
        mTvRight.setText(rightText);
    }

    public void setLeftTextColor(@ColorRes int color) {
        mTvLeftBack.setTextColor(ContextCompat.getColor(mContent, color));
    }

    public void setRightTextVisible(int visibility) {
        mTvRight.setVisibility(visibility);
    }

    /**
     * 右标题点击事件
     *
     * @param listener 点击
     */
    public void setOnRightTextListener(OnClickListener listener) {
        mTvRight.setOnClickListener(listener);
    }

    /**
     * 左标题点击事件
     *
     * @param listener 点击
     */
    public void setOnLeftTextListener(OnClickListener listener) {
        mTvLeftBack.setOnClickListener(listener);
    }

    /**
     * 用于BaseActivity中统一Toolbar设置左标题
     * 布局使用可以使用属性left_title
     *
     * @param leftTitle 标题名
     */
    public void setLeftTitle(String leftTitle) {
        if (!TextUtils.isEmpty(leftTitle)) {
            mTvLeft.setVisibility(VISIBLE);
            mTvLeft.setText(leftTitle);
            // 设置返回按钮颜色图标
            mIvLeft.setImageResource(R.drawable.icon_back_green);
            mLlLeftBack.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void bindActivity(final AppCompatActivity activity) {
        bindActivity(activity, 0, "");
    }

    public void bindActivity(final AppCompatActivity activity, @ColorRes int statusColor) {
        bindActivity(activity, statusColor, "");
    }

    /**
     * 设置白色或者灰色状态栏
     */
    public void bindActivity(final AppCompatActivity activity, @ColorRes int statusColor, String mLeftTitle) {
        setLeftTitle(mLeftTitle);
        mLlLeftBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
        mTvLeftBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
        // 版本大于6.0 改变状态栏文字颜色
        if (statusColor == 0) return;
        mFlTitle.setBackgroundResource(statusColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(activity, ContextCompat.getColor(activity, statusColor), 0);
        } else {
            StatusBarUtil.setColor(activity, ContextCompat.getColor(activity, R.color.black), 0);
        }
    }

    public TextView getTvLeft() {
        return mTvLeftBack;
    }
}
