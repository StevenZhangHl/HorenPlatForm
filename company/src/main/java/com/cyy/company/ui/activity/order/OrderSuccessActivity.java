package com.cyy.company.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.cyy.company.R;
import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.bean.RenewalAddress;
import com.cyy.company.enums.OrderType;
import com.cyy.company.ui.activity.me.OrderListActivity;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseActivity;
import com.horen.base.widget.HRTitle;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/18:24
 * @description :订单提交成功
 * @github :https://github.com/chenyy0708
 */
public class OrderSuccessActivity extends BaseActivity implements View.OnClickListener {

    private HRTitle mToolBar;
    private TextView mOrderTitle;
    private TextView mTvOrderNumber;
    private SuperButton mStbSystemWarehouse;
    private TextView mTvOrderName;
    private TextView mTvOrderAddress;
    private TextView mTvOrderContract;
    private TextView mTvOrgPhone;
    private SuperButton mBtOrderLook;
    private SuperButton mBtOrderContinue;

    private String order_type;
    private String order_id;
    private DefaultOrgBean.PdListBean address;
    private RenewalAddress.PdListBean renewalAddress;

    private String mTitle;

    public static void startAction(Context context, String order_id, String order_type, DefaultOrgBean.PdListBean address, String mTitle) {
        Intent intent = new Intent();
        intent.putExtra("mTitle", mTitle);
        intent.putExtra("order_id", order_id);
        intent.putExtra("order_type", order_type);
        intent.putExtra("address", address);
        intent.setClass(context, OrderSuccessActivity.class);
        context.startActivity(intent);
    }

    public static void startAction(Context context, String order_id_rent, String order_id_return, String order_type, RenewalAddress.PdListBean renewalAddress) {
        Intent intent = new Intent();
        intent.putExtra("order_type", order_type);
        intent.putExtra("order_id_rent", order_id_rent);
        intent.putExtra("order_id_return", order_id_return);
        intent.putExtra("renewalAddress", renewalAddress);
        intent.setClass(context, OrderSuccessActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_success;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mOrderTitle = (TextView) findViewById(R.id.order_title);
        mTvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
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
        order_id = getIntent().getStringExtra("order_id");
        order_type = getIntent().getStringExtra("order_type");
        address = (DefaultOrgBean.PdListBean) getIntent().getSerializableExtra("address");
        renewalAddress = (RenewalAddress.PdListBean) getIntent().getSerializableExtra("renewalAddress");
        // 显示订单
        if (order_type.equals(OrderType.ONE.getPosition())) {
            mOrderTitle.setText("租箱订单提交成功");
            mBtOrderContinue.setText("继续租箱");
            mTvOrderNumber.setText(order_id);
        } else if (order_type.equals(OrderType.TWO.getPosition())) {
            mOrderTitle.setText("还箱订单提交成功");
            mBtOrderContinue.setText("继续还箱");
            mTvOrderNumber.setText(order_id);
        } else if (order_type.equals(OrderType.THREE.getPosition())) {
            mOrderTitle.setText("续租订单提交成功");
            mBtOrderContinue.setText("继续续租");
            mTvOrderNumber.setText(getIntent().getStringExtra("order_id_rent"));
            // 租箱订单号
            setText(R.id.tv_order_return, getIntent().getStringExtra("order_id_return"));
            setText(R.id.tv_order_rent_name, "租箱订单号");
            findViewById(R.id.ll_order_return).setVisibility(View.VISIBLE);
        }
        if (address != null) { // 租箱还箱订单
            mTvOrderName.setText(address.getOrg_name());
            mTvOrderAddress.setText(address.getOrg_address());
            mTvOrderContract.setText(address.getOrg_consignee());
            mTvOrgPhone.setText(address.getOrg_consigneetel());
        } else { // 续租订单
            mTvOrderName.setText(renewalAddress.getOrg_name1());
            mTvOrderAddress.setText(renewalAddress.getOrg_address1());
            mTvOrderContract.setText(renewalAddress.getOrg_consignee1());
            mTvOrgPhone.setText(renewalAddress.getOrg_consigneetel1());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_order_look) { // 查看订单
            if (order_type.equals(OrderType.ONE.getPosition())) { // 租箱
                OrderListActivity.startAction(mContext, OrderListActivity.ORDER_TYPE_RENT);
            } else if (order_type.equals(OrderType.TWO.getPosition())) { // 还箱
                OrderListActivity.startAction(mContext, OrderListActivity.ORDER_TYPE_RETURN);
            } else if (order_type.equals(OrderType.THREE.getPosition())) { // 续租
                OrderListActivity.startAction(mContext, OrderListActivity.ORDER_TYPE_RENT);
            }
            finish();
        } else if (view.getId() == R.id.bt_order_continue) { // 再来一单
            if (order_type.equals(OrderType.ONE.getPosition())) { // 租箱
                CreateOrderActivity.startAction(mContext, CreateOrderActivity.ORDER_TYPE_RENT);
            } else if (order_type.equals(OrderType.TWO.getPosition())) { // 还箱  还箱入口有两个需要区分
                CreateOrderActivity.startAction(mContext, CreateOrderActivity.ORDER_TYPE_RETURN, mTitle);
            } else if (order_type.equals(OrderType.THREE.getPosition())) { // 续租
                CreateOrderActivity.startAction(mContext, CreateOrderActivity.ORDER_TYPE_RENEWAL);
            }
            finish();
        }
    }
}
