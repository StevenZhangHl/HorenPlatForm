package com.cyy.company.ui.activity.eye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.OrgPageBean;
import com.cyy.company.ui.adapter.AssetsListAdapter;
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
 * @description :资产列表
 * @github :https://github.com/chenyy0708
 */
public class AssetsListActivity extends BaseActivity implements OnRefreshLoadMoreListener {

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
    private AssetsListAdapter assetsListAdapter;

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AssetsListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_assets_list;
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
        assetsListAdapter = new AssetsListAdapter(new ArrayList<OrgPageBean.PdListBean.ListBeanX>());
        mRecyclerView.setAdapter(assetsListAdapter);
        getData();
    }

    private void getData() {
        mRxManager.add(ApiCompany.getInstance()
                .getOrgPageList(CompanyParams.getCustomerById(UserHelper.getUserInfo().getLoginInfo().getCompany_id(), pageNum, pageSize))
                .compose(RxHelper.<OrgPageBean>getResult())
                .subscribeWith(new BaseObserver<OrgPageBean>() {
                    @Override
                    protected void onSuccess(OrgPageBean bean) {
                        // 加载更多
                        if (pageNum > 1) {
                            assetsListAdapter.addData(bean.getPdList().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(bean.getPdList().getList())) {
                                RvEmptyUtils.setEmptyView(assetsListAdapter, mRecyclerView);
                            } else {
                                assetsListAdapter.setNewData(bean.getPdList().getList());
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
                            RvEmptyUtils.setEmptyView(assetsListAdapter, mRecyclerView);
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

}
