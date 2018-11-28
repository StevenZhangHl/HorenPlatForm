package com.horen.service.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.base.BaseFragment;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.listener.TimePickerListener;
import com.horen.base.widget.HRViewPager;
import com.horen.base.widget.TimePickerViewHelper;
import com.horen.service.R;
import com.horen.service.listener.IBusinessTotalCount;
import com.horen.service.ui.fragment.bill.BillDetailFragment;
import com.horen.service.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/10:10
 * @description :账单中心
 * @github :https://github.com/chenyy0708
 */
public class BillFragment extends BaseFragment implements View.OnClickListener, IBusinessTotalCount, TimePickerListener {

    private Toolbar mToolBar;
    private TextView mTvTitle;
    private ImageView mIvRight;
    private TextView mTvRight;
    private TextView mTvTotalCount;
    private TextView mTvOne;
    private ImageView mIvOne;
    private TextView mTvTwo;
    private ImageView mIvTwo;
    private HRViewPager mViewPager;

    /**
     * 应收账
     */
    public static final String RECEIPT = "1";
    /**
     * 已完成
     */
    public static final String COMPLETE = "3";
    /**
     * 当前页面，默认应收账
     */
    private String currentStatus = RECEIPT;
    private List<SupportFragment> mFragments;
    /**
     * 记录各个Fragment的总个数
     */
    private Map<String, String> totalMap = new HashMap<>(3);
    private TimePickerViewHelper pickerViewHelper;

    /**
     * 当前选择月份
     */
    private String currentDate;

    public static BillFragment newInstance() {
        Bundle args = new Bundle();
        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_bill;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (Toolbar) rootView.findViewById(R.id.tool_bar);
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        mIvRight = (ImageView) rootView.findViewById(R.id.iv_right);
        mTvRight = (TextView) rootView.findViewById(R.id.tv_right);
        mTvTotalCount = (TextView) rootView.findViewById(R.id.tv_total_count);
        mTvOne = (TextView) rootView.findViewById(R.id.tv_one);
        mIvOne = (ImageView) rootView.findViewById(R.id.iv_one);
        mTvTwo = (TextView) rootView.findViewById(R.id.tv_two);
        mIvTwo = (ImageView) rootView.findViewById(R.id.iv_two);
        mViewPager = (HRViewPager) rootView.findViewById(R.id.view_pager);
        // 代码取消margin和pading
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setPadding(0, 0, 0, 0);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTvRight.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        mTvRight.setTextSize(15);
        mTvOne.setOnClickListener(this);
        mTvTwo.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.drawable.service_ic_date);
        mTvTitle.setText(getString(R.string.service_billing_center));
        // 设置默认数据
        totalMap.put(RECEIPT, String.valueOf(0));
        totalMap.put(COMPLETE, String.valueOf(0));
        // 上一个月账单
        currentDate = StringUtils.getMonthAgo();
        mTvRight.setText(currentDate);
        initViewPager();
    }

    private void initViewPager() {
        mFragments = new ArrayList<>();
        BillDetailFragment receiptFragment = BillDetailFragment.newInstance(RECEIPT, currentDate);
        receiptFragment.setTotalCountListener(this);
        BillDetailFragment completeFragment = BillDetailFragment.newInstance(COMPLETE, currentDate);
        completeFragment.setTotalCountListener(this);
        mFragments.add(receiptFragment);
        mFragments.add(completeFragment);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragments, Arrays.asList("应收账", "已完成")));
        //初始化时间选择器
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.add(Calendar.MONTH, -1);
        Calendar startDate = Calendar.getInstance();
        // 结束时间为系统当前时间
        Calendar endDate = Calendar.getInstance();
        // 结束时间上一个月开 始，本月不统计
        endDate.add(Calendar.MONTH, -1);
        // 开始时间为当前系统时间减去一年，月份为1月1号
        startDate.set(endDate.get(GregorianCalendar.YEAR) - 1,
                0, 1);
        pickerViewHelper = new TimePickerViewHelper(_mActivity, startDate, endDate, selectedDate);
        pickerViewHelper.setTimePickerListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_one) {
            if (currentStatus.equals(RECEIPT)) {
                return;
            }
            // 待清洗
            currentStatus = RECEIPT;
            changeTab(currentStatus);
        } else if (view.getId() == R.id.tv_two) {
            if (currentStatus.equals(COMPLETE)) {
                return;
            }
            // 待清洗
            currentStatus = COMPLETE;
            changeTab(currentStatus);
        } else if (view.getId() == R.id.iv_right) {
            pickerViewHelper.show();
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
            // 应收账
            case RECEIPT:
                mTvTotalCount.setText(String.format(getString(R.string.service_pending_receipt_total), StringUtils.formatTime(currentDate), totalMap.get(RECEIPT)));
                break;
            // 已完成
            case COMPLETE:
                mTvTotalCount.setText(String.format(getString(R.string.service_month_complete_total), StringUtils.formatTime(currentDate), totalMap.get(COMPLETE)));
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
            // 应收账
            case RECEIPT:
                mTvOne.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                mIvOne.setVisibility(View.VISIBLE);
                // 隐藏其他Tab
                mTvTwo.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                mIvTwo.setVisibility(View.GONE);
                // 显示待清洗Fragment
                mViewPager.setCurrentItem(0);
                mTvTotalCount.setText(String.format(getString(R.string.service_pending_receipt_total), StringUtils.formatTime(currentDate), totalMap.get(RECEIPT)));
                break;
            // 已完成
            case COMPLETE:
                mTvTwo.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
                mIvTwo.setVisibility(View.VISIBLE);
                mTvOne.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
                mIvOne.setVisibility(View.GONE);
                mViewPager.setCurrentItem(2);
                mTvTotalCount.setText(String.format(getString(R.string.service_month_complete_total), StringUtils.formatTime(currentDate), totalMap.get(COMPLETE)));
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimePicker(String time) {
        currentDate = time;
        mTvRight.setText(time);
        switch (currentStatus) {
            // 应收账
            case RECEIPT:
                mTvTotalCount.setText(String.format(getString(R.string.service_pending_receipt_total), StringUtils.formatTime(currentDate), totalMap.get(RECEIPT)));
                break;
            // 已完成
            case COMPLETE:
                mTvTotalCount.setText(String.format(getString(R.string.service_month_complete_total), StringUtils.formatTime(currentDate), totalMap.get(COMPLETE)));
                break;
            default:
                break;
        }
        for (SupportFragment mFragment : mFragments) {
            BillDetailFragment fragment = (BillDetailFragment) mFragment;
            // 刷新数据
            fragment.refreshTime(time);
        }
    }
}
