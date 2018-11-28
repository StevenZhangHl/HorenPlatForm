package com.cyy.company.ui.fragment.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.MsgLogisticsBean;
import com.cyy.company.ui.activity.me.OrderDetailActivity;
import com.cyy.company.ui.adapter.MsgLogisticsAdapter;
import com.cyy.company.utils.RvEmptyUtils;
import com.horen.base.base.BaseFragment;
import com.horen.base.bean.BaseEntry;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/15/15:56
 * @description :物流类消息
 * @github :https://github.com/chenyy0708
 */
public class MsgLogisticsFragment extends BaseFragment implements OnRefreshLoadMoreListener, MsgLogisticsAdapter.DeleteListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    /**
     * 第几页
     */
    public int pageNum = 1;
    /**
     * 每页行数
     */
    public int pageSize = 10;

    private boolean isInit;
    private MsgLogisticsAdapter logisticsAdapter;

    public static MsgLogisticsFragment newInstance() {
        Bundle bundle = new Bundle();
        MsgLogisticsFragment fragment = new MsgLogisticsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_msg_logistics;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(_mActivity));
        logisticsAdapter = new MsgLogisticsAdapter(new ArrayList<MsgLogisticsBean.PdListBean.ListBean>(), this);
        mRecyclerView.setAdapter(logisticsAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        // 获取数据
        getData();
        isInit = true;
    }

    private void getData() {
        mRxManager.add(ApiCompany.getInstance()
                .getMessageList(CompanyParams.getMessageList(pageNum, pageSize))
                .compose(RxHelper.<MsgLogisticsBean>getResult())
                .subscribeWith(new BaseObserver<MsgLogisticsBean>() {
                    @Override
                    protected void onSuccess(MsgLogisticsBean bean) {
                        // 加载更多
                        if (pageNum > 1) {
                            logisticsAdapter.addData(bean.getPdList().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(bean.getPdList().getList())) {
                                RvEmptyUtils.setEmptyView(logisticsAdapter, mRecyclerView);
                            } else {
                                logisticsAdapter.setNewData(bean.getPdList().getList());
                            }
                            mRefreshLayout.finishRefresh(0);
                        }
                        // 是否还有下一页数据
                        mRefreshLayout.setNoMoreData(!bean.getPdList().isHasNextPage());
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        // 当加载下一页时，错误  应该减去一页，防止漏加载数据
                        if (pageNum > 1) {
                            pageNum--;
                        } else {
                            RvEmptyUtils.setEmptyView(logisticsAdapter, mRecyclerView);
                        }
                        mRefreshLayout.finishRefresh(false);
                        mRefreshLayout.finishLoadMore(false);
                    }
                }));
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageNum++;
        getData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNum = 1;
        getData();
    }

    /**
     * 删除消息
     *
     * @param position
     */
    @Override
    public void onDeleteListener(int position) {
        MsgLogisticsBean.PdListBean.ListBean item = logisticsAdapter.getItem(position);
        delMessageInfo(item.getLog_id(), position);
    }

    @Override
    public void onItemClickListener(int position) {
        MsgLogisticsBean.PdListBean.ListBean item = logisticsAdapter.getItem(position);
        if (logisticsAdapter.getItem(position).getStatus().equals("1")) { // 未读消息
            upMessageInfo(item.getLog_id());
        }
        // 更新本地状态
        item.setStatus("2");
        logisticsAdapter.notifyItemChanged(position);
        MsgLogisticsBean.PdListBean.ListBean bean = logisticsAdapter.getItem(position);
        // 订单详情
        OrderDetailActivity.startAction(_mActivity, logisticsAdapter.getItem(position).getOrder_id()
                , logisticsAdapter.getItem(position).getProduct_photo(), position, "消息中心");
    }

    /**
     * 跟新信息状态
     *
     * @param log_id log
     */
    private void upMessageInfo(String log_id) {
        mRxManager.add(ApiCompany.getInstance().upMessageInfo(CompanyParams.upMessageInfo(log_id))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        // 更新未读消息
                        EventBus.getDefault().post(EventBusCode.REFRESH_MSG_COUNT,
                                EventBusCode.REFRESH_MSG_COUNT);
                    }

                    @Override
                    protected void onError(String message) {

                    }
                }));
    }

    /**
     * 删除消息
     */
    private void delMessageInfo(String log_id, final int position) {
        mRxManager.add(ApiCompany.getInstance().delMessageInfo(CompanyParams.delMessageInfo(log_id))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        logisticsAdapter.remove(position);
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }
}
