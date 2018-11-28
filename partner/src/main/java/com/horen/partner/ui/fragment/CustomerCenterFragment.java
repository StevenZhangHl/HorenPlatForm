package com.horen.partner.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.horen.base.base.BaseTabVPFragment;
import com.horen.base.constant.ARouterPath;
import com.horen.base.constant.Constants;
import com.horen.base.listener.OnPageChangerLitener;
import com.horen.commonres.service.IRefreshFragment;
import com.horen.partner.ui.activity.customer.AddPotentialCustomerActivity;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/07/31/13:30
 * @description : 客户中心
 * @github :https://github.com/chenyy0708
 */
@Route(path = ARouterPath.PARTNER_CUSROMER_CENTER_FRAGMENT)
public class CustomerCenterFragment extends BaseTabVPFragment implements IRefreshFragment, OnPageChangerLitener {

    public static CustomerCenterFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        CustomerCenterFragment customerCenterFragment = new CustomerCenterFragment();
        customerCenterFragment.setArguments(bundle);
        return customerCenterFragment;
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    @Override
    public void initPresenter() {

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
        toolbar.setRightText("新增客户");
        toolbar.setTitle("客户中心");
        toolbar.getToolbar().setNavigationIcon(null);
        toolbar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Constants.PARTEER_CUSTOMER_TYPE, "potential");
                intent.setClass(getActivity(), AddPotentialCustomerActivity.class);
                startActivity(intent);
            }
        });
        setOnPageChangerLitener(this);
    }

    @Override
    protected String[] getTitles() {
        return new String[]{"潜在客户", "正式客户"};
    }

    @Override
    protected List<SupportFragment> getFragments() {
        List<SupportFragment> fragments = new ArrayList<>();
        fragments.add(PotentialCustomerFragment.newInstance());
        fragments.add(OfficialCustomerFragment.newInstance());
        getData();
        return fragments;
    }

    private void getData() {

    }

    /**
     * 刷新数据
     */
    @Override
    public void refresh() {
        getData();
    }

    @Override
    public void getCuttentPosition(int position) {
        switch (position) {
            case 0:
                toolbar.setTvRightVisible(true);
                break;
            case 1:
                toolbar.setTvRightVisible(false);
                break;
        }
    }
}
