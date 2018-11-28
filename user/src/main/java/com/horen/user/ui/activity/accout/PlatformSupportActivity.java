package com.horen.user.ui.activity.accout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.SheetDialog;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.bean.PlatFormSupportBean;
import com.horen.user.ui.adapter.PlatformSupportAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zhao on 2017/12/6/006.
 */
@Route(path = ARouterPath.PLATFORM_SUPPORT)
public class PlatformSupportActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, OnOperItemClickL {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private PlatformSupportAdapter adapter;
    private SheetDialog dialog;

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PlatformSupportActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_platform_support;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView(Bundle bundle) {
        showWhiteTitle("平台支援人员", R.color.white);
        getTitleBar().setBackgroundResource(R.color.white);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlatformSupportAdapter(R.layout.user_item_platform_supoort, new ArrayList<PlatFormSupportBean>());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        dialog = new SheetDialog(mContext, new String[]{"拨打电话"}, null)
                .isTitleShow(false)
                .cancelText("取消");
        dialog.setOnOperItemClickL(this);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getSupportData();
            }
        });
        mRefreshLayout.autoRefresh();
    }

    private void getSupportData() {
        mRxManager.add(ApiUser.getInstance().getPlatFormSupport(UserApiPram.getPlatFormSupport(UserHelper.getUserInfo().getLoginInfo().getUser_id()))
                .compose(RxHelper.<List<PlatFormSupportBean>>getResult())
                .subscribeWith(new BaseObserver<List<PlatFormSupportBean>>() {
                    @Override
                    protected void onSuccess(List<PlatFormSupportBean> platFormSupportBeans) {
                        showSuccess();
                        if (platFormSupportBeans.size() != 0) {
                            adapter.setNewData(platFormSupportBeans);
                        } else {
                            showEmpty();
                        }
                        mRefreshLayout.finishRefresh(0);
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        mRefreshLayout.finishRefresh(0);
                    }
                }));
    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tv_item_phone) {
            dialog.show();
        }
    }

    @Override
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: // 拨打电话
                call("18370622030");
                break;
        }
        dialog.dismiss();
    }
}

