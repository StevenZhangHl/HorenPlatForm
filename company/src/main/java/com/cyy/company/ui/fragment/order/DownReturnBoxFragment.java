package com.cyy.company.ui.fragment.order;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.cyy.company.R;
import com.cyy.company.bean.ReturnOrderPD;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.mvp.contract.DownReturnBoxContract;
import com.cyy.company.mvp.model.DownReturnBoxModel;
import com.cyy.company.mvp.presenter.DownReturnBoxPresenter;
import com.cyy.company.ui.activity.order.DownOrderSuccessActivity;
import com.cyy.company.ui.activity.order.OrderAddressActivity;
import com.cyy.company.ui.adapter.OrderReturnPDAdapter;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration_1;
import com.horen.base.util.WeakHandler;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/23/14:32
 * @description :下游还箱
 * @github :https://github.com/chenyy0708
 */
public class DownReturnBoxFragment extends BaseFragment<DownReturnBoxPresenter, DownReturnBoxModel> implements DownReturnBoxContract.View, View.OnClickListener, OrderReturnPDAdapter.ChangeListener {

    private LinearLayout mLlBottom;
    private RecyclerView mRecyclerView;
    private OrderReturnPDAdapter productAdapter;
    private SuperButton mTvSubmitClick;
    private TextView mTvNoProducts;
    private EditText mEtRemarks;

    boolean isSubmiting = false;

    public static DownReturnBoxFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        DownReturnBoxFragment fragment = new DownReturnBoxFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_return_box;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.setRootView(rootView);
        setText(R.id.tv_add_org_address_name, "添加还箱网点");
        setText(R.id.tv_bottom_money_name, "运输费:");
        setText(R.id.tv_org_type, "还箱网点");
        mTvNoProducts = (TextView) rootView.findViewById(R.id.tv_no_products);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLlBottom = (LinearLayout) rootView.findViewById(R.id.ll_bottom);
        rootView.findViewById(R.id.tv_submit_click).setOnClickListener(this);
        rootView.findViewById(R.id.ll_transport_mode).setOnClickListener(this);
        rootView.findViewById(R.id.ll_bottom_money).setOnClickListener(this);
        mTvSubmitClick = (SuperButton) rootView.findViewById(R.id.tv_submit_click);
        mTvSubmitClick.setOnClickListener(this);
        mEtRemarks = (EditText) rootView.findViewById(R.id.et_remarks);
        // 下游隐藏地址右箭头
        rootView.findViewById(R.id.iv_right_arrow).setVisibility(View.GONE);
        // 下游不需要计算运费
        rootView.findViewById(R.id.tv_amount_zero).setVisibility(View.GONE);
        rootView.findViewById(R.id.tv_bottom_money_name).setVisibility(View.GONE);
        // 获取默认的网点大仓，不可手动选地址
        mPresenter.getDownStreamOrgsList();
        initListener();
        initRecyclerView();
    }

    private void initListener() {
        // 监听软键盘关闭打开
        KeyboardVisibilityEvent.setEventListener(_mActivity, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) { // 开启
                    mLlBottom.setVisibility(View.GONE);
                } else { // 关闭
                    productAdapter.setonKeyboardClose();
                    new WeakHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLlBottom.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                }
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration_1(_mActivity));
        productAdapter = new OrderReturnPDAdapter(new ArrayList<ReturnOrderPD.PdListBean>(), this);
        mRecyclerView.setAdapter(productAdapter);
    }

    /**
     * 选择运输方式
     *
     * @param flag_send
     */
    @Override
    public void onSelectTransportMode(String flag_send) {
        checkStatus();
    }

    /**
     * 提交订单成功
     */
    @Override
    public void onSubmitSuccess(SubmitOrder submitOrder) {
        // 计算所有的箱子，得到还箱点,一个箱子=50还箱点
        int count = 0;
        for (ReturnOrderPD.PdListBean pdListBean : productAdapter.getData()) {
            count += pdListBean.getOrder_qty();
        }
        // 打开提交订单成功页面
        DownOrderSuccessActivity.startAction(_mActivity, mPresenter.getAddress(),
                getArguments().getString("title"), count, submitOrder.getOrder_list());
        _mActivity.finish();
    }

    /**
     * 还箱物品
     *
     * @param mData
     */
    @Override
    public void getOrderProdouctList(List<ReturnOrderPD.PdListBean> mData) {
        if (!CollectionUtils.isNullOrEmpty(mData)) { // 为空不显示
            mTvNoProducts.setVisibility(View.GONE);
            productAdapter.setNewData(mData);
        } else { // 没有物品
            setText(R.id.tv_amount_zero, "¥_");
            mTvNoProducts.setVisibility(View.VISIBLE);
            productAdapter.setNewData(new ArrayList<ReturnOrderPD.PdListBean>());
        }
        checkStatus();
    }

    /**
     * 物品Adapter，用于提交订单
     */
    @Override
    public OrderReturnPDAdapter getAdapter() {
        return productAdapter;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_transport_mode) { // 配送方式
            mPresenter.showTransportMode();
        } else if (view.getId() == R.id.fl_select_address || view.getId() == R.id.ll_select_address) { // 选择地址
            OrderAddressActivity.startActivityForResult(this, OrderAddressActivity.DOWN_STREAM);
        } else if (view.getId() == R.id.tv_submit_click) { // 提交订单
            if (!isSubmiting) {
                isSubmiting = true;
                mPresenter.submitOrder(mEtRemarks.getText().toString());
            }
        }
    }

    /**
     * 输入框数量发生变化
     */
    @Override
    public void onNumberChangeListener(int position) {
        checkStatus();
    }

    private void checkStatus() {
        if (mPresenter.checkStatus() && productAdapter.getData().size() > 0) {
            int returnBoxNumber = 0;
            for (ReturnOrderPD.PdListBean pdListBean : productAdapter.getData()) {
                returnBoxNumber += pdListBean.getOrder_qty();
            }
            if (returnBoxNumber != 0) { // 还箱数不能为0
                // 并且判断数量不能全为0
                mTvSubmitClick.setEnabled(true);
                mTvSubmitClick.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
            } else {
                mTvSubmitClick.setEnabled(false);
                mTvSubmitClick.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999));
                setText(R.id.tv_amount_zero, "¥_");
            }
        } else {
            setText(R.id.tv_amount_zero, "¥_");
            mTvSubmitClick.setEnabled(false);
            mTvSubmitClick.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999));
        }
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        isSubmiting = false;
    }
}
