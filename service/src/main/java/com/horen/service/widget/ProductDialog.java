package com.horen.service.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.horen.chart.barchart.BarChartHelper;
import com.horen.chart.barchart.DefaultValueFormatter;
import com.horen.chart.barchart.IBarData;
import com.horen.service.R;
import com.horen.service.bean.ProductData;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/14:45
 * @description : 产品库存Dialog
 * @github :https://github.com/chenyy0708
 */
public class ProductDialog extends BaseDialog<ProductDialog> implements View.OnClickListener {
    private TextView mTvName;
    private TextView mTvType;
    private BarChart mBarChart;

    /**
     * 总量、可用、未洗、未修
     */
    private int total, available, notWashed, notRepair;
    private String product_name, product_type;
    private ImageView mIvClose;

    public ProductDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        // 屏幕宽度
        widthScale(0.85f);
        View inflate = View.inflate(mContext, R.layout.service_dialog_product, null);
        mTvName = (TextView) inflate.findViewById(R.id.tv_name);
        mIvClose = (ImageView) inflate.findViewById(R.id.iv_close);
        mTvType = (TextView) inflate.findViewById(R.id.tv_type);
        mBarChart = (BarChart) inflate.findViewById(R.id.bar_chart);
        mIvClose.setOnClickListener(this);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        setCanceledOnTouchOutside(false);
        mTvName.setText(product_name);
        mTvType.setText(product_type);
        setBarChartData();
    }

    private void setBarChartData() {
        //模拟数据
        ArrayList<IBarData> entries = new ArrayList<>();
        entries.add(new ProductData(total, "总量"));
        entries.add(new ProductData(available, "可用"));
        entries.add(new ProductData(notWashed, "未洗"));
        entries.add(new ProductData(notRepair, "待修"));
        mBarChart.getXAxis().setTextColor(Color.parseColor("#666666"));
        mBarChart.getXAxis().setTextSize(12);
        mBarChart.getAxisLeft().setTextColor(Color.parseColor("#666666"));
        mBarChart.getAxisLeft().setTextSize(12);
        mBarChart.getAxisLeft().setLabelCount(4);
        mBarChart.getAxisLeft().setDrawAxisLine(false);
        mBarChart.setExtraBottomOffset(5);
        // 计算最大值
        int maxValue = total;
        // 设置坐标轴的最大值和最小值
        if (maxValue % 3 != 0 || maxValue % 5 != 0) {
            maxValue = ((((maxValue / 5) / 3) + 1) * 3) * 5;
        }
        //设置最大数值范围
        mBarChart.getAxisLeft().setAxisMaximum(maxValue);
        //设置最小数值范围
        mBarChart.getAxisLeft().setAxisMinimum(0);
        new BarChartHelper.Builder()
                .setContext(mContext)
                .setBarChart(mBarChart)
                .setBarData(entries)
                .setBarWidth(0.3f)
                .setValueTextSize(13)
                .setBarMultopleColor(true)
                .setBarDifferentColors(new int[]{ContextCompat.getColor(mContext, R.color.service_color_6FB), ContextCompat.getColor(mContext, R.color.service_color_EF8)
                        , ContextCompat.getColor(mContext, R.color.service_color_39C), ContextCompat.getColor(mContext, R.color.service_color_F15)})
                // 标签显示隐藏
                .setLegendEnable(false)
                .setBarColor(Color.parseColor("#F15B02"))
                .build();
        // 去除柱状图的千位符
        mBarChart.getBarData().setValueFormatter(new DefaultValueFormatter());
    }

    /**
     * 设置数据
     *
     * @param total     总量
     * @param available 可用
     * @param notWashed 未洗
     * @param notRepair 未修
     */
    public ProductDialog setData(int total, int available, int notWashed, int notRepair) {
        this.total = total;
        this.available = available;
        this.notWashed = notWashed;
        this.notRepair = notRepair;
        return this;
    }

    /**
     * 设置产品名
     *
     * @param product_name 名字
     * @param product_type 类型
     */
    public ProductDialog setProduct(String product_name, String product_type) {
        this.product_name = product_name;
        this.product_type = product_type;
        return this;
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
