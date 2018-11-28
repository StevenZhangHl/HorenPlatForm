package com.cyy.company.ui.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AddressBook;
import com.cyy.company.ui.activity.me.AddressEditActivity;
import com.cyy.company.ui.adapter.AddressBookAdapter;
import com.cyy.company.utils.RvEmptyUtils;
import com.horen.base.base.BaseFragment;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import static com.cyy.company.ui.activity.order.OrderAddressActivity.RESULT_SUCCESS;

/**
 * @author :ChenYangYi
 * @date :2018/10/15/15:56
 * @description :上下游企业
 * @github :https://github.com/chenyy0708
 */
public class AddressBookFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private AddressBookAdapter bookAdapter;
    private String org_type;

    /**
     * 订单选择地址
     */
    public static final int ORDER = 100;
    /**
     * 默认进入地址
     */
    public static final int NORMAL = 101;

    /**
     * 第几页
     */
    public int pageNum = 1;
    /**
     * 每页行数
     */
    public int pageSize = 10;

    private boolean isInit;
    private int address_type;

    public static AddressBookFragment newInstance(String org_type, int address_type) {
        Bundle bundle = new Bundle();
        bundle.putString("org_type", org_type);
        bundle.putInt("address_type", address_type);
        AddressBookFragment fragment = new AddressBookFragment();
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
        org_type = getArguments().getString("org_type");
        address_type = getArguments().getInt("address_type");
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        // 获取数据
        getData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(_mActivity));
        bookAdapter = new AddressBookAdapter(new ArrayList<AddressBook.PdListBean.ListBean>());
        bookAdapter.setOnItemChildClickListener(this);
        bookAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(bookAdapter);
        isInit = true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (address_type == NORMAL) {
            AddressEditActivity.startAction(_mActivity, bookAdapter.getItem(position), org_type);
        } else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("addressBean", bookAdapter.getData().get(position));
            _mActivity.setResult(RESULT_SUCCESS, resultIntent);
            _mActivity.finish();
        }
    }

    private void getData() {
        mRxManager.add(ApiCompany.getInstance()
                .getOrgAddressList(CompanyParams.getOrgAddressList(org_type, pageNum, pageSize))
                .compose(RxHelper.<AddressBook>getResult())
                .subscribeWith(new BaseObserver<AddressBook>() {
                    @Override
                    protected void onSuccess(AddressBook bean) {
                        // 加载更多
                        if (pageNum > 1) {
                            bookAdapter.addData(bean.getPdList().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(bean.getPdList().getList())) {
                                RvEmptyUtils.setEmptyView(bookAdapter, mRecyclerView);
                            } else {
                                bookAdapter.setNewData(bean.getPdList().getList());
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
                            RvEmptyUtils.setEmptyView(bookAdapter, mRecyclerView);
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
     * 刷新列表
     */
    @Subscriber(tag = EventBusCode.REFRESH_ADDRESS)
    private void refreshAddress(String tag) {
        if (!isInit) return;
        pageNum = 1;
        getData();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tv_edit) {
            AddressEditActivity.startAction(_mActivity, bookAdapter.getItem(position), org_type);
        }
    }
}
