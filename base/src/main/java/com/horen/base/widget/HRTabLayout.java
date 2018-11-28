package com.horen.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.R;
import com.horen.base.listener.TabChangeListener;
import com.horen.base.util.AnimationUtils;
import com.jaeger.library.StatusBarUtil;

/**
 * 用于切换两级菜单的toolbar
 */
public class HRTabLayout extends FrameLayout implements View.OnClickListener {

    private FrameLayout toolbar;
    private TextView tvLeft, tvRight;
    private Context context;
    private String leftText;
    private String rightText;
    private static float DEFAULT_MAX_TV_SIZE = 18.0f;
    private static float DEFAULT_MIN_TV_SIZE = 16.0f;
    private static float tv_max_size = DEFAULT_MAX_TV_SIZE;
    private static float tv_min_size = DEFAULT_MIN_TV_SIZE;
    /**
     * 是否是白色背景Toolbar
     */
    private boolean isWhite = true;
    /**
     * 选中左边，默认选择
     */
    private boolean isSelectLeft = true;
    /**
     * 默认2dp阴影
     */
    private int elevation = 0;
    private View driverLeft;
    private View driverRight;
    /**
     * 绑定Viewpager
     */
    private ViewPager viewPager;
    private TabChangeListener listener;
    private ImageView ivBack;

    public HRTabLayout(Context context) {
        this(context, null);
        this.context = context;
    }

    public HRTabLayout(Context context, AttributeSet attrs) {
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
                attrs, R.styleable.HRTabLayout, 0, 0);
        try {
            leftText = a.getString(R.styleable.HRTabLayout_sw_left);
            rightText = a.getString(R.styleable.HRTabLayout_sw_right);
            elevation = a.getInteger(R.styleable.HRTabLayout_sw_elevation, 0);
            isWhite = a.getBoolean(R.styleable.HRTabLayout_sw_is_white, true);
            tv_max_size = a.getDimension(R.styleable.HRTabLayout_sw_max_tv_size, DEFAULT_MAX_TV_SIZE);
            tv_min_size = a.getDimension(R.styleable.HRTabLayout_sw_max_tv_size, DEFAULT_MIN_TV_SIZE);
        } finally {
            a.recycle();
        }
        View.inflate(context, R.layout.horen_switch_tl_black, this);
        toolbar = findViewById(R.id.sw_tl_black);
        tvLeft = findViewById(R.id.tv_left);
        ivBack = findViewById(R.id.iv_back);
        tvRight = findViewById(R.id.tv_right);
        driverLeft = findViewById(R.id.driver_left);
        driverRight = findViewById(R.id.driver_right);
        findViewById(R.id.ll_left).setOnClickListener(this);
        findViewById(R.id.ll_right).setOnClickListener(this);
        if (isWhite) {
            setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            setBackgroundColor(context.getResources().getColor(R.color.color_f5));
        }
        tvLeft.setText(leftText);
        tvRight.setText(rightText);
    }

    public void bind(final AppCompatActivity activity, ViewPager viewPager) {
        bindActivity(activity);
        bindViewPager(viewPager);
    }

    /**
     * 标题背景颜色
     *
     * @param color
     */
    public void setBackGroundColor(int color) {
        toolbar.setBackgroundColor(context.getResources().getColor(color));
    }

    /**
     * 初始化toolbar
     */
    public void bindActivity(final AppCompatActivity activity) {
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(activity, ContextCompat.getColor(activity, R.color.white), 0);
        } else {
            StatusBarUtil.setColor(activity, ContextCompat.getColor(activity, R.color.black), 0);
        }
    }

    /**
     * 绑定Viewpager
     */
    public void bindViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    selectLeftTab();
                } else {
                    selectRightTab();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 左右选择listener
     *
     * @param listener
     */
    public void setTabChangeListener(TabChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_left) {
            selectLeftTab();
        } else if (v.getId() == R.id.ll_right) {
            selectRightTab();
        }
    }

    /**
     * 左边被选择
     */
    private void selectLeftTab() {
        if (!isSelectLeft) { // 当不是选择左边，左边文字变大，右边文字变小
            AnimationUtils.scaleView(tvLeft, 300, 0.95f, 1.00f); // 左边放大
            AnimationUtils.scaleView(tvRight, 300, 1.05f, 1.00f); // 右边缩小
            AnimationUtils.scaleView(driverLeft, 300, 0f, 1f);
            AnimationUtils.scaleView(driverRight, 300, 1f, 0f);
            if (viewPager != null) viewPager.setCurrentItem(0);
            if (listener != null) listener.leftClick();
            isSelectLeft = true;
        }
    }


    /**
     * 右边被选择
     */
    private void selectRightTab() {
        if (isSelectLeft) {
            AnimationUtils.scaleView(tvLeft, 300, 1.0f, 0.95f); // 左边缩小
            AnimationUtils.scaleView(tvRight, 300, 1.0f, 1.05f); // 右边放大
            driverRight.setVisibility(VISIBLE);
            AnimationUtils.scaleView(driverRight, 300, 0f, 1f);
            AnimationUtils.scaleView(driverLeft, 300, 1f, 0f);
            if (viewPager != null) viewPager.setCurrentItem(1);
            if (listener != null) listener.rightClick();
            isSelectLeft = false;
        }
    }
}
