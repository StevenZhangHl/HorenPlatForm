package com.cyy.company.ui.fragment.eye.asset;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
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
import com.cyy.company.R;
import com.cyy.company.bean.AssetMapListBean;
import com.cyy.company.bean.HomeOrgDetail;
import com.cyy.company.mvp.contract.AssetMapContract;
import com.cyy.company.mvp.model.AssetMapModel;
import com.cyy.company.mvp.presenter.AssetMapPresenter;
import com.cyy.company.ui.activity.eye.CreateReturnActivity;
import com.cyy.company.ui.adapter.HomeMapOrgAdapter;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.SPUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.util.WeakHandler;
import com.horen.base.widget.DashView;
import com.horen.maplib.MapHelper;
import com.horen.maplib.MapListener;

import java.util.ArrayList;

import cn.iwgang.countdownview.CountdownView;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/11:15
 * @description :资产分布Fragment
 * @github :https://github.com/chenyy0708
 */
public class AssetMapFragment extends BaseFragment<AssetMapPresenter, AssetMapModel> implements BaiduMap.OnMapLoadedCallback, MapListener.MapClickListener, MapListener.MarkerClickListener, View.OnClickListener, CountdownView.OnCountdownEndListener, AssetMapContract.View {

    private TextureSupportMapFragment supportMapFragment;
    private WeakHandler weakHandler = new WeakHandler();
    private MapHelper mapHelper;

    private FrameLayout mFlContainer;
    private FrameLayout mFlRefreshMap;
    private ImageView mIvRefreshMap;
    private SuperButton mSbtOrder;
    private LinearLayout mLlHeader;
    private CountdownView mCountDownView;
    private LinearLayout mLlMapHeader;
    private ImageView mIvBg;
    private TextView mTvOrgName;
    private TextView mTvAddress;
    private ImageView mIvCloseHeader;
    private DashView mDashView;
    private RecyclerView mRecyclerView;
    private TextView mTvExpendle;
    /**
     * 半小时盘点倒计时时间
     */
    private static final long HALF_AN_HOUR = 30 * 60 * 1000;

    /**
     * 是否展开状态，默认false
     */
    private boolean isExpendle = false;
    private HomeMapOrgAdapter orgAdapter;

    public static AssetMapFragment newInstance() {
        Bundle args = new Bundle();
        AssetMapFragment fragment = new AssetMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_asset_map;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mFlContainer = (FrameLayout) rootView.findViewById(R.id.fl_container);
        mFlRefreshMap = (FrameLayout) rootView.findViewById(R.id.fl_refresh_map);
        mIvRefreshMap = (ImageView) rootView.findViewById(R.id.iv_refresh_map);
        mSbtOrder = (SuperButton) rootView.findViewById(R.id.sbt_order);
        mLlHeader = (LinearLayout) rootView.findViewById(R.id.ll_header);
        mCountDownView = (CountdownView) rootView.findViewById(R.id.count_down_view);
        mLlMapHeader = (LinearLayout) rootView.findViewById(R.id.ll_map_header);
        mIvBg = (ImageView) rootView.findViewById(R.id.iv_bg);
        mTvOrgName = (TextView) rootView.findViewById(R.id.tv_org_name);
        mTvAddress = (TextView) rootView.findViewById(R.id.tv_address);
        mIvCloseHeader = (ImageView) rootView.findViewById(R.id.iv_close_header);
        mDashView = (DashView) rootView.findViewById(R.id.dash_view);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mTvExpendle = (TextView) rootView.findViewById(R.id.tv_expendle);
        mTvExpendle.setOnClickListener(this);
        mSbtOrder.setOnClickListener(this);
        rootView.findViewById(R.id.fl_refresh_map).setOnClickListener(this);
        rootView.findViewById(R.id.iv_close_header).setOnClickListener(this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        orgAdapter = new HomeMapOrgAdapter(new ArrayList<HomeOrgDetail.PdListBean>());
        mRecyclerView.setAdapter(orgAdapter);
        // 默认收缩
        setRecyclerViewHeight(DisplayUtil.dip2px(90));
        supportMapFragment = TextureSupportMapFragment.newInstance();
        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction().add(R.id.fl_container, supportMapFragment, "AssetMapFragment").commit();
        // 防止地图为空
        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (supportMapFragment.getMapView() == null) {
                    weakHandler.postDelayed(this, 200);
                } else {
                    mapHelper = new MapHelper(_mActivity, supportMapFragment.getMapView());
                    mapHelper.setOnMapLoadedCallback(AssetMapFragment.this);
                    // marker点击事件
                    mapHelper.setOnMapClickListener(AssetMapFragment.this);
                    // 地图点击事件
                    mapHelper.setOnMarkerClickListener(AssetMapFragment.this);
                }
            }
        }, 200);

        // 取出一键盘点时间戳
        long time = SPUtils.getSharedLongData(_mActivity, HRConstant.INVENTORY_TIME);
        if (time != 0) { // 盘点时间不为空，判断时间是否相隔半个小时之内
            long data = System.currentTimeMillis() - time; // 距离上次点击一键盘点的时间差
            if (data <= HALF_AN_HOUR && !UserHelper.checkDownStream() &&
                    SPUtils.getSharedStringData(_mActivity, HRConstant.INVENTORY_ACCOUNT)
                            .equals(UserHelper.getUserInfo().getLoginInfo().getUser_id())) { // 如果时间相差小于半个小时，那么倒计时亮起,下游网点账号不需要一键盘点倒计时,并且上一次发送一键盘点是该账号才倒计时
                rootView.findViewById(R.id.ll_count_down).setVisibility(View.VISIBLE);
                // 倒计时半小时
                mCountDownView.start(HALF_AN_HOUR - data);
            }
        }
        // 倒计时结束后回调
        mCountDownView.setOnCountdownEndListener(this);
        // 获取盘点状态

    }

    @Override
    public void onMapLoaded() {
        mPresenter.setMapHelper(mapHelper);
        // 首页百网千驿地点
        mPresenter.getOrgList();
    }

    @Override
    public void onMapClickListener(double lat, double lng) {
        // 没有网点数据，点击地图不响应
        if (CollectionUtils.isNullOrEmpty(mPresenter.getmData())) return;
        // 上一个点数据
        AssetMapListBean.PdListBean previousBean = mPresenter.getPositionData(mapHelper.getPreviousPosition());
        if (previousBean.getFlag_road().equals("0")) { // 网点
            mapHelper.changeMarkerIcon(mapHelper.getPreviousPosition(), R.drawable.icon_eye_org_normal, MapHelper.NORMAL_ZINDEX);
        } else if (previousBean.getFlag_road().equals("2")) { // 疑似网点
            mapHelper.changeMarkerIcon(mapHelper.getPreviousPosition(), R.drawable.icon_suspected_org_normal, MapHelper.NORMAL_ZINDEX);
        } else if (previousBean.getFlag_road().equals("1")) { // 在途
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
            AssetMapListBean.PdListBean previousBean = mPresenter.getPositionData(previousPosition);
            if (previousBean.getFlag_road().equals("0")) { // 网点
                mapHelper.changeMarkerIcon(previousPosition, R.drawable.icon_eye_org_normal, MapHelper.NORMAL_ZINDEX);
            } else if (previousBean.getFlag_road().equals("2")) { // 疑似网点
                mapHelper.changeMarkerIcon(previousPosition, R.drawable.icon_suspected_org_normal, MapHelper.NORMAL_ZINDEX);
            } else if (previousBean.getFlag_road().equals("1")) { // 在途
            }
        }
        // 当前点数据
        AssetMapListBean.PdListBean currentBean = mPresenter.getPositionData(currentPosition);
        if (currentBean.getFlag_road().equals("0")) { // 网点
            mapHelper.changeMarkerIcon(currentPosition, R.drawable.icon_eye_org_select, MapHelper.SELECT_ZINDEX);
            mPresenter.getOrgByIdsProType(currentPosition);
        } else if (currentBean.getFlag_road().equals("2")) { // 疑似网点
            mapHelper.changeMarkerIcon(currentPosition, R.drawable.icon_suspected_org_select, MapHelper.SELECT_ZINDEX);
            mPresenter.getOrgByIdsProType(currentPosition);
        } else if (currentBean.getFlag_road().equals("1")) { // 在途
        }
        // 开始动画
        mapHelper.setMarkerAnimation(currentPosition);
        // 移动地图中心
        mapHelper.moveTo(currentBean.getOrg_latitude(), currentBean.getOrg_longitude());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_expendle) { // 展开收取
            if (isExpendle) { // 展开状态，收起列表
                setDrawableRightIcon(R.drawable.icon_eye_down_arrow_gray);
                mTvExpendle.setText("展开");
                setRecyclerViewHeight(DisplayUtil.dip2px(90));
                mFlRefreshMap.setVisibility(View.VISIBLE);
                isExpendle = false;
            } else { // 收起状态，展开列表
                setDrawableRightIcon(R.drawable.icon_eye_up_arrow_gray);
                setRecyclerViewHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                mTvExpendle.setText("缩回");
                if (orgAdapter.getData().size() > 3) { // 防止数据过多挡住刷新按钮
                    mFlRefreshMap.setVisibility(View.GONE);
                }
                isExpendle = true;
            }
        } else if (view.getId() == R.id.fl_refresh_map) { // 刷新
            AnimationUtils.rotationView(mIvRefreshMap, 0f, 359f, 1000, 1);
            mPresenter.getOrgList();
            // 隐藏卡片
            if (mLlMapHeader.getVisibility() == View.VISIBLE) {
                AnimationUtils.foldingHideView(mLlMapHeader, 600);
            }
        } else if (view.getId() == R.id.iv_close_header) { // 隐藏列表
            mFlRefreshMap.setVisibility(View.VISIBLE);
            AnimationUtils.foldingHideView(mLlMapHeader, 600);
            // 更换图标
            if (mapHelper.getPreviousPosition() != MapHelper.NORMAL) {
                AssetMapListBean.PdListBean previousBean = mPresenter.getPositionData(mapHelper.getPreviousPosition());
                if (previousBean.getFlag_road().equals("0")) { // 网点
                    mapHelper.changeMarkerIcon(mapHelper.getPreviousPosition(), R.drawable.icon_eye_org_normal, MapHelper.NORMAL_ZINDEX);
                } else if (previousBean.getFlag_road().equals("2")) { // 疑似网点
                    mapHelper.changeMarkerIcon(mapHelper.getPreviousPosition(), R.drawable.icon_suspected_org_normal, MapHelper.NORMAL_ZINDEX);
                } else if (previousBean.getFlag_road().equals("1")) { // 在途
                }
            }
            mapHelper.setPreviousPosition(MapHelper.NORMAL);
        } else if (view.getId() == R.id.sbt_order) { // 创建还箱
            CreateReturnActivity.startAction(_mActivity, HRConstant.EYE);
        }
    }

    public void setDrawableRightIcon(@DrawableRes int icon) {
        Drawable drawable = _mActivity.getResources().getDrawable(icon);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvExpendle.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置RecycleView高度
     */
    private void setRecyclerViewHeight(int height) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        lp.height = height;
        mRecyclerView.setLayoutParams(lp);
    }

    /**
     * 倒计时
     */
    public void countdown() {
        rootView.findViewById(R.id.ll_count_down).setVisibility(View.VISIBLE);
        // 倒计时半小时
        mCountDownView.start(HALF_AN_HOUR);
    }

    /**
     * 倒计时结束
     */
    @Override
    public void onEnd(CountdownView cv) {
        rootView.findViewById(R.id.ll_count_down).setVisibility(View.GONE);
    }

    /**
     * 网点产品信息列表
     *
     * @param org
     */
    @Override
    public void getOrgSuccess(HomeOrgDetail orgDetail, AssetMapListBean.PdListBean org) {
        if (org.getFlag_road().equals("0")) { // 网点
            mIvBg.setBackgroundResource(R.drawable.icon_eye_org_bg);
            mTvOrgName.setText("网点: " + org.getOrg_name());
        } else if (org.getFlag_road().equals("2")) { // 疑似网点
            mIvBg.setBackgroundResource(R.drawable.icon_eye_suspected_org_bg);
            mTvOrgName.setText("疑似网点: " + "如此网点为企业下游网点，请联系平台进行维护，若不是则有丢失风险");
        }
        // 每条数据里面都有重复的地址信息，结构极其不合理
        if (!CollectionUtils.isNullOrEmpty(orgDetail.getPdList())) {
            mTvAddress.setText("地址: " + orgDetail.getPdList().get(0).getOrg_address());
        }
        // 一条数据无需展开缩回
        if (orgDetail.getPdList().size() > 1) {
            mTvExpendle.setVisibility(View.VISIBLE);
        } else {
            mTvExpendle.setVisibility(View.INVISIBLE);
        }
        // 如果数据为空，隐藏列表
        if (CollectionUtils.isNullOrEmpty(orgDetail.getPdList())) {
            mRecyclerView.setVisibility(View.GONE);
            mTvExpendle.setVisibility(View.GONE);
        } else {
            mTvExpendle.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        orgAdapter.setNewData(orgDetail.getPdList());
        // 显示卡片
        if (mLlMapHeader.getVisibility() == View.GONE) {
            AnimationUtils.foldingShowView(mLlMapHeader, 1000);
        }
    }
}
