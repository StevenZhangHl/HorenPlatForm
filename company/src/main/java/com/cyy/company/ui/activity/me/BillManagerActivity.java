package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.BillListBean;
import com.cyy.company.ui.adapter.BillManagerAdapter;
import com.cyy.company.utils.RvEmptyUtils;
import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/14:52
 * @description :账单管理
 * @github :https://github.com/chenyy0708
 */
public class BillManagerActivity extends BaseActivity implements OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private HRTitle mToolBar;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    /**
     * 第几页
     */
    public int pageNum = 1;
    /**
     * 每页行数
     */
    public int pageSize = 10;
    private BillManagerAdapter managerAdapter;

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BillManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_manager;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mToolBar.bindActivity(this, R.color.white);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        managerAdapter = new BillManagerAdapter(new ArrayList<BillListBean.PdListBean.ListBean>());
        managerAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(managerAdapter);
        getData();
    }

    private void getData() {
        mRxManager.add(ApiCompany.getInstance()
                .getCheckBillList(CompanyParams.getCheckBillList(UserHelper.getUserInfo().getLoginInfo().getCompany_id(), pageNum, pageSize))
                .compose(RxHelper.<BillListBean>getResult())
                .subscribeWith(new BaseObserver<BillListBean>() {
                    @Override
                    protected void onSuccess(BillListBean bean) {
                        // 加载更多
                        if (pageNum > 1) {
                            managerAdapter.addData(bean.getPdList().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(bean.getPdList().getList())) {
                                RvEmptyUtils.setEmptyView(managerAdapter, mRecyclerView);
                            } else {
                                managerAdapter.setNewData(bean.getPdList().getList());
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
                            RvEmptyUtils.setEmptyView(managerAdapter, mRecyclerView);
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


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BillManagerDetailActivity.startAction(mContext,managerAdapter.getItem(position));
    }
}
