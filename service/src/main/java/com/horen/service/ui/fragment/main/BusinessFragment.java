package com.horen.service.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.base.BaseFragment;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.constant.EventBusCode;
import com.horen.base.util.CollectionUtils;
import com.horen.base.widget.HRViewPager;
import com.horen.service.R;
import com.horen.service.listener.IBusinessTotalCount;
import com.horen.service.listener.IRefreshFragment;
import com.horen.service.ui.activity.service.RepairAddActivity;
import com.horen.service.ui.activity.service.RepairListActivity;
import com.horen.service.ui.fragment.business.CompleteFragment;
import com.horen.service.ui.fragment.business.PendingFragment;
import com.horen.service.utils.ServiceIdUtils;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/10:10
 * @description :业务中心
 * @github :https://github.com/chenyy0708
 */
public class BusinessFragment extends BaseFragment implements View.OnClickListener, IBusinessTotalCount {

    /**
     * 待处理
     */
    public static final String PENDING = "pengding";
    /**
     * 已完成
     */
    public static final String COMPLETE = "complete";

    private HRViewPager viewPager;
    private Toolbar toolbar;
    /**
     * Tab切换Fragment
     */
    private TextView tvPending;
    private ImageView ivPending;
    private TextView tvComplete;
    private ImageView ivComplete;
    /**
     * 当前是否在待处理，默认是
     */
    private boolean isPending = true;
    /**
     * 总个数
     */
    private TextView tvTotalCount;
    private List<SupportFragment> mFragments;
    /**
     * 记录各个Fragment的总个数
     */
    private Map<String, String> totalMap = new HashMap<>(2);
    private TextView tvTitle;
    private ImageView ivRight;

    public static BusinessFragment newInstance() {
        Bundle args = new Bundle();
        BusinessFragment fragment = new BusinessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_business;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        viewPager = rootView.findViewById(R.id.view_pager);
        toolbar = rootView.findViewById(R.id.tool_bar);
        tvTitle = rootView.findViewById(R.id.tv_title);
        ivRight = rootView.findViewById(R.id.iv_right);
        tvTotalCount = rootView.findViewById(R.id.tv_total_count);
        // Tab切换控件
        tvPending = rootView.findViewById(R.id.tv_pending);
        ivPending = rootView.findViewById(R.id.iv_pending);
        tvComplete = rootView.findViewById(R.id.tv_complete);
        ivComplete = rootView.findViewById(R.id.iv_complete);
        tvPending.setOnClickListener(this);
        tvComplete.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        ivRight.setVisibility(View.GONE);
        // 滑动Toolbar变色监听
        tvTitle.setText(getString(R.string.service_business_center));
        // 设置默认数据
        totalMap.put(PENDING, String.valueOf(0));
        totalMap.put(COMPLETE, String.valueOf(0));
        initViewPager();
    }

    private void initViewPager() {
        mFragments = new ArrayList<>();
        PendingFragment pendingFragment = PendingFragment.newInstance();
        CompleteFragment completeFragment = CompleteFragment.newInstance();
        // 添加获取总个数监听
        pendingFragment.setTotalCountListener(this);
        completeFragment.setTotalCountListener(this);
        mFragments.add(pendingFragment);
        mFragments.add(completeFragment);
        viewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragments, Arrays.asList("待处理", "已完成")));
    }


    @Override
    public void onClick(View view) {
        // 待处理
        if (view.getId() == R.id.tv_pending) {
            // 当前状态为已完成，改变文字颜色
            if (!isPending) {
                tvPending.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                ivPending.setVisibility(View.VISIBLE);
                // 隐藏已完成图标
                tvComplete.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                ivComplete.setVisibility(View.GONE);
                // 显示待处理Fragment
                viewPager.setCurrentItem(0);
                isPending = true;
                tvTotalCount.setText(String.format(getString(R.string.service_pending_number), totalMap.get(PENDING)));
            }
        } else if (view.getId() == R.id.tv_complete) {
            // 当前状态为待处理，改变文字颜色
            if (isPending) {
                tvPending.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                ivPending.setVisibility(View.GONE);
                // 隐藏已完成图标
                tvComplete.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                ivComplete.setVisibility(View.VISIBLE);
                // 显示已完成Fragment
                viewPager.setCurrentItem(1);
                isPending = false;
                tvTotalCount.setText(String.format(getString(R.string.service_complete_number), totalMap.get(COMPLETE)));
            }
        } else if (view.getId() == R.id.iv_right) {
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
        // 实际应该显示数据
        if (isPending) {
            tvTotalCount.setText(String.format(getString(R.string.service_pending_number), totalMap.get(PENDING)));
        } else {
            tvTotalCount.setText(String.format(getString(R.string.service_complete_number), totalMap.get(COMPLETE)));
        }
    }

    /**
     * 刷新所有列表数据
     */
    @Subscriber(tag = EventBusCode.REFRESH_ORDER)
    private void refreshOrder(String s) {
        for (SupportFragment mFragment : mFragments) {
            IRefreshFragment fragment = (IRefreshFragment) mFragment;
            fragment.onRefresh();
        }
    }
}
