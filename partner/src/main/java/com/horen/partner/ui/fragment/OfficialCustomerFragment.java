package com.horen.partner.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.ToastUitl;
import com.horen.partner.R;
import com.horen.partner.adapter.OfficalListAdapter;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.PotentialBean;
import com.horen.partner.ui.activity.customer.CustomerDetailActivity;
import com.horen.partner.util.RvEmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Author:Steven
 * Time:2018/8/6 17:23
 * Description:This isOfficialCustomerFragment 正式客户
 */
public class OfficialCustomerFragment extends BaseFragment {
    private RecyclerView mRecyclerviewPotential;
    private SmartRefreshLayout mRefreshviewOfficical;
    private RecyclerView.LayoutManager layoutManager;
    private OfficalListAdapter officalListAdapter;

    public static OfficialCustomerFragment newInstance() {
        Bundle bundle = new Bundle();
        OfficialCustomerFragment officialCustomerFragment = new OfficialCustomerFragment();
        officialCustomerFragment.setArguments(bundle);
        return officialCustomerFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partner_fragment_official_customer;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerviewPotential = (RecyclerView) rootView.findViewById(R.id.recyclerview_potential);
        mRefreshviewOfficical = (SmartRefreshLayout) rootView.findViewById(R.id.refreshview_officical);
        initRecycleView();
    }

    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerviewPotential.setLayoutManager(layoutManager);
        //自定义分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(_mActivity, R.drawable.list_divider_10dp));
        mRecyclerviewPotential.addItemDecoration(itemDecoration);
        officalListAdapter = new OfficalListAdapter(R.layout.partner_item_offical, new ArrayList<PotentialBean.ListBean>());
        mRecyclerviewPotential.setAdapter(officalListAdapter);
        mRefreshviewOfficical.setEnableLoadMore(false);
        mRefreshviewOfficical.setEnableAutoLoadMore(false);
        mRefreshviewOfficical.setEnableLoadMoreWhenContentNotFull(false);
        initRecycleViewOnListener();
        setRefresh();
        mRefreshviewOfficical.autoRefresh();
    }

    private void initRecycleViewOnListener() {
        officalListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CustomerDetailActivity.launchActivity(_mActivity, officalListAdapter.getData().get(position).getCustomer_id(), "official");
            }
        });
        mRecyclerviewPotential.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int id = view.getId();
                if (id == R.id.iv_detail) {
                    CustomerDetailActivity.launchActivity(_mActivity, officalListAdapter.getData().get(position).getCustomer_id(), "official");
                }
            }
        });
    }

    private void setRefresh() {
        mRefreshviewOfficical.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                mRxManager.add(ApiPartner.getInstance().getOfficalData(ApiRequest.getOfficalCustomerData("", "")).compose(RxHelper.<PotentialBean>getResult()).subscribeWith(new BaseObserver<PotentialBean>(_mActivity, false) {
                    @Override
                    protected void onSuccess(PotentialBean potentialBean) {
                        refreshLayout.finishRefresh();
                        if (potentialBean.getList()==null||potentialBean.getList().size() == 0) {
                            showEmptyData();
                        } else {
                            officalListAdapter.setNewData(potentialBean.getList());
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        refreshLayout.finishRefresh();
                    }
                }));
            }
        });
    }

    private void showEmptyData() {
        RvEmptyUtils.setEmptyView(officalListAdapter,mRecyclerviewPotential,R.layout.include_official_data_empty);
    }

    @Override
    protected void reLoadData() {
        mRefreshviewOfficical.autoRefresh();
    }
}
