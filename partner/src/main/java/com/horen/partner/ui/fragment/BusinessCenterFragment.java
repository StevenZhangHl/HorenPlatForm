package com.horen.partner.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.horen.base.base.BaseTabVPFragment;
import com.horen.base.listener.OnPageChangerLitener;
import com.horen.partner.event.EventConstans;
import com.horen.base.constant.MsgEvent;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author:Steven
 * Time:2018/8/3 15:52
 * Description:This isRiskManageFragment  业务中心
 */
public class BusinessCenterFragment extends BaseTabVPFragment implements OnPageChangerLitener {

    public static BusinessCenterFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        BusinessCenterFragment assetAllocationFragment = new BusinessCenterFragment();
        assetAllocationFragment.setArguments(bundle);
        return assetAllocationFragment;
    }

    /**
     * 初始化view
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        initToolbar(toolbar.getToolbar());
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("业务中心");
        toolbar.getToolbar().setNavigationIcon(null);
        setOnPageChangerLitener(this);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected String[] getTitles() {
        return new String[]{"租赁业务", "销售业务", "租赁资产"};
    }

    @Override
    protected List<SupportFragment> getFragments() {
        List<SupportFragment> fragments = new ArrayList<>();
        fragments.add(LeaseBusinessFragment.newInstance());
        fragments.add(SellBusinessFragment.newInstance());
        fragments.add(LeasePropertyFragment.newInstance());
        return fragments;
    }

    @Override
    public void getCuttentPosition(int position) {
        switch (position) {
            case 0:
                EventBus.getDefault().post(new MsgEvent(EventConstans.CHAGE_MAIN_TAB_SHOW_EVENT));
                break;
            case 1:
                EventBus.getDefault().post(new MsgEvent(EventConstans.CHAGE_MAIN_TAB_SHOW_EVENT));
                break;
            case 2:
                EventBus.getDefault().post(new MsgEvent(EventConstans.CHAGE_MAIN_TAB_HIDE_EVENT));
                break;
        }
    }
}
