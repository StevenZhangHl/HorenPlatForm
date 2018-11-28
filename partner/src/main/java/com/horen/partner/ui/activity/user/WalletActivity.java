package com.horen.partner.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.Constants;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.NumberUtil;
import com.horen.base.util.ToastUitl;
import com.horen.partner.R;
import com.horen.partner.adapter.WalletBillAdapter;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.WalletBillBean;
import com.horen.partner.event.EventConstans;
import com.horen.base.constant.MsgEvent;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvMoney;
    private RelativeLayout mRlCashApply;
    private RecyclerView mRecyclerviewWallet;
    private WalletBillAdapter walletBillAdapter;
    private double money;
    private final int APPLYCASH_CODE = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("我的钱包", R.color.white);
        getTitleBar().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mRlCashApply = (RelativeLayout) findViewById(R.id.rl_cash_apply);
        mRecyclerviewWallet = (RecyclerView) findViewById(R.id.recyclerview_wallet);
        mRlCashApply.setOnClickListener(this);
        initRecyclerView();
        getIntentData();
    }

    private void initRecyclerView() {
        mRecyclerviewWallet.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.list_divider_f5));
        mRecyclerviewWallet.addItemDecoration(itemDecoration);
        walletBillAdapter = new WalletBillAdapter(R.layout.partner_item_wallet_list, new ArrayList<WalletBillBean.ListBean>());
        mRecyclerviewWallet.setAdapter(walletBillAdapter);
        mRecyclerviewWallet.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(WalletActivity.this, WalletBillDetailActivity.class);
                intent.putExtra("walletBillDetail", listBeans.get(position));
                startActivity(intent);
            }
        });
    }

    private void getIntentData() {
        money = getIntent().getDoubleExtra(Constants.PARTNER_USER_WALLET_MONEY, 0.00);
        mTvMoney.setText("¥ " + NumberUtil.formitNumber(money));
        getData();
    }

    private List<WalletBillBean.ListBean> listBeans = new ArrayList<>();

    private void getData() {
        mRxManager.add(ApiPartner.getInstance().getWalletListData(ApiRequest.getWalletList(1, 10)).compose(RxHelper.<WalletBillBean>getResult()).subscribeWith(new BaseObserver<WalletBillBean>(this, true) {
            @Override
            protected void onSuccess(WalletBillBean walletBillBean) {
                walletBillAdapter.setNewData(walletBillBean.getList());
                listBeans.clear();
                listBeans.addAll(walletBillBean.getList());
            }

            @Override
            protected void onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view == mRlCashApply) {
            Intent intent = new Intent();
            intent.setClass(this, CashApplyActivity.class);
            intent.putExtra("cashMoney", money);
            startActivityForResult(intent, APPLYCASH_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == APPLYCASH_CODE) {
                double applyMoney = data.getDoubleExtra("applyMoney", 0);
                money = money - applyMoney;
                mTvMoney.setText("¥ " + NumberUtil.formitNumber(money));
                EventBus.getDefault().post(new MsgEvent(EventConstans.REFRESH_WALLET_AMOUNT));
                getData();
            }
        }
    }
}
