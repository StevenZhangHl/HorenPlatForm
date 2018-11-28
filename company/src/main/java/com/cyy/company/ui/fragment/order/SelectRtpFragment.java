package com.cyy.company.ui.fragment.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyy.company.R;
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.listener.ProductSelectListener;
import com.cyy.company.ui.adapter.OrderProutAdapter;
import com.horen.base.util.LogUtils;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/09:38
 * @description :选择物品
 * @github :https://github.com/chenyy0708
 */
public class SelectRtpFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private OrderProducts orderProducts;
    private OrderProutAdapter proutAdapter;

    private ProductSelectListener listener;

    public static SelectRtpFragment newInstance(OrderProducts products) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("products", products);
        SelectRtpFragment fragment = new SelectRtpFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_rtp, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        orderProducts = (OrderProducts) getArguments().getSerializable("products");
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        proutAdapter = new OrderProutAdapter(orderProducts.getPdList());
        mRecyclerView.setAdapter(proutAdapter);
        proutAdapter.setOnItemClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onScrollListener != null)
                    onScrollListener.onScrolled(recyclerView, dx, dy);
            }
        });
        return rootView;
    }

    public void setListener(ProductSelectListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderProducts.PdListBean item = proutAdapter.getItem(position);
        if (item.isSelect()) return; // 已选择不能点击
        listener.onSelectRtp(item);
    }

    private RecyclerView.OnScrollListener onScrollListener;

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
}
