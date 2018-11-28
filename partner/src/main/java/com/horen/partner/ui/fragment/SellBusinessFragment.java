package com.horen.partner.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.base.BaseFragment;
import com.horen.base.bean.TypeBean;
import com.horen.base.util.ToastUitl;
import com.horen.partner.R;
import com.horen.partner.adapter.SellBusinessAdapter;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.OrderBean;
import com.horen.partner.mvp.contract.SellBusinessContract;
import com.horen.partner.mvp.model.SellBusinessModel;
import com.horen.partner.mvp.presenter.SellBusinessPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/9 14:04
 * Description:This isSellBusinessFragment 销售业务
 */
public class SellBusinessFragment extends BaseFragment<SellBusinessPresenter, SellBusinessModel> implements SellBusinessContract.View, View.OnClickListener {

    /**
     * 客户名称
     */
    private TextView mTvCustomName;
    /**
     * 选择时间
     */
    private TextView mTvShowTime;
    private SellBusinessAdapter sellBusinessAdapter;
    private SmartRefreshLayout refreshview_sellbusiness;
    private RecyclerView recyclerview_sellbusiness;
    private String selectCompanyId;
    private String selectMonth;
    private LinearLayout ll_data_empty;
    private TextView tv_empty_type;
    private int pageNum = 1;
    private int totalPages;
    private List<OrderBean.Order> orderList = new ArrayList<>();

    public static SellBusinessFragment newInstance() {
        Bundle bundle = new Bundle();
        SellBusinessFragment fragment = new SellBusinessFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partner_fragment_sell_business;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ll_data_empty = (LinearLayout) rootView.findViewById(R.id.ll_data_empty);
        tv_empty_type = (TextView) rootView.findViewById(R.id.tv_empty_type);
        mTvCustomName = (TextView) rootView.findViewById(R.id.tv_custom_name);
        mTvShowTime = (TextView) rootView.findViewById(R.id.tv_show_time);
        recyclerview_sellbusiness = (RecyclerView) rootView.findViewById(R.id.recyclerview_sellbusiness);
        refreshview_sellbusiness = (SmartRefreshLayout) rootView.findViewById(R.id.refreshview_sellbusiness);
        mTvShowTime.setOnClickListener(this);
        mTvCustomName.setOnClickListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerview_sellbusiness.setLayoutManager(new LinearLayoutManager(_mActivity));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(_mActivity, R.drawable.list_divider_10dp));
        recyclerview_sellbusiness.addItemDecoration(itemDecoration);
        sellBusinessAdapter = new SellBusinessAdapter(R.layout.partner_item_business_order, new ArrayList<OrderBean.Order>());
        recyclerview_sellbusiness.setAdapter(sellBusinessAdapter);
        refreshview_sellbusiness.setEnableLoadMore(false);
        refreshview_sellbusiness.setEnableLoadMoreWhenContentNotFull(false);
        refreshview_sellbusiness.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.getCompanyData();
                refreshLayout.finishRefresh();
            }
        });
        refreshview_sellbusiness.autoRefresh();
    }

    @Override
    public void setSelectCompanyInfo(TypeBean typeBean) {
        selectCompanyId = typeBean.getTypeId();
        mTvCustomName.setText(typeBean.getTypeName());
        getCurrentMonth();
        mPresenter.getSellOrderInfo(selectCompanyId, selectMonth, "11", 0);
    }

    @Override
    public void setOrderData(List<OrderBean.Order> orderBeans, int pageNum, int pageTotal) {
        totalPages = pageTotal;
        orderList.clear();
        orderList.addAll(orderBeans);
        sellBusinessAdapter.setNewData(orderBeans);
        if (orderBeans.size() == 0) {
            ll_data_empty.setVisibility(View.VISIBLE);
            tv_empty_type.setText("本月暂无销售无业务产生");
        } else {
            ll_data_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSelectTime(String selectTime) {
        Calendar endDate = Calendar.getInstance();
        String year = String.valueOf(endDate.get(GregorianCalendar.YEAR));
        String month = String.valueOf(endDate.get(GregorianCalendar.MONTH) + 1);
        if (year.equals(selectTime.substring(0, 4))) {
            if (month.equals(selectTime.substring(6, selectTime.length()))) {
                mTvShowTime.setText("本月");
            } else {
                mTvShowTime.setText(selectTime);
            }
        } else {
            mTvShowTime.setText(selectTime);
        }
        selectMonth = selectTime;
        mPresenter.getSellOrderInfo(selectCompanyId, selectMonth, "11", 0);
    }

    private void getCurrentMonth() {
        Calendar endDate = Calendar.getInstance();
        String year = String.valueOf(endDate.get(GregorianCalendar.YEAR));
        String month = String.valueOf(endDate.get(GregorianCalendar.MONTH) + 1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        selectMonth = year + "-" + month;
        mTvShowTime.setText("本月");
    }

    @Override
    public void showDefaultCompanyInfo(CompanyBean defaultCompany, boolean isFirst) {
        if (isFirst) {
            mTvCustomName.setText(defaultCompany.getCompany_name());
            selectCompanyId = defaultCompany.getCompany_id();
        }
        getCurrentMonth();
        mPresenter.getSellOrderInfo(selectCompanyId, selectMonth, "11", 0);
    }

    @Override
    public void showEmptyView() {
        ToastUitl.showShort("您还没有正式客户");
    }

    @Override
    public void onClick(View view) {
        if (view == mTvCustomName) {
            mPresenter.showCompanyDialog(_mActivity, selectCompanyId);
        }
        if (view == mTvShowTime) {
            mPresenter.showSelectTimeDialog(_mActivity);
        }
    }

    @Override
    protected void reLoadData() {
        refreshview_sellbusiness.autoRefresh();
    }
}
