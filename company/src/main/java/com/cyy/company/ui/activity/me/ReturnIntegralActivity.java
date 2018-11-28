package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.ReturnIntegra;
import com.cyy.company.ui.adapter.ReturnIntegralAdapter;
import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerTopBottomDecoration_1;
import com.horen.base.widget.HRTitle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/11/19/09:57
 * @description :还箱点
 * @github :https://github.com/chenyy0708
 */
public class ReturnIntegralActivity extends BaseActivity {

    private SmartRefreshLayout refreshLayout;
    private TextView mTvMouth;
    private TextView mTvObtain;
    private TextView mTvUse;
    private RecyclerView mRecyclerView;

    private HRTitle toolBar;
    private ReturnIntegralAdapter.IntegralAdapter integralAdapter;

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ReturnIntegralActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_return_integral;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        toolBar = (HRTitle) findViewById(R.id.tool_bar);
        toolBar.bindActivity(this, R.color.white, "我");
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mTvMouth = (TextView) findViewById(R.id.tv_mouth);
        mTvObtain = (TextView) findViewById(R.id.tv_Obtain);
        mTvUse = (TextView) findViewById(R.id.tv_use);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerTopBottomDecoration_1(mContext));
        integralAdapter = new ReturnIntegralAdapter.IntegralAdapter(new ArrayList<ReturnIntegra.PdListBean>());
        mRecyclerView.setAdapter(integralAdapter);
        getCusLinesList();
    }

    private void getCusLinesList() {
        mRxManager.add(ApiCompany.getInstance().getCusLinesList(CompanyParams.getMessageList(1, 100))
                .compose(RxHelper.<ReturnIntegra>getResult())
                .subscribeWith(new BaseObserver<ReturnIntegra>() {
                    @Override
                    protected void onSuccess(ReturnIntegra entry) {
                        // 还箱点总数
                        mTvObtain.setText(String.valueOf(entry.getCount()));
                        if (!CollectionUtils.isNullOrEmpty(entry.getPdList())) {
                            integralAdapter.setNewData(entry.getPdList());
                        } else {
                            showEmpty();
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }
}
