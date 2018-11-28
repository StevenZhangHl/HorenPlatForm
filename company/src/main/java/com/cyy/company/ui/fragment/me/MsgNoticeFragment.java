package com.cyy.company.ui.fragment.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.MessageNoticeBean;
import com.cyy.company.ui.adapter.MsgLogisticsNoticeAdapter;
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
 * @description :通知类消息
 * @github :https://github.com/chenyy0708
 */
public class MsgNoticeFragment extends BaseFragment implements MsgLogisticsNoticeAdapter.DeleteListener, OnRefreshLoadMoreListener {

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
    private MsgLogisticsNoticeAdapter logisticsNoticeAdapter;

    public static MsgNoticeFragment newInstance() {
        Bundle bundle = new Bundle();
        MsgNoticeFragment fragment = new MsgNoticeFragment();
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
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(_mActivity));
        logisticsNoticeAdapter = new MsgLogisticsNoticeAdapter(new ArrayList<MessageNoticeBean.PdListBean.ListBean>(), this);
        mRecyclerView.setAdapter(logisticsNoticeAdapter);
        getData();
    }

    private void getData() {
        mRxManager.add(ApiCompany.getInstance()
                .getMessageNoticeList(CompanyParams.getMessageList(pageNum, pageSize))
                .compose(RxHelper.<MessageNoticeBean>getResult())
                .subscribeWith(new BaseObserver<MessageNoticeBean>() {
                    @Override
                    protected void onSuccess(MessageNoticeBean bean) {
                        // 加载更多
                        if (pageNum > 1) {
                            logisticsNoticeAdapter.addData(bean.getPdList().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(bean.getPdList().getList())) {
                                RvEmptyUtils.setEmptyView(logisticsNoticeAdapter, mRecyclerView);
                            } else {
                                logisticsNoticeAdapter.setNewData(bean.getPdList().getList());
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
                            RvEmptyUtils.setEmptyView(logisticsNoticeAdapter, mRecyclerView);
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
        MessageNoticeBean.PdListBean.ListBean item = logisticsNoticeAdapter.getItem(position);
        delMessageInfo(item.getMsg_id(), position);
    }

    @Override
    public void onItemClickListener(int position) {
        MessageNoticeBean.PdListBean.ListBean item = logisticsNoticeAdapter.getItem(position);
        if (logisticsNoticeAdapter.getItem(position).getSTATUS().equals("1")) { // 未读消息
            upMessageInfo(item.getMsg_id());
        }
        // 更新本地状态
        item.setSTATUS("2");
        logisticsNoticeAdapter.notifyItemChanged(position);
    }

    /**
     * 跟新信息状态
     *
     * @param log_id log
     */
    private void upMessageInfo(String log_id) {
        mRxManager.add(ApiCompany.getInstance().upMessageNoticeInfo(CompanyParams.upMessageInfo(log_id))
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
        mRxManager.add(ApiCompany.getInstance().delMessageNoticeInfo(CompanyParams.delMessageInfo(log_id))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        logisticsNoticeAdapter.remove(position);
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }
}
