package com.horen.partner.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.NumberUtil;
import com.horen.base.util.RecycleViewSmoothScroller;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.HRToolbar;
import com.horen.chart.barchart.IBarData;
import com.horen.chart.linechart.BillLineChart;
import com.horen.chart.linechart.ILineChartData;
import com.horen.chart.linechart.LineChartHelper;
import com.horen.chart.linechart.LineChartUtils;
import com.horen.chart.linechart.MyLineChart;
import com.horen.chart.marker.BillMarkerView;
import com.horen.chart.marker.DetailsMarkerView;
import com.horen.chart.marker.RoundMarker;
import com.horen.partner.R;
import com.horen.partner.adapter.BillAdapter;
import com.horen.partner.adapter.BillDetaiAwardsAdapter;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.BillChartBean;
import com.horen.partner.bean.BillListBean;
import com.horen.partner.bean.LineChartBean;
import com.horen.partner.ui.activity.bill.AwardsDetailActivity;
import com.horen.partner.ui.activity.bill.BillDetailActivity;
import com.horen.partner.ui.widget.ExpandView;
import com.horen.partner.util.CalculateUtil;
import com.horen.partner.util.RvEmptyUtils;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/3 15:56
 * Description:This isBillCenterFragment  账单页
 */
public class BillCenterFragment extends BaseFragment implements View.OnClickListener {
    private HRToolbar tool_bar;
    private BillLineChart bill_linechart;
    private RecyclerView bill_recycleview;
    private RecyclerView award_recyclerview;
    private BillAdapter billAdapter;
    private BillDetaiAwardsAdapter awardsAdapter;
    private SmartRefreshLayout refreshview_bill;
    private LinearLayout ll_bill_linechart;
    private ImageView iv_expand_bill_view;
    private ExpandView expand_bill;
    private ExpandView expand_awards;
    private ImageView iv_collapse_bill_view;
    private NestedScrollView nestedScrollView_bill;
    private RelativeLayout rl_rebate;
    private ImageView iv_expand_awards_view;
    private ImageView iv_collapse_awards_view;
    private RelativeLayout rl_break;
    private TextView tv_break;
    /**
     * 返利奖
     */
    private TextView tv_rebate;
    /**
     * 总业绩奖
     */
    private TextView tv_total_bonus;
    private int pageNum = 1;
    private int totalPages;
    private String selectMonth;
    private List<ILineChartData> lineChartDatas;
    private List<BillListBean.OrdersBean> ordersBeanList = new ArrayList<>();
    private List<BillListBean.AwardsBean> awardsBeanList = new ArrayList<>();
    private List<String> months = new ArrayList<>();

    public static BillCenterFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        BillCenterFragment billCenterFragment = new BillCenterFragment();
        billCenterFragment.setArguments(bundle);
        return billCenterFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partner_fragment_bill_center;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tool_bar = rootView.findViewById(R.id.tool_bar);
        ll_bill_linechart = (LinearLayout) rootView.findViewById(R.id.ll_bill_linechart);
        bill_recycleview = rootView.findViewById(R.id.bill_recycleview);
        award_recyclerview = (RecyclerView) rootView.findViewById(R.id.award_recyclerview);
        refreshview_bill = (SmartRefreshLayout) rootView.findViewById(R.id.refreshview_bill);
        bill_linechart = rootView.findViewById(R.id.bill_linechart);
        iv_expand_bill_view = (ImageView) rootView.findViewById(R.id.iv_expand_bill_view);
        expand_bill = (ExpandView) rootView.findViewById(R.id.expand_bill);
        iv_collapse_bill_view = (ImageView) rootView.findViewById(R.id.iv_collapse_bill_view);
        nestedScrollView_bill = (NestedScrollView) rootView.findViewById(R.id.nestedScrollView_bill);
        tv_total_bonus = (TextView) rootView.findViewById(R.id.tv_total_bonus);
        rl_rebate = (RelativeLayout) rootView.findViewById(R.id.rl_rebate);
        tv_rebate = (TextView) rootView.findViewById(R.id.tv_rebate);
        iv_expand_awards_view = (ImageView) rootView.findViewById(R.id.iv_expand_awards_view);
        iv_collapse_awards_view = (ImageView) rootView.findViewById(R.id.iv_collapse_awards_view);
        expand_awards = (ExpandView) rootView.findViewById(R.id.expand_awards);
        rl_break = (RelativeLayout) rootView.findViewById(R.id.rl_break);
        tv_break = (TextView) rootView.findViewById(R.id.tv_break);
        mBaseLoadService = LoadSir.getDefault().register(refreshview_bill, this);
        initToolbar(tool_bar.getToolbar());
        tool_bar.getToolbar().setNavigationIcon(null);
        tool_bar.setTitle("账单中心");
        iv_expand_bill_view.setOnClickListener(this);
        iv_collapse_bill_view.setOnClickListener(this);
        iv_collapse_awards_view.setOnClickListener(this);
        iv_expand_awards_view.setOnClickListener(this);
        initRecyclerView();
        initChart();
    }

    private LinearLayoutManager layoutManager1;

    private void initRecyclerView() {
        layoutManager1 = new LinearLayoutManager(_mActivity);
        layoutManager1.setSmoothScrollbarEnabled(true);
        layoutManager1.setAutoMeasureEnabled(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(_mActivity);
        layoutManager2.setSmoothScrollbarEnabled(true);
        layoutManager2.setAutoMeasureEnabled(true);
        bill_recycleview.setLayoutManager(layoutManager1);
        bill_recycleview.setHasFixedSize(true);
        bill_recycleview.setNestedScrollingEnabled(false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider_f5));
        bill_recycleview.addItemDecoration(itemDecoration);
        award_recyclerview.addItemDecoration(itemDecoration);
        award_recyclerview.setNestedScrollingEnabled(false);
        award_recyclerview.setHasFixedSize(true);
        award_recyclerview.setLayoutManager(layoutManager2);
        billAdapter = new BillAdapter(R.layout.partner_item__bill, new ArrayList<BillListBean.OrdersBean>());
        billAdapter.openLoadAnimation(BaseQuickAdapter.HEADER_VIEW);
        awardsAdapter = new BillDetaiAwardsAdapter(R.layout.partner_item_bill_detail_awards, new ArrayList<BillListBean.AwardsBean>());
        bill_recycleview.setAdapter(billAdapter);
        award_recyclerview.setAdapter(awardsAdapter);
        refreshview_bill.setEnableLoadMoreWhenContentNotFull(false);
        refreshview_bill.setEnableLoadMore(false);
        refreshview_bill.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (expand_bill.isExpand()) {
                    collapseView();
                }
                pageNum = 1;
                getLineChartData();
                refreshLayout.finishRefresh();
            }
        });
        bill_recycleview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(_mActivity, BillDetailActivity.class);
                intent.putExtra("month", selectMonth);
                intent.putExtra("billInfo", ordersBeanList.get(position));
                startActivity(intent);
            }
        });
        award_recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(_mActivity, AwardsDetailActivity.class);
                intent.putExtra("month", selectMonth);
                intent.putExtra("awardsInfo", awardsBeanList.get(position));
                startActivity(intent);
            }
        });
    }


    private void initChart() {
        bill_linechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (expand_bill.isExpand()) {
                    collapseView();
                }
                Log.i("tag", h.toString() + "");
                selectMonth = getSelectMonth((int) e.getX());
                createMarker();
                bill_linechart.highlightValue(h);
                getServerData();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        lineChartDatas = new ArrayList<>();
        createMarker();
        refreshview_bill.autoRefresh();
    }

    /**
     * 得到选中的月份
     *
     * @param s
     * @return
     */
    private String getSelectMonth(int s) {
        String month = null;
        for (int i = 0; i < months.size(); i++) {
            if (s == i) {
                month = months.get(i);
            }
        }
        return month;
    }

    /**
     * 获取折现数据
     */
    private void getLineChartData() {
        mRxManager.add(ApiPartner.getInstance().getBillChartData(ApiRequest.getBillChartData()).compose(RxHelper.<List<BillChartBean>>getResult()).subscribeWith(new BaseObserver<List<BillChartBean>>(_mActivity, true) {
            @Override
            protected void onSuccess(List<BillChartBean> billChartBeans) {
                List<ILineChartData> chartDatas = new ArrayList<>();
                for (int j = 0; j < billChartBeans.size(); j++) {
                    months.add(billChartBeans.get(j).getMonths());
                    if (billChartBeans.get(j).getAmount() > 30000) {
                        billChartBeans.get(j).setAmount(30000);
                    }
                    chartDatas.add(new LineChartBean((billChartBeans.get(j).getAmount()), billChartBeans.get(j).getMonths()));
                }
                lineChartDatas.clear();
                lineChartDatas.addAll(chartDatas);
                setLineData();
            }

            @Override
            protected void onError(String message) {
                showToast(message);
            }
        }));
    }

    /**
     * 根据月份查询数据
     */
    private void getServerData() {
        mRxManager.add(ApiPartner.getInstance().getBillListByMonth(ApiRequest.getBillListByMonth(selectMonth)).compose(RxHelper.<BillListBean>getResult()).subscribeWith(new BaseObserver<BillListBean>(_mActivity, true) {
            @Override
            protected void onSuccess(BillListBean billListBeans) {
                awardsAdapter.setNewData(billListBeans.getAwards());
                billAdapter.setNewData(billListBeans.getOrders());
                ordersBeanList.clear();
                ordersBeanList.addAll(billListBeans.getOrders());
                awardsBeanList.clear();
                awardsBeanList.addAll(billListBeans.getAwards());
                if (ordersBeanList.size() == 0) {
                    iv_expand_bill_view.setVisibility(View.INVISIBLE);
                } else {
                    iv_expand_bill_view.setVisibility(View.VISIBLE);
                }
                if (awardsBeanList.size() == 0) {
                    iv_expand_awards_view.setVisibility(View.INVISIBLE);
                } else {
                    iv_expand_awards_view.setVisibility(View.VISIBLE);
                }
                if (billListBeans.getAwards().size() == 0 && billListBeans.getOrders().size() == 0) {
                    RvEmptyUtils.setEmptyView(billAdapter, bill_recycleview, R.layout.include_bill_data_empty);
                }
                rl_rebate.setVisibility(View.VISIBLE);
                rl_break.setVisibility(View.VISIBLE);
                tv_rebate.setText(NumberUtil.formitNumberTwoPoint(billListBeans.getCommission().getCommission_amount()));
                tv_total_bonus.setText(NumberUtil.formitNumberTwoPoint(billListBeans.getPerformance().getAmount()));
                tv_break.setText(NumberUtil.formitNumberTwoPoint(billListBeans.getTopaward().getAward_amount()));
            }

            @Override
            protected void onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }

    /**
     * 根据不同的曲线创建不同的marker
     */
    private void createMarker() {
        BillMarkerView detailsMarkerView = new BillMarkerView(_mActivity, R.layout.partner_chart_marker_view);
        detailsMarkerView.setChartView(bill_linechart);
        bill_linechart.setBillMarkerView(detailsMarkerView);
        bill_linechart.setRoundMarker(new RoundMarker(_mActivity, R.layout.item_chart_third_round));
    }

    private void setLineData() {
        int maxValue = 0;
        for (int i = 0; i < lineChartDatas.size(); i++) {
            if (lineChartDatas.get(i).getValue() > maxValue) {
                maxValue = (int) lineChartDatas.get(i).getValue();
            }
        }
        bill_linechart.setVisibility(View.VISIBLE);
        LineChartUtils.initLineChart(getActivity(), bill_linechart, CalculateUtil.getBillChartMaxY(maxValue), 0, true);
        // X轴文字斜体
        bill_linechart.getXAxis().setLabelRotationAngle(-45);
        LineChartHelper lineChartHelper = new LineChartHelper(bill_linechart);
        lineChartHelper.showLine(lineChartDatas, getResources().getColor(R.color.base_text_color_light), 6);
        ll_bill_linechart.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(selectMonth)) {
            Calendar endDate = Calendar.getInstance();
            String year = String.valueOf(endDate.get(GregorianCalendar.YEAR));
            String month = String.valueOf(endDate.get(GregorianCalendar.MONTH) + 1);
            if (month.length() == 1) {
                month = "0" + month;
            }
            selectMonth = year + "-" + month;
        }
        bill_linechart.setExtraTopOffset(10);
        bill_linechart.setExtraRightOffset(35);
        bill_linechart.setExtraBottomOffset(10);
        XAxis xAxis = bill_linechart.getXAxis();
        xAxis.setXOffset(20);
        handler.postDelayed(runnable, 1500);
        bill_linechart.moveViewToAnimated(11, 0, YAxis.AxisDependency.LEFT, 1500);
        getServerData();
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            bill_linechart.highlightValue(11, 0);
        }
    };

    @Override
    protected void reLoadData() {
        refreshview_bill.autoRefresh();
    }

    @Override
    protected boolean isCustomLoadingLayout() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void expandView() {
        expand_bill.setVisibility(View.VISIBLE);
        expand_bill.expand();
        iv_expand_bill_view.setVisibility(View.INVISIBLE);
    }

    private void collapseView() {
        expand_bill.collapse();
        iv_expand_bill_view.setVisibility(View.VISIBLE);
        nestedScrollView_bill.scrollTo(0, 0);
    }

    private void expandAwardsView() {
        expand_awards.setVisibility(View.VISIBLE);
        expand_awards.expand();
        iv_expand_awards_view.setVisibility(View.INVISIBLE);
    }

    private void collapseAwardsView() {
        expand_awards.collapse();
        iv_expand_awards_view.setVisibility(View.VISIBLE);
        nestedScrollView_bill.scrollTo(0, 0);
    }

    @Override
    public void onClick(View view) {
        if (view == iv_expand_bill_view) {
            expandView();
        }
        if (view == iv_collapse_bill_view) {
            collapseView();
        }
        if (view == iv_expand_awards_view) {
            expandAwardsView();
        }
        if (view == iv_collapse_awards_view) {
            collapseAwardsView();
        }
    }
}
