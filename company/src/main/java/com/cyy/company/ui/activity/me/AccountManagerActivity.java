package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AccountManager;
import com.cyy.company.ui.adapter.AccountManagerAdapter;
import com.cyy.company.utils.RvEmptyUtils;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/14:52
 * @description :账号管理
 * @github :https://github.com/chenyy0708
 */
public class AccountManagerActivity extends BaseActivity implements OnRefreshLoadMoreListener, AccountManagerAdapter.AccountListener {

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
    private AccountManagerAdapter managerAdapter;

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AccountManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_manager;
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
        // 新增账号
        mToolBar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAccountActivity.startAction(mContext, null);
            }
        });
        // 获取操作员列表
        getData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        managerAdapter = new AccountManagerAdapter(new ArrayList<AccountManager.CustomerUsersBean.ListBean>(), this);
        mRecyclerView.setAdapter(managerAdapter);
    }

    private void getData() {
        mRxManager.add(ApiCompany.getInstance()
                .getCustomerById(CompanyParams.getCustomerById(UserHelper.getUserInfo().getLoginInfo().getCompany_id(), pageNum, pageSize))
                .compose(RxHelper.<AccountManager>getResult())
                .subscribeWith(new BaseObserver<AccountManager>() {
                    @Override
                    protected void onSuccess(AccountManager bean) {
                        // 加载更多
                        if (pageNum > 1) {
                            managerAdapter.addData(bean.getCustomer_users().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(bean.getCustomer_users().getList())) {
                                RvEmptyUtils.setEmptyView(managerAdapter, mRecyclerView);
                            } else {
                                managerAdapter.setNewData(bean.getCustomer_users().getList());
                            }
                            mRefreshLayout.finishRefresh(0);
                        }
                        // 是否还有下一页数据
                        mRefreshLayout.setNoMoreData(!bean.getCustomer_users().isHasNextPage());
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

    /**
     * 账号启用停用
     *
     * @param position
     */
    @Override
    public void onEnableListener(int position) {
        AccountManager.CustomerUsersBean.ListBean item = managerAdapter.getItem(position);
        String status = "1";
        // 启用/停用
        if (item.getStatus().equals("1")) {
            status = "2";
        } else if (item.getStatus().equals("2")) {
            status = "1";
        }
        mRxManager.add(ApiCompany.getInstance().updateUserStatusCust(
                CompanyParams.updateUserStatusCust(status, item.getUser_id()))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        // 刷新列表数据
                        pageNum = 1;
                        getData();
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    /**
     * 刷新列表
     */
    @Subscriber(tag = EventBusCode.REFRESH_ACCOUNT)
    private void refreshList(String tag) {
        pageNum = 1;
        getData();
    }

}
