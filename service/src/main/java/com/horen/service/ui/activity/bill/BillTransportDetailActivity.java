package com.horen.service.ui.activity.bill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.NumberUtil;
import com.horen.base.widget.HRTabView;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.BillTransportBean;
import com.horen.service.bean.BillTransportDetail;
import com.horen.service.ui.activity.adapter.BilTransportlDateAdapter;
import com.horen.service.ui.activity.adapter.BillTransportPdAdapter;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/09/13/15:37
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillTransportDetailActivity extends BaseActivity {

    private TextView mTvMoneyTitle;
    private TextView mTvMoney;
    private TextView mTvTotalAmount;
    private TextView mTvPaidTotalAmount;
    private TextView mTvOrderName;
    private TextView mTvOrderNumber;
    private HRTabView mTabView;
    private RecyclerView mRvProduct;
    private RecyclerView mRvMoney;
    private BillTransportPdAdapter productAdapter;
    private BilTransportlDateAdapter dateAdapter;
    private LinearLayout mLlCompleteDate;
    private TextView mTvCompleteDate;

    private LinearLayout mLlAddress;
    private TextView mTvStartAddress;
    private TextView mTvEndAddress;
    private View mViewStorageTime;
    private BillTransportBean.PageInfoBean.ListBean bean;
    private TextView mTvPaidTotalName;


    public static void startActivity(Context context, BillTransportBean.PageInfoBean.ListBean bean) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        intent.setClass(context, BillTransportDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_bill_detail;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlCompleteDate = (LinearLayout) findViewById(R.id.ll_complete_date);
        mTvCompleteDate = (TextView) findViewById(R.id.tv_complete_date);
        mTvMoneyTitle = (TextView) findViewById(R.id.tv_money_title);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvTotalAmount = (TextView) findViewById(R.id.tv_total_amount);
        mTvPaidTotalAmount = (TextView) findViewById(R.id.tv_paid_total_amount);
        mTvPaidTotalName = (TextView) findViewById(R.id.tv_paid_total_name);
        mTvOrderName = (TextView) findViewById(R.id.tv_order_name);
        mTvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        mTabView = (HRTabView) findViewById(R.id.tab_view);
        mRvProduct = (RecyclerView) findViewById(R.id.rv_product);
        mRvMoney = (RecyclerView) findViewById(R.id.rv_money);
        mViewStorageTime = (View) findViewById(R.id.view_storage_time);
        mLlAddress = (LinearLayout) findViewById(R.id.ll_address);
        mTvStartAddress = (TextView) findViewById(R.id.tv_start_address);
        mTvEndAddress = (TextView) findViewById(R.id.tv_end_address);
        // 根据不同类型，显示不同的布局
        bean = (BillTransportBean.PageInfoBean.ListBean) getIntent().getSerializableExtra("bean");
        showWhiteTitle("运输费", R.color.white);
        initRecyclerView();
        // 获取数据
        getData();
        // 设置金额
        initHeader();
    }

    /**
     * 金额数据设置
     */
    private void initHeader() {
        mTvMoneyTitle.setText("未收金额");
        // 未付金额
        mTvMoney.setText(NumberUtil.formitNumber(Double.valueOf(bean.getDiff_amt())));
        // 总金额
        mTvTotalAmount.setText("¥" + NumberUtil.formitNumber(Double.valueOf(bean.getTotal_ar())));
        // 已付金额
        mTvPaidTotalAmount.setText("¥" + NumberUtil.formitNumber(Double.valueOf(bean.getConfirm_amt())));
        // 完成时间
        mLlCompleteDate.setVisibility(View.VISIBLE);
        // 派单单号
        mTvOrderNumber.setText(bean.getOrderallot_id());
        mTvCompleteDate.setText(bean.getCreate_time());
        mTvPaidTotalName.setText("已付金额");
    }

    private void initRecyclerView() {
        mRvMoney.setNestedScrollingEnabled(false);
        mRvProduct.setNestedScrollingEnabled(false);
        mRvMoney.setLayoutManager(new LinearLayoutManager(mContext));
        mRvProduct.setLayoutManager(new LinearLayoutManager(mContext));
        productAdapter = new BillTransportPdAdapter(new ArrayList<BillTransportDetail.FreightBillInfoBean.ProListBean>());
        mRvProduct.setAdapter(productAdapter);
        dateAdapter = new BilTransportlDateAdapter(new ArrayList<BillTransportDetail.BillLogListBean>());
        mRvMoney.setAdapter(dateAdapter);
    }

    private void getData() {
        mRxManager.add(Api.getInstance().getFreightBillInfo(ServiceParams.getFreightBillInfo(bean.getAccount_id()))
                .compose(RxHelper.<BillTransportDetail>getResult())
                .subscribeWith(new BaseObserver<BillTransportDetail>(mContext, true) {
                    @Override
                    protected void onSuccess(BillTransportDetail bean) {
                        findViewById(R.id.ll_order).setVisibility(View.VISIBLE);
                        findViewById(R.id.view_order).setVisibility(View.VISIBLE);
                        findViewById(R.id.view_tab).setVisibility(View.GONE);
                        mLlAddress.setVisibility(View.VISIBLE);
                        // 起点  终点
                        mTvStartAddress.setText(bean.getFreightBillInfo().getStart_address());
                        mTvEndAddress.setText(bean.getFreightBillInfo().getEnd_address());
                        // 产品列表
                        mTabView.setData(new String[]{"名称", "型号", "出库量"}, new String[]{"1", "1", "1"});
                        productAdapter.setNewData(bean.getFreightBillInfo().getProList());
                        dateAdapter.setNewData(bean.getBillLogList());
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }
}
