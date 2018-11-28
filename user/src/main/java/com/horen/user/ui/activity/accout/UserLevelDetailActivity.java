package com.horen.user.ui.activity.accout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.UserLevelBean;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.NumberUtil;
import com.horen.base.widget.TrigonView;
import com.horen.user.R;
import com.horen.user.ui.adapter.UserLevelTipAdapter;

import java.util.ArrayList;

public class UserLevelDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TrigonView trigon_view;
    private String levelType;
    /**
     * 液体等级说明
     */
    private TextView tv_current_type;
    /**
     * Y轴四级
     */
    private TextView tv_y_four;
    private LinearLayout ll_left_y;
    /**
     * 当前业绩
     */
    private TextView tv_balance_value;
    /**
     * 当前等级
     */
    private TextView tv_current_level;
    /**
     * 四级
     */
    private TextView tv_x_one;
    /**
     * 三级
     */
    private TextView tv_x_two;
    /**
     * 二级
     */
    private TextView tv_x_three;
    /**
     * 一级
     */
    private TextView tv_x_four;
    private ImageView iv_current_level;
    private RelativeLayout rl_left_x;
    private RecyclerView recyclerview_level_tip;
    private UserLevelTipAdapter levelTipAdapter;
    private RelativeLayout rl_trigonView;
    private RelativeLayout rl_level_zero;
    private ImageView iv_level_zero;
    /**
     * 图形父控件的最大宽度
     */
    private int maxWidth;
    /**
     * 图形父控件的最大高度
     */
    private int maxHeight;
    private int firstDashHeight = 40;
    private int sencondDashHeight = 100;


    public static void startLevelDetailActivity(Context context, String levelType, UserLevelBean userLevelBean) {
        Intent intent = new Intent();
        intent.setClass(context, UserLevelDetailActivity.class);
        intent.putExtra("levelType", levelType);
        intent.putExtra("levelInfo", userLevelBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_level_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_current_type = (TextView) findViewById(R.id.tv_current_type);
        tv_y_four = (TextView) findViewById(R.id.tv_y_four);
        ll_left_y = (LinearLayout) findViewById(R.id.ll_left_y);
        tv_balance_value = (TextView) findViewById(R.id.tv_balance_value);
        tv_current_level = (TextView) findViewById(R.id.tv_current_level);
        iv_current_level = (ImageView) findViewById(R.id.iv_current_level);
        trigon_view = (TrigonView) findViewById(R.id.trigon_view);
        tv_x_one = (TextView) findViewById(R.id.tv_x_one);
        tv_x_two = (TextView) findViewById(R.id.tv_x_two);
        tv_x_three = (TextView) findViewById(R.id.tv_x_three);
        tv_x_four = (TextView) findViewById(R.id.tv_x_four);
        rl_left_x = (RelativeLayout) findViewById(R.id.rl_left_x);
        recyclerview_level_tip = (RecyclerView) findViewById(R.id.recyclerview_level_tip);
        rl_trigonView = (RelativeLayout) findViewById(R.id.rl_trigonView);
        rl_level_zero = (RelativeLayout) findViewById(R.id.rl_level_zero);
        iv_level_zero = (ImageView) findViewById(R.id.iv_level_zero);
        iv_back.setOnClickListener(this);
        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
        recyclerview_level_tip.setLayoutManager(new LinearLayoutManager(this));
        levelTipAdapter = new UserLevelTipAdapter(R.layout.user_item_level_tip, new ArrayList<String>());
        recyclerview_level_tip.setAdapter(levelTipAdapter);
    }

    private void getData() {
        ViewTreeObserver viewTreeObserver = trigon_view.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                trigon_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                maxWidth = rl_trigonView.getWidth();
                maxHeight = rl_trigonView.getHeight();
                getIntentData();
            }
        });
    }

    private void getIntentData() {
        UserLevelBean userLevelBean = (UserLevelBean) getIntent().getSerializableExtra("levelInfo");
        levelType = getIntent().getStringExtra("levelType");
        levelTipAdapter.getSetType(levelType);
        if ("液体".equals(levelType)) {
            UserLevelBean.LiquidBean liquidBean = userLevelBean.getLiquid();
            setLiquidData(liquidBean);
        }
        if ("生鲜".equals(levelType)) {
            UserLevelBean.FreshBean freshBean = userLevelBean.getFresh();
            setFreshData(freshBean);
        }
        if ("汽配".equals(levelType)) {
            UserLevelBean.PartsBean partsBean = userLevelBean.getParts();
            setPartsData(partsBean);
        }
    }

    /**
     * 当前类型为液体时设置数据
     *
     * @param liquidBean
     */
    private void setLiquidData(UserLevelBean.LiquidBean liquidBean) {
        tv_current_type.setText(levelType + "等级说明");
        if ("0".equals(liquidBean.getNext())){
            tv_balance_value.setText("已达最高等级");
        }else {
            tv_balance_value.setText("距下一级差" + liquidBean.getNext() + "业绩");
        }
        int level = Integer.parseInt(liquidBean.getLevel());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_trigonView.getLayoutParams();
        maxHeight = maxHeight + sencondDashHeight;
        String levelStr = "";
        switch (level) {
            case 1:
                levelStr = "一级";
                layoutParams.width = 0;
                layoutParams.height = 0;
                iv_level_zero.setImageResource(R.mipmap.icon_small_fluid_one);
                rl_level_zero.setVisibility(View.VISIBLE);
                break;
            case 2:
                levelStr = "二级";
                layoutParams.width = maxWidth / 3;
                layoutParams.height = maxHeight / 3;
                iv_current_level.setImageResource(R.mipmap.icon_small_fluid_two);
                break;
            case 3:
                levelStr = "三级";
                layoutParams.height = maxHeight * 2 / 3 - DisplayUtil.dip2px(10);
                layoutParams.width = maxWidth * 2 / 3 + DisplayUtil.dip2px(8);
                iv_current_level.setImageResource(R.mipmap.icon_small_fluid_three);
                break;
            case 4:
                levelStr = "四级";
                iv_current_level.setImageResource(R.mipmap.icon_small_fluid_four);
                break;
        }
        tv_current_level.setText(levelStr);
        rl_trigonView.setLayoutParams(layoutParams);
        trigon_view.postInvalidate();
        if (liquidBean.getTend() != null && liquidBean.getTend().size() != 0) {
            tv_x_one.setText(liquidBean.getTend().get(0));
            tv_x_two.setText(liquidBean.getTend().get(1));
            tv_x_three.setText(liquidBean.getTend().get(2));
            tv_x_four.setText(liquidBean.getTend().get(3));
        }
        levelTipAdapter.setNewData(liquidBean.getTend_data());
    }

    /**
     * 当前类型为生鲜时设置数据
     *
     * @param freshBean
     */
    private void setFreshData(UserLevelBean.FreshBean freshBean) {
        tv_current_type.setText(levelType + "等级说明");
        if ("0".equals(freshBean.getNext())){
            tv_balance_value.setText("已达最高等级");
        }else {
            tv_balance_value.setText("距下一级差" + freshBean.getNext() + "业绩");
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_trigonView.getLayoutParams();
        int level = Integer.parseInt(freshBean.getLevel());
        maxHeight = maxHeight - firstDashHeight;
        String levelStr = "";
        switch (level) {
            case 1:
                levelStr = "一级";
                layoutParams.width = 0;
                layoutParams.height = 0;
                iv_level_zero.setImageResource(R.mipmap.icon_small_fluid_one);
                rl_level_zero.setVisibility(View.VISIBLE);
                break;
            case 2:
                levelStr = "二级";
                iv_current_level.setImageResource(R.mipmap.icon_small_fluid_two);
                layoutParams.width = maxWidth / 2;
                layoutParams.height = maxHeight / 2;
                break;
            case 3:
                levelStr = "三级";
                iv_current_level.setImageResource(R.mipmap.icon_small_fluid_three);
                layoutParams.height = maxHeight * 2 / 3 + 50;
                break;
            case 4:
                levelStr = "四级";
                iv_current_level.setImageResource(R.mipmap.icon_small_fluid_four);
                break;
        }
        tv_current_level.setText(levelStr);
        rl_trigonView.setLayoutParams(layoutParams);
        trigon_view.postInvalidate();
        if (freshBean.getTend() != null && freshBean.getTend().size() != 0) {
            tv_x_one.setText(freshBean.getTend().get(0));
            tv_x_two.setText(freshBean.getTend().get(1));
            tv_x_four.setText(freshBean.getTend().get(2));
        }
        tv_x_three.setVisibility(View.GONE);
        tv_y_four.setVisibility(View.GONE);
        levelTipAdapter.setNewData(freshBean.getTend_data());
    }

    /**
     * 当前类型为汽配时设置数据
     *
     * @param partsBean
     */
    private void setPartsData(UserLevelBean.PartsBean partsBean) {
        tv_current_type.setText(levelType + "等级说明");
        if ("0".equals(partsBean.getNext())){
            tv_balance_value.setText("已达最高等级");
        }else {
            tv_balance_value.setText("距下一级差" + partsBean.getNext() + "业绩");
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_trigonView.getLayoutParams();
        int level = Integer.parseInt(partsBean.getLevel());
        maxHeight = maxHeight + sencondDashHeight;
        String levelStr = "";
        switch (level) {
            case 1:
                levelStr = "一级";
                layoutParams.height = 0;
                layoutParams.width = 0;
                iv_level_zero.setImageResource(R.mipmap.icon_small_automobiellevel_one);
                rl_level_zero.setVisibility(View.VISIBLE);
                break;
            case 2:
                levelStr = "二级";
                layoutParams.height = maxHeight * 1 / 3;
                layoutParams.width = maxWidth * 1 / 3;
                iv_current_level.setImageResource(R.mipmap.icon_small_automobiellevel_two);
                break;
            case 3:
                levelStr = "三级";
                layoutParams.height = maxHeight * 2 / 3 - DisplayUtil.dip2px(10);
                layoutParams.width = maxWidth * 2 / 3 + DisplayUtil.dip2px(8);
                iv_current_level.setImageResource(R.mipmap.icon_small_automobiellevel_three);
                break;
            case 4:
                levelStr = "四级";
                iv_current_level.setImageResource(R.mipmap.icon_small_automobiellevel_four);
                break;
        }
        tv_current_level.setText(levelStr);
        rl_trigonView.setLayoutParams(layoutParams);
        trigon_view.postInvalidate();
        if (partsBean.getTend() != null && partsBean.getTend().size() != 0) {
            tv_x_one.setText(partsBean.getTend().get(0));
            tv_x_two.setText(partsBean.getTend().get(1));
            tv_x_three.setText(partsBean.getTend().get(2));
            tv_x_four.setText(partsBean.getTend().get(3));
        }
        levelTipAdapter.setNewData(partsBean.getTend_data());
    }

    @Override
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }
}
