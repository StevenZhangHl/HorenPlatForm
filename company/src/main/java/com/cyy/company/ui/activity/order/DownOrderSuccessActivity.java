package com.cyy.company.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.cyy.company.R;
import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.ui.activity.eye.CreateReturnActivity;
import com.cyy.company.ui.activity.me.ReturnBoxListActivity;
import com.cyy.company.ui.adapter.DownOrderSubmitAdapter;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseActivity;
import com.horen.base.widget.HRTitle;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/18:24
 * @description :下游订单提交成功
 * @github :https://github.com/chenyy0708
 */
public class DownOrderSuccessActivity extends BaseActivity implements View.OnClickListener {

    private HRTitle mToolBar;
    private TextView mOrderTitle;
    private TextView mTvReturnIntegral;
    private RecyclerView mRecyclerView;
    private TextView mTvTip;
    private SuperButton mStbSystemWarehouse;
    private TextView mTvOrderName;
    private TextView mTvOrderAddress;
    private TextView mTvOrderContract;
    private TextView mTvOrgPhone;
    private SuperButton mBtOrderLook;
    private SuperButton mBtOrderContinue;

    private DefaultOrgBean.PdListBean address;
    private String mTitle;
    private ArrayList<String> orderId_list;

    public static void startAction(Context context, DefaultOrgBean.PdListBean address, String mTitle, int return_integral, ArrayList<String> orderId_list) {
        Intent intent = new Intent();
        intent.putExtra("mTitle", mTitle);
        intent.putExtra("address", address);
        intent.putExtra("return_integral", return_integral);
        intent.putStringArrayListExtra("orderId_list", orderId_list);
        intent.setClass(context, DownOrderSuccessActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_down_order_success;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        orderId_list = getIntent().getStringArrayListExtra("orderId_list");
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mOrderTitle = (TextView) findViewById(R.id.order_title);
        mTvReturnIntegral = (TextView) findViewById(R.id.tv_return_integral);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mStbSystemWarehouse = (SuperButton) findViewById(R.id.stb_system_warehouse);
        mTvOrderName = (TextView) findViewById(R.id.tv_order_name);
        mTvOrderAddress = (TextView) findViewById(R.id.tv_order_address);
        mTvOrderContract = (TextView) findViewById(R.id.tv_order_contract);
        mTvOrgPhone = (TextView) findViewById(R.id.tv_org_phone);
        mBtOrderLook = (SuperButton) findViewById(R.id.bt_order_look);
        mBtOrderContinue = (SuperButton) findViewById(R.id.bt_order_continue);
        mTitle = getIntent().getStringExtra("mTitle");
        mToolBar.bindActivity(this, R.color.white, TextUtils.isEmpty(mTitle) ? HRConstant.HUNDRED_KILOMETERS : mTitle);
        mBtOrderLook.setOnClickListener(this);
        mBtOrderContinue.setOnClickListener(this);
        address = (DefaultOrgBean.PdListBean) getIntent().getSerializableExtra("address");
        mOrderTitle.setText("还箱订单提交成功");
        mBtOrderContinue.setText("继续还箱");
        setText(R.id.tv_return_integral, "注: 订单处理完成即可获得" + getIntent().getIntExtra("return_integral", 0) + "还箱点");
        setText(R.id.tv_tip, "还箱有" + orderId_list.size() + "个上游企业,故拆分" + orderId_list.size() + "个还箱订单");
        if (address != null) { // 租箱还箱订单
            mTvOrderName.setText(address.getOrg_name());
            mTvOrderAddress.setText(address.getOrg_address());
            mTvOrderContract.setText(address.getOrg_consignee());
            mTvOrgPhone.setText(address.getOrg_consigneetel());
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(new DownOrderSubmitAdapter(orderId_list));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_order_look) { // 查看订单
            startActivity(ReturnBoxListActivity.class);
            finish();
        } else if (view.getId() == R.id.bt_order_continue) { // 再来一单
            CreateReturnActivity.startAction(mContext, mTitle);
            finish();
        }
    }
}
