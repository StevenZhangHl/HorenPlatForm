package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.BillDetailBean;
import com.cyy.company.bean.BillListBean;
import com.cyy.company.ui.adapter.BillManagerDetailAdapter;
import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.DividerItemDecoration;
import com.horen.base.util.NumberUtil;
import com.horen.base.widget.HRTitle;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/18/13:00
 * @description :账单管理详情
 * @github :https://github.com/chenyy0708
 */
public class BillManagerDetailActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener {

    private HRTitle mToolBar;
    private View mViewDiver;
    private TextView mTvBillId;
    private TextView mTvWriteMoney;
    private TextView mTvWriteTip;
    private TextView mTvStatus;
    private TextView mTvAllMoney;
    private RecyclerView mRecyclerView;
    private NestedScrollView mScrollView;
    private BillListBean.PdListBean.ListBean billBean;
    private BillManagerDetailAdapter managerDetailAdapter;

    public static void startAction(Context context, BillListBean.PdListBean.ListBean billBean) {
        Intent intent = new Intent();
        intent.putExtra("billBean", billBean);
        intent.setClass(context, BillManagerDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_manager_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mViewDiver = (View) findViewById(R.id.view_diver);
        mTvBillId = (TextView) findViewById(R.id.tv_bill_id);
        mTvWriteMoney = (TextView) findViewById(R.id.tv_write_money);
        mTvWriteTip = (TextView) findViewById(R.id.tv_write_tip);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mTvAllMoney = (TextView) findViewById(R.id.tv_all_money);
        mScrollView = (NestedScrollView) findViewById(R.id.nested_scrollview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mToolBar.bindActivity(this, R.color.white);
        initRecyclerView();
        mScrollView.setOnScrollChangeListener(this);
        billBean = (BillListBean.PdListBean.ListBean) getIntent().getSerializableExtra("billBean");
        // 账单号
        setText(R.id.tv_bill_id, billBean.getAccount_bill_id());
        setText(R.id.tv_write_money, String.valueOf("¥ " + NumberUtil.formitNumber(billBean.getConfirm_amt())));
        setText(R.id.tv_all_money, String.valueOf("¥ " + NumberUtil.formitNumber(billBean.getTotal_ar())));
        if (billBean.getAccount_type().equals("1")) { // 已结清
            setText(R.id.tv_status, "已结清");
            mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.base_text_color_light));
            mTvStatus.setBackgroundColor(Color.parseColor("#ffd8f1c2"));
        } else { // 未结清
            setText(R.id.tv_status, "未结清");
            mTvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_F15));
            mTvStatus.setBackgroundColor(Color.parseColor("#33f15b02"));
        }
        getData();
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext));
        managerDetailAdapter = new BillManagerDetailAdapter(new ArrayList<BillDetailBean.PdListBean.ListBean>());
        mRecyclerView.setAdapter(managerDetailAdapter);
    }

    private void getData() {
        mRxManager.add(ApiCompany.getInstance()
                .getCheckBillDetailsList(CompanyParams.getCheckBillDetailsList(billBean.getAccount_bill_id(), 1, 10))
                .compose(RxHelper.<BillDetailBean>getResult())
                .subscribeWith(new BaseObserver<BillDetailBean>() {
                    @Override
                    protected void onSuccess(BillDetailBean billDetailBean) {
                        managerDetailAdapter.setNewData(billDetailBean.getPdList().getList());
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == 0) {
            mViewDiver.setVisibility(View.GONE);
        } else {
            mViewDiver.setVisibility(View.VISIBLE);
        }
    }
}
