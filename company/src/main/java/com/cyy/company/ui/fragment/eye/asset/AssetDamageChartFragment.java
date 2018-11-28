package com.cyy.company.ui.fragment.eye.asset;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AnalysisHistogram;
import com.horen.base.base.BaseFragment;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.chart.hrlinechart.HRChart;
import com.horen.chart.hrlinechart.LineEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/15:05
 * @description :资产图表Fragment
 * @github :https://github.com/chenyy0708
 */
public class AssetDamageChartFragment extends BaseFragment implements HRChart.onChartTouchListener {

    private int type;

    private HRChart mChart;
    private FrameLayout mFlChartTop;
    private RecyclerView mRecyclerView;
    private List<LineEntry> lines;

    public static AssetDamageChartFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        AssetDamageChartFragment fragment = new AssetDamageChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_assets_chart;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        mChart = (HRChart) rootView.findViewById(R.id.chart);
        mFlChartTop = (FrameLayout) rootView.findViewById(R.id.fl_chart_top);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mChart.setTouchListener(this);
        mFlChartTop.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mChart.setDefaultColor(R.color.color_F15);
        // 分析:资产损坏量统计
        getAnalysisHistogram();
    }

    @Override
    public void OnTouch() {
        rootView.findViewById(R.id.tv_box_number).setVisibility(View.GONE);
    }

    @Override
    public void onUnTouch() {
        rootView.findViewById(R.id.tv_box_number).setVisibility(View.VISIBLE);
    }

    private void getAnalysisHistogram() {
        mRxManager.add(ApiCompany.getInstance()
                .getAnalysisHistogram(CompanyParams.getCompanyId())
                .compose(RxHelper.<AnalysisHistogram>getResult())
                .subscribeWith(new BaseObserver<AnalysisHistogram>() {
                    @Override
                    protected void onSuccess(AnalysisHistogram histogram) {
                        if (!CollectionUtils.isNullOrEmpty(histogram.getPdList())) {
                            showLineChart(histogram.getPdList());
                        } else {
                            showEmpty();
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    /**
     * 资产损坏量折线图
     */
    private void showLineChart(List<AnalysisHistogram.PdListBean> pdListBean) {
        lines = new ArrayList<>();
        for (AnalysisHistogram.PdListBean listBean : pdListBean) {
            lines.add(new LineEntry(listBean.getNumber(), listBean.getDate()));
        }
        mChart.getSuitlines().feedWithAnim(lines);
    }

}
