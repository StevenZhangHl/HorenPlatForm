package com.cyy.company.ui.fragment.eye.asset;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AnalysisHistogram;
import com.cyy.company.listener.IRefreshFragment;
import com.cyy.company.ui.activity.eye.AssetsChartActivity;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragment;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.chart.hrlinechart.LineEntry;
import com.horen.chart.hrlinechart.SuitLines;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/13:34
 * @description :资产损坏统计折线图
 * @github :https://github.com/chenyy0708
 */
public class AssetDamageFragment extends BaseFragment implements View.OnClickListener, IRefreshFragment {

    private SuitLines mLineChart;

    private int type = HRConstant.RENT;
    private RecyclerView mRecyclerView;
    private List<LineEntry> lines;

    private boolean isInit = false;

    public static AssetDamageFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        AssetDamageFragment fragment = new AssetDamageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_assets_rent;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        isInit = true;
        type = getArguments().getInt("type");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLineChart = (SuitLines) rootView.findViewById(R.id.line_chart);
        mLineChart.setOnClickListener(this);
        mRecyclerView.setVisibility(View.GONE);
        mLineChart.setDefaultOneLineColor(ContextCompat.getColor(_mActivity, R.color.color_F15));
        mLineChart.setOnTouch(false);
        mLineChart.setShowXGrid(false);
        mLineChart.setShowYGrid(false);
        mLineChart.setShowXExtend(true);
        mRecyclerView.setLayoutManager(new FlexboxLayoutManager(_mActivity));
        // 分析:损坏数统计-柱状图
        getAnalysisHistogram();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.line_chart) {
            AssetsChartActivity.startAction(_mActivity, type);
        }
    }

    @Override
    public void onRefreshListener() {
        getAnalysisHistogram();
    }

    /**
     * 资产损坏量折线图
     */
    private void showLineChart(List<AnalysisHistogram.PdListBean> pdListBean) {
        lines = new ArrayList<>();
        for (AnalysisHistogram.PdListBean listBean : pdListBean) {
            lines.add(new LineEntry(listBean.getNumber(), listBean.getDate()));
        }
        mLineChart.feedWithAnim(lines);
    }
}
