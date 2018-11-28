package com.horen.service.ui.fragment.bill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.NumberUtil;
import com.horen.base.widget.CortpFooter;
import com.horen.chart.piechart.IPieData;
import com.horen.chart.piechart.PieChartHelper;
import com.horen.service.R;
import com.horen.service.bean.BillMainBean;
import com.horen.service.enumeration.bill.BillType;
import com.horen.service.listener.IBusinessTotalCount;
import com.horen.service.mvp.contract.BillMainContract;
import com.horen.service.mvp.model.BillMainModel;
import com.horen.service.mvp.presenter.BillMainPresenter;
import com.horen.service.ui.activity.bill.BillCompleteActivity;
import com.horen.service.ui.activity.bill.BillDetailActivity;
import com.horen.service.ui.activity.bill.BillTransportActivity;
import com.horen.service.ui.fragment.adapter.BillMainAdapter;
import com.horen.service.ui.fragment.adapter.BillMainCompleteAdapter;
import com.horen.service.ui.fragment.main.BillFragment;
import com.horen.service.utils.RvEmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/03/11:17
 * @description :账单中心---每个模块Fragment
 * @github :https://github.com/chenyy0708
 */
public class BillDetailFragment extends BaseFragment<BillMainPresenter, BillMainModel> implements BaseQuickAdapter.OnItemClickListener, BillMainContract.View, OnRefreshListener {

    private SmartRefreshLayout mRefreshLayout;
    private PieChart mPieChart;
    private RecyclerView mRecyclerView;

    private IBusinessTotalCount totalCountListener;
    private String type;
    private PieChartHelper chartHelper;

    /**
     * 饼状图显示数据
     */
    private List<IPieData> pieData = new ArrayList<>();
    private BaseQuickAdapter mainAdapter;
    /**
     * 选择时间
     */
    private String time;

    /**
     * 点击饼状图显示类型,默认为耗材
     */
    private String showType = "3";
    /**
     * 账单汇总数据
     */
    private List<BillMainBean.BillListBean> mData;

    private boolean isInit;

    public static BillDetailFragment newInstance(String type, String time) {
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("time", time);
        BillDetailFragment fragment = new BillDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_bill_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("type");
        time = getArguments().getString("time");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mPieChart = (PieChart) rootView.findViewById(R.id.pie_chart);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRefreshLayout.setRefreshFooter(new CortpFooter(_mActivity).setPrimaryColor(ContextCompat.getColor(_mActivity, R.color.color_f5)));
        mRefreshLayout.setOnRefreshListener(this);
        initRecyclerView();
        // 获取数据
        mRefreshLayout.autoRefresh();
        isInit = true;
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        if (type.equals(BillFragment.RECEIPT)) {
            mainAdapter = new BillMainAdapter(new ArrayList<BillMainBean.BillListBean>());
        } else {
            mainAdapter = new BillMainCompleteAdapter(new ArrayList<BillMainBean.BillListBean>());
        }
        mainAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mainAdapter);
        mPieChart.setBackgroundResource(R.color.white);
        mPieChart.setExtraOffsets(11.5f, 11.5f, 11.5f, 11.5f);
        mPieChart.setCenterTextSize(11);
        mPieChart.setCenterTextColor(ContextCompat.getColor(_mActivity, R.color.color_333));
        chartHelper = new PieChartHelper.Builder()
                .setLableTextColor(ContextCompat.getColor(_mActivity, R.color.color_333))
                .setLableTextSize(8)
                .setContext(_mActivity)
                .setPieChart(mPieChart)
                .setPieData(pieData)
                .build();
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // 更新选择状态
                showType = mData.get((int) h.getX()).getCost_type();
                // 列表显示当前选中状态
//                mainAdapter.setNewData(Arrays.asList(mData.get((int) h.getX())));
            }

            @Override
            public void onNothingSelected() {
                // 选择查看所有
                showType = "";
                mainAdapter.setNewData(mData);
            }
        });

    }


    private void setPieChartData(List<BillMainBean.BillListBean> serviceList) {
        pieData.clear();
        pieData.addAll(serviceList);
        if (serviceList.size() == 1) {
            mPieChart.setRotationAngle(-50);
        }
        chartHelper.setNewData(pieData);
    }

    /**
     * 总数监听
     */
    public void setTotalCountListener(IBusinessTotalCount totalCountListener) {
        this.totalCountListener = totalCountListener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BillMainBean.BillListBean bean = (BillMainBean.BillListBean) mainAdapter.getData().get(position);
        if (type.equals(BillFragment.RECEIPT)) {
            // 运输费
            if (bean.getCost_type().equals("5")) {
                BillTransportActivity.startActivity(_mActivity, bean);
            } else {
                BillDetailActivity.startActivity(_mActivity, bean);
            }
        } else {
            BillCompleteActivity.startActivity(_mActivity, bean);
        }
    }

    @Override
    protected void reLoadData() {
        mRefreshLayout.autoRefresh();
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.getAllBillList(time, type);
    }

    @Override
    public void getBillSuccess(List<BillMainBean.BillListBean> mData) {
        this.mData = mData;
        if (CollectionUtils.isNullOrEmpty(mData)) {
            // 隐藏饼状图
            mPieChart.setVisibility(View.GONE);
            mRefreshLayout.setBackgroundResource(R.color.color_f5);
            RvEmptyUtils.setEmptyView(mainAdapter, mRecyclerView, type.equals(BillType.NO_COMPLETE.getStatus()) ? "本月暂无应收账单产生" : "本月暂无已完成账单");
        } else {
            mPieChart.setVisibility(View.VISIBLE);
            mRefreshLayout.setBackgroundResource(R.color.white);
        }
        // 设置饼状图数据
        setPieChartData(mData);
        // 显示所有数据
        mainAdapter.setNewData(mData);
        mRefreshLayout.finishRefresh(0);
    }

    @Override
    public void setBillAllMoney(double allMoney) {
        if (type.equals(BillType.NO_COMPLETE.getStatus())) {
            mPieChart.setCenterText("未收总金额" + "\n" + NumberUtil.formitNumberTenthousand(allMoney) + "元");
        } else {
            mPieChart.setCenterText("已收总金额" + "\n" + NumberUtil.formitNumberTenthousand(allMoney) + "元");
        }
        mPieChart.invalidate();
        totalCountListener.setTotalCount(NumberUtil.formitNumber(allMoney), type);
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        mRefreshLayout.finishRefresh();
    }

    /**
     * 重新选择时间，刷新数据
     */
    public void refreshTime(String time) {
        this.time = time;
        if (isInit) {
            mPresenter.getAllBillList(time, type);
        }
    }
}
