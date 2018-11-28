package com.horen.partner.ui.activity.bill;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.NumberUtil;
import com.horen.partner.R;
import com.horen.partner.adapter.BillAdapter;
import com.horen.partner.adapter.BillDetaiAwardsAdapter;
import com.horen.partner.adapter.BillDetailOrderAdapter;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.BillDetailBean;
import com.horen.partner.bean.BillListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class BillDetailActivity extends BaseActivity {
    /**
     * 公司名称
     */
    private TextView mTvCompanyName;
    /**
     * 总收入
     */
    private TextView mTvTotalMoney;
    /**
     * 订单列表
     */
    private RecyclerView mRecyclerviewBillOrder;
    private BillDetailOrderAdapter orderAdapter;
    private String companyId;
    private String month;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("提成");
        getTitleBar().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mTvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mTvTotalMoney = (TextView) findViewById(R.id.tv_total_money);
        mRecyclerviewBillOrder = (RecyclerView) findViewById(R.id.recyclerview_bill_order);
        getIntentData();
        initRecyclerView();
    }

    private void getIntentData() {
        BillListBean.OrdersBean info = (BillListBean.OrdersBean) getIntent().getSerializableExtra("billInfo");
        companyId = info.getCompany_id();
        month = getIntent().getStringExtra("month");
        mTvCompanyName.setText(info.getCompany_name());
        mTvTotalMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(info.getAmount()));
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerviewBillOrder.setLayoutManager(layoutManager);
        mRecyclerviewBillOrder.setHasFixedSize(true);
        mRecyclerviewBillOrder.setNestedScrollingEnabled(false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.list_divider_10dp));
        mRecyclerviewBillOrder.addItemDecoration(dividerItemDecoration);
        orderAdapter = new BillDetailOrderAdapter(R.layout.partner_item_bill_detial_order, new ArrayList<BillDetailBean>());
        mRecyclerviewBillOrder.setAdapter(orderAdapter);
        getServerData();
    }

    private void getServerData() {
        mRxManager.add(ApiPartner.getInstance().getBillDetail(ApiRequest.getBillDetail(companyId, month)).compose(RxHelper.<List<BillDetailBean>>getResult()).subscribeWith(new BaseObserver<List<BillDetailBean>>(this, true) {
            @Override
            protected void onSuccess(List<BillDetailBean> billDetailBean) {
                orderAdapter.setNewData(billDetailBean);
            }

            @Override
            protected void onError(String message) {

            }
        }));
    }
}
