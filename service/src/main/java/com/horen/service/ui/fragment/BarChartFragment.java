package com.horen.service.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.ImageLoader;
import com.horen.base.widget.HRTabView;
import com.horen.base.widget.SheetDialog;
import com.horen.chart.barchart.BarChartHelper;
import com.horen.chart.barchart.IBarData;
import com.horen.service.R;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/02/09:53
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BarChartFragment extends BaseFragment {

    private BarChart barChart;
    private BarChart singleBarChart;
    private ImageView imageView;
    private HRTabView hrTabView;

    public static BarChartFragment newInstance() {
        Bundle args = new Bundle();
        BarChartFragment fragment = new BarChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取布局文件
     *
     * @return 布局Id
     */
    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_barchart;
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
     * @param savedInstanceState 保存数据
     */
    @Override
    public void initView(Bundle savedInstanceState) {
        barChart = rootView.findViewById(R.id.bar_chart);
        singleBarChart = rootView.findViewById(R.id.single_bar_chart);
        hrTabView = rootView.findViewById(R.id.tab_view);
        imageView = rootView.findViewById(R.id.iv);
        ImageLoader.load(_mActivity, "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3323017670,3063552351&fm=27&gp=0.jpg", imageView);
        onBarChart();
        twoBarChart();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SheetDialog dialog = new SheetDialog(_mActivity, new String[]{"相册选择", "拍照"}, null);
                dialog.isTitleShow(false)
                        .cancelText(getString(com.horen.base.R.string.disagree))
                        .show();
            }
        });

        singleBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
            }

            @Override
            public void onNothingSelected() {

            }
        });

        hrTabView.setTabText("2", "喉箍", "FOoW", "500", "1000");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageLoader.load(_mActivity, Matisse.obtainPathResult(data).get(0), imageView);
        }
    }

    private void onBarChart() {
        //模拟数据
        ArrayList<IBarData> entries = new ArrayList<>();
        // X轴文字斜体
        singleBarChart.getXAxis().setTextColor(Color.parseColor("#666666"));
        singleBarChart.getXAxis().setTextSize(12);
        singleBarChart.getAxisLeft().setTextColor(Color.parseColor("#666666"));
        singleBarChart.getAxisLeft().setTextSize(12);
        // 自定义MarkerView
//        singleBarChart.setMarker(new BarValueMarker(_mActivity, "天", entries));
        new BarChartHelper.Builder()
                .setContext(_mActivity)
                .setBarChart(singleBarChart)
                .setBarData(entries)
                .setBarWidth(0.15f)
                .setValueTextSize(13)
                .setBarMultopleColor(true)
                .setBarDifferentColors(new int[]{ContextCompat.getColor(_mActivity, R.color.service_color_6FB), ContextCompat.getColor(_mActivity, R.color.service_color_EF8)
                        , ContextCompat.getColor(_mActivity, R.color.service_color_39C), ContextCompat.getColor(_mActivity, R.color.service_color_F15)})
                // 标签显示隐藏
                .setLegendEnable(false)
                .setBarColor(Color.parseColor("#F15B02"))
                .build();
//        singleBarChart.getBarData().setDrawValues(false);
        singleBarChart.getBarData().setValueFormatter(new LargeValueFormatter(""));
    }

    /**
     * 两条柱状图
     */
    private void twoBarChart() {
        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("入库");
        names.add("出库");
        names.add("在库");
        names.add("采购");
        // 多条柱状图数据集合
        List<List<IBarData>> data = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            // 单个柱状图数据
            ArrayList<IBarData> entries = new ArrayList<>();
        }
    }
}
