package com.horen.user.ui.activity.service;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudRgcInfo;
import com.baidu.mapapi.cloud.CloudRgcResult;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.constant.EventBusCode;
import com.horen.base.util.DisplayUtil;
import com.horen.maplib.MapHelper;
import com.horen.user.R;
import com.horen.user.bean.OrgInfo;
import com.horen.user.mvp.contract.BusinessScopeContract;
import com.horen.user.mvp.model.BusinessScopeModel;
import com.horen.user.mvp.presenter.BusinessScopePresenter;
import com.horen.user.ui.activity.accout.ChangeContactsActivity;
import com.horen.user.ui.activity.accout.ChangePhoneActivity;
import com.jaeger.library.StatusBarUtil;

import org.simple.eventbus.Subscriber;

/**
 * @author :ChenYangYi
 * @date :2018/09/04/16:45
 * @description :业务范围
 * @github :https://github.com/chenyy0708
 */
@Route(path = ARouterPath.BUSINESS_SCOPE)
public class BusinessScopeActivity extends BaseActivity<BusinessScopePresenter, BusinessScopeModel> implements View.OnClickListener, CloudListener, BusinessScopeContract.View {

    private TextureMapView mMapView;
    private Toolbar mToolBar;
    private TextView mTvTitle;
    private LinearLayout mLlAddress;
    private TextView mTvAddress;
    private LinearLayout mLlContact;
    private TextView mTvContact;
    private LinearLayout mLlPhone;
    private TextView mTvPhone;
    private MapHelper mapHelper;
    private OrgInfo mOrgInfo;

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_business_scope;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        // 改变状态栏文字颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        mMapView = (TextureMapView) findViewById(R.id.map_view);
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mLlAddress = (LinearLayout) findViewById(R.id.ll_address);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mLlContact = (LinearLayout) findViewById(R.id.ll_contact);
        mTvContact = (TextView) findViewById(R.id.tv_contact);
        mLlPhone = (LinearLayout) findViewById(R.id.ll_phone);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mLlAddress.setOnClickListener(this);
        mLlContact.setOnClickListener(this);
        mLlPhone.setOnClickListener(this);
        initToolbar(mToolBar, false);
        mapHelper = new MapHelper(mContext, mMapView);
        // 地图初始化完毕
        mapHelper.getBaiduMap().setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                // 初始化反地理编译
                CloudManager.getInstance().init(BusinessScopeActivity.this);
                // 获取网点信息
                mPresenter.getOrgInfo();
            }
        });

    }

    @Override
    protected void setFitsSystemWindows() {
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_address) {
            AddressSugActivity.startActivityForResult(this);
        } else if (view.getId() == R.id.ll_contact) {
            ChangeContactsActivity.startActivity(mContext, "", ChangeContactsActivity.NAME_CONTACT, mOrgInfo.getOrgInfo().getOrg_id());
        } else if (view.getId() == R.id.ll_phone) {
            ChangePhoneActivity.startActivity(mContext, mTvPhone.getText().toString(), ChangePhoneActivity.PHONE_CONTACT, mOrgInfo.getOrgInfo().getOrg_id());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //AddressSugActivity返回地址结果，后续可根据需要增加返回字段，如经纬度
        if (resultCode == AddressSugActivity.RESULT_CODE) {
            String org_address = data.getStringExtra(AddressSugActivity.RESULT_ADDRESS);
            String lat = data.getStringExtra(AddressSugActivity.RESULT_LAT);
            String lng = data.getStringExtra(AddressSugActivity.RESULT_LNG);
            // 更新网点地址
            mPresenter.updateOrgAddress(org_address, mOrgInfo.getOrgInfo().getOrg_id(), Double.valueOf(lat), Double.valueOf(lng));
        }
    }

    /**
     * 根据经纬度 反地理编码获取城市名
     *
     * @param lat 经度
     * @param lng 纬度
     */
    private void getCity(String lat, String lng) {
        // 标记当前市
        CloudRgcInfo crinfo = new CloudRgcInfo();
        crinfo.geoTableId = 194362;
        crinfo.location = lat + "," + lng;
        // 发起云反地理编码检索
        CloudManager.getInstance().rgcSearch(crinfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CloudManager.getInstance().destroy();
    }

    @Override
    public void onGetSearchResult(CloudSearchResult cloudSearchResult, int i) {

    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult detailSearchResult, int i) {

    }

    @Override
    public void onGetCloudRgcResult(CloudRgcResult cloudRgcResult, int i) {
        //获取云反地理编码检索结果
        mapHelper.clearCityOverLay();
        mapHelper.setHighlightCity(cloudRgcResult.addressCompents.city, DisplayUtil.dip2px(5), Color.parseColor("#D8F1C2"),
                Color.parseColor("#336FBA2C"));
    }

    /**
     * 网点信息
     *
     * @param orgInfo 网点
     */
    @Override
    public void getOrgInfoSuccess(OrgInfo orgInfo) {
        mOrgInfo = orgInfo;
        // 标记网点所在城市
        if (orgInfo.getOrgInfo().getOrg_latitude() != 0 && orgInfo.getOrgInfo().getOrg_longitude() != 0) {
            getCity(String.valueOf(orgInfo.getOrgInfo().getOrg_latitude()), String.valueOf(orgInfo.getOrgInfo().getOrg_longitude()));
        }
        // 添加网点图标
        mapHelper.addMarker(orgInfo.getOrgInfo().getOrg_latitude(), orgInfo.getOrgInfo().getOrg_longitude(),
                R.drawable.ic_location, 0, MapHelper.NORMAL_ZINDEX);
        // 联系人、电话、地址
        mTvContact.setText(orgInfo.getOrgInfo().getOrg_contact());
        mTvPhone.setText(orgInfo.getOrgInfo().getOrg_tel());
        mTvAddress.setText(orgInfo.getOrgInfo().getOrg_address());
    }

    /**
     * 网点信息
     *
     * @param org_address   网点地址
     * @param org_latitude  纬度
     * @param org_longitude 经度
     */
    @Override
    public void updateOrgAddressSuccess(String org_address, double org_latitude, double org_longitude) {
        // 添加Maker标记
        mapHelper.clearMap();
        mapHelper.addMarker(org_latitude, org_longitude, R.drawable.ic_location, 0, MapHelper.NORMAL_ZINDEX);
        getCity(String.valueOf(org_latitude), String.valueOf(org_longitude));
        mTvAddress.setText(org_address);
    }

    /**
     * 更换联系人电话
     */
    @Subscriber(tag = EventBusCode.CHANGE_ORG_CONTACT)
    private void changeOrgContact(String contact) {
        showToast("更改网点联系人成功");
        mTvContact.setText(contact);
    }

    /**
     * 更换联系人成功
     */
    @Subscriber(tag = EventBusCode.CHANGE_ORG_PHONE)
    private void changeOrgPhone(String phone) {
        showToast("更改网点联系电话成功");
        mTvPhone.setText(phone);
    }
}
