package com.cyy.company.ui.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.allen.library.SuperButton;
import com.cyy.company.R;
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.enums.OrderType;
import com.cyy.company.mvp.contract.RentBoxContract;
import com.cyy.company.mvp.model.RentBoxModel;
import com.cyy.company.mvp.presenter.RentBoxPresenter;
import com.cyy.company.ui.activity.order.OrderAddressActivity;
import com.cyy.company.ui.activity.order.OrderSuccessActivity;
import com.cyy.company.ui.adapter.OrderRtpAdapter;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.DividerItemDecoration_1;
import com.horen.base.util.WeakHandler;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/19/16:08
 * @description :租箱
 * @github :https://github.com/chenyy0708
 */
public class RentBoxFragment extends BaseFragment<RentBoxPresenter, RentBoxModel> implements View.OnClickListener, RentBoxContract.View, OrderRtpAdapter.DeleteListener {

    private LinearLayout mLlRentalDays;
    private SuperButton mSbtSelectProduct;
    private RecyclerView mRecyclerView;
    private LinearLayout mLlTransportMode;
    private FrameLayout mFlTime;
    private EditText mEtRemarks;
    private LinearLayout mLlBottomMoney;
    private LinearLayout mLlMoneyNull;
    private SuperButton mTvSubmitClick;
    private OrderRtpAdapter productAdapter;
    private LinearLayout mLlBottom;

    boolean isSubmiting = false;

    public static RentBoxFragment newInstance() {
        Bundle bundle = new Bundle();
        RentBoxFragment fragment = new RentBoxFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rent_box;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.setRootView(rootView);
        mLlRentalDays = (LinearLayout) rootView.findViewById(R.id.ll_rental_days);
        mSbtSelectProduct = (SuperButton) rootView.findViewById(R.id.sbt_select_product);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLlTransportMode = (LinearLayout) rootView.findViewById(R.id.ll_transport_mode);
        mFlTime = (FrameLayout) rootView.findViewById(R.id.fl_time);
        mEtRemarks = (EditText) rootView.findViewById(R.id.et_remarks);
        mLlBottom = (LinearLayout) rootView.findViewById(R.id.ll_bottom);
        mLlBottomMoney = (LinearLayout) rootView.findViewById(R.id.ll_bottom_money);
        mLlMoneyNull = (LinearLayout) rootView.findViewById(R.id.ll_money_null);
        mTvSubmitClick = (SuperButton) rootView.findViewById(R.id.tv_submit_click);
        rootView.findViewById(R.id.fl_select_address).setOnClickListener(this);
        rootView.findViewById(R.id.ll_select_address).setOnClickListener(this);
        mTvSubmitClick.setOnClickListener(this);
        mSbtSelectProduct.setOnClickListener(this);
        mLlRentalDays.setOnClickListener(this);
        mLlTransportMode.setOnClickListener(this);
        mLlBottomMoney.setOnClickListener(this);
        mFlTime.setOnClickListener(this);
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
        productAdapter = new OrderRtpAdapter(new ArrayList<OrderProducts.PdListBean>(), this);
        mRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sbt_select_product) { // 选择物品
            mPresenter.showSelectProducts(getFragmentManager(), productAdapter.getData());
        } else if (view.getId() == R.id.ll_rental_days) { // 租赁天数
            mPresenter.showRentalDays();
        } else if (view.getId() == R.id.ll_transport_mode) { // 配送方式
            mPresenter.showTransportMode();
        } else if (view.getId() == R.id.fl_time) { // 预计配送时间
            mPresenter.showTimeSelect();
        } else if (view.getId() == R.id.fl_select_address || view.getId() == R.id.ll_select_address) { // 选择地址
            OrderAddressActivity.startActivityForResult(this, OrderAddressActivity.UP_STREAM);
        } else if (view.getId() == R.id.ll_bottom_money) { // 金额明细
            mPresenter.showAmountDialog();
        } else if (view.getId() == R.id.tv_submit_click) { // 提交订单
            if (!isSubmiting) {
                isSubmiting = true;
                mPresenter.submitOrder(mEtRemarks.getText().toString());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择物品
     */
    @Override
    public void onSelectProducts(OrderProducts.PdListBean bean) {
        productAdapter.addData(bean);
        checkStatus();
    }

    /**
     * 选择租赁天数
     */
    @Override
    public void onSelectRentalDays(String rental_days) {
        productAdapter.setNewData(new ArrayList<OrderProducts.PdListBean>());
        checkStatus();
    }

    @Override
    public void onSelectTransportMode(String flag_send) {
        checkStatus();
    }

    @Override
    public void onSelectTime(String expect_arrivedate) {
        checkStatus();
    }

    /**
     * 选择物品Adapter，用于提交订单
     */
    @Override
    public OrderRtpAdapter getAdapter() {
        return productAdapter;
    }

    /**
     * 提交订单成功
     *
     * @param order_id
     */
    @Override
    public void onSubmitSuccess(String order_id) {
        // 打开提交订单成功页面
        OrderSuccessActivity.startAction(_mActivity, order_id, OrderType.ONE.getPosition(), mPresenter.getAddress(), HRConstant.HUNDRED_KILOMETERS);
        _mActivity.finish();
    }

    /**
     * 删除物品
     */
    @Override
    public void onDeleteListener(int position) {
        checkStatus();
    }

    /**
     * 输入框数量发生变化
     */
    @Override
    public void onNumberChangeListener(int position) {
        checkStatus();
    }

    /**
     * 检查提交按钮状态（地址、租赁天数、运输方式、预计使用时间、选择物品不为空）
     */
    private void checkStatus() {
        if (mPresenter.checkStatus() && productAdapter.getData().size() > 0) { // 可以提交
            mTvSubmitClick.setEnabled(true);
            mTvSubmitClick.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
            mLlMoneyNull.setVisibility(View.GONE);
            mLlBottomMoney.setVisibility(View.VISIBLE);
            // 计算总费用价格
            mPresenter.calculate(productAdapter.getData());
        } else { // 有一个条件没有满足
            mTvSubmitClick.setEnabled(false);
            mTvSubmitClick.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999));
            mLlMoneyNull.setVisibility(View.VISIBLE);
            mLlBottomMoney.setVisibility(View.GONE);
        }
        if (productAdapter.getData().size() > 0) {
            mSbtSelectProduct.setText("新增物品");
        } else {
            mSbtSelectProduct.setText("选择物品");
        }
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        isSubmiting = false;
    }
}
