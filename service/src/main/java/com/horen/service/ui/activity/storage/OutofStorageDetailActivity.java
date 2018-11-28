package com.horen.service.ui.activity.storage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.chart.linechart.ILineChartData;
import com.horen.chart.linechart.LineChartHelper;
import com.horen.chart.linechart.MyLineChart;
import com.horen.chart.marker.DetailsMarkerView;
import com.horen.chart.marker.RoundMarker;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.LineData;
import com.horen.service.bean.OutInDetailBean;
import com.horen.service.bean.StorageCenterBean;
import com.horen.service.ui.activity.adapter.OutofStorageDetailAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/27/12:53
 * @description :出入库详情
 * @github :https://github.com/chenyy0708
 */
public class OutofStorageDetailActivity extends BaseActivity {

    private TextView mTvName;
    private TextView mTvType;
    private MyLineChart mLineChart;
    private RecyclerView mRecyclerView;
    private OutofStorageDetailAdapter storageDetailAdapter;
    private List<List<ILineChartData>> dataSet;
    private StorageCenterBean.ListBean bean;

    private int xPosition = 0;


    public static void startActivity(Context context, StorageCenterBean.ListBean bean) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        intent.setClass(context, OutofStorageDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_storage_activity_detail;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle(getString(R.string.service_storage_detail), R.color.white);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mLineChart = (MyLineChart) findViewById(R.id.line_chart);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        bean = (StorageCenterBean.ListBean) getIntent().getSerializableExtra("bean");
        // 产品名和类型
        mTvName.setText(bean.getProduct_name());
        mTvType.setText(bean.getProduct_type());
        initRecyclerView();
        // 获取数据
        getStoreDeliverDetails();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        storageDetailAdapter = new OutofStorageDetailAdapter(new ArrayList<OutInDetailBean.OutInStorageListBean>());
        mRecyclerView.setAdapter(storageDetailAdapter);
    }

    /**
     * 初始化图表
     */
    private void initChart(OutInDetailBean outInDetailBean) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("MM-dd");
        createMarker(0, 0);
        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("出库");
        names.add("入库");
        // 多条柱状图数据集合
        dataSet = new ArrayList<>();
        List<ILineChartData> outData = new ArrayList<>();
        List<ILineChartData> inData = new ArrayList<>();
        // 出库
        for (OutInDetailBean.OutInStorageListBean outInStorageListBean : outInDetailBean.getOutStorageList()) {
            try {
                outData.add(new LineData(format2.format(format1.parse(outInStorageListBean.getCreateTime())), Integer.valueOf(outInStorageListBean.getStorage_qty())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 入库
        for (OutInDetailBean.OutInStorageListBean outInStorageListBean : outInDetailBean.getEnterStorageList()) {
            try {
                inData.add(new LineData(format2.format(format1.parse(outInStorageListBean.getCreateTime())), Integer.valueOf(outInStorageListBean.getStorage_qty())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dataSet.add(outData);
        dataSet.add(inData);
        // 折线图颜色
        List<Integer> chartColors = new ArrayList<>();
        chartColors.add(Color.parseColor("#6FBA2C"));
        chartColors.add(Color.parseColor("#EF8D06"));
        LineChartHelper lineChartHelper = new LineChartHelper(mLineChart);
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
        // 斜体
        mLineChart.getXAxis().setLabelRotationAngle(-45);
        mLineChart.getXAxis().setTextColor(ContextCompat.getColor(mContext, R.color.color_666));
        mLineChart.getXAxis().setTextSize(12);
        float yOffset = mLineChart.getXAxis().getYOffset();
        yOffset += 3;
        mLineChart.getXAxis().setYOffset(yOffset);

        lineChartHelper.showLines(dataSet, names, chartColors, 6, maxValue, false, false);
        // X轴距离Y轴边距
        mLineChart.setExtraRightOffset(20);
        mLineChart.setExtraBottomOffset(-20);
        // X轴距离Y轴边距
        mLineChart.getXAxis().setSpaceMin(0.5f);
        mLineChart.getAxisLeft().setTextColor(Color.parseColor("#666666"));
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                mLineChart.highlightValue(h);
                createMarker((int) e.getX(), (int) e.getY());
                xPosition = (int) h.getX();
                highLightValues(xPosition);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mLineChart.moveViewToAnimated(dataSet.get(0).size() - 1, 0, YAxis.AxisDependency.RIGHT, 2500);
    }

    /**
     * 出入库详情
     */
    private void getStoreDeliverDetails() {
        mRxManager.add(Api.getInstance().getStoreDeliverDetails(ServiceParams.getStoreDeliverDetails(bean.getProduct_id()))
                .compose(RxHelper.<OutInDetailBean>getResult())
                .subscribeWith(new BaseObserver<OutInDetailBean>(mContext, true) {
                    @Override
                    protected void onSuccess(OutInDetailBean outInDetailBean) {
                        // 得到出入库集合数据,过滤数量为0的数据
                        List<OutInDetailBean.OutInStorageListBean> mData = new ArrayList<>();
                        for (OutInDetailBean.OutInStorageListBean bean : outInDetailBean.getOutStorageList()) {
                            if (Integer.valueOf(bean.getStorage_qty()) != 0) mData.add(bean);
                        }
                        for (OutInDetailBean.OutInStorageListBean bean : outInDetailBean.getEnterStorageList()) {
                            if (Integer.valueOf(bean.getStorage_qty()) != 0) mData.add(bean);
                        }
                        if (!CollectionUtils.isNullOrEmpty(mData)) {
                            // 整合出入库数据
                            storageDetailAdapter.setNewData(mData);
                        } else {
                            showEmpty();
                        }
                        // 初始化图表
                        initChart(outInDetailBean);
                    }

                    @Override
                    protected void onError(String message) {

                    }
                }));
    }

    /**
     * 根据不同的曲线创建不同的marker
     */
    private void createMarker(int x, int y) {
        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(mContext, "", R.layout.chart_marker_view);
        detailsMarkerView.setChartView(mLineChart);
        mLineChart.setDetailsMarkerView(detailsMarkerView);
        mLineChart.setRoundMarker(new RoundMarker(mContext, R.layout.item_chart_first_round));
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

}
