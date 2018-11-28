package com.horen.partner.ui.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.horen.base.base.BaseFragment;
import com.horen.base.bean.TypeBean;
import com.horen.base.util.LogUtils;
import com.horen.base.util.ToastUitl;
import com.horen.maplib.LocationHelper;
import com.horen.maplib.MapHelper;
import com.horen.maplib.MapListener;
import com.horen.partner.R;
import com.horen.partner.adapter.PropertyListAdapter;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.PropertyBean;
import com.horen.partner.bean.PropertySingleBean;
import com.horen.partner.mvp.contract.LeasePropertyContract;
import com.horen.partner.mvp.model.PropertyModel;
import com.horen.partner.mvp.presenter.LeasePropertyPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/9 14:06
 * Description:This isLeasePropertyFragment 租赁资产
 */
public class LeasePropertyFragment extends BaseFragment<LeasePropertyPresenter, PropertyModel> implements LeasePropertyContract.View, MapListener.MapClickListener, MapListener.MarkerClickListener, View.OnClickListener {
    private MapHelper mapHelper;
    private List<LatLng> latLngs;
    /**
     * 客户名称
     */
    private TextView tv_custom_name;
    private MapView mapview;
    /**
     * 地址
     */
    private RelativeLayout rl_customer_bg;
    private RecyclerView asset_recycleview;
    private BottomSheetBehavior behavior;
    private TextView tv_bottomsheet;
    private CardView bottomSheet;
    private ImageView iv_refresh_map;
    private PropertyListAdapter propertyListAdapter;
    private RelativeLayout rl_bottom_sheet;
    private CardView rl_bottom_sheet_hiden;
    private List<LatLng> currentLatLng = new ArrayList<>();

    public static LeasePropertyFragment newInstance() {
        Bundle bundle = new Bundle();
        LeasePropertyFragment fragment = new LeasePropertyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partner_fragment_lease_property;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rl_bottom_sheet_hiden = (CardView) rootView.findViewById(R.id.rl_bottom_sheet_hiden);
        tv_custom_name = (TextView) rootView.findViewById(R.id.tv_custom_name);
        mapview = (MapView) rootView.findViewById(R.id.mapview);
        rl_customer_bg = (RelativeLayout) rootView.findViewById(R.id.rl_customer_bg);
        asset_recycleview = (RecyclerView) rootView.findViewById(R.id.asset_recycleview);
        bottomSheet = (CardView) rootView.findViewById(R.id.design_bottom_sheet);
        tv_bottomsheet = (TextView) rootView.findViewById(R.id.tv_bottomsheet);
        iv_refresh_map = (ImageView) rootView.findViewById(R.id.iv_refresh_map);
        rl_bottom_sheet = (RelativeLayout) rootView.findViewById(R.id.rl_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        rl_customer_bg.setBackgroundColor(_mActivity.getResources().getColor(R.color.transparent));
                        bottomSheet.setBackgroundResource(R.drawable.bg_textview_gradient);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        rl_customer_bg.setBackgroundColor(_mActivity.getResources().getColor(R.color.white));
                        bottomSheet.setBackgroundColor(getResources().getColor(R.color.color_f5));
                        break;
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                LogUtils.i("slideoffet:", slideOffset);
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
        initBottomSheetState();
        tv_custom_name.setOnClickListener(this);
        tv_bottomsheet.setOnClickListener(this);
        iv_refresh_map.setOnClickListener(this);
        rl_bottom_sheet.setOnClickListener(this);
        initRecyclerView();
        initMap();
        mPresenter.getCompanyData();
    }

    private void initRecyclerView() {
        asset_recycleview.setLayoutManager(new LinearLayoutManager(_mActivity));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider_10dp));
        asset_recycleview.addItemDecoration(itemDecoration);
        propertyListAdapter = new PropertyListAdapter(R.layout.partner_item_property, new ArrayList<PropertyBean>());
        asset_recycleview.setAdapter(propertyListAdapter);
    }

    private void initBottomSheetState() {
        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheet.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            bottomSheet.setBackgroundColor(getResources().getColor(R.color.color_f5));
        }
    }

    private void initMap() {
        mapHelper = new MapHelper(_mActivity, mapview);
        mapHelper.getBaiduMap().setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
            }
        });
        mapHelper.setOnMapClickListener(this);
        LocationHelper.getLocation(_mActivity, new MapListener.OnLocationListener() {
            @Override
            public void location(double lat, double lng) {
                LogUtils.d(lat + "-----" + lng);
                currentLatLng.add(new LatLng(lat, lng));
                mapHelper.moveTo(lat, lng, MapHelper.COUNTRY_ZINDEX);
            }
        });
    }

    @Override
    public void onMapClickListener(double lat, double lng) {

    }

    @Override
    public void onMarkerClickListener(int currentPosition, int previousPosition) {

    }

    @Override
    public void onClick(View view) {
        if (view == tv_bottomsheet) {
            if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
        if (view == rl_bottom_sheet) {
            if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }
        if (view == tv_custom_name) {
            mPresenter.showCompanyDialog(_mActivity, selectCompanyId);
        }
        if (view == iv_refresh_map) {
            RotateAnimation rotate = new RotateAnimation(0, 360,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(1000);
            iv_refresh_map.startAnimation(rotate);
            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mPresenter.getCompanyData();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    private String selectCompanyId;

    @Override
    public void setSelectCompanyInfo(TypeBean typeBean) {
        mPresenter.getPropertyData(typeBean.getTypeId());
        mPresenter.getSingleData(typeBean.getTypeId());
        selectCompanyId = typeBean.getTypeId();
        tv_custom_name.setText(typeBean.getTypeName());
    }

    @Override
    public void setMapData(List<PropertySingleBean.SingleBean> propertySingleBeans) {
        latLngs = new ArrayList<>();
        for (int i = 0; i < propertySingleBeans.size(); i++) {
            latLngs.add(new LatLng(propertySingleBeans.get(i).getLat(), propertySingleBeans.get(i).getLng()));
        }
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_map_marker);
        for (int i = 0; i < latLngs.size(); i++) {
            LatLng latLng = latLngs.get(i);
            LatLng point1 = new LatLng(latLng.latitude, latLng.longitude);
            //创建OverlayOptions属性
            OverlayOptions option1 = new MarkerOptions()
                    .position(point1)
                    .zIndex(MapHelper.NORMAL_ZINDEX)
                    .icon(bitmap);
            options.add(option1);
        }
        mapHelper.addMarkers(options);
        mapHelper.setZoomWithLatLngs(latLngs);
    }

    @Override
    public void setBottomSheetData(List<PropertyBean> propertyBeans) {
        rl_bottom_sheet_hiden.setVisibility(View.GONE);
        bottomSheet.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp)
                .repeat(0)
                .duration(1500)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {

                    }
                })
                .playOn(bottomSheet);
        propertyListAdapter.setNewData(propertyBeans);
    }

    @Override
    public void clearMapMarker() {
        mapHelper.removeMarkers();
    }

    @Override
    public void setDefaultCompanyInfo(CompanyBean companyInfo, boolean isFirst) {
        if (isFirst) {
            tv_custom_name.setText(companyInfo.getCompany_name());
            selectCompanyId = companyInfo.getCompany_id();
        }
        mPresenter.getPropertyData(selectCompanyId);
        mPresenter.getSingleData(selectCompanyId);
    }

    @Override
    public void showEmptyData() {
        bottomSheet.setVisibility(View.GONE);
        rl_bottom_sheet_hiden.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        ToastUitl.showShort(error);
    }

    @Override
    public void showEmptyCompany() {
        ToastUitl.showShort("您还没有正式客户");
    }
}
