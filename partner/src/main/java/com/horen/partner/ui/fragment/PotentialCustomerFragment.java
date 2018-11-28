package com.horen.partner.ui.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.RecycleViewSmoothScroller;
import com.horen.partner.R;
import com.horen.partner.adapter.BarChartAdapter;
import com.horen.partner.adapter.BarChartXAdapter;
import com.horen.partner.adapter.PotentialListAdapter;
import com.horen.partner.bean.BarChartListBean;
import com.horen.partner.bean.PotentialBean;
import com.horen.partner.event.EventConstans;
import com.horen.base.constant.MsgEvent;
import com.horen.partner.mvp.contract.PotentialCustomerContract;
import com.horen.partner.mvp.model.PotentialCustomerModel;
import com.horen.partner.mvp.presenter.PotentialCustomerPresenter;
import com.horen.partner.ui.activity.customer.CustomerDetailActivity;
import com.horen.partner.ui.widget.MyHorizontalScrollView;
import com.horen.partner.util.RvEmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/6 17:22
 * Description:This isPotentialCustomerFragment 潜在客户
 */
public class PotentialCustomerFragment extends BaseFragment<PotentialCustomerPresenter, PotentialCustomerModel> implements PotentialCustomerContract.View, View.OnClickListener {
    private RecyclerView recyclerview_potential;
    private SmartRefreshLayout refreshview_potential;
    private PotentialListAdapter potentialListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PotentialBean.ListBean> data = new ArrayList<>();
    public static final int ADD_POENTIAL_CUSTOMER_CODE = 3;//新增潜在客户
    private int pageNum = 1;
    private int pageSize = 10;
    private RecyclerView recyclerview_y;
    private BarChartAdapter yAdapter;
    private RecyclerView.LayoutManager layoutManagerY;
    private RecyclerView.LayoutManager layoutManagerX;
    private LinearLayout ll_bar_height;
    private int barHeight;
    private RecyclerView recyclerview_x;
    private BarChartXAdapter xAdapter;
    private List<String> xValues = new ArrayList<>();
    private MyHorizontalScrollView scrollView_y;
    private MyHorizontalScrollView scrollView_x;

    public static PotentialCustomerFragment newInstance() {
        Bundle bundle = new Bundle();
        PotentialCustomerFragment potentialCustomerFragment = new PotentialCustomerFragment();
        potentialCustomerFragment.setArguments(bundle);
        return potentialCustomerFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partner_fragment_potential_customer;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initView(Bundle savedInstanceState) {
        recyclerview_x = (RecyclerView) rootView.findViewById(R.id.recyclerview_x);
        recyclerview_y = (RecyclerView) rootView.findViewById(R.id.recyclerview_y);
        ll_bar_height = (LinearLayout) rootView.findViewById(R.id.ll_bar_height);
        scrollView_x = (MyHorizontalScrollView) rootView.findViewById(R.id.scrollView_x);
        scrollView_y = (MyHorizontalScrollView) rootView.findViewById(R.id.scrollView_y);
        recyclerview_potential = (RecyclerView) rootView.findViewById(R.id.recyclerview_potential);
        refreshview_potential = (SmartRefreshLayout) rootView.findViewById(R.id.refreshview_potential);
        initRecycleView();
        initScrollView();
        EventBus.getDefault().register(this);
    }

    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(_mActivity);
        recyclerview_potential.setLayoutManager(layoutManager);
        //自定义分割线
        DividerItemDecoration itemDecoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(_mActivity, R.drawable.list_divider_10dp));
        recyclerview_potential.addItemDecoration(itemDecoration);
        potentialListAdapter = new PotentialListAdapter(R.layout.partner_item_potential_customer, new ArrayList<PotentialBean.ListBean>());
        potentialListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recyclerview_potential.setAdapter(potentialListAdapter);
        refreshview_potential.setEnableLoadMore(false);
//        refreshview_potential.setEnableAutoLoadMore(false);
        refreshview_potential.setEnableLoadMoreWhenContentNotFull(false);
        setRefresh();
        refreshview_potential.autoRefresh();
        potentialListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CustomerDetailActivity.launchActivity(_mActivity, data.get(position).getCustomer_id(), "potential");
            }
        });
        recyclerview_potential.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int viewId = view.getId();
                if (viewId == R.id.iv_detail) {
                    CustomerDetailActivity.launchActivity(_mActivity, data.get(position).getCustomer_id(), "potential");
                }
            }
        });
        layoutManagerY = new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerview_y.setLayoutManager(layoutManagerY);
        layoutManagerX = new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerview_x.setLayoutManager(layoutManagerX);
        xAdapter = new BarChartXAdapter(R.layout.partner_item_barchart_x, new ArrayList<String>());
        recyclerview_x.setAdapter(xAdapter);
        ViewTreeObserver vto = ll_bar_height.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_bar_height.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                barHeight = ll_bar_height.getHeight();
                yAdapter = new BarChartAdapter(R.layout.partner_item_barchart, new ArrayList<BarChartListBean>(), barHeight);
                yAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                recyclerview_y.setAdapter(yAdapter);
                refreshview_potential.autoRefresh();
            }
        });
        recyclerview_y.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                isRefresh = false;
                setDoubleRecyclerView(position);
                scrollToCenter(position);
            }
        });
        recyclerview_x.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                isRefresh = false;
                setDoubleRecyclerView(position);
                scrollToCenter(position);
            }
        });
    }

    private void initScrollView() {
        scrollView_x.setOtherHsv(scrollView_y);
        scrollView_y.setOtherHsv(scrollView_x);
    }

    private void setDoubleRecyclerView(int position) {
        xAdapter.setCurrentPosition(position);
        yAdapter.setCurrentPosition(position);
    }

    private void setRefresh() {
        refreshview_potential.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNum = 1;
                mPresenter.getPotentialList("", "");
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected void initToolbar(@NonNull Toolbar toolbar) {
        super.initToolbar(toolbar);
    }


    /**
     * 点击图表某条后使下方列表滚到到可见位置
     *
     * @param position
     */
    private void scrollToCenter(int position) {
        RecycleViewSmoothScroller smoothScroller = new RecycleViewSmoothScroller(_mActivity);
        smoothScroller.setTargetPosition(position);
        layoutManager.startSmoothScroll(smoothScroller);
        if (isRefresh) {
            position = -1;
        }
        potentialListAdapter.setSelectPostion(position);
    }

    @Override
    public void onClick(View view) {

    }

    private boolean isAddSuccess = false;
    private boolean isRefresh = false;

    @Override
    public void setPotentialData(List<PotentialBean.ListBean> result, int total) {
        potentialListAdapter.setNewData(result);
        if (isAddSuccess) {
            scrollToCenter(result.size() - 1);
        } else {
            isRefresh = true;
            scrollToCenter(0);
        }
        data.clear();
        data.addAll(result);
    }

    List<BarChartListBean> iBarData = new ArrayList<>();
    private boolean isFirstRefresh = true;

    @Override
    public void setBarData(List<PotentialBean.ListBean> result) {
        yAdapter.setCurrentPosition(-1);
        xAdapter.setCurrentPosition(-1);
        iBarData.clear();
        xValues.clear();
        if (result.size() != 0) {
            for (int j = 0; j < result.size(); j++) {
                xValues.add(getCutdownName(result.get(j).getCustomer_name()));
            }
            for (int i = 0; i < result.size(); i++) {
                iBarData.add(new BarChartListBean(result.get(i).getDays()));
            }
        }
        yAdapter.setNewData(iBarData);
        xAdapter.setNewData(xValues);
        if (isAddSuccess) {
            scrollToLeftOrRight(2, result.size() - 1);
        } else {
            if (!isFirstRefresh) {
                scrollToLeftOrRight(1, -1);
            }
        }
        isFirstRefresh = false;
    }

    private Handler handler = new Handler();

    /**
     * 不是新增数据刷新后滚动到最左边,反之滚动到最右边
     */
    private void scrollToLeftOrRight(final int type, final int position) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //设置ScrollView滚动到右边
                if (type == 2) {
                    scrollView_y.fullScroll(ScrollView.FOCUS_RIGHT);
                    yAdapter.setCurrentPosition(position);
                    xAdapter.setCurrentPosition(position);
                    isAddSuccess = false;
                } else {
                    //设置ScrollView滚动到左边
                    scrollView_y.fullScroll(ScrollView.FOCUS_LEFT);

                }
            }
        });
    }

    /**
     * 截取名字
     *
     * @param name
     * @return
     */
    private String getCutdownName(String name) {
        String s = null;
        if (name.length() > 4) {
            s = name.substring(0, 4) + "...";
        } else {
            s = name;
        }
        return s;
    }

    @Override
    public void showDataEmpty() {
        RvEmptyUtils.setEmptyView(potentialListAdapter, recyclerview_potential, R.layout.include_custom_data_empty);
    }

    private List<Integer> warings = new ArrayList<>();

    @Override
    public void setWaringPosition(List<Integer> waringPositions) {
        warings.addAll(waringPositions);
        yAdapter.setWaringPostionMax(waringPositions.size() - 1);
    }

    @Subscriber
    public void onEvent(MsgEvent event) {
        if (EventConstans.ADD_CUSTOMTER_SUCCESS.equals(event.getEvent())) {
            isAddSuccess = true;
            refreshview_potential.autoRefresh();
        }
        if (EventConstans.UPDATE_CUSTOMER_SUCCESS.equals(event.getEvent())) {
            refreshview_potential.autoRefresh();
        }
    }

    @Override
    protected void reLoadData() {
        refreshview_potential.autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        potentialListAdapter.removeHandler(true);
        handler.removeCallbacksAndMessages(null);
    }

}
