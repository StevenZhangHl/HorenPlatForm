package com.cyy.company.ui.activity.me;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.cyy.company.R;
import com.cyy.company.bean.EvalNotify;
import com.cyy.company.bean.OrderDetailBean;
import com.cyy.company.enums.OrderStatus;
import com.cyy.company.enums.OrderType;
import com.cyy.company.mvp.contract.OrderDetailContract;
import com.cyy.company.mvp.model.OrderDetailModel;
import com.cyy.company.mvp.presenter.OrderDetailPresenter;
import com.cyy.company.ui.adapter.OrderDetailAdapter;
import com.cyy.company.ui.adapter.OrderOverPriceAdapter;
import com.cyy.company.utils.DateUtils;
import com.cyy.company.utils.OrderUtils;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.EventBusCode;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DividerItemDecoration_1;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.NumberUtil;
import com.horen.base.util.SpanUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;
import com.kingja.loadsir.core.LoadSir;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/16/15:31
 * @description :订单详情
 * @github :https://github.com/chenyy0708
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter, OrderDetailModel> implements View.OnClickListener, OrderDetailContract.View {

    private HRTitle mToolBar;
    private FrameLayout mFlOrderHeader;
    private TextView mTvOrderStatus;
    private TextView mTvOrderTime;
    private ImageView mIvOrderStatus;
    private TextView mTvFromName;
    private TextView mTvFromPhone;
    private TextView mTvFromOrgAddress;
    private TextView mTvToName;
    private TextView mTvToPhone;
    private TextView mTvToOrgAddress;
    private RecyclerView mRecyclerView;
    private TextView mTvRentMoney;
    private TextView mTvSuppliesMoney;
    private TextView mTvFreightMoney;
    private TextView mTvCleaningMoney;
    private TextView mTvAllMoney;
    private TextView mTvPreferentialMoney;
    private TextView mTvActualMoney;
    private TextView mTvDayCount;
    private TextView mTvTransportMode;
    private TextView mTvRenewalRent;
    private TextView mTvEstimatedTime;
    private TextView mTvPreferentialTip;
    private TextView mTvRemarks;
    private TextView mTvOrderNumber;
    private TextView mTvOrderInfoTime;
    private TextView mTvOrderInfoName;
    private FrameLayout mLlBottom;
    private SuperButton mSbtBottom;

    /**
     * 订单号
     */
    private String order_id;
    /**
     * 订单状态
     */
    private String order_status;
    /**
     * 订单类型
     */
    private String order_type;
    /**
     * 评价状态
     */
    private String eval_status;
    private TextView mTvOrgType;
    private OrderDetailAdapter detailAdapter;
    private String photo;
    private int position;
    private LinearLayout mLLMoney;
    private TextView mTvActualMoneyName;

    private OrderDetailBean.PageInfoBean addressBean;
    private RecyclerView mRvOverDueDetail;

    /**
     * @param order_id 订单号
     * @param position 列表位置
     * @param title    上一个页面标题
     */
    public static void startAction(Context context, String order_id,
                                   String photo, int position, String title) {
        Intent intent = new Intent();
        intent.putExtra("order_id", order_id);
        intent.putExtra("position", position);
        intent.putExtra("photo", photo);
        intent.putExtra("title", title);
        intent.setClass(context, OrderDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mFlOrderHeader = (FrameLayout) findViewById(R.id.fl_order_header);
        mTvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        mTvOrgType = (TextView) findViewById(R.id.tv_org_type);
        mTvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        mIvOrderStatus = (ImageView) findViewById(R.id.iv_order_status);
        mTvFromName = (TextView) findViewById(R.id.tv_from_name);
        mTvFromPhone = (TextView) findViewById(R.id.tv_from_phone);
        mTvFromOrgAddress = (TextView) findViewById(R.id.tv_from_org_address);
        mTvToName = (TextView) findViewById(R.id.tv_to_name);
        mTvToPhone = (TextView) findViewById(R.id.tv_to_phone);
        mTvToOrgAddress = (TextView) findViewById(R.id.tv_to_org_address);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRvOverDueDetail = (RecyclerView) findViewById(R.id.rv_overdue_detail);
        mTvRentMoney = (TextView) findViewById(R.id.tv_rent_money);
        mTvSuppliesMoney = (TextView) findViewById(R.id.tv_supplies_money);
        mTvFreightMoney = (TextView) findViewById(R.id.tv_freight_money);
        mTvCleaningMoney = (TextView) findViewById(R.id.tv_cleaning_money);
        mTvAllMoney = (TextView) findViewById(R.id.tv_all_money);
        mTvPreferentialMoney = (TextView) findViewById(R.id.tv_preferential_money);
        mTvActualMoney = (TextView) findViewById(R.id.tv_actual_money);
        mTvDayCount = (TextView) findViewById(R.id.tv_day_count);
        mTvTransportMode = (TextView) findViewById(R.id.tv_transport_mode);
        mTvRenewalRent = (TextView) findViewById(R.id.tv_renewal_rent);
        mTvEstimatedTime = (TextView) findViewById(R.id.tv_estimated_time);
        mTvPreferentialTip = (TextView) findViewById(R.id.tv_preferential_tip);
        mTvRemarks = (TextView) findViewById(R.id.tv_remarks);
        mTvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        mTvOrderInfoTime = (TextView) findViewById(R.id.tv_order_info_time);
        mTvOrderInfoName = (TextView) findViewById(R.id.tv_order_info_name);
        mLlBottom = (FrameLayout) findViewById(R.id.ll_bottom);
        mLLMoney = (LinearLayout) findViewById(R.id.ll_money);
        mTvActualMoneyName = (TextView) findViewById(R.id.tv_actual_money_name);
        mSbtBottom = (SuperButton) findViewById(R.id.sbt_bottom);
        mToolBar.bindActivity(this, R.color.white, getIntent().getStringExtra("title"));
        mBaseLoadService = LoadSir.getDefault().register(findViewById(R.id.ll_content), this);
        mSbtBottom.setOnClickListener(this);
        findViewById(R.id.tv_copy).setOnClickListener(this);
        mFlOrderHeader.setOnClickListener(this);
        order_id = getIntent().getStringExtra("order_id");
        photo = getIntent().getStringExtra("photo");
        position = getIntent().getIntExtra("position", 0);
        // 初始化物品列表
        initRecyclerView();
        // 获取订单详情数据
        mPresenter.getOrderLineList(order_id, "", "",
                UserHelper.getUserInfo().getLoginInfo().getCompany_id());
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration_1(this));
        detailAdapter = new OrderDetailAdapter(new ArrayList<OrderDetailBean.PageInfoBean.ListBean>());
        mRecyclerView.setAdapter(detailAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sbt_bottom) {
            if (order_status.equals(OrderStatus.FIVE.getPosition()) || order_status.equals(OrderStatus.SIX.getPosition())) { // 评价、已评价
                EvaluationActivity.startAction(mContext, order_id, photo,
                        eval_status, position);
            } else if (order_status.equals(OrderStatus.ONE.getPosition())) { // 待处理显示取消订单
                mPresenter.cancelOrderInfo(order_id);
            }
        } else if (view.getId() == R.id.tv_copy) { // 复制
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(order_id);
            showToast("复制订单号号成功");
        } else if (view.getId() == R.id.fl_order_header) { // 物流跟踪
            LogisticsMapActivity.startAction(mContext, addressBean);
        }
    }

    /**
     * 评价成功，刷新页面
     */
    @Subscriber(tag = EventBusCode.EVAL_COMPLETE)
    private void evalComplete(EvalNotify evalNotify) {
        // 当前订单已经评价
        if (order_id.equals(evalNotify.getOrder_id())) {
            eval_status = "1";
            mSbtBottom.setText("已评价");
        }
    }

    /**
     * 收发货地址
     *
     * @param bean
     */
    @Override
    public void getAddressSuccess(OrderDetailBean.PageInfoBean bean) {
        order_status = bean.getOrder_status();
        order_type = bean.getOrder_type();
        eval_status = bean.getEval_status();
        addressBean = bean;
        // 执行状态
        mTvOrderStatus.setText(OrderStatus.fromTabPosition(bean.getOrder_status()));
        // 时间
        mTvOrderTime.setText(bean.getCreate_date());
        // 状态图片
        mIvOrderStatus.setImageResource(OrderUtils.getOrderHeaderImg(bean.getOrder_status()));
        // 背景图片
        mFlOrderHeader.setBackgroundResource(OrderUtils.getOrderHeaderBG(bean.getOrder_status()));
        // 网点地址
        mTvOrgType.setText(bean.getOrder_type().equals(OrderType.ONE.getPosition()) ? "租箱网点" : "还箱网点");
        mTvFromName.setText(bean.getEnd_contact());
        mTvFromPhone.setText(FormatUtil.phoneSetMiddleEmpty(bean.getEnd_tel()));
        mTvFromOrgAddress.setText(new SpanUtils()
                .append(" " + bean.getEnd_orgname() + " ")
                .setFontSize(12, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.base_text_color_light))
                .setBackgroundColor(Color.parseColor("#336fba2c"))
                .append("  " + bean.getEnd_address())
                .setFontSize(13, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333)).create());
        // 百网千翌地址
        mTvToName.setText(bean.getStart_contact());
        mTvToPhone.setText(FormatUtil.phoneSetMiddleEmpty(bean.getStart_tel()));
        mTvToOrgAddress.setText(new SpanUtils()
                .append(" " + bean.getStart_orgname() + " ")
                .setFontSize(12, true)
                .setForegroundColor(Color.parseColor("#F15B02"))
                .setBackgroundColor(Color.parseColor("#33f15b02"))
                .append("  " + bean.getStart_address())
                .setFontSize(13, true)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333)).create());
        // 备注
        mTvRemarks.setText(TextUtils.isEmpty(bean.getOrder_note()) ? "无" : bean.getOrder_note());
        // 已完成、异常完成显示评价、已评价
        if (bean.getOrder_status().equals(OrderStatus.FIVE.getPosition()) || bean.getOrder_status().equals(OrderStatus.SIX.getPosition())) {
            mLlBottom.setVisibility(View.VISIBLE);
            mSbtBottom.setText(eval_status.equals("1") ? "已评价" : "评价");
        } else if (bean.getOrder_status().equals(OrderStatus.ONE.getPosition())) { // 待处理显示取消订单
            mLlBottom.setVisibility(View.VISIBLE);
            mSbtBottom.setText("取消订单");
        }
    }

    /**
     * 订单物品列表
     */
    @Override
    public void orderProducts(OrderDetailBean.PageInfoBean bean) {
        detailAdapter.setNewData(bean.getList());
        if (!CollectionUtils.isNullOrEmpty(bean.getList())) {
            photo = bean.getList().get(0).getProduct_photo();
        }
        if (bean.getOrder_type().equals(OrderType.ONE.getPosition())) {
            mLLMoney.setVisibility(View.VISIBLE);
            // 租箱费
            mTvRentMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_rentar()));
            // 耗材费
            mTvSuppliesMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_salear()));
            // 运费
            mTvFreightMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_tmsar()));
            // 清洗费
            mTvCleaningMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_washar()));
            // 总费用
            mTvAllMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_ar() + bean.getTotal_discountar()));
            // 优惠
            mTvPreferentialMoney.setText("- ¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_discountar()));
            mTvActualMoneyName.setText("实付金额");
        } else { // 还箱只有运费
            mLLMoney.setVisibility(View.GONE);
            mTvActualMoneyName.setText("运费");
        }
        // 实付金额
        mTvActualMoney.setText("¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_ar()));
        // 租赁天数

    }

    /**
     * 租赁方式
     *
     * @param bean
     */
    @Override
    public void leaseMode(OrderDetailBean.PageInfoBean bean) {
        if (bean.getOrder_type().equals(OrderType.ONE.getPosition())) {
            // 租赁天数
            mTvDayCount.setText(bean.getRent_days() + "天");
            // 运输方式  :0：配送 1：自提
            mTvTransportMode.setText(bean.getFlag_send().equals("0") ? "配送" : "自提");
            // 是否续租 (是否续租):0-正常 1-续租
            mTvRenewalRent.setText(bean.getFlag_relet().equals("0") ? "否" : "是");
            // 预计使用时间
            mTvEstimatedTime.setText(DateUtils.formatYearMonthDay(bean.getExpect_arrivedate()));
        } else { // 还箱
            findViewById(R.id.ll_day_count).setVisibility(View.GONE);
            findViewById(R.id.ll_renewal_rent).setVisibility(View.GONE);
            findViewById(R.id.ll_estimated_time).setVisibility(View.GONE);
            // 运输方式  :0：收箱 1：自送
            mTvTransportMode.setText(bean.getFlag_send().equals("0") ? "收箱" : "自送");
        }
    }

    /**
     * 订单信息
     *
     * @param bean
     */
    @Override
    public void orderInfo(OrderDetailBean.PageInfoBean bean) {
        // 订单号
        mTvOrderNumber.setText(bean.getOrder_id());
        // 下单时间
        mTvOrderInfoTime.setText(bean.getCreate_date());
        // 下单人姓名
        mTvOrderInfoName.setText(bean.getEnd_contact());
        if (order_type.equals(OrderType.TWO.getPosition()) && !UserHelper.checkDownStream()
                && bean.getTotal_overprice() > 0 && !CollectionUtils.isNullOrEmpty(bean.getOverList())) { // 还箱订单、上游 并且有超期费用才显示
            // 是否有超期费用
            findViewById(R.id.ll_overdue_detail).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_overdue_money).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_overdue_transit_money).setVisibility(View.VISIBLE);
            mRvOverDueDetail.setLayoutManager(new LinearLayoutManager(mContext));
            mRvOverDueDetail.setAdapter(new OrderOverPriceAdapter(bean.getOverList()));
            // 超期费用、运费
            setText(R.id.tv_overdue_money, "¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_overprice()));
            setText(R.id.tv_overdue_transit_money, "¥ " + NumberUtil.formitNumberTwoPoint(bean.getTotal_tmsar()));
            mTvActualMoneyName.setText("实付金额");
        }
    }

    /**
     * 取消订单成功
     */
    @Override
    public void cancelOrderSuccess() {
        // 发送取消订单成功通知
        EventBus.getDefault().post(new EvalNotify(position, order_id),
                EventBusCode.CANCEL_ORDER);
        order_status = OrderStatus.FOUR.getPosition();
        // 重新打开订单详情页面
        OrderDetailActivity.startAction(mContext, order_id
                , photo, position, getIntent().getStringExtra("title"));
        finish();
    }

    @Override
    protected boolean isCustomLoadingLayout() {
        return true;
    }

    /**
     * 空数据
     */
    @Override
    public void setEmptyView() {
        mLlBottom.setVisibility(View.GONE);
        showEmpty();
    }
}
