package com.horen.service.ui.activity.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.service.R;
import com.horen.service.bean.OrderDetail;
import com.horen.service.bean.StorageBean;
import com.horen.service.enumeration.business.OrderStatus;
import com.horen.service.mvp.contract.BusinessContract;
import com.horen.service.mvp.model.BusinessModel;
import com.horen.service.mvp.presenter.BusinessPresenter;
import com.horen.service.ui.activity.adapter.PhotoPreviewAdapter;
import com.horen.service.ui.activity.adapter.SchedulingCompleteAdapter;
import com.horen.service.utils.OrderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/15:31
 * @description :平台调度已完成
 * @github :https://github.com/chenyy0708
 */
public class SchedulingCompleteActivity extends BaseActivity<BusinessPresenter, BusinessModel> implements View.OnClickListener, BusinessContract.View {
    /**
     * 打开相册请求码
     */
    public static final int PHOTO_REQUEST_CODE = 100;
    /**
     * 任务单类型
     */
    private static final String ORDER_TYPE = "order_type";
    /**
     * 任务单ID
     */
    private static final String ORDERALLOT_ID = "orderallot_id";
    private TextView tvOrderType;
    private TextView tvCompleteDate;
    private TextView tvOrderNumber;
    private TextView tvOrderTime;
    private RecyclerView rvOrderChild;
    private TextView petRemake;

    private String order_type;
    private String orderallot_id;
    private SchedulingCompleteAdapter schedulingAdapter;
    private RecyclerView rvPhoto;

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_platform_scheduling_complete;
    }

    public static void startActivity(Context context, String order_type, String orderallot_id) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_TYPE, order_type);
        intent.putExtra(ORDERALLOT_ID, orderallot_id);
        intent.setClass(context, SchedulingCompleteActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle(getString(R.string.service_platform_scheduling));
        tvOrderType = (TextView) findViewById(R.id.tv_order_type);
        tvCompleteDate = (TextView) findViewById(R.id.tv_complete_date);
        tvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        tvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        rvOrderChild = (RecyclerView) findViewById(R.id.rv_order_child);
        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        petRemake = (TextView) findViewById(R.id.pet_remake);
        initRecyclerView();
        order_type = getIntent().getStringExtra(ORDER_TYPE);
        orderallot_id = getIntent().getStringExtra(ORDERALLOT_ID);
        mPresenter.getAllotAndTransInfo(order_type, orderallot_id, OrderStatus.COMPLETE.getStatus());
    }

    private void initRecyclerView() {
        // 订单列表
        rvOrderChild.setNestedScrollingEnabled(false);
        rvOrderChild.setLayoutManager(new LinearLayoutManager(mContext));
        schedulingAdapter = new SchedulingCompleteAdapter(R.layout.service_item_order_list);
        rvOrderChild.setAdapter(schedulingAdapter);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void getInfoSuccess(OrderDetail orderDetail) {
        // 调拨单类型
        tvOrderType.setText(String.format(getString(R.string.service_scheduling_type), OrderUtils.checkSchedulingStatus(orderDetail.getOrderAllotInfo().getOrder_type())));
        // 调度单号
        tvOrderNumber.setText(String.format(getString(R.string.service_scheduling_order_number), orderDetail.getOrderAllotInfo().getOrderallot_id()));
        // 完成时间
        if (TextUtils.isEmpty(orderDetail.getOrderAllotInfo().getEnd_time())) {
            tvCompleteDate.setVisibility(View.GONE);
        } else {
            tvCompleteDate.setText(String.format(getString(R.string.service_complete_date_tip), orderDetail.getOrderAllotInfo().getEnd_time()));
        }
        // 时间
        tvOrderTime.setText(String.format(getString(R.string.service_order_time), orderDetail.getOrderAllotInfo().getAudit_time()));
        // 物品列表
        StorageBean storageBean = orderDetail.getStorageList().get(0);
        schedulingAdapter.setNewData(storageBean.getProList());
        // 设置提示文字，防止EditText获取不到焦点
        petRemake.setText(storageBean.getRemark());
        // 图片集合
        List<String> imageUrls = new ArrayList<>();
        if (!TextUtils.isEmpty(storageBean.getImg1())) {
            imageUrls.add(storageBean.getImg1());
        }
        if (!TextUtils.isEmpty(storageBean.getImg2())) {
            imageUrls.add(storageBean.getImg2());
        }
        // 交割图片集合
        rvPhoto.setNestedScrollingEnabled(false);
        rvPhoto.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvPhoto.setAdapter(new PhotoPreviewAdapter(R.layout.service_item_photo_preview, imageUrls, PhotoPreviewAdapter.BUSINESS));
    }

    @Override
    public void submitSuccess() {

    }
}
