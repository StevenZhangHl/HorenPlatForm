package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.cyy.company.R;
import com.cyy.company.bean.TabTextDto;
import com.cyy.company.enums.OrderStatus;
import com.cyy.company.enums.OrderType;
import com.cyy.company.ui.fragment.me.OrderListFragment;
import com.cyy.company.widget.HRNavigatorAdapter;
import com.horen.base.app.BaseApp;
import com.horen.base.base.BaseFragmentAdapter;
import com.jaeger.library.StatusBarUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/10/16/08:59
 * @description :订单列表
 * @github :https://github.com/chenyy0708
 */
public class OrderListActivity extends SupportActivity implements View.OnClickListener {

    public static int ORDER_TYPE_RENT = 0;
    public static int ORDER_TYPE_RETURN = 1;

    private ArrayList<TabTextDto> mList;

    private ImageView mIvBack;
    private MagicIndicator mMagicIndicator;
    private ViewPager mViewPager;

    private List<SupportFragment> mFragments = new ArrayList<>();

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, OrderListActivity.class);
        intent.putExtra("position", 0);
        context.startActivity(intent);
    }

    public static void startAction(Context context, int position) {
        Intent intent = new Intent();
        intent.setClass(context, OrderListActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView(savedInstanceState);
    }

    public void initView(Bundle savedInstanceState) {
        setWhiteStatusBar(R.color.white);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mMagicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mIvBack.setOnClickListener(this);
        initTitleTabText();
        // 订单类型
        mFragments.add(OrderListFragment.newInstance(mList, OrderType.ONE.getPosition()));
        mFragments.add(OrderListFragment.newInstance(mList, OrderType.TWO.getPosition()));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, new String[]{"租箱", "还箱"}));
        final List<String> mTitle = Arrays.asList("租箱", "还箱");
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new HRNavigatorAdapter(mTitle, mViewPager));
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        mViewPager.setCurrentItem(getIntent().getIntExtra("position", ORDER_TYPE_RENT));
    }

    private void initTitleTabText() {
        //添加二级菜单数据
        mList = new ArrayList<>();
        mList.add(new TabTextDto(OrderStatus.ALL.getText(), OrderStatus.ALL.getPosition()));
        mList.add(new TabTextDto(OrderStatus.ONE.getText(), OrderStatus.ONE.getPosition()));
        mList.add(new TabTextDto(OrderStatus.TWO.getText(), OrderStatus.TWO.getPosition()));
        mList.add(new TabTextDto(OrderStatus.THREE.getText(), OrderStatus.THREE.getPosition()));
        mList.add(new TabTextDto(OrderStatus.FOUR.getText(), OrderStatus.FOUR.getPosition()));
        mList.add(new TabTextDto(OrderStatus.FIVE.getText(), OrderStatus.FIVE.getPosition()));
        mList.add(new TabTextDto(OrderStatus.SIX.getText(), OrderStatus.SIX.getPosition()));
    }

    /**
     * 白色状态栏
     * 6.0以上状态栏修改为白色，状态栏字体为黑色
     * 6.0以下状态栏为黑色
     */
    protected void setWhiteStatusBar(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(this, BaseApp.getAppResources().getColor(color), 0);
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(com.horen.base.R.color.black), 0);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        }
    }
}
