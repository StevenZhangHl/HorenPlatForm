package com.horen.service.ui.activity.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.EventBusCode;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.SpanUtils;
import com.horen.base.widget.HRToolbar;
import com.horen.service.R;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.mvp.contract.RepairDealWithContract;
import com.horen.service.mvp.model.RepairDealWithModel;
import com.horen.service.mvp.presenter.RepairDealWithPresenter;
import com.horen.service.ui.activity.adapter.PhotoPreviewAdapter;
import com.horen.service.ui.activity.adapter.SuppliesAdapter;
import com.horen.service.utils.OrderUtils;
import com.horen.service.utils.RepairUtils;

import org.simple.eventbus.EventBus;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/13:47
 * @description :维修处理
 * @github :https://github.com/chenyy0708
 */
public class RepairDealWithActivity extends BaseActivity<RepairDealWithPresenter, RepairDealWithModel> implements View.OnClickListener, RepairDealWithContract.View {

    private HRToolbar mToolBar;
    private NestedScrollView mScrollView;
    private TextView mTvOrderNumber;
    private TextView mTvBoxNumber;
    private TextView mTvResponsibleParty;
    private TextView mTvReportDate;
    private RecyclerView mRvPhoto;
    private RecyclerView mRvSupplies;
    private EditText mPetRemake;
    private ImageView mIvEditRemake;
    private SuperButton mSbtComplete;
    private TextView mTvServiceRemake;
    private String service_id;
    private TextView mTvDamageLocation;
    private TextView mTvName;
    private LinearLayout mLlNormalLocation;
    private TextView mTvNormalLocation;
    private LinearLayout mLlRefuseLocation;
    private TextView mTvRefuseLocation;


    public static void startActivity(Context context, String service_id) {
        Intent intent = new Intent();
        intent.putExtra("service_id", service_id);
        intent.setClass(context, RepairDealWithActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_repair_deal_with;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRToolbar) findViewById(R.id.tool_bar);
        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        mTvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        mTvBoxNumber = (TextView) findViewById(R.id.tv_box_number);
        mTvDamageLocation = (TextView) findViewById(R.id.tv_damage_location);
        mTvServiceRemake = (TextView) findViewById(R.id.tv_service_remake);
        mTvResponsibleParty = (TextView) findViewById(R.id.tv_responsible_party);
        mTvReportDate = (TextView) findViewById(R.id.tv_report_date);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mRvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        mRvSupplies = (RecyclerView) findViewById(R.id.rv_supplies);
        mPetRemake = (EditText) findViewById(R.id.pet_remake);
        mIvEditRemake = (ImageView) findViewById(R.id.iv_edit_remake);
        mLlNormalLocation = (LinearLayout) findViewById(R.id.ll_normal_location);
        mTvNormalLocation = (TextView) findViewById(R.id.tv_normal_location);
        mLlRefuseLocation = (LinearLayout) findViewById(R.id.ll_refuse_location);
        mTvRefuseLocation = (TextView) findViewById(R.id.tv_refuse_location);
        mIvEditRemake.setOnClickListener(this);
        mSbtComplete = (SuperButton) findViewById(R.id.sbt_complete);
        mSbtComplete.setEnabled(true);
        mSbtComplete.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        mSbtComplete.setOnClickListener(this);
        initToolbar(mToolBar.getToolbar(), true, R.color.white);
        initRecyclerView();
        mTvServiceRemake.setText(new SpanUtils().append(getString(R.string.service_service_remake)).setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333))
                .append(" 是的服务而非我而非我而非我而非我发").setForegroundColor(ContextCompat.getColor(mContext, R.color.color_666)).create());
        service_id = getIntent().getStringExtra("service_id");
        // 获取详情数据
        mPresenter.getRepairDetail(service_id);
    }

    private void initRecyclerView() {
        mRvPhoto.setNestedScrollingEnabled(false);
        mRvSupplies.setNestedScrollingEnabled(false);
        mRvSupplies.setLayoutManager(new LinearLayoutManager(mContext));
        mRvPhoto.setLayoutManager(new GridLayoutManager(mContext, 4));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_edit_remake) {
            // 使EditText可编辑
            showSoftInputFromWindow(this, mPetRemake);
        } else if (view.getId() == R.id.sbt_complete) {
            mPresenter.repairComplete(service_id, mPetRemake.getText().toString().trim());
        }
    }

    private void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.findFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 显示输入法
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        // 光标设置在最后
        editText.setSelection(editText.getText().toString().length());
    }

    /**
     * 获取数据详情
     */
    @Override
    public void getRepairDetailSuccess(RepairDetailBean detailBean) {
        if (CollectionUtils.isNullOrEmpty(detailBean.getServiceList())) {
            return;
        }
        RepairDetailBean.ServiceListBean serviceListBean = detailBean.getServiceList().get(0);
        // 服务单号
        mTvOrderNumber.setText(serviceListBean.getService_id());
        // 箱号
        mTvBoxNumber.setText(serviceListBean.getCtnr_sn());
        // 名称
        mTvName.setText(serviceListBean.getProduct_name());
        // 责任方 责任人：0：仓储 1：服务商
        mTvResponsibleParty.setText(OrderUtils.checkPerson(serviceListBean.getIs_person()));
        // 报备日期
        mTvReportDate.setText(serviceListBean.getCreate_time());
        // 备注
        mPetRemake.setText(serviceListBean.getRemark());
        // 损坏位置
        RepairUtils.setLocation(mLlNormalLocation, mTvNormalLocation, mLlRefuseLocation, mTvRefuseLocation, serviceListBean.getPositionList());
        // 图片集合
        if (!CollectionUtils.isNullOrEmpty(serviceListBean.getPositionList()))
            mRvPhoto.setAdapter(new PhotoPreviewAdapter(R.layout.service_item_photo_preview, serviceListBean.getPositionList().get(0).getImgList()));
        // 耗材列表
        mRvSupplies.setAdapter(new SuppliesAdapter(R.layout.service_item_reapir_info_supplies, serviceListBean.getConsumablesList()));
    }

    /**
     * 提交成功
     */
    @Override
    public void repairCompleteSuccess() {
        finish();
        // 通知页面刷新数据
        EventBus.getDefault().post(EventBusCode.REFRESH_REPAIR_LIST, EventBusCode.REFRESH_REPAIR_LIST);
        // 通知待维修刷新
        EventBus.getDefault().post(EventBusCode.REFRESH_REPAIR_FRAGMENT_LIST, EventBusCode.REFRESH_REPAIR_FRAGMENT_LIST);
    }
}
