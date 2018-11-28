package com.cyy.company.ui.activity.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.cyy.company.R;
import com.cyy.company.bean.TabTextDto;
import com.cyy.company.enums.OrderStatus;
import com.cyy.company.enums.OrderType;
import com.cyy.company.ui.fragment.me.OrderListFragment;
import com.cyy.company.ui.fragment.order.ReturnBoxFragment;
import com.horen.base.base.AppManager;
import com.horen.base.widget.HRTitle;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author :ChenYangYi
 * @date :2018/10/30/11:24
 * @description :还箱订单
 * @github :https://github.com/chenyy0708
 */
public class ReturnBoxListActivity extends SupportActivity {
    private HRTitle mToolBar;
    private FrameLayout mFlContainer;

    private ArrayList<TabTextDto> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_return);
        AppManager.getAppManager().addActivity(this);
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mToolBar.bindActivity(this, R.color.white);
        mToolBar.setTitle("还箱");
        mToolBar.setIsClose(false);
        initTitleTabText();
        mFlContainer = (FrameLayout) findViewById(R.id.fl_container);
        loadMultipleRootFragment(
                R.id.fl_container,
                0,
                OrderListFragment.newInstance(mList, OrderType.TWO.getPosition())
        );
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

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }
}
