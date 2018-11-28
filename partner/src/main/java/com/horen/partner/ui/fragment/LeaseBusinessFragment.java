package com.horen.partner.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.bean.TypeBean;
import com.horen.base.util.ToastUitl;
import com.horen.chart.barchart.IBarData;
import com.horen.chart.linechart.ILineChartData;
import com.horen.chart.linechart.LineChartHelper;
import com.horen.chart.linechart.LineChartUtils;
import com.horen.chart.linechart.MyLineChart;
import com.horen.chart.marker.DetailsMarkerView;
import com.horen.chart.marker.RoundMarker;
import com.horen.partner.R;
import com.horen.partner.adapter.LeaseBusinessAdapter;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.OrderBean;
import com.horen.partner.mvp.contract.LeaseBusinessContract;
import com.horen.partner.mvp.model.LeaseBusinessModel;
import com.horen.partner.mvp.presenter.LeaseBusinessPresenter;
import com.horen.partner.util.RvEmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/9 14:03
 * Description:This isLeaseBusinessFragment 租赁业务
 */
public class LeaseBusinessFragment extends BaseFragment<LeaseBusinessPresenter, LeaseBusinessModel> implements LeaseBusinessContract.View, View.OnClickListener {
    private MyLineChart bill_linechart;
    private RecyclerView order_recycleview;
    private TextView tv_custom_name;
    private LeaseBusinessAdapter leaseBusinessAdapter;
    private String selectCompanyId;
    private SmartRefreshLayout refreshview_leasebusiness;
    private int pageNum = 1;
    private int totalPages;
    private List<String> mMonths = new ArrayList<>();
    private List<OrderBean.Order> orderList = new ArrayList<>();
    private LinearLayout ll_bill_linechart;
    private TextView tv_day;

    public static LeaseBusinessFragment newInstance() {
        Bundle bundle = new Bundle();
        LeaseBusinessFragment fragment = new LeaseBusinessFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partner_fragment_lease_business;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ll_bill_linechart = (LinearLayout) rootView.findViewById(R.id.ll_bill_linechart);
        tv_custom_name = (TextView) rootView.findViewById(R.id.tv_custom_name);
        order_recycleview = (RecyclerView) rootView.findViewById(R.id.order_recycleview);
        refreshview_leasebusiness = (SmartRefreshLayout) rootView.findViewById(R.id.refreshview_leasebusiness);
        tv_day = (TextView)rootView.findViewById(R.id.tv_day);
        tv_custom_name.setOnClickListener(this);
        initRecyclerView();
        initChart();
        getData();
    }

    private void initRecyclerView() {
        order_recycleview.setLayoutManager(new LinearLayoutManager(_mActivity));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider_10dp));
        order_recycleview.addItemDecoration(itemDecoration);
        leaseBusinessAdapter = new LeaseBusinessAdapter(R.layout.partner_item_business_order, new ArrayList<OrderBean.Order>());
        order_recycleview.setAdapter(leaseBusinessAdapter);
        refreshview_leasebusiness.setEnableLoadMore(false);
        refreshview_leasebusiness.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.getCompanyData();
                refreshLayout.finishRefresh();
            }
        });
        refreshview_leasebusiness.setEnableLoadMoreWhenContentNotFull(false);

    }

    private int xPosition = -1;

    private void initChart() {
        bill_linechart = rootView.findViewById(R.id.lease_linechart);
        bill_linechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("tag", h.toString() + "");
                selectMonth = getSelectMonth((int) e.getX());
                type = getSelectOrderType((int) e.getX(), e.getY());
                createMarker();
                xPosition = (int) h.getX();
                highLightValues(xPosition);
                getServerData();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    /**
     * 根据不同的曲线创建不同的marker
     */
    private void createMarker() {
        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(_mActivity, "", R.layout.chart_marker_view);
        detailsMarkerView.setChartView(bill_linechart);
        bill_linechart.setDetailsMarkerView(detailsMarkerView);
        bill_linechart.setRoundMarker(new RoundMarker(_mActivity, R.layout.item_chart_first_round));
    }

    private String type = null;

    /**
     * 根据类型获取数据
     */
    private void getServerData() {
//        if (TextUtils.isEmpty(type)) {
        mPresenter.getRTpSellOrderData(selectCompanyId, selectMonth, pageNum);
//        } else {
//            mPresenter.getRTpOrderInfo(selectCompanyId, selectMonth, type, pageNum);
//        }
    }

    /**
     * 通过x,y值得到点击的线的类型
     *
     * @param x
     * @param value
     * @return
     */
    private String getSelectOrderType(int x, double value) {
        String rtpType = "";
        String sendType = "";
        List<ILineChartData> rtpDatas = lineChartDatas.get(0);//租
        List<ILineChartData> sendDatas = lineChartDatas.get(1);//还
        if (rtpDatas.get(x).getValue() == value) {
            type = "12";
            rtpType = "12";
        }
        if (sendDatas.get(x).getValue() == value) {
            type = "11";
            sendType = "11";
        }
        if (rtpType.equals(sendType)) {
            type = "";
        }
        return type;
    }

    /**
     * 得到选中的月份
     *
     * @param s
     * @return
     */
    private String getSelectMonth(int s) {
        String month = null;
        for (int i = 0; i < mMonths.size(); i++) {
            if (s == i) {
                month = mMonths.get(i);
            }
        }
        return month;
    }

    /**
     * 开始获取公司数据
     */
    private void getData() {
        mPresenter.getCompanyData();
        lineChartDatas = new ArrayList<>();
    }

    @Override
    public void setSelectCompanyInfo(TypeBean typeBean) {
        tv_custom_name.setText(typeBean.getTypeName());
        selectCompanyId = typeBean.getTypeId();
        mPresenter.getLineChartData(selectCompanyId);
    }

    @Override
    public void setOrderData(List<OrderBean.Order> orderBeans, int pageNum, int pageTotal) {
        totalPages = pageTotal;
        orderList.clear();
        orderList.addAll(orderBeans);
        leaseBusinessAdapter.setNewData(orderBeans);
    }

    @Override
    public void setLineChartData(List<List<ILineChartData>> lineChartData, List<String> names, List<String> months, int maxY) {
        ArrayList<IBarData> entries = new ArrayList<>();
        // X轴文字斜体
        bill_linechart.getXAxis().setLabelRotationAngle(-45);
        LineChartUtils.initLineChart(getActivity(), bill_linechart, maxY, 0, true);
        int[] colors = new int[]{getResources().getColor(R.color.base_text_color_light), getResources().getColor(R.color.line_chart_send_color)};
        List<Integer> chartColors = new ArrayList<>();
        for (int color : colors) {
            chartColors.add(color);
        }
        LineChartHelper lineChartHelper = new LineChartHelper(bill_linechart);
        lineChartHelper.showLines(lineChartData, names, chartColors, 6, maxY, false, false);
        ll_bill_linechart.setVisibility(View.VISIBLE);
        tv_day.setVisibility(View.VISIBLE);
        lineChartDatas.clear();
        lineChartDatas.addAll(lineChartData);
        if (TextUtils.isEmpty(selectMonth)) {
            Calendar endDate = Calendar.getInstance();
            String year = String.valueOf(endDate.get(GregorianCalendar.YEAR));
            String month = String.valueOf(endDate.get(GregorianCalendar.MONTH) + 1);
            if (month.length() == 1) {
                month = "0" + month;
            }
            selectMonth = year + "-" + month;
        }
        mMonths.clear();
        mMonths.addAll(months);
        handler.postDelayed(runnable, 1500);
        bill_linechart.highlightValue(11, 0);
        bill_linechart.moveViewToAnimated(11, 0, YAxis.AxisDependency.RIGHT, 1500);
        bill_linechart.setExtraBottomOffset(-30);
        bill_linechart.setExtraTopOffset(10);
        bill_linechart.setExtraRightOffset(30);
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            bill_linechart.highlightValue(11, 0);
        }
    };
    private String selectMonth;
    private List<List<ILineChartData>> lineChartDatas;

    @Override
    public void showDefaultCompanyInfo(CompanyBean defaultCompany, boolean isFirst) {
        if (isFirst) {
            tv_custom_name.setText(defaultCompany.getCompany_name());
            selectCompanyId = defaultCompany.getCompany_id();
        }
        type = "11";
        createMarker();
        mPresenter.getLineChartData(selectCompanyId);
    }

    @Override
    public void showEmptyView() {
        RvEmptyUtils.setEmptyView(leaseBusinessAdapter, order_recycleview);
    }

    @Override
    public void showNoCompany() {
        ToastUitl.showShort("您还没有正式客户");
    }

    @Override
    public void onClick(View view) {
        if (view == tv_custom_name) {
            mPresenter.showCompanyDialog(_mActivity, selectCompanyId);
        }
    }

    @Override
    protected void reLoadData() {
        refreshview_leasebusiness.autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 一次选中多条线
     *
     * @param xPosition
     */
    private void highLightValues(int xPosition) {
        Highlight highlight1 = new Highlight(xPosition, 0, -1);
        Highlight highlight2 = new Highlight(xPosition, 1, -1);
        int y1 = (int) lineChartDatas.get(0).get(xPosition).getValue();
        int y2 = (int) lineChartDatas.get(1).get((xPosition)).getValue();
        if (y1 >= y2) {
            bill_linechart.highlightValues(new Highlight[]{highlight1, highlight2});
        } else {
            bill_linechart.highlightValues(new Highlight[]{highlight2, highlight1});
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (xPosition != -1) {
            highLightValues(xPosition);
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }
}
