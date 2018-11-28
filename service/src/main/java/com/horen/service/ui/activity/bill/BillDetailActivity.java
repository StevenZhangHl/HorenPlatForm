package com.horen.service.ui.activity.bill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.horen.base.base.BaseActivity;
import com.horen.base.util.NumberUtil;
import com.horen.base.widget.HRTabView;
import com.horen.service.R;
import com.horen.service.bean.BillDetailBean;
import com.horen.service.bean.BillMainBean;
import com.horen.service.mvp.contract.BillDetailContract;
import com.horen.service.mvp.model.BillDetailModel;
import com.horen.service.mvp.presenter.BillDetailPresenter;
import com.horen.service.ui.activity.adapter.BillDateAdapter;
import com.horen.service.ui.activity.adapter.BillSuppliesPdAdapter;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/09/03/13:56
 * @description :账单详情
 * @github :https://github.com/chenyy0708
 */
public class BillDetailActivity extends BaseActivity<BillDetailPresenter, BillDetailModel> implements BillDetailContract.View {

    private TextView mTvMoneyTitle;
    private TextView mTvMoney;
    private TextView mTvTotalAmount;
    private TextView mTvPaidTotalAmount;
    private TextView mTvOrderName;
    private TextView mTvOrderNumber;
    private HRTabView mTabView;
    private RecyclerView mRvProduct;
    private RecyclerView mRvMoney;
    private BaseQuickAdapter productAdapter;
    private BillDateAdapter dateAdapter;


    private LinearLayout mLlAddress;
    private TextView mTvStartAddress;
    private TextView mTvEndAddress;
    private View mViewStorageTime;
    private BillMainBean.BillListBean bean;


    public static void startActivity(Context context, BillMainBean.BillListBean bean) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        intent.setClass(context, BillDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_bill_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvMoneyTitle = (TextView) findViewById(R.id.tv_money_title);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvTotalAmount = (TextView) findViewById(R.id.tv_total_amount);
        mTvPaidTotalAmount = (TextView) findViewById(R.id.tv_paid_total_amount);
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
        bean = (BillMainBean.BillListBean) getIntent().getSerializableExtra("bean");
        // 根据类型显示标题
        switch (bean.getCost_type()) {
            case "3": // 耗材服务费
                showWhiteTitle("耗材服务总费", R.color.white);
                break;
            case "2": // 产品服务费
                showWhiteTitle("产品服务总费", R.color.white);
                break;
            case "4": // 维修费
                showWhiteTitle("维修总费", R.color.white);
                break;
            default:
                break;
        }
        initRecyclerView();
        // 获取数据
        mPresenter.getBillDetail(bean.getCost_type(), bean.getRelative_month());
        // 设置金额
        initHeader();
    }

    /**
     * 金额数据设置
     */
    private void initHeader() {
        // 未付金额
        mTvMoney.setText(NumberUtil.formitNumber(Double.valueOf(bean.getDiff_amt())));
        // 总金额
        mTvTotalAmount.setText("¥" + NumberUtil.formitNumber(Double.valueOf(bean.getTotal_ar())));
        // 已付金额
        mTvPaidTotalAmount.setText("¥" + NumberUtil.formitNumber(Double.valueOf(bean.getConfirm_amt())));
    }

    private void initRecyclerView() {
        mRvMoney.setNestedScrollingEnabled(false);
        mRvProduct.setNestedScrollingEnabled(false);
        mRvMoney.setLayoutManager(new LinearLayoutManager(mContext));
        mRvProduct.setLayoutManager(new LinearLayoutManager(mContext));
        dateAdapter = new BillDateAdapter(new ArrayList<BillDetailBean.BillLogListBean>());
        mRvMoney.setAdapter(dateAdapter);
    }

    /**
     * 耗材服务费
     */
    @Override
    public void setSuppliesCharge(BillDetailBean mData) {
        productAdapter = new BillSuppliesPdAdapter(mData.getBillList());
        mRvProduct.setAdapter(productAdapter);
        dateAdapter.setNewData(mData.getBillLogList());
    }

    /**
     * 维修费
     *
     * @param mData
     */
    @Override
    public void setRepairCharge(BillDetailBean mData) {
        // 应收总金额
        mTvMoneyTitle.setText("应收总金额");
        // 产品列表
        mTabView.setTabText("日期", "产品名称", "型号", "数量");
        mViewStorageTime.setVisibility(View.VISIBLE);
        productAdapter = new BillSuppliesPdAdapter(mData.getBillList());
        dateAdapter.setNewData(mData.getBillLogList());
        mRvProduct.setAdapter(productAdapter);
    }
}
