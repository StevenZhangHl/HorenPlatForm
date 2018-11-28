package com.horen.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.R;
import com.horen.base.util.ArgbEvaluatorHolder;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.NavigatorHelper;


/**
 * 底部Tab
 */
public class HRBottomTab extends FrameLayout implements View.OnClickListener, NavigatorHelper.OnNavigatorScrollListener, ViewPager.OnPageChangeListener {

    private Context context;
    private String leftText;
    private String rightText;

    private ImageView mIvBg;
    private TextView mTab1;
    private TextView mTab2;

    private ViewPager viewPager;
    private NavigatorHelper navigatorHelper = new NavigatorHelper();
    /**
     * 监听
     */
    private int screenWidth;

    public HRBottomTab(Context context) {
        this(context, null);
        this.context = context;
    }

    public HRBottomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setUp(attrs);
    }

    /**
     * 设置属性
     *
     * @param attrs
     */
    private void setUp(AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomBottomTab, 0, 0);
        try {
            leftText = a.getString(R.styleable.CustomBottomTab_mTabLeftText);
            rightText = a.getString(R.styleable.CustomBottomTab_mTabRightText);
        } finally {
            a.recycle();
        }
        View.inflate(context, R.layout.include_custom_tab, this);

        mIvBg = (ImageView) findViewById(R.id.iv_bg);
        mTab1 = (TextView) findViewById(R.id.tab_1);
        mTab2 = (TextView) findViewById(R.id.tab_2);
//        mTab1.setText(leftText);
//        mTab2.setText(rightText);

        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        navigatorHelper.setNavigatorScrollListener(this);
        navigatorHelper.setSkimOver(true);
        screenWidth = DisplayUtil.getScreenWidth(context);
        DisplayUtil.setWidgetWidth(mIvBg, (int) (screenWidth * 0.5)); // 设置背景图片的宽度
    }

    /**
     * 设置左侧字符串
     *
     * @param tvLeftText
     */
    public void setTvLeft(String tvLeftText) {
        mTab1.setText(tvLeftText);
    }

    /**
     * 设置右侧字符串
     *
     * @param tvRightText
     */
    public void setTvRight(String tvRightText) {
        mTab2.setText(tvRightText);
    }

    public TextView getRbLeftText() {
        return mTab1;
    }

    public TextView getRbRightText() {
        return mTab2;
    }

    /**
     * 设置左侧选中
     */
    public void setTvLeftChecked() {

    }

    /**
     * 设置右侧选中
     */
    public void setTvRightChecked() {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tab_1) {//左
            viewPager.setCurrentItem(0);
        } else if (i == R.id.tab_2) {//右
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (index == 1) { // 第一个tab滑向第二个tab
            mIvBg.setTranslationX(screenWidth * (0.5f * enterPercent)); // imageview X轴移动距离
            //tab宽度
            mTab1.setTextColor(ArgbEvaluatorHolder.eval(enterPercent, Color.WHITE, getResources().getColor(R.color.color_999)));
            mTab2.setTextColor(ArgbEvaluatorHolder.eval(enterPercent, getResources().getColor(R.color.color_999), Color.WHITE));
        } else { // 滑回第一个tab
            mIvBg.setTranslationX(screenWidth * (0.5f * (1 - enterPercent)));
            mTab2.setTextColor(ArgbEvaluatorHolder.eval(enterPercent, Color.WHITE, getResources().getColor(R.color.color_999)));
            mTab1.setTextColor(ArgbEvaluatorHolder.eval(enterPercent, getResources().getColor(R.color.color_999), Color.WHITE));
        }
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

    }

    @Override
    public void onSelected(int index, int totalCount) {

    }

    @Override
    public void onDeselected(int index, int totalCount) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        navigatorHelper.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        navigatorHelper.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        navigatorHelper.onPageScrollStateChanged(state);
    }

    public interface OnClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public void bindViewPgaer(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
    }
}
