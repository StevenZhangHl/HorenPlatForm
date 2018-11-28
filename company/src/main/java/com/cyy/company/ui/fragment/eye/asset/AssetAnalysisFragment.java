package com.cyy.company.ui.fragment.eye.asset;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AnalysisTotal;
import com.cyy.company.listener.IRefreshFragment;
import com.cyy.company.ui.adapter.AnalysisListAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragment;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.DisplayUtil;
import com.horen.base.widget.NumberAnimTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/11:15
 * @description :资产分析Fragment
 * @github :https://github.com/chenyy0708
 */
public class AssetAnalysisFragment extends BaseFragment implements OnTabSelectListener, View.OnClickListener, OnRefreshListener {

    private SmartRefreshLayout mRefreshLayout;
    private NumberAnimTextView mTvAssetMoney;
    private TextView mTvEmptyCount;
    private TextView mTvFullCount;
    private TextView mTvTransitCount;
    private RecyclerView mRecyclerView;
    private TextView mTvExpendle;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] mTitles = {"资产在租量统计", "资产损坏数统计"};

    /**
     * 是否展开状态，默认false
     */
    private boolean isExpendle = false;

    private List<SupportFragment> mFragments = new ArrayList<>();
    private LinearLayout mLLTopContent;
    private int mVpHeight;
    private AnalysisListAdapter orgAdapter;
    /**
     * 资产总数
     */
    private int total;

    public static AssetAnalysisFragment newInstance() {
        Bundle args = new Bundle();
        AssetAnalysisFragment fragment = new AssetAnalysisFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_asset_analysis;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mLLTopContent = (LinearLayout) rootView.findViewById(R.id.ll_top_content);
        mTvAssetMoney = (NumberAnimTextView) rootView.findViewById(R.id.tv_asset_money);
        mTvEmptyCount = (TextView) rootView.findViewById(R.id.tv_empty_count);
        mTvFullCount = (TextView) rootView.findViewById(R.id.tv_full_count);
        mTvTransitCount = (TextView) rootView.findViewById(R.id.tv_transit_count);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mTvExpendle = (TextView) rootView.findViewById(R.id.tv_expendle);
        mTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mRefreshLayout.setOnRefreshListener(this);
        mTvExpendle.setOnClickListener(this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        orgAdapter = new AnalysisListAdapter(new ArrayList<AnalysisTotal.PdListBean>());
        mRecyclerView.setAdapter(orgAdapter);
        mFragments.add(AssetRentFragment.newInstance(HRConstant.RENT));
        mFragments.add(AssetDamageFragment.newInstance(HRConstant.DAMAGE));
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setViewPager(mViewPager, mTitles);
        mTabLayout.setOnTabSelectListener(this);
        // 动态计算底部ViewPager的高度
        mLLTopContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLLTopContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // 头部内容高度
                int topHeight = mLLTopContent.getHeight();
                // ViewPager高度 = 屏幕高度 - 头部Title - Tablayout高度 - 底部切换Tab - 头部内容高度
                mVpHeight = DisplayUtil.getScreenHeight(_mActivity)
                        - DisplayUtil.dip2px(55 + 25)  // 头部Title
                        - DisplayUtil.dip2px(30) // Tablayout高度
                        - DisplayUtil.dip2px(55) // 底部切换Tab
                        - topHeight;
                // 设置ViewPager高度
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
//                params.height = mVpHeight;
//                mViewPager.setLayoutParams(params);
            }
        });
        // 分析:资产总数查询
        getAnalysisTotalList();
    }

    /**
     * 分析:资产总数查询
     */
    private void getAnalysisTotalList() {
        mRxManager.add(ApiCompany.getInstance()
                .getAnalysisTotalList(CompanyParams.getCompanyId())
                .compose(RxHelper.<AnalysisTotal>getResult())
                .subscribeWith(new BaseObserver<AnalysisTotal>() {
                    @Override
                    protected void onSuccess(AnalysisTotal analysisTotal) {
                        total = 0;
                        // 总空箱数
                        int empty = 0;
                        // 满箱数
                        int full = 0;
                        // 在途数
                        int onWay = 0;
                        for (AnalysisTotal.PdListBean pdListBean : analysisTotal.getPdList()) {
                            total += pdListBean.getTotal_qty();
                            empty += pdListBean.getEmpty_qty();
                            full += pdListBean.getFull_qty();
                            onWay += pdListBean.getEn_route_qty();
                        }
                        mTvAssetMoney.setNumberString(total + "");
                        mTvEmptyCount.setText("空箱数: " + empty);
                        mTvFullCount.setText("满箱数: " + full);
                        mTvTransitCount.setText("在途数: " + onWay);
                        orgAdapter.setNewData(analysisTotal.getPdList());
                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                        mRefreshLayout.finishRefresh();
                    }
                }));
    }

    @Override
    public void onTabSelect(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_expendle) { // 展开收取
            if (isExpendle) { // 展开状态，收起列表
                setDrawableRightIcon(R.drawable.icon_eye_down_arrow_gray);
                mTvExpendle.setText("展开");
                AnimationUtils.scaleRecyclerView(mRecyclerView, 300, DisplayUtil.dip2px(orgAdapter.getData().size() * 90),
                        DisplayUtil.dip2px(90));
                isExpendle = false;
            } else { // 收起状态，展开列表
                setDrawableRightIcon(R.drawable.icon_eye_up_arrow_gray);
                AnimationUtils.scaleRecyclerView(mRecyclerView, 300, DisplayUtil.dip2px(90), DisplayUtil.dip2px(orgAdapter.getData().size() * 90));
                mTvExpendle.setText("缩回");
                isExpendle = true;
            }
        }
    }

    public void setDrawableRightIcon(@DrawableRes int icon) {
        Drawable drawable = _mActivity.getResources().getDrawable(icon);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvExpendle.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getAnalysisTotalList();
        // 刷新Fragment折线图
        IRefreshFragment fragment = (IRefreshFragment) mFragments.get(mTabLayout.getCurrentTab());
        fragment.onRefreshListener();
    }
}
