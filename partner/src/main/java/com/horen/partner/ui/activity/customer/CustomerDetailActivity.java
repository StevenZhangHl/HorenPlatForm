package com.horen.partner.ui.activity.customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.Constants;
import com.horen.base.util.DisplayUtil;
import com.horen.base.widget.ObservableScrollView;
import com.horen.partner.R;
import com.horen.partner.adapter.CustomerDetalPhotoAdapter;
import com.horen.partner.adapter.VisiteNoteAdapter;
import com.horen.partner.bean.CustomerBean;
import com.horen.partner.bean.VisiteNoteBaseBean;
import com.horen.partner.mvp.contract.CustomerDetailContract;
import com.horen.partner.mvp.model.CustomerDetailModel;
import com.horen.partner.mvp.presenter.CustomerDetailPresenter;
import com.horen.partner.ui.widget.MyToobar;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetailActivity extends BaseActivity<CustomerDetailPresenter, CustomerDetailModel> implements CustomerDetailContract.View, View.OnClickListener, AppBarLayout.OnOffsetChangedListener {
    /**
     * 公司名称
     */
    private TextView tv_customer_name;
    /**
     * 公司地址
     */
    private TextView tv_customer_address;
    /**
     * 行业
     */
    private TextView tv_customer_inductry;
    /**
     * 公司电话
     */
    private TextView tv_customer_tel;

    /**
     * 需求列表
     */
    private RecyclerView recyclerview_needs_images;
    /**
     * 编辑信息
     */
    private TextView tv_edit_info;
    /**
     * 联系人
     */
    private TextView tv_contanct;
    /**
     * 联系邮箱
     */
    private TextView tv_customer_email;
    /**
     * 拜访记录
     */
    private RecyclerView recyclerview_visitenotes;
    /**
     * 需求信息
     */
    private TextView tv_needs_info;
    /**
     * 增加拜访
     */
    private TextView tv_add_visitenote;
    private View view_line;
    private TextView tv_needs_info_title;
    private MyToobar tool_bar;
    private CustomerDetalPhotoAdapter photoAdapter;
    private VisiteNoteAdapter visiteNoteAdapter;
    private String customerType;
    private String customerId;
    private final int PARTNER_ADD_VISITENOTE_CODE = 1;
    private final int PARTNER_UPDATE_CUSTOMER_CODE = 2;
    private AppBarLayout appBarLayout;

    public static void launchActivity(Context context, String customerId, String customerType) {
        Intent intent = new Intent();
        intent.setClass(context, CustomerDetailActivity.class);
        intent.putExtra(Constants.PARTNER_CUSTOMER_CUSTOMER_ID, customerId);
        intent.putExtra(Constants.PARTEER_CUSTOMER_TYPE, customerType);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_customer_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        // 隐藏系统状态栏
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        tool_bar = (MyToobar) findViewById(R.id.tool_bar);
        tv_customer_name = (TextView) findViewById(R.id.tv_customer_name);
        tv_customer_address = (TextView) findViewById(R.id.tv_customer_address);
        tv_customer_inductry = (TextView) findViewById(R.id.tv_customer_inductry);
        tv_customer_tel = (TextView) findViewById(R.id.tv_customer_tel);
        recyclerview_needs_images = (RecyclerView) findViewById(R.id.recyclerview_needs_images);
        tv_needs_info = (TextView) findViewById(R.id.tv_needs_info);
        tv_contanct = (TextView) findViewById(R.id.tv_contanct);
        tv_edit_info = (TextView) findViewById(R.id.tv_edit_info);
        tv_customer_email = (TextView) findViewById(R.id.tv_customer_email);
        recyclerview_visitenotes = (RecyclerView) findViewById(R.id.recyclerview_visitenotes);
        tv_needs_info_title = (TextView) findViewById(R.id.tv_needs_info_title);
        view_line = (View) findViewById(R.id.view_line);
        tv_add_visitenote = (TextView) findViewById(R.id.tv_add_visitenote);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_cutomer_detail);
        initToolbar(tool_bar.getToolbar(), false);
        tool_bar.getToolbar().setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        tool_bar.setTitle(getResources().getString(R.string.visite_detail));
        tv_add_visitenote.setOnClickListener(this);
        tv_edit_info.setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(this);
        initRecycleView();
        getDetailData();
    }

    private void initRecycleView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_needs_images.setLayoutManager(gridLayoutManager);
        recyclerview_needs_images.setHasFixedSize(true);
        recyclerview_needs_images.setNestedScrollingEnabled(false);
        photoAdapter = new CustomerDetalPhotoAdapter(R.layout.partner_item_customer_detail_photo, new ArrayList<String>());
        recyclerview_needs_images.setAdapter(photoAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.list_divider_10dp));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerview_visitenotes.addItemDecoration(itemDecoration);
        recyclerview_visitenotes.setLayoutManager(layoutManager);
        recyclerview_visitenotes.setHasFixedSize(true);
        recyclerview_visitenotes.setNestedScrollingEnabled(false);
        visiteNoteAdapter = new VisiteNoteAdapter(R.layout.partner_item_visite_note, new ArrayList<VisiteNoteBaseBean>());
        recyclerview_visitenotes.setAdapter(visiteNoteAdapter);
        recyclerview_visitenotes.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                int viewId = view.getId();
                if (viewId == R.id.iv_edit_visite) {
                    Intent intent = new Intent();
                    intent.setClass(CustomerDetailActivity.this, AddVisiteActivity.class);
                    intent.putExtra(Constants.PARTNER_VISITE_INFO, visiteNoteAdapter.getData().get(position));
                    startActivityForResult(intent, PARTNER_ADD_VISITENOTE_CODE);
                }
            }
        });
    }

    /**
     * 获取数据
     */
    private void getDetailData() {
        if (getIntent() != null) {
            customerId = getIntent().getStringExtra(Constants.PARTNER_CUSTOMER_CUSTOMER_ID);
            customerType = getIntent().getStringExtra(Constants.PARTEER_CUSTOMER_TYPE);
        }
        mPresenter.getCustomerDetailData(customerId);
        mPresenter.getVisiteNoteData(customerId);
    }

    private CustomerBean currentInfo;

    @Override
    public void setViewCustomerData(CustomerBean bean) {
        currentInfo = bean;
        tv_customer_address.setText(bean.getCustomer_address());
        tv_customer_inductry.setText(bean.getIndustry_name());
        tv_customer_name.setText(bean.getCustomer_name());
        tv_customer_email.setText(bean.getCustomer_mail());
        tv_contanct.setText(bean.getCustomer_contact());
        tv_customer_tel.setText(bean.getCustomer_tel());
        if ("potential".equals(customerType)) {
            showHidenNeedView(true);
            tv_needs_info.setText(bean.getRequirements());
            photoAdapter.setNewData(bean.getPhoto_urls());
        } else {
            showHidenNeedView(false);
        }
    }

    @Override
    public void setViewVisiteNoteData(List<VisiteNoteBaseBean> bean) {
        visiteNoteAdapter.setNewData(bean);
    }

    /**
     * 根据客户类型控制需求信息的显示与隐藏
     *
     * @param b
     */
    private void showHidenNeedView(boolean b) {
        if (b) {
            tv_needs_info.setVisibility(View.VISIBLE);
            tv_needs_info_title.setVisibility(View.VISIBLE);
            recyclerview_needs_images.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
        } else {
            tv_needs_info.setVisibility(View.GONE);
            tv_needs_info_title.setVisibility(View.GONE);
            recyclerview_needs_images.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == tv_add_visitenote) {
            Intent intent = new Intent();
            intent.setClass(this, AddVisiteActivity.class);
            intent.putExtra(Constants.PARTNER_CUSTOMER_CUSTOMER_ID, customerId);
            startActivityForResult(intent, PARTNER_ADD_VISITENOTE_CODE);
        }
        if (view == tv_edit_info) {
            Intent intent = new Intent();
            intent.setClass(CustomerDetailActivity.this, AddPotentialCustomerActivity.class);
            intent.putExtra(Constants.PARTNER_CUSTOMER_INFO, currentInfo);
            intent.putExtra(Constants.PARTEER_CUSTOMER_TYPE, customerType);
            startActivityForResult(intent, PARTNER_UPDATE_CUSTOMER_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PARTNER_ADD_VISITENOTE_CODE) {
                mPresenter.getVisiteNoteData(customerId);
            }
            if (requestCode == PARTNER_UPDATE_CUSTOMER_CODE) {
                mPresenter.getCustomerDetailData(customerId);
            }
        }

    }

    @Override
    protected void setFitsSystemWindows() {
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int height = 150; // 滑动到banner刚好不可见，标题栏变为不透明
        float f = (float) -verticalOffset / height;
        if (f > 1) {
            f = 1;
        }
        float alpha = (255 * f);
        tool_bar.getToolbar().setBackgroundColor(Color.argb((int) alpha, 135, 205, 37));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.argb((int) alpha, 135, 205, 37));
        }
    }
}
