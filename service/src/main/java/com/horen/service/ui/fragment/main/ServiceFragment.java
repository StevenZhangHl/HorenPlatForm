package com.horen.service.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.base.BaseFragment;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.util.CollectionUtils;
import com.horen.base.widget.HRViewPager;
import com.horen.service.R;
import com.horen.service.enumeration.service.ServiceType;
import com.horen.service.listener.IBusinessTotalCount;
import com.horen.service.ui.activity.service.RepairAddActivity;
import com.horen.service.ui.activity.service.RepairListActivity;
import com.horen.service.ui.fragment.service.ServiceCompleteFragment;
import com.horen.service.ui.fragment.service.ServicePieChartFragment;
import com.horen.service.utils.ServiceIdUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/10:10
 * @description :服务中心
 * @github :https://github.com/chenyy0708
 */
public class ServiceFragment extends BaseFragment implements View.OnClickListener, IBusinessTotalCount {

    private Toolbar toolBar;
    private TextView tvTitle;
    private ImageView ivRight;
    private TextView tvRight;
    private TextView tvTotalCount;
    private TextView tvClean;
    private ImageView ivClean;
    private TextView tvRepair;
    private ImageView ivRepair;
    private TextView tvComplete;
    private ImageView ivComplete;
    private HRViewPager viewPager;

    /**
     * 待清洗
     */
    public static final String CLEANED = "cleaned";
    /**
     * 待维修
     */
    public static final String REPAIR = "repair";
    /**
     * 已完成
     */
    public static final String COMPLETE = "complete";
    /**
     * 当前页面，默认待清理
     */
    private String currentStatus = CLEANED;
    private List<SupportFragment> mFragments;
    /**
     * 记录各个Fragment的总个数
     */
    private Map<String, String> totalMap = new HashMap<>(3);

    public static ServiceFragment newInstance() {
        Bundle args = new Bundle();
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_service;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        toolBar = (Toolbar) rootView.findViewById(R.id.tool_bar);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        ivRight = (ImageView) rootView.findViewById(R.id.iv_right);
        tvRight = (TextView) rootView.findViewById(R.id.tv_right);
        tvTotalCount = (TextView) rootView.findViewById(R.id.tv_total_count);
        tvClean = (TextView) rootView.findViewById(R.id.tv_clean);
        ivClean = (ImageView) rootView.findViewById(R.id.iv_clean);
        tvRepair = (TextView) rootView.findViewById(R.id.tv_repair);
        ivRepair = (ImageView) rootView.findViewById(R.id.iv_repair);
        tvComplete = (TextView) rootView.findViewById(R.id.tv_complete);
        ivComplete = (ImageView) rootView.findViewById(R.id.iv_complete);
        viewPager = (HRViewPager) rootView.findViewById(R.id.view_pager);
        tvClean.setOnClickListener(this);
        tvRepair.setOnClickListener(this);
        tvComplete.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.service_ic_scan);
        tvRight.setText(R.string.service_new_repair);
        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
        // 初始化Fragment数据
        tvTitle.setText(getString(R.string.service_center));
        // 设置默认数据
        totalMap.put(CLEANED, String.valueOf(0));
        totalMap.put(REPAIR, String.valueOf(0));
        totalMap.put(COMPLETE, String.valueOf(0));
        initViewPager();
    }

    private void initViewPager() {
        mFragments = new ArrayList<>();
        ServicePieChartFragment pendingFragment = ServicePieChartFragment.newInstance(ServiceType.CLEAN.getStatus());
        pendingFragment.setTotalCountListener(this);
        ServicePieChartFragment pendingFragment1 = ServicePieChartFragment.newInstance(ServiceType.REPAIR.getStatus());
        pendingFragment1.setTotalCountListener(this);
        ServiceCompleteFragment completeFragment = ServiceCompleteFragment.newInstance();
        completeFragment.setTotalCountListener(this);
        // 添加获取总个数监听
        mFragments.add(pendingFragment);
        mFragments.add(pendingFragment1);
        mFragments.add(completeFragment);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragments, Arrays.asList("待清洗", "待维修", "已完成")));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_clean) {
            if (currentStatus.equals(CLEANED)) {
                return;
            }
            // 待清洗
            currentStatus = CLEANED;
            changeTab(currentStatus);
        } else if (view.getId() == R.id.tv_repair) {
            if (currentStatus.equals(REPAIR)) {
                return;
            }
            // 待维修
            currentStatus = REPAIR;
            changeTab(currentStatus);
        } else if (view.getId() == R.id.tv_complete) {
            if (currentStatus.equals(COMPLETE)) {
                return;
            }
            // 待清洗
            currentStatus = COMPLETE;
            changeTab(currentStatus);
        } else if (view.getId() == R.id.iv_right) {
            // 判断本地是否存储了服务id
            if (CollectionUtils.isNullOrEmpty(ServiceIdUtils.getServiceIdList())) {
                RepairAddActivity.startActivity(_mActivity);
            } else {
                RepairListActivity.startActivity(_mActivity);
            }
        } else if (view.getId() == R.id.tv_right) {
            // 判断本地是否存储了服务id
            if (CollectionUtils.isNullOrEmpty(ServiceIdUtils.getServiceIdList())) {
                RepairAddActivity.startActivity(_mActivity);
            } else {
                RepairListActivity.startActivity(_mActivity);
            }
        }
    }

    /**
     * 设置业务中心总个数
     *
     * @param totalCount
     */
    @Override
    public void setTotalCount(String totalCount, String type) {
        // 保存新数据
        totalMap.put(type, totalCount);
        switch (currentStatus) {
            // 待清洗
            case CLEANED:
                tvTotalCount.setText(String.format(getString(R.string.service_clean_number), totalMap.get(CLEANED)));
                break;
            // 待维修
            case REPAIR:
                tvTotalCount.setText(String.format(getString(R.string.service_repair_number), totalMap.get(REPAIR)));
                break;
            // 已完成
            case COMPLETE:
                tvTotalCount.setText(String.format(getString(R.string.service_complete_number_this_year), totalMap.get(COMPLETE)));
                break;
            default:
                break;
        }
    }

    /**
     * 改变Tab按钮状态
     *
     * @param status 当前按钮状态
     */
    private void changeTab(String status) {
        switch (status) {
            // 待清洗
            case CLEANED:
                tvClean.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                ivClean.setVisibility(View.VISIBLE);
                // 隐藏其他Tab
                tvRepair.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                tvComplete.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                ivRepair.setVisibility(View.GONE);
                ivComplete.setVisibility(View.GONE);
                // 显示待清洗Fragment
                viewPager.setCurrentItem(0);
                tvTotalCount.setText(String.format(getString(R.string.service_clean_number), totalMap.get(CLEANED)));
//                tvRight.setVisibility(View.GONE);
//                ivRight.setVisibility(View.VISIBLE);
                break;
            // 待维修
            case REPAIR:
                tvRepair.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                ivRepair.setVisibility(View.VISIBLE);
                // 隐藏其他Tab
                tvClean.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                tvComplete.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                ivClean.setVisibility(View.GONE);
                ivComplete.setVisibility(View.GONE);
                // 显示待维修Fragment
                viewPager.setCurrentItem(1);
                tvTotalCount.setText(String.format(getString(R.string.service_repair_number), totalMap.get(REPAIR)));
                // 待维修模块显示报修
//                ivRight.setVisibility(View.GONE);
//                tvRight.setVisibility(View.VISIBLE);
                break;
            // 已完成
            case COMPLETE:
                tvComplete.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                ivComplete.setVisibility(View.VISIBLE);
                // 隐藏其他Tab
                tvClean.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                tvRepair.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                ivClean.setVisibility(View.GONE);
                ivRepair.setVisibility(View.GONE);
                // 显示已完成Fragment
//                tvRight.setVisibility(View.GONE);
//                ivRight.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(2);
                tvTotalCount.setText(String.format(getString(R.string.service_complete_number_this_year), totalMap.get(COMPLETE)));
                break;
            default:
                break;
        }
    }
}
