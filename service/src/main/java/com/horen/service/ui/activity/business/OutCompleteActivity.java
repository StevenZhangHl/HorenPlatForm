package com.horen.service.ui.activity.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.service.R;
import com.horen.service.bean.OrderAllotInfoBean;
import com.horen.service.bean.OrderDetail;
import com.horen.service.enumeration.business.OrderStatus;
import com.horen.service.mvp.contract.BusinessContract;
import com.horen.service.mvp.model.BusinessModel;
import com.horen.service.mvp.presenter.BusinessPresenter;
import com.horen.service.ui.activity.adapter.CompleteGoodsAdapter;
import com.horen.service.ui.activity.adapter.OutOrderDeliveryAdapter;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/13:02
 * @description :出库已完成
 * @github :https://github.com/chenyy0708
 */
public class OutCompleteActivity extends BaseActivity<BusinessPresenter, BusinessModel> implements BusinessContract.View {

    /**
     * 任务单类型
     */
    private static final String ORDER_TYPE = "order_type";
    /**
     * 任务单ID
     */
    private static final String ORDERALLOT_ID = "orderallot_id";

    private NestedScrollView scrollView;
    private TextView tvAddressNameTop;
    private TextView tvAddressCompanyTop;
    private TextView tvAddressMobileTop;
    private TextView tvAddressLocationTop;
    private TextView tvAddressNameBottom;
    private TextView tvAddressCompanyBottom;
    private TextView tvAddressMobileBottom;
    private TextView tvAddressLocationBottom;
    private TextView tvDistributionOrder;
    private TextView tvDistributionTime;
    private TextView tvCompleteDate;
    private RecyclerView rvOrderGoods;
    private RecyclerView rvOutOrder;
    private CompleteGoodsAdapter goodsAdapter;
    private String order_type;
    private String orderallot_id;
    private OutOrderDeliveryAdapter deliveryAdapter;


    public static void startActivity(Context context, String order_type, String orderallot_id) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_TYPE, order_type);
        intent.putExtra(ORDERALLOT_ID, orderallot_id);
        intent.setClass(context, OutCompleteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_out_complete_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle(getString(R.string.service_out_library));
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        tvAddressNameTop = (TextView) findViewById(R.id.tv_address_name_top);
        tvAddressCompanyTop = (TextView) findViewById(R.id.tv_address_company_top);
        tvAddressMobileTop = (TextView) findViewById(R.id.tv_address_mobile_top);
        tvAddressLocationTop = (TextView) findViewById(R.id.tv_address_location_top);
        tvAddressNameBottom = (TextView) findViewById(R.id.tv_address_name_bottom);
        tvAddressCompanyBottom = (TextView) findViewById(R.id.tv_address_company_bottom);
        tvAddressMobileBottom = (TextView) findViewById(R.id.tv_address_mobile_bottom);
        tvAddressLocationBottom = (TextView) findViewById(R.id.tv_address_location_bottom);
        tvDistributionOrder = (TextView) findViewById(R.id.tv_distribution_order);
        tvDistributionTime = (TextView) findViewById(R.id.tv_distribution_time);
        tvCompleteDate = (TextView) findViewById(R.id.tv_complete_date);
        rvOrderGoods = (RecyclerView) findViewById(R.id.rv_order_goods);
        rvOutOrder = (RecyclerView) findViewById(R.id.rv_out_order);
        order_type = getIntent().getStringExtra(ORDER_TYPE);
        orderallot_id = getIntent().getStringExtra(ORDERALLOT_ID);
        initRecyclerView();
        mPresenter.getAllotAndTransInfo(order_type, orderallot_id, OrderStatus.COMPLETE.getStatus());
    }

    private void initRecyclerView() {
        rvOrderGoods.setNestedScrollingEnabled(false);
        rvOutOrder.setNestedScrollingEnabled(false);
        rvOutOrder.setLayoutManager(new LinearLayoutManager(mContext));
        rvOrderGoods.setLayoutManager(new LinearLayoutManager(mContext));
        // 订单物品
        goodsAdapter = new CompleteGoodsAdapter(R.layout.service_item_out_complete_goods, order_type);
        rvOrderGoods.setAdapter(goodsAdapter);
        // 出库单号
        deliveryAdapter = new OutOrderDeliveryAdapter(R.layout.service_item_out_complete_order, order_type);
        rvOutOrder.setAdapter(deliveryAdapter);
    }

    @Override
    public void getInfoSuccess(OrderDetail orderDetail) {
        // 初始化头部物流地址数据
        initTopAddress(orderDetail.getOrderAllotInfo());
        // 订单信息
        initOrderInfo(orderDetail.getOrderAllotInfo());
        // 订单物品
        goodsAdapter.setNewData(orderDetail.getOrderAllotInfo().getProList());
        // 交割记录
        deliveryAdapter.setNewData(orderDetail.getStorageList());
    }

    @Override
    public void submitSuccess() {

    }

    /**
     * 初始化头部物流地址数据
     */
    private void initTopAddress(OrderAllotInfoBean infoBean) {
        // from联系人
        tvAddressNameTop.setText(String.format(mContext.getString(R.string.service_contact), infoBean.getStart_contact()));
        // from公司名
        tvAddressCompanyTop.setText(infoBean.getStart_orgname());
        // from联系电话
        tvAddressMobileTop.setText(infoBean.getStart_tel());
        // from地址
        tvAddressLocationTop.setText(infoBean.getStart_address());
        // to联系人
        tvAddressNameBottom.setText(String.format(mContext.getString(R.string.service_contact), infoBean.getEnd_contact()));
        // to公司名
        tvAddressCompanyBottom.setText(infoBean.getEnd_orgname());
        // to联系电话
        tvAddressMobileBottom.setText(infoBean.getEnd_tel());
        // to地址
        tvAddressLocationBottom.setText(infoBean.getEnd_address());
    }

    /**
     * 订单信息
     */
    private void initOrderInfo(OrderAllotInfoBean infoBean) {
        // 分配单号
        tvDistributionOrder.setText(infoBean.getOrderallot_id());
        // 分配时间
        tvDistributionTime.setText(infoBean.getAudit_time());
        // 完成日期
        tvCompleteDate.setText(infoBean.getEnd_time());
    }
}
