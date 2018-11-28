package com.horen.service.ui.fragment.service;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration;
import com.horen.base.util.RecycleViewSmoothScroller;
import com.horen.chart.piechart.IPieData;
import com.horen.chart.piechart.PieChartHelper;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.ServiceListBean;
import com.horen.service.enumeration.service.ServiceType;
import com.horen.service.listener.IBusinessTotalCount;
import com.horen.service.listener.UpdateListener;
import com.horen.service.ui.fragment.adapter.ServiceAdapter;
import com.horen.service.ui.fragment.main.ServiceFragment;
import com.horen.service.utils.RvEmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/13:10
 * @description :服务中心饼状图Fragment  待清洗、待维修Fragment
 * @github :https://github.com/chenyy0708
 */
public class ServicePieChartFragment extends BaseFragment implements UpdateListener, OnRefreshListener, OnChartValueSelectedListener {

    private PieChart mPieChart;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    /**
     * 监听总个数获取
     */
    private IBusinessTotalCount totalCountListener;
    private String type;
    private ServiceAdapter serviceAdapter;
    private LinearLayoutManager layoutManager;
    /**
     * 饼状图显示数据
     */
    private List<IPieData> pieData = new ArrayList<>();
    private PieChartHelper pieChartHelper;
    private boolean isInit;

    public static ServicePieChartFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        ServicePieChartFragment fragment = new ServicePieChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_piechart_service;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPieChart = rootView.findViewById(R.id.pie_chart);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        type = getArguments().getString("type");
        // 上拉下拉刷新监听
        mRefreshLayout.setOnRefreshListener(this);
        // 初始化列表
        initRecyclerView();
        // 获取数据
//        initData(true);
        mRefreshLayout.autoRefresh();
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        serviceAdapter = new ServiceAdapter(R.layout.service_service_item, type);
        mRecyclerView.setAdapter(serviceAdapter);
        // 分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(_mActivity));
        serviceAdapter.setOnUpdateListener(this);
        mPieChart.setBackgroundResource(R.color.white);
        mPieChart.setExtraOffsets(11.5f, 11.5f, 11.5f, 11.5f);
        mPieChart.setCenterText("待清洗" + "\n" + "型号");
        mPieChart.setCenterTextSize(12);
        mPieChart.setCenterTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
        pieChartHelper = new PieChartHelper.Builder()
                .setLableTextColor(ContextCompat.getColor(_mActivity, R.color.color_333))
                .setContext(_mActivity)
                .setPieChart(mPieChart)
                .setPieData(pieData)
                .build();
        // 饼状图点击监听
        mPieChart.setOnChartValueSelectedListener(this);
        isInit = true;
    }

    /**
     * 获取服务列表
     */
    private void initData(boolean showDialog) {
        mRxManager.add(Api.getInstance().getRtpServiceList(ServiceParams.getRtpServiceList("0", type))
                .compose(RxHelper.<ServiceListBean>getResult())
                .subscribeWith(new BaseObserver<ServiceListBean>(_mActivity, showDialog) {
                    @Override
                    protected void onSuccess(ServiceListBean serviceListBean) {
                        // 初始化饼状图
                        setPieChartData(serviceListBean.getServiceList());
                        if (CollectionUtils.isNullOrEmpty(serviceListBean.getServiceList())) {
                            // 隐藏饼状图
                            mPieChart.setVisibility(View.GONE);
                            setEmptyView();
                            mRefreshLayout.setBackgroundResource(R.color.color_f5);
                        } else {
                            mPieChart.setVisibility(View.VISIBLE);
                            serviceAdapter.setNewData(serviceListBean.getServiceList());
                            mRefreshLayout.setBackgroundResource(R.color.white);
                        }
                        setTotalCount();
                        // 通知主页面设置总个数
                        mRefreshLayout.finishRefresh(0);
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        mRefreshLayout.finishRefresh(0);
                    }
                }));
    }

    @Override
    protected void reLoadData() {
        initData(true);
    }

    /**
     * 通知主页面设置总个数
     */
    private void setTotalCount() {
        if (totalCountListener != null) {
            int total = 0;
            for (ServiceListBean.ServiceBean serviceBean : serviceAdapter.getData()) {
                total += serviceBean.getService_qty();
            }
            totalCountListener.setTotalCount(String.valueOf(total),
                    type.equals(ServiceType.CLEAN.getStatus()) ? ServiceFragment.CLEANED : ServiceFragment.REPAIR);
        }
    }

    private void setPieChartData(List<ServiceListBean.ServiceBean> serviceList) {
        pieData.clear();
        pieData.addAll(serviceList);
        pieChartHelper.setNewData(pieData);
    }

    /**
     * 点击图表某条后使下方列表滚到到可见位置
     *
     * @param position 位置
     */
    private void scrollToTop(int position) {
        RecycleViewSmoothScroller smoothScroller = new RecycleViewSmoothScroller(_mActivity);
        smoothScroller.setTargetPosition(position);
        layoutManager.startSmoothScroll(smoothScroller);
    }


    /**
     * 总数监听
     */
    public void setTotalCountListener(IBusinessTotalCount totalCountListener) {
        this.totalCountListener = totalCountListener;
    }

    /**
     * 更新首页饼状图数据
     */
    @Override
    public void onUpdateListener() {
        pieData.clear();
        pieData.addAll(serviceAdapter.getData());
        pieChartHelper.setNewData(pieData);
        setTotalCount();
        // 通知已完成刷新
        EventBus.getDefault().post(EventBusCode.REFRESH_COMPLETE_LIST, EventBusCode.REFRESH_COMPLETE_LIST);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        initData(false);
    }

    /**
     * 点击饼状图跳转列表
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        scrollToTop((int) h.getX());
    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * 暂无数据
     */
    private void setEmptyView() {
        mRefreshLayout.setEnableLoadMore(false);
        RvEmptyUtils.setEmptyView(serviceAdapter, mRecyclerView);
    }

    /**
     * 刷新列表数据
     */
    @Subscriber(tag = EventBusCode.REFRESH_REPAIR_FRAGMENT_LIST)
    private void refreshList(String s) {
        // 刷新待维修列表数据
        if (isInit && type.equals(ServiceType.REPAIR.getStatus())) {
            initData(false);
        }
    }

}
