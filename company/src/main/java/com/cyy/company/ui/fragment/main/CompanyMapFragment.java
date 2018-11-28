package com.cyy.company.ui.fragment.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureSupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyy.company.R;
import com.cyy.company.bean.HomeCoor;
import com.cyy.company.bean.HomeOkuraDetail;
import com.cyy.company.bean.HomeOrgDetail;
import com.cyy.company.bean.MapOkuraBean;
import com.cyy.company.enums.OrgStatus;
import com.cyy.company.mvp.contract.HomeMapContract;
import com.cyy.company.mvp.model.HomeMapModel;
import com.cyy.company.mvp.presenter.HomeMapPresenter;
import com.cyy.company.ui.activity.eye.CreateReturnActivity;
import com.cyy.company.ui.activity.order.CreateOrderActivity;
import com.cyy.company.ui.adapter.HomeMapOkuraAdapter;
import com.cyy.company.ui.adapter.HomeMapOrgAdapter;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragment;
import com.horen.base.ui.ScanActivity;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.NumberUtil;
import com.horen.base.util.UserHelper;
import com.horen.base.util.WeakHandler;
import com.horen.maplib.MapHelper;
import com.horen.maplib.MapListener;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/19/13:18
 * @description :百网地图
 * @github :https://github.com/chenyy0708
 */
public class CompanyMapFragment extends BaseFragment<HomeMapPresenter, HomeMapModel> implements BaiduMap.OnMapLoadedCallback, MapListener.MapClickListener, MapListener.MarkerClickListener, View.OnClickListener, HomeMapContract.View {
    private TextureSupportMapFragment supportMapFragment;
    private WeakHandler weakHandler = new WeakHandler();
    private MapHelper mapHelper;

    private FrameLayout mFlMapView;
    private ImageView mIvScan;
    private SuperButton mSbtOrder;
    private LinearLayout mLlMapHeader;
    private TextView mTvDistance;
    private TextView mTvOrgName;
    private TextView mTvAddress;
    private RecyclerView mRecyclerView;
    private ImageView mIvRefreshMap;
    private ImageView mIvLocationMap;
    private BaseQuickAdapter mapAdapter;


    public static CompanyMapFragment newInstance() {
        Bundle args = new Bundle();
        CompanyMapFragment fragment = new CompanyMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_company_map;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initMapView();
    }

    private void initMapView() {
        mFlMapView = (FrameLayout) rootView.findViewById(R.id.fl_map_view);
        mIvScan = (ImageView) rootView.findViewById(R.id.iv_scan);
        mIvRefreshMap = (ImageView) rootView.findViewById(R.id.iv_refresh_map);
        mIvLocationMap = (ImageView) rootView.findViewById(R.id.iv_location_map);
        mSbtOrder = (SuperButton) rootView.findViewById(R.id.sbt_order);
        mLlMapHeader = (LinearLayout) rootView.findViewById(R.id.ll_map_header);
        mTvDistance = (TextView) rootView.findViewById(R.id.tv_distance);
        mTvOrgName = (TextView) rootView.findViewById(R.id.tv_org_name);
        mTvAddress = (TextView) rootView.findViewById(R.id.tv_address);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rootView.findViewById(R.id.fl_refresh_map).setOnClickListener(this);
        rootView.findViewById(R.id.fl_location_map).setOnClickListener(this);
        mIvScan.setOnClickListener(this);
        mSbtOrder.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        supportMapFragment = TextureSupportMapFragment.newInstance();
        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction().add(R.id.fl_map_view, supportMapFragment, "CompanyMapFragment").commit();
        // 防止地图为空
        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (supportMapFragment.getMapView() == null) {
                    weakHandler.postDelayed(this, 200);
                } else {
                    mapHelper = new MapHelper(_mActivity, supportMapFragment.getMapView());
                    mapHelper.setOnMapLoadedCallback(CompanyMapFragment.this);
                    // marker点击事件
                    mapHelper.setOnMapClickListener(CompanyMapFragment.this);
                    // 地图点击事件
                    mapHelper.setOnMarkerClickListener(CompanyMapFragment.this);
                }
            }
        }, 200);

    }

    /**
     * 地图加载成功
     */
    @Override
    public void onMapLoaded() {
        mPresenter.setMapHelper(mapHelper);
        // 首页百网千驿地点
        mPresenter.getHomeCoorList();
    }

    @Override
    public void onMapClickListener(double lat, double lng) {
        // 没有网点数据，点击地图不响应
        if (CollectionUtils.isNullOrEmpty(mPresenter.getmData())) return;
        // 上一个点数据
        HomeCoor.PdListBean previousBean = mPresenter.getPositionData(mapHelper.getPreviousPosition());
        if (previousBean.getOrg_status().equals(OrgStatus.ONE.getPosition())) { // 大仓
            mapHelper.changeMarkerIcon(mapHelper.getPreviousPosition(), R.drawable.icon_warehouse_normal, MapHelper.NORMAL_ZINDEX);
        } else if (previousBean.getOrg_status().equals(OrgStatus.THREE.getPosition())) { // 下游网点
            mapHelper.changeMarkerIcon(mapHelper.getPreviousPosition(), R.drawable.icon_org_normal, MapHelper.NORMAL_ZINDEX);
        }
        mapHelper.setPreviousPosition(MapHelper.NORMAL);
        // 隐藏卡片
        if (mLlMapHeader.getVisibility() == View.VISIBLE) {
            AnimationUtils.foldingHideView(mLlMapHeader, 600);
        }
    }

    @Override
    public void onMarkerClickListener(int currentPosition, int previousPosition) {
        if (previousPosition != MapHelper.NORMAL) {
            // 上一个点数据
            HomeCoor.PdListBean previousBean = mPresenter.getPositionData(previousPosition);
            if (previousBean.getOrg_status().equals(OrgStatus.ONE.getPosition())) { // 大仓
                mapHelper.changeMarkerIcon(previousPosition, R.drawable.icon_warehouse_normal, MapHelper.NORMAL_ZINDEX);
            } else if (previousBean.getOrg_status().equals(OrgStatus.THREE.getPosition())) { // 下游网点
                mapHelper.changeMarkerIcon(previousPosition, R.drawable.icon_org_normal, MapHelper.NORMAL_ZINDEX);
            }
        }
        // 当前点数据
        HomeCoor.PdListBean currentBean = mPresenter.getPositionData(currentPosition);
        if (currentBean.getOrg_status().equals(OrgStatus.ONE.getPosition())) { // 大仓
            mapHelper.changeMarkerIcon(currentPosition, R.drawable.icon_warehouse_select, MapHelper.SELECT_ZINDEX);
            // 大仓数据
            mPresenter.getHomeOkuraList(currentPosition);
        } else if (currentBean.getOrg_status().equals(OrgStatus.THREE.getPosition())) { // 下游网点
            mapHelper.changeMarkerIcon(currentPosition, R.drawable.icon_org_select, MapHelper.SELECT_ZINDEX);
            // 下游网点数据
            mPresenter.getOrgByIdsProType(currentPosition);
        }
        // 开始动画
        mapHelper.setMarkerAnimation(currentPosition);
        // 移动地图中心
        mapHelper.moveTo(currentBean.getOrg_latitude(), currentBean.getOrg_longitude());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sbt_order) {
            if (UserHelper.checkDownStream()) { // 下游只能还箱
                CreateReturnActivity.startAction(_mActivity, HRConstant.HUNDRED_KILOMETERS);
            } else {
                CreateOrderActivity.startAction(_mActivity);
            }
        } else if (view.getId() == R.id.iv_scan) { // 扫描
            ScanActivity.startActivityForResult(_mActivity);
        } else if (view.getId() == R.id.fl_location_map) { // 定位
            AnimationUtils.scaleView(mIvLocationMap, 1000);
            mapHelper.moveTo(mPresenter.getPositionData(mPresenter.getDefaultOutlet()).getOrg_latitude(),
                    mPresenter.getPositionData(mPresenter.getDefaultOutlet()).getOrg_longitude());
        } else if (view.getId() == R.id.fl_refresh_map) { // 刷新
            AnimationUtils.rotationView(mIvRefreshMap, 0f, 359f, 1000, 1);
            mPresenter.getHomeCoorList();
            // 隐藏卡片
            if (mLlMapHeader.getVisibility() == View.VISIBLE) {
                AnimationUtils.foldingHideView(mLlMapHeader, 600);
            }
        }
    }

    /**
     * 网点产品信息列表
     *
     * @param mData
     */
    @Override
    public void getOrgSuccess(List<HomeOrgDetail.PdListBean> mData, HomeCoor.PdListBean address) {
        // 设置网点数据
        if (address.getOrg_status().equals(OrgStatus.ONE.getPosition())) { // 大仓
            mTvOrgName.setText("百网千驿: " + address.getOrg_name());
        } else if (address.getOrg_status().equals(OrgStatus.THREE.getPosition())) { // 下游网点
            mTvOrgName.setText("网点: " + address.getOrg_name());
            mapAdapter = new HomeMapOrgAdapter(mData);
            mRecyclerView.setAdapter(mapAdapter);
        }
        mTvAddress.setText("地址: " + address.getOrg_address());
        double distance = DistanceUtil.getDistance(new LatLng(mPresenter.getPositionData(mPresenter.getDefaultOutlet()).getOrg_latitude(),
                mPresenter.getPositionData(mPresenter.getDefaultOutlet()).getOrg_longitude()), new LatLng(address.getOrg_latitude(), address.getOrg_longitude()));
        double disAddress = distance / 1000;
        mTvDistance.setText(NumberUtil.formitNumberTwoPoint(disAddress) + "KM");
        // 动态计算RecycleView的高度
        int height = 0;
        for (HomeOrgDetail.PdListBean mDatum : mData) {
            height += (DisplayUtil.dip2px(35) +
                    DisplayUtil.dip2px(20) +
                    DisplayUtil.dip2px(10) + DisplayUtil.dip2px(25));
        }
        // 超过200dp，设置固定高度，否则自适应
        setRecyclerViewHeight(mRecyclerView, height);
        // 显示卡片
        if (mLlMapHeader.getVisibility() == View.GONE) {
            AnimationUtils.foldingShowView(mLlMapHeader, 1000);
        }
    }

    @Override
    public void getOkuraSuccess(List<MapOkuraBean> mData, HomeCoor.PdListBean address) {
        // 设置网点数据
        if (address.getOrg_status().equals(OrgStatus.ONE.getPosition())) { // 大仓
            mTvOrgName.setText("百网千驿: " + address.getOrg_name());
            mapAdapter = new HomeMapOkuraAdapter(mData);
            mRecyclerView.setAdapter(mapAdapter);
        } else if (address.getOrg_status().equals(OrgStatus.THREE.getPosition())) { // 下游网点
            mTvOrgName.setText("网点: " + address.getOrg_name());
        }
        mTvAddress.setText("地址: " + address.getOrg_address());
        double distance = DistanceUtil.getDistance(new LatLng(mPresenter.getPositionData(mPresenter.getDefaultOutlet()).getOrg_latitude(),
                mPresenter.getPositionData(mPresenter.getDefaultOutlet()).getOrg_longitude()), new LatLng(address.getOrg_latitude(), address.getOrg_longitude()));
        double disAddress = distance / 1000;
        mTvDistance.setText(NumberUtil.formitNumberTwoPoint(disAddress) + "KM");
        // 动态计算RecycleView的高度
        int height = 0;
        for (MapOkuraBean mDatum : mData) {
            // 类型高度
            height += DisplayUtil.dip2px(30);
            // RecycleView子item高度
            for (HomeOkuraDetail.PdListBean pdListBean : mDatum.getmData()) {
                height += DisplayUtil.dip2px(25);
            }
        }
        // 超过200dp，设置固定高度，否则自适应
        setRecyclerViewHeight(mRecyclerView, height);
        // 显示卡片
        if (mLlMapHeader.getVisibility() == View.GONE) {
            AnimationUtils.foldingShowView(mLlMapHeader, 1000);
        }
    }

    /**
     * 设置RecycleView高度
     */
    private void setRecyclerViewHeight(RecyclerView mRecyclerView, int height) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mRecyclerView.getLayoutParams();
        if (height > DisplayUtil.dip2px(200)) {
            lp.height = DisplayUtil.dip2px(200);
        } else {
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
        mRecyclerView.setLayoutParams(lp);
    }
}

