package com.cyy.company.ui.fragment.order;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.allen.library.SuperButton;
import com.cyy.company.R;
import com.cyy.company.bean.RenewalRtp;
import com.cyy.company.bean.SubmitOrder;
import com.cyy.company.enums.OrderType;
import com.cyy.company.mvp.contract.RenewalBoxContract;
import com.cyy.company.mvp.model.RenewalBoxModel;
import com.cyy.company.mvp.presenter.RenewalBoxPresenter;
import com.cyy.company.ui.activity.order.OrderSuccessActivity;
import com.cyy.company.ui.adapter.RenewalRtpAdapter;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.DividerItemDecoration_1;
import com.horen.base.util.WeakHandler;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/19/16:08
 * @description :续租
 * @github :https://github.com/chenyy0708
 */
public class RenewalBoxFragment extends BaseFragment<RenewalBoxPresenter, RenewalBoxModel> implements View.OnClickListener, RenewalBoxContract.View, RenewalRtpAdapter.DeleteListener {

    private LinearLayout mLlRentalDays;
    private RecyclerView mRecyclerView;
    private LinearLayout mLlTransportMode;
    private EditText mEtRemarks;
    private LinearLayout mLlBottomMoney;
    private LinearLayout mLlMoneyNull;
    private SuperButton mTvSubmitClick;
    private RenewalRtpAdapter productAdapter;
    private LinearLayout mLlBottom;

    /**
     * 是否在提交中
     */
    boolean isSubmiting = false;

    public static RenewalBoxFragment newInstance() {
        Bundle bundle = new Bundle();
        RenewalBoxFragment fragment = new RenewalBoxFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_renewal_box;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.setRootView(rootView);
        mLlRentalDays = (LinearLayout) rootView.findViewById(R.id.ll_rental_days);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLlTransportMode = (LinearLayout) rootView.findViewById(R.id.ll_transport_mode);
        mEtRemarks = (EditText) rootView.findViewById(R.id.et_remarks);
        mLlBottom = (LinearLayout) rootView.findViewById(R.id.ll_bottom);
        mLlBottomMoney = (LinearLayout) rootView.findViewById(R.id.ll_bottom_money);
        mLlMoneyNull = (LinearLayout) rootView.findViewById(R.id.ll_money_null);
        mTvSubmitClick = (SuperButton) rootView.findViewById(R.id.tv_submit_click);
        rootView.findViewById(R.id.iv_right_arrow).setVisibility(View.GONE);
        mTvSubmitClick.setOnClickListener(this);
        mLlRentalDays.setOnClickListener(this);
        mLlTransportMode.setOnClickListener(this);
        mLlBottomMoney.setOnClickListener(this);
        initListener();
        initRecyclerView();
        // 获取默认的网点地址
        mPresenter.getDefaultOrgsList();
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
        productAdapter = new RenewalRtpAdapter(new ArrayList<RenewalRtp.PdListBean>(), this);
        mRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_rental_days) { // 租赁天数
            mPresenter.showRentalDays();
        } else if (view.getId() == R.id.ll_transport_mode) { // 配送方式
            mPresenter.showTransportMode();
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
     * 选择租赁天数
     */
    @Override
    public void onSelectRentalDays(String rental_days) {
        // 获取新的续租物品
        mPresenter.getProductsList(rental_days);
    }

    /**
     * 获取续租物品
     *
     * @param mData
     */
    @Override
    public void getProductsList(List<RenewalRtp.PdListBean> mData) {
        productAdapter.setNewData(mData);
        checkStatus();
    }

    @Override
    public void onSelectTransportMode(String flag_send) {
        checkStatus();
    }

    /**
     * 选择物品Adapter，用于提交订单
     */
    @Override
    public RenewalRtpAdapter getAdapter() {
        return productAdapter;
    }

    /**
     * 提交订单成功
     */
    @Override
    public void onSubmitSuccess(SubmitOrder order) {
        isSubmiting = false;
        // 打开提交订单成功页面
        OrderSuccessActivity.startAction(_mActivity, order.getOrder_idS(), order.getOrder_idZ(), OrderType.THREE.getPosition(), mPresenter.getAddress());
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
            int returnBoxNumber = 0;
            for (RenewalRtp.PdListBean pdListBean : productAdapter.getData()) {
                returnBoxNumber += pdListBean.getOrder_qty();
            }
            if (returnBoxNumber != 0) { // 还箱数不能为0
                mTvSubmitClick.setEnabled(true);
                mTvSubmitClick.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
                mLlMoneyNull.setVisibility(View.GONE);
                mLlBottomMoney.setVisibility(View.VISIBLE);
                // 计算总费用价格
                mPresenter.calculate(productAdapter.getData());
            } else {
                mTvSubmitClick.setEnabled(false);
                mTvSubmitClick.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999));
                mLlMoneyNull.setVisibility(View.VISIBLE);
                mLlBottomMoney.setVisibility(View.GONE);
            }
        } else { // 有一个条件没有满足
            mTvSubmitClick.setEnabled(false);
            mTvSubmitClick.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999));
            mLlMoneyNull.setVisibility(View.VISIBLE);
            mLlBottomMoney.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        isSubmiting = false;
    }

}
