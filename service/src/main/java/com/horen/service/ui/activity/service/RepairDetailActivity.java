package com.horen.service.ui.activity.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DisplayUtil;
import com.horen.base.widget.CortpHeader;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.bean.RepairListDetailBean;
import com.horen.service.bean.ServiceListBean;
import com.horen.service.enumeration.service.ServiceType;
import com.horen.service.ui.activity.adapter.RepairDetailAdapter;
import com.horen.service.utils.OrderUtils;
import com.horen.service.utils.RvEmptyUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/08:40
 * @description : 维修详情
 * @github :https://github.com/chenyy0708
 */
public class RepairDetailActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private TextView mTvType;
    private TextView mTvModel;
    private TextView mTvDamage;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private RepairDetailAdapter detailAdapter;
    private NestedScrollView nestedScrollView;
    private LinearLayout llHeader;
    private int headerHeight;
    private Toolbar mToolBar;
    private TextView mTitle;

    private int mOffset = 0;
    private FrameLayout flToolBar;
    /**
     * 第几页
     */
    public int pageNum = 1;
    /**
     * 每页行数
     */
    public int pageSize = 5;
    /**
     * 维修物品信息
     */
    private ServiceListBean.ServiceBean repairBean;


    public static void startActivity(Context context, ServiceListBean.ServiceBean repairBean) {
        Intent intent = new Intent();
        intent.putExtra("repairBean", repairBean);
        intent.setClass(context, RepairDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_repair_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        flToolBar = (FrameLayout) findViewById(R.id.fl_tool_bar);
        llHeader = (LinearLayout) findViewById(R.id.ll_header);
        nestedScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvModel = (TextView) findViewById(R.id.tv_model);
        mTvDamage = (TextView) findViewById(R.id.tv_damage);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        initToolbar(mToolBar, false);
        initRecyclerView();
        repairBean = (ServiceListBean.ServiceBean) getIntent().getSerializableExtra("repairBean");
        initData(true);
    }

    private void initData(boolean isShowDialog) {
        // 型号
        mTvType.setText(repairBean.getProduct_name());
        // 名称
        mTvModel.setText(repairBean.getProduct_type());
        // 损坏量
        mTvDamage.setText(String.valueOf(repairBean.getService_qty()));
        // 获取数据
        mRxManager.add(Api.getInstance()
                .getServiceRtpInfo(ServiceParams.getServiceRtpInfo(pageNum, pageSize, repairBean.getProduct_id(), "1", ServiceType.REPAIR.getStatus()))
                .compose(RxHelper.<RepairListDetailBean>getResult())
                .subscribeWith(new BaseObserver<RepairListDetailBean>(mContext, isShowDialog) {
                    @Override
                    protected void onSuccess(RepairListDetailBean detailBean) {
                        // 加载更多
                        if (pageNum > 1) {
                            detailAdapter.addData(detailBean.getPageInfo().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(detailBean.getPageInfo().getList())) {
                                setEmptyView();
                            } else {
                                detailAdapter.setNewData(detailBean.getPageInfo().getList());
                                mTvDamage.setText(String.valueOf(detailBean.getPageInfo().getTotal()));
                            }
                            mRefreshLayout.finishRefresh(0);
                        }
                        // 是否还有下一页数据
                        mRefreshLayout.setNoMoreData(!detailBean.getPageInfo().isHasNextPage());
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        // 当加载下一页时，错误  应该减去一页，防止漏加载数据
                        if (pageNum > 1) {
                            pageNum--;
                        } else {
                            setEmptyView();
                        }
                        mRefreshLayout.finishRefresh(false);
                        mRefreshLayout.finishLoadMore(false);
                    }
                }));
    }

    /**
     * 初始化刷新头部和列表
     */
    private void initRecyclerView() {
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        CortpHeader cortpHeader = new CortpHeader(mContext);
        cortpHeader.setMainColorBg(ContextCompat.getColor(mContext, R.color.transparent));
        mRefreshLayout.setRefreshHeader(cortpHeader);
        ViewGroup.LayoutParams layoutParams = cortpHeader.getLayoutParams();
        layoutParams.height = DisplayUtil.dip2px(75);
        cortpHeader.setLayoutParams(layoutParams);
        // 上拉下拉刷新监听
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        detailAdapter = new RepairDetailAdapter(R.layout.service_item_repair_detail);
        mRecyclerView.setAdapter(detailAdapter);
        nestedScrollView.setOnScrollChangeListener(this);
        // 监听头部高度
        llHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llHeader.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                headerHeight = llHeader.getHeight();
            }
        });
        // 刷新头部显示
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
                mOffset = offset / 2;
                flToolBar.setAlpha(1 - Math.min(percent, 1));
                mTitle.setAlpha(1 - Math.min(percent, 1));
            }

            @Override
            public void onHeaderReleasing(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
                mOffset = offset / 2;
                flToolBar.setAlpha(1 - Math.min(percent, 1));
                mTitle.setAlpha(1 - Math.min(percent, 1));
            }
        });
        detailAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        // 隐藏系统状态栏
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

    @Override
    protected void setFitsSystemWindows() {
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY <= 0) {
            mToolBar.setBackgroundColor(Color.argb(0, 135, 205, 37));
        } else if (scrollY > 0 && scrollY <= headerHeight) {
            float scale = (float) scrollY / headerHeight;
            float alpha = (255 * scale);
            mToolBar.setBackgroundColor(Color.argb((int) alpha, 135, 205, 37));
        } else {
            mToolBar.setBackgroundColor(Color.argb(255, 135, 205, 37));
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageNum++;
        initData(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNum = 1;
        initData(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        // 待维修
        RepairDetailBean.ServiceListBean bean = detailAdapter.getData().get(position);
        if (!OrderUtils.checkServiceStatus(bean.getService_status())) {
            RepairDealWithActivity.startActivity(mContext, bean.getService_id());
        }
    }

    /**
     * 暂无数据
     */
    private void setEmptyView() {
        RvEmptyUtils.setEmptyView(detailAdapter, mRecyclerView);
        // 设置RecycleView高度，防止空VIew位置错乱
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
//        layoutParams.height = DisplayUtil.getScreenHeight(mContext) - headerHeight;
//        mRecyclerView.setLayoutParams(layoutParams);
    }

    /**
     * 刷新列表数据
     */
    @Subscriber(tag = EventBusCode.REFRESH_REPAIR_LIST)
    private void refreshList(String s) {
        pageNum = 1;
        initData(false);
        // 删除成功
        if (s.equals(EventBusCode.DELETE)) {
            // 删除成功，损坏量减1
            repairBean.setService_qty(repairBean.getService_qty() - 1);
            mTvDamage.setText(String.valueOf(repairBean.getService_qty()));
            // 通知待维修刷新
            EventBus.getDefault().post(EventBusCode.REFRESH_REPAIR_FRAGMENT_LIST, EventBusCode.REFRESH_REPAIR_FRAGMENT_LIST);
        }
    }
}
