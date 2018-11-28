package com.cyy.company.ui.fragment.eye.asset;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AnalysisLineChart;
import com.cyy.company.listener.IRefreshFragment;
import com.cyy.company.ui.activity.eye.AssetsChartActivity;
import com.cyy.company.ui.adapter.AssetsTagAdapter;
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
 * @description :资产在租统计折线图
 * @github :https://github.com/chenyy0708
 */
public class AssetRentFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, IRefreshFragment {

    private SuitLines mLineChart;
    private int type = HRConstant.RENT;
    private RecyclerView mRecyclerView;
    private List<LineEntry> lines;
    private AssetsTagAdapter tagAdapter;
    /**
     * 选中tag位置
     */
    private int currentPosition = 0;

    private boolean isInit = false;

    public static AssetRentFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        AssetRentFragment fragment = new AssetRentFragment();
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
        mLineChart.setDefaultOneLineColor(ContextCompat.getColor(_mActivity, R.color.base_text_color_light));
        mRecyclerView.setVisibility(View.VISIBLE);
        mLineChart.setOnTouch(false);
        mLineChart.setShowXGrid(false);
        mLineChart.setShowYGrid(false);
        mLineChart.setShowXExtend(true);
        mRecyclerView.setLayoutManager(new FlexboxLayoutManager(_mActivity));
        tagAdapter = new AssetsTagAdapter(new ArrayList<AnalysisLineChart.PdListBean>());
        tagAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(tagAdapter);
        // 分析:资产在租量统计
        getAnalysisLineChart();
    }

    private void getAnalysisLineChart() {
        mRxManager.add(ApiCompany.getInstance()
                .getAnalysisLineChart(CompanyParams.getCompanyId())
                .compose(RxHelper.<AnalysisLineChart>getResult())
                .subscribeWith(new BaseObserver<AnalysisLineChart>() {
                    @Override
                    protected void onSuccess(AnalysisLineChart analysisLineChart) {
                        if (!CollectionUtils.isNullOrEmpty(analysisLineChart.getPdList())) {
                            tagAdapter.notifyData(currentPosition, analysisLineChart.getPdList());
                            // 默认显示第一条数据
                            showLineChart(analysisLineChart.getPdList().get(currentPosition));
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
     * 资产再租量折线图
     */
    private void showLineChart(AnalysisLineChart.PdListBean pdListBean) {
        lines = new ArrayList<>();
        for (AnalysisLineChart.PdListBean.ListBean listBean : pdListBean.getList()) {
            lines.add(new LineEntry(listBean.getNumber(), listBean.getDate()));
        }
        mLineChart.feedWithAnim(lines);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.line_chart) {
            AssetsChartActivity.startAction(_mActivity, type);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!tagAdapter.getItem(position).isSelect()) {
            currentPosition = position;
            tagAdapter.setSelectPosition(position);
            // 更新图表数据
            showLineChart(tagAdapter.getItem(position));
        }
    }

    @Override
    public void onRefreshListener() {
        if (isInit) getAnalysisLineChart();
    }
}
