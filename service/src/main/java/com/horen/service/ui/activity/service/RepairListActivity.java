package com.horen.service.ui.activity.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.allen.library.SuperButton;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.RadioSelectBean;
import com.horen.base.constant.EventBusCode;
import com.horen.base.listener.RadioSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.widget.HRToolbar;
import com.horen.base.widget.MessageDialog;
import com.horen.base.widget.RadioSelectDialog;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.bean.UpdatePerson;
import com.horen.service.ui.activity.adapter.RepairListAdapter;
import com.horen.service.utils.OrderUtils;
import com.horen.service.utils.RvEmptyUtils;
import com.horen.service.utils.ServiceIdUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/21/10:00
 * @description :报修列表
 * @github :https://github.com/chenyy0708
 */
public class RepairListActivity extends BaseActivity implements View.OnClickListener, RadioSelectListener, OnRefreshListener {

    private HRToolbar mToolBar;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private EditText mEtPersonLiable;
    private View mViewPersonLiable;
    private SuperButton mSbtComplete;

    private RepairListAdapter repairListAdapter;
    private RadioSelectDialog dialog;
    private String orderallot_id;

    public static void startActivity(Context context, String orderallot_id) {
        Intent intent = new Intent();
        intent.putExtra("orderallot_id", orderallot_id);
        intent.setClass(context, RepairListActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RepairListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_repair_list;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mToolBar = (HRToolbar) findViewById(R.id.tool_bar);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEtPersonLiable = (EditText) findViewById(R.id.et_damage_locaon);
        mViewPersonLiable = (View) findViewById(R.id.view_person_liable);
        mSbtComplete = (SuperButton) findViewById(R.id.sbt_complete);
        mSbtComplete.setText(R.string.service_repair_declaration);
        mViewPersonLiable.setOnClickListener(this);
        mSbtComplete.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        orderallot_id = getIntent().getStringExtra("orderallot_id");
        initToolbar(mToolBar.getToolbar(), true);
        mToolBar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RepairAddActivity.startActivity(mContext);
            }
        });
        initRecyclerView();
        initData(true);
    }

    /**
     * 获取报修列表
     */
    private void initData(boolean isShowDialog) {
        if (CollectionUtils.isNullOrEmpty(ServiceIdUtils.getServiceIdList())) {
            setEmptyView();
            mRefreshLayout.finishRefresh(0);
            return;
        }
        mRxManager.add(Api.getInstance()
                .getRepairList(ServiceParams.getRepairList(ServiceIdUtils.getServiceIdList()))
                .compose(RxHelper.<RepairDetailBean>getResult())
                .subscribeWith(new BaseObserver<RepairDetailBean>(mContext, isShowDialog) {
                    @Override
                    protected void onSuccess(RepairDetailBean detailBean) {
                        // 没有数据展示空View
                        if (CollectionUtils.isNullOrEmpty(detailBean.getServiceList())) {
                            setEmptyView();
                        } else {
                            repairListAdapter.setNewData(detailBean.getServiceList());
                        }
                        mRefreshLayout.finishRefresh(0);
                        checkSumitBt();
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        mRefreshLayout.finishRefresh(0);
                    }
                }));
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        repairListAdapter = new RepairListAdapter(R.layout.service_item_repair_list);
        mRecyclerView.setAdapter(repairListAdapter);
        // 初始化dialog
        dialog = new RadioSelectDialog(mContext, OrderUtils.getPerson(), getString(R.string.service_person_liable));
        dialog.setRadioSelectListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_person_liable) {
            dialog.show();
        } else if (view.getId() == R.id.sbt_complete) {
            // 维修申报
            updateServicePerson();
        }
    }

    @Override
    public void onSelected(RadioSelectBean bean,int position) {
        mEtPersonLiable.setText(bean.getTabName());
        checkSumitBt();
    }

    @Override
    public void onBackPressed() {
        if (!CollectionUtils.isNullOrEmpty(repairListAdapter.getData())) {
            new MessageDialog(mContext)
                    .showTitle("返回")
                    .showContent("是否保存当前编辑")
                    .setButtonTexts("不保存", "保存")
                    .setOnClickListene(new MessageDialog.OnClickListener() {
                        @Override
                        public void onLeftClick() {
                            // 不保存，清空服务id
                            ServiceIdUtils.clear();
                            finish();
                        }

                        @Override
                        public void onRightClick() {
                            // 保存 关闭页面
                            finish();
                        }
                    }).show();
        } else {
            finish();
        }
    }

    /**
     * 暂无数据
     */
    private void setEmptyView() {
        RvEmptyUtils.setEmptyView(repairListAdapter, mRecyclerView);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        initData(false);
    }

    /**
     * 刷新列表数据
     */
    @Subscriber(tag = EventBusCode.REFRESH_REPAIR_LIST)
    private void refreshList(String s) {
        mRefreshLayout.autoRefresh();
    }

    /**
     * 控制按钮显示
     */
    private void checkSumitBt() {
        if (!CollectionUtils.isNullOrEmpty(repairListAdapter.getData())) {
            mSbtComplete.setEnabled(true);
            mSbtComplete.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            mSbtComplete.setEnabled(false);
            mSbtComplete.setTextColor(ContextCompat.getColor(mContext, R.color.color_999));
        }
    }

    /**
     * 维修申报
     */
    private void updateServicePerson() {
        // 责任人0仓储，1服务商
        String isPerson = OrderUtils.getPerson(mEtPersonLiable.getText().toString().trim());
        // 维修人上传信息
        List<UpdatePerson> updatePeople = new ArrayList<>();
        for (RepairDetailBean.ServiceListBean serviceListBean : repairListAdapter.getData()) {
            updatePeople.add(new UpdatePerson(serviceListBean.getProduct_id(), serviceListBean.getService_id()));
        }
        // 提交
        mRxManager.add(Api.getInstance().updateServicePerson(ServiceParams.updateServicePerson(orderallot_id, updatePeople))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>(mContext, true) {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        // 清空选择信息
                        setEmptyView();
                        // 初始化dialog
                        dialog = new RadioSelectDialog(mContext, OrderUtils.getPerson(), getString(R.string.service_person_liable));
                        dialog.setRadioSelectListener(RepairListActivity.this);
                        mEtPersonLiable.setText("");
                        // 重置按钮状态
//                        checkSumitBt();
                        showToast("申报成功");
                        finish();
                        // 清空本地信息
                        ServiceIdUtils.clear();
                        // 通知待维修刷新
                        EventBus.getDefault().post(EventBusCode.REFRESH_REPAIR_FRAGMENT_LIST, EventBusCode.REFRESH_REPAIR_FRAGMENT_LIST);
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }
}
