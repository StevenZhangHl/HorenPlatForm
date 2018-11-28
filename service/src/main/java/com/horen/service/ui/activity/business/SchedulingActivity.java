package com.horen.service.ui.activity.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.EventBusCode;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.PhotoPickerHelper;
import com.horen.service.R;
import com.horen.service.bean.OrderAllotInfoBean;
import com.horen.service.bean.OrderDetail;
import com.horen.service.bean.StorageSubmitBean;
import com.horen.service.enumeration.business.OrderStatus;
import com.horen.service.mvp.contract.BusinessContract;
import com.horen.service.mvp.model.BusinessModel;
import com.horen.service.mvp.presenter.BusinessPresenter;
import com.horen.service.ui.activity.adapter.PhotoPickerAdapter;
import com.horen.service.ui.activity.adapter.SchedulingAdapter;
import com.horen.service.utils.OrderUtils;
import com.zhihu.matisse.Matisse;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/15:31
 * @description :平台调度待处理
 * @github :https://github.com/chenyy0708
 */
public class SchedulingActivity extends BaseActivity<BusinessPresenter, BusinessModel> implements View.OnClickListener, PhotoPickerAdapter.OnPickerListener, BusinessContract.View {


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

    private NestedScrollView scrollView;
    private SuperButton sbtOrderType;
    private TextView tvOrderNumber;
    private TextView tvOrderTime;
    private RecyclerView rvOrderChild;
    private EditText petRemake;
    private ImageView ivEditRemake;
    private RecyclerView rvPhotoPicker;
    private SuperButton sbtConfirmUpload;
    private PhotoPickerAdapter showImageAdapter;
    private String order_type;
    private String orderallot_id;
    private SchedulingAdapter schedulingAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_platform_scheduling;
    }

    public static void startActivity(Context context, String order_type, String orderallot_id) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_TYPE, order_type);
        intent.putExtra(ORDERALLOT_ID, orderallot_id);
        intent.setClass(context, SchedulingActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle(getString(R.string.service_platform_scheduling));
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        sbtOrderType = (SuperButton) findViewById(R.id.sbt_order_type);
        tvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        tvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        rvOrderChild = (RecyclerView) findViewById(R.id.rv_order_child);
        petRemake = (EditText) findViewById(R.id.pet_remake);
        ivEditRemake = (ImageView) findViewById(R.id.iv_edit_remake);
        rvPhotoPicker = (RecyclerView) findViewById(R.id.rv_photo_picker);
        sbtConfirmUpload = (SuperButton) findViewById(R.id.sbt_confirm_upload);
        ivEditRemake.setOnClickListener(this);
        sbtConfirmUpload.setOnClickListener(this);
        initRecyclerView();
        order_type = getIntent().getStringExtra(ORDER_TYPE);
        orderallot_id = getIntent().getStringExtra(ORDERALLOT_ID);
        mPresenter.getAllotAndTransInfo(order_type, orderallot_id, OrderStatus.PENDING.getStatus());
    }

    private void initRecyclerView() {
        // 选择图片
        rvPhotoPicker.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        showImageAdapter = new PhotoPickerAdapter(mContext);
        showImageAdapter.setOnPickerListener(this);
        rvPhotoPicker.setAdapter(showImageAdapter);
        rvPhotoPicker.setNestedScrollingEnabled(false);
        // 订单列表
        rvOrderChild.setNestedScrollingEnabled(false);
        rvOrderChild.setLayoutManager(new LinearLayoutManager(mContext));
        schedulingAdapter = new SchedulingAdapter(R.layout.service_item_order_list);
        rvOrderChild.setAdapter(schedulingAdapter);
        // 触摸监听
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mPresenter.inputClose(scrollView, mContext);
                return SchedulingActivity.this.onTouchEvent(motionEvent);
            }
        });
        // 监听软键盘关闭打开
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // 软键盘关闭
                        if (!isOpen) {
                            // 当软键盘关闭，设置Edittext不可操作
                            if (petRemake != null) {
                                // 显示输入框最前面的文字
                                petRemake.setSelection(0);
                                petRemake.setEnabled(false);
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_edit_remake) {
            petRemake.setEnabled(true);
            mPresenter.showSoftInputFromWindow(this, petRemake);
        } else if (view.getId() == R.id.sbt_confirm_upload) {
            if (CollectionUtils.isNullOrEmpty(showImageAdapter.getmImageUrlList())) {
                onError("请添加图片");
                return;
            }
            List<StorageSubmitBean> mData = new ArrayList<>();
            // 确认上传
            for (OrderAllotInfoBean.ProListBean proListBean : schedulingAdapter.getData()) {
                // 实际回收量、产品id、维修量
                mData.add(new StorageSubmitBean(String.valueOf(proListBean.getOrder_qty()), proListBean.getProduct_id(), ""));
            }
            // 提交任务单
            mPresenter.submit(order_type, orderallot_id, showImageAdapter.getmImageUrlList(), petRemake.getText().toString().trim(), mData);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> photoPaths = Matisse.obtainPathResult(data);
            showImageAdapter.addData(photoPaths);
        }
    }

    @Override
    public void onPicker(int position) {
        PhotoPickerHelper.start(this, PhotoPickerAdapter.MAX_NUMBER - showImageAdapter.getmImageUrlList().size(), PHOTO_REQUEST_CODE);
    }

    @Override
    public void getInfoSuccess(OrderDetail orderDetail) {
        order_type = orderDetail.getOrderAllotInfo().getOrder_type();
        // 调拨单类型
        sbtOrderType.setText(String.format(getString(R.string.service_scheduling_type), OrderUtils.checkSchedulingStatus(orderDetail.getOrderAllotInfo().getOrder_type())));
        // 调度单号
        tvOrderNumber.setText(String.format(getString(R.string.service_scheduling_order_number), orderDetail.getOrderAllotInfo().getOrderallot_id()));
        // 时间
        tvOrderTime.setText(String.format(getString(R.string.service_order_time), orderDetail.getOrderAllotInfo().getAudit_time()));
        // 物品列表
        schedulingAdapter.setNewData(orderDetail.getOrderAllotInfo().getProList());
    }

    @Override
    public void submitSuccess() {
        finish();
        // 发送通知，刷新订单数据
        EventBus.getDefault().post(EventBusCode.REFRESH_ORDER, EventBusCode.REFRESH_ORDER);
    }
}
