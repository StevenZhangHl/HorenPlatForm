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
import com.horen.partner.adapter.AwardsDetailAdapter;
import com.horen.partner.adapter.BillDetailOrderAdapter;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.AwardsDetailBean;
import com.horen.partner.bean.BillDetailBean;
import com.horen.partner.bean.BillListBean;

import java.util.ArrayList;
import java.util.List;

public class AwardsDetailActivity extends BaseActivity {

    private TextView mTvTotalMoney;
    private TextView tv_company_name;
    private RecyclerView recyclerview_awards_order;
    private AwardsDetailAdapter awardsDetailAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_awards_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("突破奖");
        getTitleBar().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mTvTotalMoney = (TextView) findViewById(R.id.tv_total_money);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        recyclerview_awards_order = (RecyclerView) findViewById(R.id.recyclerview_awards_order);
        initRecyclerView();
        getIntentData();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.list_divider_10dp));
        recyclerview_awards_order.addItemDecoration(dividerItemDecoration);
        recyclerview_awards_order.setLayoutManager(layoutManager);
        recyclerview_awards_order.setHasFixedSize(true);
        recyclerview_awards_order.setNestedScrollingEnabled(false);
        awardsDetailAdapter = new AwardsDetailAdapter(R.layout.partner_awards_item_product, new ArrayList<AwardsDetailBean>());
        recyclerview_awards_order.setAdapter(awardsDetailAdapter);

    }

    private void getServerData(String companyId, String month) {
        ApiPartner.getInstance().getAwardsDetail(ApiRequest.getAwardsDetail(companyId, month)).compose(RxHelper.<List<AwardsDetailBean>>getResult()).subscribeWith(new BaseObserver<List<AwardsDetailBean>>(this, true) {
            @Override
            protected void onSuccess(List<AwardsDetailBean> awardsDetailBeans) {
                awardsDetailAdapter.setNewData(awardsDetailBeans);
            }

            @Override
            protected void onError(String message) {

            }
        });
    }

    private void getIntentData() {
        String month = getIntent().getStringExtra("month");
        BillListBean.AwardsBean info = (BillListBean.AwardsBean) getIntent().getSerializableExtra("awardsInfo");
        if (info != null) {
            mTvTotalMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(info.getAmount()));
            tv_company_name.setText(info.getCompany_name());
            String companyId = info.getCompany_id();
            getServerData(companyId, month);
        }
    }
}
