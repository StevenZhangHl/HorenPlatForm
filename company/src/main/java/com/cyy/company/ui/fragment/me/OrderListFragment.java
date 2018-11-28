package com.cyy.company.ui.fragment.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.EvalNotify;
import com.cyy.company.bean.OrderListBean;
import com.cyy.company.bean.TabTextDto;
import com.cyy.company.enums.OrderStatus;
import com.cyy.company.ui.activity.me.EvaluationActivity;
import com.cyy.company.ui.activity.me.OrderDetailActivity;
import com.cyy.company.ui.adapter.OrderListAdapter;
import com.cyy.company.utils.RvEmptyUtils;
import com.horen.base.base.BaseFragment;
import com.horen.base.bean.OrderPopupBean;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.DividerItemDecoration;
import com.horen.base.widget.CortpFooter;
import com.horen.base.widget.SelectDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/16/09:45
 * @description :订单列表Fragment
 * @github :https://github.com/chenyy0708
 */
public class OrderListFragment extends BaseFragment implements View.OnClickListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    private TextView mTvOrderStatus;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    // 订单类型
    private String order_type;
    // 订单状态  默认全部
    private String order_status = OrderStatus.ALL.getPosition();
    //二级Tab标题
    private ArrayList<TabTextDto> textDtos;
    //记录二级筛选选中
    private int orderTypePosition = 0;
    private LinearLayout mLLOrderStatus;
    private View viewDialogScreen;
    /**
     * 第几页
     */
    public int pageNum = 1;
    /**
     * 每页行数
     */
    public int pageSize = 10;
    private OrderListAdapter orderListAdapter;

    private boolean isInit;
    private LinearLayoutManager layoutManager;

    /**
     * 状态按钮是否在左边
     */
    private boolean isInLeft = false;

    public static OrderListFragment newInstance(ArrayList<TabTextDto> textDtos, String order_type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("textDtos", textDtos);
        bundle.putString("order_type", order_type);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        viewDialogScreen = (View) rootView.findViewById(R.id.view_dialog_screen);
        mTvOrderStatus = (TextView) rootView.findViewById(R.id.tv_order_status);
        mLLOrderStatus = (LinearLayout) rootView.findViewById(R.id.ll_order_status);
        mRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mTvOrderStatus.setOnClickListener(this);
        order_type = getArguments().getString("order_type");
        textDtos = (ArrayList<TabTextDto>) getArguments().getSerializable("textDtos");
        initRecycleView();
        getOrderList();
        isInit = true;
    }

    /**
     * 初始化列表
     */
    private void initRecycleView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(_mActivity));
        orderListAdapter = new OrderListAdapter(new ArrayList<OrderListBean.PdListBean.ListBeanX>(), order_type);
        orderListAdapter.setOnItemChildClickListener(this);
        orderListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(orderListAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
                // 移动到中间
                if (firstItemPosition == 0) {
                    if (isInLeft) {
                        int distance = mLLOrderStatus.getWidth() / 2 - mTvOrderStatus.getWidth() / 2 - DisplayUtil.dip2px(5);
                        isInLeft = false;
                        AnimationUtils.translationViewX(mTvOrderStatus, -Float.valueOf(distance), 0, 300);
                    }
                } else { // 移动到屏幕左边
                    if (!isInLeft) {
                        int distance = mLLOrderStatus.getWidth() / 2 - mTvOrderStatus.getWidth() / 2 - DisplayUtil.dip2px(5);
                        isInLeft = true;
                        AnimationUtils.translationViewX(mTvOrderStatus, 0, -Float.valueOf(distance), 300);
                    }
                }
            }
        });
    }

    /**
     * 获取订单列表
     */
    private void getOrderList() {
        mRxManager.add(ApiCompany.getInstance()
                .getOrderList(CompanyParams.getOrderList(order_type, order_status, pageNum, pageSize))
                .compose(RxHelper.<OrderListBean>getResult())
                .subscribeWith(new BaseObserver<OrderListBean>() {
                    @Override
                    protected void onSuccess(OrderListBean bean) {
                        // 加载更多
                        if (pageNum > 1) {
                            orderListAdapter.addData(bean.getPdList().getList());
                            mRefreshLayout.finishLoadMore(0);
                        } else {
                            // 没有数据展示空View
                            if (CollectionUtils.isNullOrEmpty(bean.getPdList().getList())) {
                                RvEmptyUtils.setEmptyView(orderListAdapter, mRecyclerView);
                            } else {
                                orderListAdapter.setNewData(bean.getPdList().getList());
                            }
                            mRefreshLayout.finishRefresh(0);
                        }
                        // 设置脚布局加载完成文字
                        if (!bean.getPdList().isHasNextPage()) {
                            CortpFooter footer = (CortpFooter) mRefreshLayout.getRefreshFooter();
                            if (footer != null)
                                footer.setLoadCompleteText(R.string.show_orders_nearly_one_year);
                        }
                        // 是否还有下一页数据
                        mRefreshLayout.setNoMoreData(!bean.getPdList().isHasNextPage());
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        // 当加载下一页时，错误  应该减去一页，防止漏加载数据
                        if (pageNum > 1) {
                            pageNum--;
                        } else {
                            RvEmptyUtils.setEmptyView(orderListAdapter, mRecyclerView);
                        }
                        mRefreshLayout.finishRefresh(false);
                        mRefreshLayout.finishLoadMore(false);
                    }
                }));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_order_status) { // 筛选订单
            List<OrderPopupBean> orderStatusList = new ArrayList<>();
            for (TabTextDto tabTextDto : textDtos) {
                orderStatusList.add(new OrderPopupBean(tabTextDto.getText(), tabTextDto.getPosition()));
            }
            new SelectDialog(getActivity(), orderStatusList, orderTypePosition)
                    .setSelectLinstener(new SelectDialog.onSelectLinstener() {
                        @Override
                        public void onItemSelectLinstener(int position, String name, String type) {
                            if (!mRefreshLayout.isRefreshing()) {
                                mTvOrderStatus.setText(name);
                                orderTypePosition = position;
                                order_status = type;
                                mRefreshLayout.autoRefresh();
                            }
                        }
                    }).showPopupWindow(viewDialogScreen);
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageNum++;
        getOrderList();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNum = 1;
        getOrderList();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        // 评价
        if (view.getId() == R.id.sbt_evaluation) {
            OrderListBean.PdListBean.ListBeanX bean = orderListAdapter.getItem(position);
            String picUrl = "";
            if (!CollectionUtils.isNullOrEmpty(bean.getList())) {
                picUrl = bean.getList().get(0).getProduct_photo();
            }
            EvaluationActivity.startAction(_mActivity, bean.getOrder_id(), picUrl, bean.getEval_status(), position);
        }
    }

    /**
     * 评价成功，刷新页面
     */
    @Subscriber(tag = EventBusCode.EVAL_COMPLETE)
    private void evalComplete(EvalNotify evalNotify) {
        if (!isInit) return;
        OrderListBean.PdListBean.ListBeanX bean = orderListAdapter.getItem(evalNotify.getPosition());
        // 当前订单已经评价
        if (evalNotify.getOrder_id().equals(bean.getOrder_id())) {
            bean.setEval_status("1");
            orderListAdapter.notifyItemChanged(evalNotify.getPosition());
        }
    }

    /**
     * 取消订单成功
     */
    @Subscriber(tag = EventBusCode.CANCEL_ORDER)
    private void canCelOrder(EvalNotify evalNotify) {
        if (!isInit) return;
//        OrderListBean.PdListBean.ListBeanX bean = orderListAdapter.getItem(evalNotify.getPosition());
//        // 当前订单已经取消
//        if (evalNotify.getOrder_id().equals(bean.getOrder_id())) {
//            bean.setOrder_status(OrderStatus.FOUR.getPosition());
//            orderListAdapter.notifyItemChanged(evalNotify.getPosition());
//        }
        pageNum = 1;
        getOrderList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderListBean.PdListBean.ListBeanX bean = orderListAdapter.getItem(position);
        String picUrl = "";
        if (!CollectionUtils.isNullOrEmpty(bean.getList())) {
            picUrl = bean.getList().get(0).getProduct_photo();
        }
        OrderDetailActivity.startAction(_mActivity, bean.getOrder_id()
                , picUrl, position, "订单列表");
    }
}
