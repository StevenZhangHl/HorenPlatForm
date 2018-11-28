package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.OrderDetailBean;
import com.cyy.company.bean.OrderLogs;
import com.cyy.company.ui.adapter.LogisticsAdapter;
import com.cyy.company.widget.MyDrivingRouteOverlay;
import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.LogUtils;
import com.horen.maplib.DrivingRouteOverlay;
import com.horen.maplib.MapHelper;
import com.jaeger.library.StatusBarUtil;

import java.util.Arrays;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/17:45
 * @description :物流跟踪
 * @github :https://github.com/chenyy0708
 */
public class LogisticsMapActivity extends BaseActivity implements View.OnClickListener {

    private TextureMapView mMapView;
    private View mViewBg;
    private ImageView mIvBack;
    private ImageView mIvPhoto;
    private TextView mTvStatus;
    private TextView mTvOrderNumber;
    private TextView mTvOrderTime;
    private CardView mDesignBottomSheet;
    private RecyclerView mRecyclerView;

    private BottomSheetBehavior behavior;
    private MapHelper mapHelper;
    private ImageView mIvSwitch;
    private OrderDetailBean.PageInfoBean bean;

    public static void startAction(Context context, OrderDetailBean.PageInfoBean bean) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        intent.setClass(context, LogisticsMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_logistics_map;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        // 改变状态栏文字颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        mMapView = (TextureMapView) findViewById(R.id.map_view);
        mViewBg = (View) findViewById(R.id.view_bg);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvPhoto = (ImageView) findViewById(R.id.iv_photo);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mTvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        mTvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        mIvSwitch = (ImageView) findViewById(R.id.iv_switch);
        mDesignBottomSheet = (CardView) findViewById(R.id.design_bottom_sheet);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mIvBack.setOnClickListener(this);
        mIvSwitch.setOnClickListener(this);
        behavior = BottomSheetBehavior.from(mDesignBottomSheet);
        mapHelper = new MapHelper(mContext, mMapView);
        mMapView.onCreate(mContext, savedInstanceState);
        // 地址信息
        bean = (OrderDetailBean.PageInfoBean) getIntent().getSerializableExtra("bean");
        // 地图加载完成
        mapHelper.getBaiduMap().setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                getData();
                // 路线规划
                getRoutePlanSearch();
            }
        });
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                mViewBg.setBackgroundColor(Color.argb((int) (slideOffset * 255), 245, 245, 245));
            }
        });
    }

    private void getData() {
        ImageLoader.load(mContext, bean.getList().get(0).getProduct_photo(), mIvPhoto);
        // 订单号
        mTvOrderNumber.setText("订单编号: " + bean.getOrder_id());
        mTvOrderTime.setText("预计完成时间: " + bean.getExpect_arrivedate());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        // 获取物流日志
        getOrderLogsList();
    }

    /**
     * 物流日志
     */
    private void getOrderLogsList() {
        mRxManager.add(ApiCompany.getInstance()
                .getOrderLogsList(CompanyParams.getOrderId(bean.getOrder_id()))
                .compose(RxHelper.<OrderLogs>getResult())
                .subscribeWith(new BaseObserver<OrderLogs>() {
                    @Override
                    protected void onSuccess(OrderLogs orderLogs) {
                        for (OrderLogs.OrderIdBean orderIdBean : orderLogs.getOrder_id()) {
                            if (orderIdBean.getMovement().equals("16")) // 部分签收状态需要取出值
                                orderIdBean.setPartList(orderLogs.getPartList());
                        }
                        mRecyclerView.setAdapter(new LogisticsAdapter(orderLogs.getOrder_id()));
                    }

                    @Override
                    protected void onError(String message) {

                    }
                }));
    }


    private void getRoutePlanSearch() {
        if (TextUtils.isEmpty(bean.getStart_latitude()) || TextUtils.isEmpty(bean.getEnd_latitude()))
            return;
        if (Double.valueOf(bean.getStart_latitude()) == 0 || Double.valueOf(bean.getEnd_latitude()) == 0)
            return;
        // 驾车路线规划
        RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
        // 创建驾车线路规划检索监听者
        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                LogUtils.d("获取驾车线路规划结果");
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mapHelper.getBaiduMap(), mContext,
                            bean.getStart_orgname(), bean.getEnd_orgname(), bean.getCreate_date());
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };
        // 设置驾车线路规划检索监听者
        mSearch.setOnGetRoutePlanResultListener(listener);
        // 准备检索起、终点信息
        LatLng startLng = new LatLng(Double.valueOf(bean.getStart_latitude()), Double.valueOf(bean.getStart_longitude()));
        LatLng endLng = new LatLng(Double.valueOf(bean.getEnd_latitude()), Double.valueOf(bean.getEnd_longitude()));
        PlanNode stNode = PlanNode.withLocation(startLng);
        PlanNode enNode = PlanNode.withLocation(endLng);
        // 发起驾车线路规划检索
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
        // 缩放比例
        LogUtils.d(Math.abs(startLng.latitude - endLng.latitude) + "");
        LogUtils.d(Math.abs(startLng.latitude - endLng.latitude) * 104.8 + "");
        mapHelper.scrollBy(Arrays.asList(startLng, endLng));
    }

    @Override
    protected void setFitsSystemWindows() {
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        } else if (view.getId() == R.id.iv_switch) { // 开关
            if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
    }
}
