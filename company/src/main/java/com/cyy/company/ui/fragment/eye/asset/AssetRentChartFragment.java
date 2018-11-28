package com.cyy.company.ui.fragment.eye.asset;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AnalysisLineChart;
import com.cyy.company.ui.adapter.AssetsTagAdapter;
import com.google.android.flexbox.FlexboxLayoutManager;
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
public class AssetRentChartFragment extends BaseFragment implements HRChart.onChartTouchListener, BaseQuickAdapter.OnItemClickListener {

    private int type;

    private HRChart mChart;
    private FrameLayout mFlChartTop;
    private RecyclerView mRecyclerView;
    private List<LineEntry> lines;
    private AssetsTagAdapter tagAdapter;

    public static AssetRentChartFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        AssetRentChartFragment fragment = new AssetRentChartFragment();
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
        mChart.setDefaultColor(R.color.base_text_color_light);
        // 设置适配器
        mRecyclerView.setLayoutManager(new FlexboxLayoutManager(_mActivity));
        tagAdapter = new AssetsTagAdapter(new ArrayList<AnalysisLineChart.PdListBean>());
        tagAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(tagAdapter);
        // 分析:资产在租量统计
        getAnalysisLineChart();
    }

    @Override
    public void OnTouch() {
        mFlChartTop.setVisibility(View.GONE);
    }

    @Override
    public void onUnTouch() {
        mFlChartTop.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!tagAdapter.getItem(position).isSelect()) {
            tagAdapter.setSelectPosition(position);
            // 更新图表数据
            showLineChart(tagAdapter.getItem(position));
        }
    }

    private void getAnalysisLineChart() {
        mRxManager.add(ApiCompany.getInstance()
                .getAnalysisLineChart(CompanyParams.getCompanyId())
                .compose(RxHelper.<AnalysisLineChart>getResult())
                .subscribeWith(new BaseObserver<AnalysisLineChart>() {
                    @Override
                    protected void onSuccess(AnalysisLineChart analysisLineChart) {
                        if (!CollectionUtils.isNullOrEmpty(analysisLineChart.getPdList())) {
                            tagAdapter.setNewData(analysisLineChart.getPdList());
                            // 默认显示第一条数据
                            showLineChart(analysisLineChart.getPdList().get(0));
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
        mChart.getSuitlines().feedWithAnim(lines);
    }
}
