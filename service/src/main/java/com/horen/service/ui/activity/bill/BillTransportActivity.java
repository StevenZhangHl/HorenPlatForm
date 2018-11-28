package com.horen.service.ui.activity.bill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.NumberUtil;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.BillMainBean;
import com.horen.service.bean.BillTransportBean;
import com.horen.service.ui.activity.adapter.BillTransportAdapter;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/09/13/15:14
 * @description :运费详情
 * @github :https://github.com/chenyy0708
 */
public class BillTransportActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private TextView mTvMoneyTitle;
    private TextView mTvMoney;
    private TextView mTvTotalAmount;
    private TextView mTvPaidTotalAmount;
    private RecyclerView mRecyclerView;
    private BillMainBean.BillListBean bean;
    private BillTransportAdapter transportAdapter;

    public static void startActivity(Context context, BillMainBean.BillListBean bean) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        intent.setClass(context, BillTransportActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_bill_transport;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvMoneyTitle = (TextView) findViewById(R.id.tv_money_title);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvTotalAmount = (TextView) findViewById(R.id.tv_total_amount);
        mTvPaidTotalAmount = (TextView) findViewById(R.id.tv_paid_total_amount);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        bean = (BillMainBean.BillListBean) getIntent().getSerializableExtra("bean");
        showWhiteTitle("运输总费", R.color.white);
        getTitleBar().setBackgroundResource(R.color.white);
        initHeader();
        initRecyclerView();
        // 派单号
        getFreightBillList();
    }

    private void getFreightBillList() {
        mRxManager.add(Api.getInstance().getFreightBillList(ServiceParams.getBillDetail(bean.getCost_type(), bean.getRelative_month()))
                .compose(RxHelper.<BillTransportBean>getResult())
                .subscribeWith(new BaseObserver<BillTransportBean>(mContext, true) {
                    @Override
                    protected void onSuccess(BillTransportBean billTransportBean) {
                        // 派单列表
                        transportAdapter.setNewData(billTransportBean.getPageInfo().getList());
                    }

                    @Override
                    protected void onError(String message) {

                    }
                }));
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        transportAdapter = new BillTransportAdapter(new ArrayList<BillTransportBean.PageInfoBean.ListBean>());
        mRecyclerView.setAdapter(transportAdapter);
        transportAdapter.setOnItemClickListener(this);
    }

    /**
     * 金额数据设置
     */
    private void initHeader() {
        mTvMoneyTitle.setText("未收总金额");
        // 未付金额
        mTvMoney.setText(NumberUtil.formitNumber(Double.valueOf(bean.getDiff_amt())));
        // 总金额
        mTvTotalAmount.setText("¥" + NumberUtil.formitNumber(Double.valueOf(bean.getTotal_ar())));
        // 已付金额
        mTvPaidTotalAmount.setText("¥" + NumberUtil.formitNumber(Double.valueOf(bean.getConfirm_amt())));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BillTransportDetailActivity.startActivity(mContext, transportAdapter.getData().get(position));
    }
}
