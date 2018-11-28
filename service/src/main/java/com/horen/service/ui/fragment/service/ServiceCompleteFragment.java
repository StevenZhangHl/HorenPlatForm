package com.horen.service.ui.fragment.service;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.horen.base.base.BaseFragment;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.ServiceListBean;
import com.horen.service.enumeration.service.ServiceType;
import com.horen.service.listener.IBusinessTotalCount;
import com.horen.service.ui.activity.adapter.ServiceCompleteAdapter;
import com.horen.service.ui.activity.service.CleanCompleteActivity;
import com.horen.service.ui.activity.service.RepairCompleteActivity;
import com.horen.service.ui.fragment.main.ServiceFragment;
import com.horen.service.utils.RvEmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.simple.eventbus.Subscriber;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/13:10
 * @description :服务中心已完成
 * @github :https://github.com/chenyy0708
 */
public class ServiceCompleteFragment extends BaseFragment implements OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private PieChart mPieChart;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    /**
     * 监听总个数获取
     */
    private IBusinessTotalCount totalCountListener;
    private ServiceCompleteAdapter completeAdapter;
    private LinearLayoutManager layoutManager;
    private boolean isInit;

    public static ServiceCompleteFragment newInstance() {
        Bundle args = new Bundle();
        ServiceCompleteFragment fragment = new ServiceCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_piechart_service;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPieChart = rootView.findViewById(R.id.pie_chart);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setBackgroundResource(R.color.color_f5);
        mPieChart.setVisibility(View.GONE);
        // 上拉下拉刷新监听
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        // 初始化列表
        initRecyclerView();
        // 获取数据
        mRefreshLayout.autoRefresh();
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        completeAdapter = new ServiceCompleteAdapter(R.layout.service_item_service_complete);
        completeAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(completeAdapter);
        isInit = true;
    }

    /**
     * 获取服务列表
     */
    private void initData(boolean showDialog) {
        mRxManager.add(Api.getInstance().getRtpServiceList(ServiceParams.getRtpServiceList("4", ""))
                .compose(RxHelper.<ServiceListBean>getResult())
                .subscribeWith(new BaseObserver<ServiceListBean>(_mActivity, showDialog) {
                    @Override
                    protected void onSuccess(ServiceListBean serviceListBean) {
                        // 通知主页面设置总个数
                        if (CollectionUtils.isNullOrEmpty(serviceListBean.getServiceList())) {
                            setEmptyView();
                        } else {
                            completeAdapter.setNewData(serviceListBean.getServiceList());
                        }
                        setTotalCount();
                        mRefreshLayout.finishRefresh(0);
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        mRefreshLayout.finishRefresh(0);
                    }
                }));
    }

    /**
     * 通知主页面设置总个数
     */
    private void setTotalCount() {
        if (totalCountListener != null) {
            int total = 0;
            for (ServiceListBean.ServiceBean serviceBean : completeAdapter.getData()) {
                total += serviceBean.getService_qty();
            }
            totalCountListener.setTotalCount(String.valueOf(total),
                    ServiceFragment.COMPLETE);
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        initData(false);
    }

    /**
     * 总数监听
     */
    public void setTotalCountListener(IBusinessTotalCount totalCountListener) {
        this.totalCountListener = totalCountListener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ServiceListBean.ServiceBean bean = completeAdapter.getData().get(position);
        if (bean.getService_type().equals(ServiceType.CLEAN.getStatus())) {
            // 清洗
            CleanCompleteActivity.startActivity(_mActivity, bean);
        } else {
            // 维修
            RepairCompleteActivity.startActivity(_mActivity, bean);
        }
    }

    /**
     * 暂无数据
     */
    private void setEmptyView() {
        mRefreshLayout.setEnableLoadMore(false);
        RvEmptyUtils.setEmptyView(completeAdapter,mRecyclerView);
    }

    /**
     * 刷新列表数据
     */
    @Subscriber(tag = EventBusCode.REFRESH_COMPLETE_LIST)
    private void refreshList(String s) {
        if (isInit) {
            initData(false);
        }
    }

    @Override
    protected void reLoadData() {
        initData(true);
    }
}
