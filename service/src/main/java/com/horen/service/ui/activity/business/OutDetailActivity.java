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
import android.widget.ScrollView;
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
import com.horen.service.ui.activity.adapter.OutBoundGoodsAdapter;
import com.horen.service.ui.activity.adapter.PhotoPickerAdapter;
import com.zhihu.matisse.Matisse;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/15:31
 * @description :出库待处理
 * @github :https://github.com/chenyy0708
 */
public class OutDetailActivity extends BaseActivity<BusinessPresenter, BusinessModel> implements View.OnClickListener, PhotoPickerAdapter.OnPickerListener, BusinessContract.View {
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
    private TextView tvAddressNameTop;
    private TextView tvAddressCompanyTop;
    private TextView tvAddressMobileTop;
    private TextView tvAddressLocationTop;
    private TextView tvAddressNameBottom;
    private TextView tvAddressCompanyBottom;
    private TextView tvAddressMobileBottom;
    private TextView tvAddressLocationBottom;
    private TextView tvDistributionOrder;
    private EditText petRemake;
    private ImageView ivEditRemake;
    private SuperButton sBtConfirmUpload;
    private RecyclerView rvPhotoPicker;
    private PhotoPickerAdapter showImageAdapter;
    private RecyclerView rvOrderGoods;
    private OutBoundGoodsAdapter goodsAdapter;
    private TextView tvNeedTime;
    private TextView tvRemainingTime;
    private ScrollView scrollView;
    private String order_type;
    private String orderallot_id;

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_out_detail;
    }

    public static void startActivity(Context context, String order_type, String orderallot_id) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_TYPE, order_type);
        intent.putExtra(ORDERALLOT_ID, orderallot_id);
        intent.setClass(context, OutDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected boolean isCheckNetWork() {
        return true;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        // 使用BaseActivity Toolbar
        showWhiteTitle(getString(R.string.service_out_library));
        tvAddressNameTop = findViewById(R.id.tv_address_name_top);
        tvAddressCompanyTop = findViewById(R.id.tv_address_company_top);
        tvAddressMobileTop = findViewById(R.id.tv_address_mobile_top);
        tvAddressLocationTop = findViewById(R.id.tv_address_location_top);
        tvAddressNameBottom = findViewById(R.id.tv_address_name_bottom);
        tvAddressCompanyBottom = findViewById(R.id.tv_address_company_bottom);
        tvAddressMobileBottom = findViewById(R.id.tv_address_mobile_bottom);
        tvAddressLocationBottom = findViewById(R.id.tv_address_location_bottom);
        tvDistributionOrder = findViewById(R.id.tv_distribution_order);
        tvNeedTime = findViewById(R.id.tv_need_time);
        tvRemainingTime = findViewById(R.id.tv_remaining_time);
        petRemake = findViewById(R.id.pet_remake);
        ivEditRemake = findViewById(R.id.iv_edit_remake);
        sBtConfirmUpload = findViewById(R.id.sbt_confirm_upload);
        rvPhotoPicker = findViewById(R.id.rv_photo_picker);
        rvOrderGoods = findViewById(R.id.rv_order_goods);
        scrollView = findViewById(R.id.scrollView);
        ivEditRemake.setOnClickListener(this);
        sBtConfirmUpload.setOnClickListener(this);
        order_type = getIntent().getStringExtra(ORDER_TYPE);
        orderallot_id = getIntent().getStringExtra(ORDERALLOT_ID);
        initRecyclerView();
        // 请求订单详情数据
        mPresenter.getAllotAndTransInfo(order_type, orderallot_id, OrderStatus.PENDING.getStatus());
    }

    /**
     * 初始化RecycleView
     */
    private void initRecyclerView() {
        rvPhotoPicker.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvOrderGoods.setLayoutManager(new LinearLayoutManager(mContext));
        showImageAdapter = new PhotoPickerAdapter(mContext);
        // 图片点击事件
        showImageAdapter.setOnPickerListener(this);
        goodsAdapter = new OutBoundGoodsAdapter(R.layout.service_item_out_goods);
        rvPhotoPicker.setAdapter(showImageAdapter);
        rvOrderGoods.setAdapter(goodsAdapter);
        rvOrderGoods.setNestedScrollingEnabled(false);
        rvPhotoPicker.setNestedScrollingEnabled(false);
        // 触摸监听
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mPresenter.inputClose(scrollView, mContext);
                return OutDetailActivity.this.onTouchEvent(motionEvent);
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
                                petRemake.setEnabled(false);
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_edit_remake) {
            // 使EditText可编辑
            petRemake.setEnabled(true);
            mPresenter.showSoftInputFromWindow(this, petRemake);
        } else if (view.getId() == R.id.sbt_confirm_upload) {
            if (CollectionUtils.isNullOrEmpty(showImageAdapter.getmImageUrlList())) {
                onError("请添加图片");
                return;
            }
            // 保证出库量一定不能全为0
            boolean isAllZero = true;
            List<StorageSubmitBean> mData = new ArrayList<>();
            // 确认上传
            for (OrderAllotInfoBean.ProListBean proListBean : goodsAdapter.getData()) {
                // 出库量不为0
                if (proListBean.getDistribution_qty() != 0) {
                    isAllZero = false;
                }
                mData.add(new StorageSubmitBean(String.valueOf(proListBean.getDistribution_qty()), proListBean.getProduct_id(), ""));
            }
            // 提示必须填写出库量
            if (isAllZero) {
                showToast("出库量不能为空");
                return;
            }
            // 提交任务单
            mPresenter.submit(order_type, orderallot_id, showImageAdapter.getmImageUrlList(), petRemake.getText().toString().trim(), mData);
        }
    }

    /**
     * 获取任务单详情成功
     *
     * @param orderDetail 订单详情信息
     */
    @Override
    public void getInfoSuccess(OrderDetail orderDetail) {
        // 初始化头部物流地址数据
        initTopAddress(orderDetail.getOrderAllotInfo());
        // 订单信息
        initOrderInfo(orderDetail.getOrderAllotInfo());
        // 订单物品
        goodsAdapter.setNewData(orderDetail.getOrderAllotInfo().getProList());
    }

    /**
     * 提交成功
     */
    @Override
    public void submitSuccess() {
        // 提交成功
        finish();
        // 发送通知，刷新订单数据
        EventBus.getDefault().post(EventBusCode.REFRESH_ORDER, EventBusCode.REFRESH_ORDER);
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
        // 要货时间
        tvNeedTime.setText(infoBean.getExp_time());
        // 超期红色提醒
        if (Integer.valueOf(infoBean.getTime_difference()) >= 0) {
            tvRemainingTime.setText(infoBean.getTime_difference() + mContext.getString(R.string.service_day));
        } else { //  剩余时间小于0，显示超期时间，标红
            tvRemainingTime.setText("超期" + Math.abs(Integer.valueOf(infoBean.getTime_difference())) + mContext.getString(R.string.service_day));
        }
    }

    /**
     * 选择图片
     */
    @Override
    public void onPicker(int position) {
        PhotoPickerHelper.start(this, PhotoPickerAdapter.MAX_NUMBER - showImageAdapter.getmImageUrlList().size(), PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 从相册选择图片
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> photoPaths = Matisse.obtainPathResult(data);
            showImageAdapter.addData(photoPaths);
        }
    }

    @Override
    protected void reLoadData() {
        mPresenter.getAllotAndTransInfo(order_type, orderallot_id,OrderStatus.PENDING.getStatus());
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
//        showServiceError();
    }
}
