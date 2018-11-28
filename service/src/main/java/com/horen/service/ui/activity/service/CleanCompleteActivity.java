package com.horen.service.ui.activity.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.widget.CortpFooter;
import com.horen.base.widget.HRToolbar;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.RepairListDetailBean;
import com.horen.service.bean.ServiceListBean;
import com.horen.service.enumeration.service.ServiceType;
import com.horen.service.ui.activity.adapter.CleanCompleteAdapter;
import com.horen.service.utils.RvEmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @author :ChenYangYi
 * @date :2018/08/21/14:54
 * @description :清洗历史
 * @github :https://github.com/chenyy0708
 */
public class CleanCompleteActivity extends BaseActivity implements OnRefreshLoadMoreListener {


    private HRToolbar mToolBar;
    private TextView mTvRepairTotal;
    private TextView mTvName;
    private TextView mTvType;
    private RecyclerView mRecyclerView;
    private ServiceListBean.ServiceBean bean;
    private CleanCompleteAdapter cleanCompleteAdapter;

    /**
     * 第几页
     */
    public int pageNum = 1;
    /**
     * 每页行数
     */
    public int pageSize = 10;
    private SmartRefreshLayout mRefreshLayout;

    public static void startActivity(Context context, ServiceListBean.ServiceBean bean) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        intent.setClass(context, CleanCompleteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_clean_complete;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRToolbar) findViewById(R.id.tool_bar);
        mTvRepairTotal = (TextView) findViewById(R.id.tv_repair_total);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        initToolbar(mToolBar.getToolbar(), true, R.color.white);
        initRecyclerView();
        bean = (ServiceListBean.ServiceBean) getIntent().getSerializableExtra("bean");
        // 型号
        mTvType.setText(bean.getProduct_type());
        // 名称
        mTvName.setText(bean.getProduct_name());
        // 清洗总量
        mTvRepairTotal.setText(String.format(mContext.getString(R.string.service_clean_total), String.valueOf(bean.getService_qty())));
        initData(true);
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        cleanCompleteAdapter = new CleanCompleteAdapter(R.layout.service_item_clean_complete);
        mRecyclerView.setAdapter(cleanCompleteAdapter);
    }

    private void initData(boolean isShowDialog) {
        // 获取数据
        mRxManager.add(Api.getInstance()
                .getServiceRtpInfo(ServiceParams.getServiceRtpInfo(pageNum, pageSize, bean.getProduct_id(), "4", ServiceType.CLEAN.getStatus()))
                .compose(RxHelper.<RepairListDetailBean>getResult())
                .subscribeWith(new BaseObserver<RepairListDetailBean>(mContext, isShowDialog) {
                    @Override
                    protected void onSuccess(RepairListDetailBean detailBean) {
                        // 加载更多
                        if (pageNum > 1) {
                            cleanCompleteAdapter.addData(detailBean.getPageInfo().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(detailBean.getPageInfo().getList())) {
                                setEmptyView();
                            } else {
                                cleanCompleteAdapter.setNewData(detailBean.getPageInfo().getList());
                            }
                            mRefreshLayout.finishRefresh(0);
                        }
                        // 设置脚布局加载完成文字
                        if (!detailBean.getPageInfo().isHasNextPage()) {
                            CortpFooter footer = (CortpFooter) mRefreshLayout.getRefreshFooter();
                            if (footer != null)
                                footer.setLoadCompleteText(R.string.show_orders_nearly_one_year);
                        }
                        // 是否还有下一页数据
                        mRefreshLayout.setNoMoreData(!detailBean.getPageInfo().isHasNextPage());
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        // 当加载下一页时，错误  应该减去一页，防止漏加载数据
                        if (pageNum > 1) {
                            pageNum--;
                        } else {
                            setEmptyView();
                        }
                        mRefreshLayout.finishRefresh(false);
                        mRefreshLayout.finishLoadMore(false);
                    }
                }));
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageNum++;
        initData(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNum = 1;
        initData(false);
    }

    /**
     * 暂无数据
     */
    private void setEmptyView() {
        RvEmptyUtils.setEmptyView(cleanCompleteAdapter, mRecyclerView);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
