package com.horen.service.ui.fragment.storage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.RecycleViewSmoothScroller;
import com.horen.chart.BarValueMarker;
import com.horen.chart.XAxisRendererUtils;
import com.horen.chart.barchart.BarChartHelper;
import com.horen.chart.barchart.IBarData;
import com.horen.service.R;
import com.horen.service.bean.BarData;
import com.horen.service.bean.StorageCenterBean;
import com.horen.service.enumeration.service.StorageType;
import com.horen.service.mvp.contract.StorageContract;
import com.horen.service.mvp.model.StorageModel;
import com.horen.service.mvp.presenter.StoragePresenter;
import com.horen.service.ui.fragment.adapter.StorageSuppliesAdapter;
import com.horen.service.utils.RvEmptyUtils;
import com.horen.service.utils.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/27/10:22
 * @description :耗材库存
 * @github :https://github.com/chenyy0708
 */
public class SuppliesFragment extends BaseFragment<StoragePresenter, StorageModel> implements OnRefreshListener, StorageContract.View, OnChartValueSelectedListener {
    private LinearLayoutManager layoutManager;

    private BarChart mBarChart;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private StorageSuppliesAdapter suppliesAdapter;
    private BarChartHelper chartHelper;
    private LinearLayout llHeader;

    public static SuppliesFragment newInstance() {
        Bundle args = new Bundle();
        SuppliesFragment fragment = new SuppliesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_supplies;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        llHeader = (LinearLayout) rootView.findViewById(R.id.ll_header);
        mBarChart = (BarChart) rootView.findViewById(R.id.bar_chart);
        mRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRefreshLayout.setOnRefreshListener(this);
        initRecyclerView();
        setBarChart();
        mBarChart.setNoDataText("暂无数据");
        mBarChart.invalidate();
        mRefreshLayout.autoRefresh();
    }

    /**
     * 初始化柱状图
     */
    private void setBarChart() {
        mBarChart.getXAxis().setTextColor(Color.parseColor("#666666"));
        mBarChart.getXAxis().setTextSize(12);
        mBarChart.getAxisLeft().setTextColor(Color.parseColor("#666666"));
        mBarChart.getAxisLeft().setTextSize(13);
        mBarChart.getAxisLeft().setDrawAxisLine(false);
        mBarChart.setExtraBottomOffset(20);
        // 斜体
        mBarChart.getXAxis().setLabelRotationAngle(-45);
        mBarChart.getXAxis().setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
        mBarChart.getXAxis().setTextSize(12);
        // 固定X轴Label的大小，适用于旋转角度的X轴   放在高度不固定乱跳的情况
        mBarChart.setXAxisRenderer(new XAxisRendererUtils(mBarChart.getViewPortHandler(),
                mBarChart.getXAxis(), mBarChart.getTransformer(mBarChart.getAxisLeft().getAxisDependency())));
        mBarChart.setOnChartValueSelectedListener(this);
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        suppliesAdapter = new StorageSuppliesAdapter(new ArrayList<StorageCenterBean.ListBean>());
        mRecyclerView.setAdapter(suppliesAdapter);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.storageList(StorageType.SUPPLIES.getStatus());
    }

    @Override
    public void getStorageListSuccess(StorageCenterBean bean) {
        if (!CollectionUtils.isNullOrEmpty(bean.getList())) {
            llHeader.setVisibility(View.VISIBLE);
            suppliesAdapter.setNewData(bean.getList());
            // 得到柱状图数据
            List<IBarData> barData = new ArrayList<>();
            for (StorageCenterBean.ListBean listBean : bean.getList()) {
                barData.add(new BarData(StringUtils.substring(listBean.getProduct_name()), listBean.getWarehouse_qty()));
            }
            // 设置柱状图
            mBarChart.setMarker(new BarValueMarker(_mActivity, "", barData));
            setBarChartData(barData);
            mBarChart.getBarData().setDrawValues(false);
        } else {
            llHeader.setVisibility(View.GONE);
            RvEmptyUtils.setEmptyView(suppliesAdapter, mRecyclerView);
        }
        mRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        mRefreshLayout.finishRefresh(0);
    }

    /**
     * 设置/更新 柱状图数据
     *
     * @param barData 新数据
     */
    private void setBarChartData(List<IBarData> barData) {
        // 计算最大值
        int maxValue = 0;
        for (IBarData barDatum : barData) {
            if (barDatum.getValue() > maxValue)
                maxValue = (int) barDatum.getValue();
        }
        // 设置坐标轴的最大值和最小值
        if (maxValue % 3 != 0 || maxValue % 5 != 0) {
            maxValue = ((((maxValue / 5) / 3) + 1) * 3) * 5;
        }
        //设置最大数值范围
        mBarChart.getAxisLeft().setAxisMaximum(maxValue);
        //设置最小数值范围
        mBarChart.getAxisLeft().setAxisMinimum(0);
        if (chartHelper == null) {
            chartHelper = new BarChartHelper.Builder()
                    .setContext(_mActivity)
                    .setBarChart(mBarChart)
                    .setBarData(barData)
                    .setGradualColor(true)
                    .setBarWidth(0.15f)
                    .setNoDataText("暂无数据")
                    .setDisplayCount(5)
                    .setStartGradualColor(ContextCompat.getColor(_mActivity, R.color.service_color_6FB))
                    .setEndGradualColor(ContextCompat.getColor(_mActivity, R.color.color_main))
                    .build();
        } else {
            chartHelper.setNewData(barData);
        }
    }

    @Override
    protected void reLoadData() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        scrollToTop((int) h.getX());
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

    @Override
    public void onNothingSelected() {

    }
}
