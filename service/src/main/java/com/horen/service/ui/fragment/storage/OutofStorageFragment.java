package com.horen.service.ui.fragment.storage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.RecycleViewSmoothScroller;
import com.horen.chart.XAxisRendererUtils;
import com.horen.chart.linechart.ILineChartData;
import com.horen.chart.linechart.LineChartHelper;
import com.horen.chart.linechart.MyLineChart;
import com.horen.chart.marker.DetailsMarkerView;
import com.horen.chart.marker.RoundMarker;
import com.horen.service.R;
import com.horen.service.bean.LineData;
import com.horen.service.bean.StorageCenterBean;
import com.horen.service.enumeration.service.StorageType;
import com.horen.service.mvp.contract.StorageContract;
import com.horen.service.mvp.model.StorageModel;
import com.horen.service.mvp.presenter.StoragePresenter;
import com.horen.service.ui.activity.storage.OutofStorageDetailActivity;
import com.horen.service.ui.fragment.adapter.OutofStorageAdapter;
import com.horen.base.util.DividerItemDecoration;
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
 * @description :出入库
 * @github :https://github.com/chenyy0708
 */
public class OutofStorageFragment extends BaseFragment<StoragePresenter, StorageModel> implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, StorageContract.View {
    private MyLineChart mLineChart;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private OutofStorageAdapter storageAdapter;
    private LinearLayoutManager layoutManager;
    private List<String> names;
    private List<Integer> chartColors;
    private LineChartHelper chartHelper;
    private List<List<ILineChartData>> dataSet = new ArrayList<>();
    private LinearLayout llHeader;

    public static OutofStorageFragment newInstance() {
        Bundle args = new Bundle();
        OutofStorageFragment fragment = new OutofStorageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_out_of_storage;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        llHeader = (LinearLayout) rootView.findViewById(R.id.ll_header);
        mLineChart = (MyLineChart) rootView.findViewById(R.id.line_chart);
        mRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRefreshLayout.setOnRefreshListener(this);
        initRecyclerView();
        mLineChart.setNoDataText("暂无数据");
        mLineChart.invalidate();
        mRefreshLayout.autoRefresh();
    }

    /**
     * 根据不同的曲线创建不同的marker
     */
    private void createMarker(int x, int y) {
        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(_mActivity, "天", R.layout.chart_marker_view);
        detailsMarkerView.setChartView(mLineChart);
        mLineChart.setDetailsMarkerView(detailsMarkerView);
        mLineChart.setRoundMarker(new RoundMarker(_mActivity, R.layout.item_chart_first_round));
    }

    /**
     * 一次选中多条线
     *
     * @param xPosition
     */
    private void highLightValues(int xPosition) {
        Highlight highlight1 = new Highlight(xPosition, 0, -1);
        Highlight highlight2 = new Highlight(xPosition, 1, -1);
        int y1 = (int) dataSet.get(0).get(xPosition).getValue();
        int y2 = (int) dataSet.get(1).get((xPosition)).getValue();
        if (y1 >= y2) {
            mLineChart.highlightValues(new Highlight[]{highlight1, highlight2});
        } else {
            mLineChart.highlightValues(new Highlight[]{highlight2, highlight1});
        }
    }

    private int xPosition = 0;

    /**
     * 初始折线图和点击事件
     */
    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        storageAdapter = new OutofStorageAdapter(new ArrayList<StorageCenterBean.ListBean>());
        mRecyclerView.setAdapter(storageAdapter);
        // 分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(_mActivity));
        storageAdapter.setOnItemClickListener(this);
        // 折线图颜色
        chartColors = new ArrayList<>();
        chartColors.add(Color.parseColor("#6FBA2C"));
        chartColors.add(Color.parseColor("#EF8D06"));
        //线的名字集合
        names = new ArrayList<>();
        names.add("出库");
        names.add("入库");
        createMarker(0, 0);
        // 折线图点击事件
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                scrollToTop((int) e.getX());
                // 创建一个新的Marker
                createMarker((int) e.getX(), (int) e.getY());
                xPosition = (int) h.getX();
                highLightValues(xPosition);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        // 斜体
        mLineChart.getXAxis().setLabelRotationAngle(-45);
        mLineChart.getXAxis().setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666));
        mLineChart.getXAxis().setTextSize(12);
        // 固定X轴Label的大小，适用于旋转角度的X轴   放在高度不固定乱跳的情况
        mLineChart.setXAxisRenderer(new XAxisRendererUtils(mLineChart.getViewPortHandler(), mLineChart.getXAxis(),
                mLineChart.getTransformer(mLineChart.getAxisLeft().getAxisDependency())));
        float yOffset = mLineChart.getXAxis().getYOffset();
        yOffset += 1;
        mLineChart.getXAxis().setYOffset(yOffset);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StorageCenterBean.ListBean bean = storageAdapter.getData().get(position);
        OutofStorageDetailActivity.startActivity(_mActivity, bean);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.storageList(StorageType.OUTIN.getStatus());
    }

    /**
     * 获取数据成功
     */
    @Override
    public void getStorageListSuccess(StorageCenterBean bean) {
        // 没有数据展示空View
        if (!CollectionUtils.isNullOrEmpty(bean.getList())) {
            mRefreshLayout.setBackgroundResource(R.color.white);
            llHeader.setVisibility(View.VISIBLE);
            storageAdapter.setNewData(bean.getList());
            // 多条柱状图数据集合
            dataSet = new ArrayList<>();
            // 出库集合
            ArrayList<ILineChartData> outData = new ArrayList<>();
            // 入库集合
            ArrayList<ILineChartData> inData = new ArrayList<>();
            for (int i = 0; i < bean.getList().size(); i++) {
                StorageCenterBean.ListBean listBean = bean.getList().get(i);
                outData.add(new LineData(StringUtils.substring(listBean.getProduct_name()), listBean.getStorage_qty()));
                inData.add(new LineData(StringUtils.substring(listBean.getProduct_name()), listBean.getEnterQty()));
            }
            dataSet.add(outData);
            dataSet.add(inData);
            // 初始化折线图
            if (chartHelper == null) {
                chartHelper = new LineChartHelper(mLineChart);
            }
            // 计算最大值
            float maxValue1 = 0;
            float maxValue2 = 0;
            for (ILineChartData outDatum : outData) {
                if (outDatum.getValue() > maxValue1)
                    maxValue1 = outDatum.getValue();
            }
            for (ILineChartData inDatum : inData) {
                if (inDatum.getValue() > maxValue2)
                    maxValue2 = inDatum.getValue();
            }
            int maxValue = (int) Math.max(maxValue1, maxValue2);
            // 初始化线
            chartHelper.showLines(dataSet, names, chartColors, 6, maxValue, false, false);
            mLineChart.setExtraBottomOffset(-25);
//            mLineChart.setExtraRightOffset(25);
            // X轴距离Y轴边距
//            mLineChart.getXAxis().setSpaceMin(0.5f);
        } else {
            llHeader.setVisibility(View.GONE);
            mRefreshLayout.setBackgroundResource(R.color.color_f5);
            RvEmptyUtils.setEmptyView(storageAdapter, mRecyclerView);
        }
        mRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        RvEmptyUtils.setEmptyView(storageAdapter, mRecyclerView);
        llHeader.setVisibility(View.GONE);
        mRefreshLayout.finishRefresh(0);
    }


    @Override
    protected void reLoadData() {
        mRefreshLayout.autoRefresh();
    }
}
