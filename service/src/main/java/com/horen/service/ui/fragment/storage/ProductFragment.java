package com.horen.service.ui.fragment.storage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration;
import com.horen.service.R;
import com.horen.service.bean.StorageCenterBean;
import com.horen.service.enumeration.service.StorageType;
import com.horen.service.listener.IStorageTotalCount;
import com.horen.service.mvp.contract.StorageContract;
import com.horen.service.mvp.model.StorageModel;
import com.horen.service.mvp.presenter.StoragePresenter;
import com.horen.service.ui.fragment.adapter.ProductAdapter;
import com.horen.service.utils.RvEmptyUtils;
import com.horen.service.widget.ProductDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/08/27/10:22
 * @description :产品库存
 * @github :https://github.com/chenyy0708
 */
public class ProductFragment extends BaseFragment<StoragePresenter, StorageModel> implements BaseQuickAdapter.OnItemClickListener, StorageContract.View, OnRefreshListener {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;

    private IStorageTotalCount totalCountListener;
    private ProductAdapter productAdapter;

    public static ProductFragment newInstance() {
        Bundle args = new Bundle();
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_fragment_product;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        initRecyclerView();
//        mPresenter.storageList(StorageType.PRODUCT.getStatus());
        mRefreshLayout.autoRefresh();
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(new ArrayList<StorageCenterBean.ListBean>());
        mRecyclerView.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        // 分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(_mActivity));
    }

    /**
     * 总数监听
     */
    public void setTotalCountListener(IStorageTotalCount totalCountListener) {
        this.totalCountListener = totalCountListener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        StorageCenterBean.ListBean bean = productAdapter.getData().get(position);
        new ProductDialog(_mActivity)
                .setProduct(bean.getProduct_name(), bean.getProduct_type())
                .setData(bean.getWarehouse_qty(), bean.getAvailable_qty(), bean.getClean_qty(), bean.getRepair_qty())
                .show();
    }

    @Override
    public void getStorageListSuccess(StorageCenterBean bean) {
        if (!CollectionUtils.isNullOrEmpty(bean.getList())) {
            productAdapter.setNewData(bean.getList());
        } else {
            RvEmptyUtils.setEmptyView(productAdapter, mRecyclerView);
        }
        totalCountListener.setTotalCount(String.valueOf(bean.getWarehousesInfo().getClean_qty()), String.valueOf(bean.getWarehousesInfo().getRepair_qty()));
        mRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.storageList(StorageType.PRODUCT.getStatus());
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        mRefreshLayout.finishRefresh(0);
    }

    @Override
    protected void reLoadData() {
        mRefreshLayout.autoRefresh();
    }
}
