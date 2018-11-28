package com.cyy.company.ui.fragment.order;

import android.content.Intent;
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
import com.cyy.company.enums.OrderType;
import com.cyy.company.mvp.contract.ReturnBoxContract;
import com.cyy.company.mvp.model.ReturnBoxModel;
import com.cyy.company.mvp.presenter.ReturnBoxPresenter;
import com.cyy.company.ui.activity.order.OrderAddressActivity;
import com.cyy.company.ui.activity.order.OrderSuccessActivity;
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
 * @description :还箱
 * @github :https://github.com/chenyy0708
 */
public class ReturnBoxFragment extends BaseFragment<ReturnBoxPresenter, ReturnBoxModel> implements ReturnBoxContract.View, View.OnClickListener, OrderReturnPDAdapter.ChangeListener {

    private LinearLayout mLlBottom;
    private RecyclerView mRecyclerView;
    private OrderReturnPDAdapter productAdapter;
    private SuperButton mTvSubmitClick;
    private TextView mTvNoProducts;
    private EditText mEtRemarks;

    boolean isSubmiting = false;

    public static ReturnBoxFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        ReturnBoxFragment fragment = new ReturnBoxFragment();
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
        rootView.findViewById(R.id.fl_select_address).setOnClickListener(this);
        rootView.findViewById(R.id.ll_select_address).setOnClickListener(this);
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
     *
     * @param order_id
     */
    @Override
    public void onSubmitSuccess(String order_id) {
        // 打开提交订单成功页面
        OrderSuccessActivity.startAction(_mActivity, order_id, OrderType.TWO.getPosition(), mPresenter.getAddress(), getArguments().getString("title"));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_transport_mode) { // 配送方式
            mPresenter.showTransportMode();
        } else if (view.getId() == R.id.fl_select_address || view.getId() == R.id.ll_select_address) { // 选择地址
            OrderAddressActivity.startActivityForResult(this, OrderAddressActivity.DOWN_STREAM);
        } else if (view.getId() == R.id.ll_bottom_money) { // 金额明细
            mPresenter.showAmountDialog();
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
                // 计算运费金额
                mPresenter.calculate(productAdapter.getData());
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
