package com.horen.service.ui.fragment.business;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.horen.base.base.BaseFragment;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration;
import com.horen.base.widget.CortpFooter;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.OrderTransList;
import com.horen.service.enumeration.business.OrderStatus;
import com.horen.service.enumeration.business.OrderType;
import com.horen.service.listener.IBusinessTotalCount;
import com.horen.service.listener.IRefreshFragment;
import com.horen.service.ui.activity.business.OutCompleteActivity;
import com.horen.service.ui.activity.business.SchedulingCompleteActivity;
import com.horen.service.ui.activity.business.StorageCompleteActivity;
import com.horen.service.ui.fragment.adapter.CompleteAdapter;
import com.horen.service.ui.fragment.main.BusinessFragment;
import com.horen.service.utils.RvEmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/10:55
 * @description : 业务中心---已完成
 * @github :https://github.com/chenyy0708
 */
public class CompleteFragment extends BaseFragment implements OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener, IRefreshFragment {

    /**
     * 监听总个数获取
     */
    private IBusinessTotalCount totalCountListener;

    /**
     * 待处理Adapter
     */
    private CompleteAdapter completeAdapter;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    /**
     * 第几页
     */
    public int pageNum = 1;
    /**
     * 每页行数
     */
    public int pageSize = 10;

    public static CompleteFragment newInstance() {
        Bundle args = new Bundle();
        CompleteFragment fragment = new CompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_business_pending;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        refreshLayout = rootView.findViewById(R.id.refresh_layout);
        // 上拉下拉刷新监听
        refreshLayout.setOnRefreshLoadMoreListener(this);
        // 初始化列表
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        completeAdapter = new CompleteAdapter(R.layout.service_item_business_list);
        // 分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(_mActivity));
        recyclerView.setAdapter(completeAdapter);
        // 点击事件
        completeAdapter.setOnItemClickListener(this);
        // 获取订单数据
        refreshLayout.autoRefresh();
    }

    /**
     * 获取订单列表
     *
     * @param isShowLoading 展示加载弹框
     */
    private void getData(boolean isShowLoading) {
        mRxManager.add(Api.getInstance().orderAllotAndTransList(ServiceParams.orderAllotAndTransList(pageNum, pageSize, OrderStatus.COMPLETE.getStatus()))
                .compose(RxHelper.<OrderTransList>getResult())
                .subscribeWith(new BaseObserver<OrderTransList>(_mActivity, isShowLoading) {
                    @Override
                    protected void onSuccess(OrderTransList orderTransList) {
                        // 通知主页面设置总个数
                        if (totalCountListener != null) {
                            totalCountListener.setTotalCount(String.valueOf(orderTransList.getPageInfo().getTotal()), BusinessFragment.COMPLETE);
                        }
                        // 加载更多
                        if (pageNum > 1) {
                            completeAdapter.addData(orderTransList.getPageInfo().getList());
                            refreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(orderTransList.getPageInfo().getList())) {
                                setEmptyView();
                            } else {
                                completeAdapter.setNewData(orderTransList.getPageInfo().getList());
                            }
                            refreshLayout.finishRefresh(0);
                        }
                        // 设置脚布局加载完成文字
                        if (!orderTransList.getPageInfo().isHasNextPage()) {
                            CortpFooter footer = (CortpFooter) refreshLayout.getRefreshFooter();
                            if (footer != null)
                                footer.setLoadCompleteText(R.string.show_orders_nearly_one_year);
                        }
                        // 是否还有下一页数据
                        refreshLayout.setNoMoreData(!orderTransList.getPageInfo().isHasNextPage());
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
                        refreshLayout.finishRefresh(false);
                        refreshLayout.finishLoadMore(false);
                    }
                }));
    }

    /**
     * 总数监听
     */
    public void setTotalCountListener(IBusinessTotalCount totalCountListener) {
        this.totalCountListener = totalCountListener;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageNum++;
        getData(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNum = 1;
        getData(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderTransList.PageInfoBean.ListBean bean = completeAdapter.getData().get(position);
        if (bean.getOrder_type().equals(OrderType.STORAGE.getStatus())) {
            // 入库
            StorageCompleteActivity.startActivity(_mActivity, bean.getOrder_type(), bean.getOrderallot_id());
        } else if (bean.getOrder_type().equals(OrderType.OUTBOUND.getStatus())) {
            // 出库
            OutCompleteActivity.startActivity(_mActivity, bean.getOrder_type(), bean.getOrderallot_id());
        } else if (bean.getOrder_type().equals(OrderType.SCHEDULING.getStatus())) {
            // 调度
            SchedulingCompleteActivity.startActivity(_mActivity, bean.getOrder_type(), bean.getOrderallot_id());
        }
    }

    /**
     * 暂无数据
     */
    private void setEmptyView() {
        refreshLayout.setEnableLoadMore(false);
        completeAdapter.setNewData(null);
        RvEmptyUtils.setEmptyView(completeAdapter, recyclerView);
    }

    @Override
    public void onRefresh() {
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    @Override
    protected void reLoadData() {
        getData(true);
    }
}
