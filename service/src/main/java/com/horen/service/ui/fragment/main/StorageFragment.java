package com.horen.service.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.base.BaseFragment;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.util.CollectionUtils;
import com.horen.base.widget.HRViewPager;
import com.horen.service.R;
import com.horen.service.listener.IStorageTotalCount;
import com.horen.service.ui.activity.service.RepairAddActivity;
import com.horen.service.ui.activity.service.RepairListActivity;
import com.horen.service.ui.fragment.storage.OutofStorageFragment;
import com.horen.service.ui.fragment.storage.ProductFragment;
import com.horen.service.ui.fragment.storage.SuppliesFragment;
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
 * @description :仓储中心
 * @github :https://github.com/chenyy0708
 */
public class StorageFragment extends BaseFragment implements View.OnClickListener, IStorageTotalCount {

    private Toolbar mToolBar;
    private TextView mTvTitle;
    private ImageView mIvRight;
    private TextView mTvRight;
    private TextView mTvNotWashed;
    private TextView mTvNotRepair;
    private TextView mTvOne;
    private ImageView mIvOne;
    private TextView mTvTwo;
    private ImageView mIvTwo;
    private TextView mTvThree;
    private ImageView mIvThree;

    /**
     * 出入库
     */
    public static final String ONE = "one";
    /**
     * 产品库存
     */
    public static final String TWO = "two";
    /**
     * 耗材库存
     */
    public static final String THREE = "three";
    /**
     * 当前页面，默认出入库
     */
    private String currentStatus = ONE;
    private List<SupportFragment> mFragments;
    /**
     * 记录各个Fragment的总个数
     */
    private Map<String, String> totalMap = new HashMap<>(3);
    private HRViewPager mViewPager;
    private FrameLayout mFlContainer;

    public static StorageFragment newInstance() {
        Bundle args = new Bundle();
        StorageFragment fragment = new StorageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_storage;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mFlContainer = (FrameLayout) rootView.findViewById(R.id.fl_container);
        mToolBar = (Toolbar) rootView.findViewById(R.id.tool_bar);
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        mIvRight = (ImageView) rootView.findViewById(R.id.iv_right);
        mTvRight = (TextView) rootView.findViewById(R.id.tv_right);
        mTvNotWashed = (TextView) rootView.findViewById(R.id.tv_not_washed);
        mTvNotRepair = (TextView) rootView.findViewById(R.id.tv_not_repair);
        mTvOne = (TextView) rootView.findViewById(R.id.tv_one);
        mIvOne = (ImageView) rootView.findViewById(R.id.iv_one);
        mTvTwo = (TextView) rootView.findViewById(R.id.tv_two);
        mIvTwo = (ImageView) rootView.findViewById(R.id.iv_two);
        mTvThree = (TextView) rootView.findViewById(R.id.tv_three);
        mIvThree = (ImageView) rootView.findViewById(R.id.iv_three);
        mViewPager = (HRViewPager) rootView.findViewById(R.id.view_pager);

        mTvOne.setOnClickListener(this);
        mTvTwo.setOnClickListener(this);
        mTvThree.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.drawable.service_ic_scan);
        mIvRight.setVisibility(View.GONE);
        // 初始化Fragment数据
        mTvTitle.setText(getString(R.string.service_warehouse_center));
        // 设置默认数据
        totalMap.put("1", String.valueOf(0));
        totalMap.put("2", String.valueOf(0));
        initViewPager();
    }

    private void initViewPager() {
        mFragments = new ArrayList<>();
        ProductFragment productFragment = ProductFragment.newInstance();
        productFragment.setTotalCountListener(this);
        mFragments.add(OutofStorageFragment.newInstance());
        mFragments.add(productFragment);
        mFragments.add(SuppliesFragment.newInstance());
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragments, Arrays.asList("出入库", "产品库存", "耗材库存")));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_one) {
            if (currentStatus.equals(ONE)) {
                return;
            }
            currentStatus = ONE;
            changeTab(currentStatus);
        } else if (view.getId() == R.id.tv_two) {
            if (currentStatus.equals(TWO)) {
                return;
            }
            // 待维修
            currentStatus = TWO;
            changeTab(currentStatus);
        } else if (view.getId() == R.id.tv_three) {
            if (currentStatus.equals(THREE)) {
                return;
            }
            // 待清洗
            currentStatus = THREE;
            changeTab(currentStatus);
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
     * @param notWashed 未清洗
     * @param notRepair 未维修
     */
    @Override
    public void setTotalCount(String notWashed, String notRepair) {
        // 保存新数据
        totalMap.put("1", notWashed);
        totalMap.put("2", notRepair);
        // 当前显示页面仓储中心
        if (currentStatus.equals(TWO)) {
            mTvNotWashed.setText(String.format(getString(R.string.service_total_not_washed), notWashed));
            mTvNotRepair.setText(String.format(getString(R.string.service_not_repaired), notRepair));
        }
    }

    /**
     * 改变Tab按钮状态
     *
     * @param status 当前按钮状态
     */
    private void changeTab(String status) {
        switch (status) {
            case ONE:
                mTvOne.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                mIvOne.setVisibility(View.VISIBLE);
                // 隐藏其他Tab
                mTvTwo.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                mTvThree.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                mIvTwo.setVisibility(View.GONE);
                mIvThree.setVisibility(View.GONE);
                mViewPager.setCurrentItem(0);
                mTvNotWashed.setText("最近一个月出入库汇总");
                mTvNotRepair.setText("");
                mFlContainer.setBackgroundResource(R.color.white);
                break;
            case TWO:
                mTvTwo.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                mIvTwo.setVisibility(View.VISIBLE);
                // 隐藏其他Tab
                mTvOne.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                mTvThree.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                mIvOne.setVisibility(View.GONE);
                mIvThree.setVisibility(View.GONE);
                mViewPager.setCurrentItem(1);
                // 未清洗数
                mTvNotWashed.setText(String.format(getString(R.string.service_total_not_washed), totalMap.get("1")));
                // 待修总量
                mTvNotRepair.setText(String.format(getString(R.string.service_not_repaired), totalMap.get("2")));
                mFlContainer.setBackgroundResource(R.color.color_f5);
                break;
            case THREE:
                mTvThree.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                mIvThree.setVisibility(View.VISIBLE);
                // 隐藏其他Tab
                mTvTwo.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                mTvOne.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                mIvOne.setVisibility(View.GONE);
                mIvTwo.setVisibility(View.GONE);
                mViewPager.setCurrentItem(2);
                mTvNotWashed.setText("最近一个月耗材库存汇总");
                mTvNotRepair.setText("");
                mFlContainer.setBackgroundResource(R.color.white);
                break;
            default:
                break;
        }
    }
}
