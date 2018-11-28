package com.horen.partner.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.horen.base.base.BaseFragment;
import com.horen.base.util.DisplayUtil;
import com.horen.base.widget.HomeBannerIndexAdapter;
import com.horen.partner.R;
import com.horen.partner.adapter.HomeAdapter;
import com.horen.partner.adapter.HomeBannerAdapter;
import com.horen.partner.adapter.HomeTabAdapter;
import com.horen.partner.bean.HomeBanner;
import com.horen.partner.bean.PlanTypeList;
import com.horen.partner.mvp.contract.PlatformHomeContract;
import com.horen.partner.mvp.model.PlatFromModel;
import com.horen.partner.mvp.presenter.PlatformPresenter;
import com.horen.partner.ui.activity.PartnerActivity;
import com.horen.partner.ui.activity.PlatformWebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/3 16:02
 * Description:This isPlatformFragment  万箱页
 */
public class PlatformFragment extends BaseFragment<PlatformPresenter, PlatFromModel> implements PlatformHomeContract.View, View.OnClickListener, AppBarLayout.OnOffsetChangedListener {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private ConvenientBanner banner;
    private AppBarLayout appBarLayout;
    private LinearLayout layoutTitle;
    private LinearLayout layoutIndex;
    private LinearLayoutManager mLinearLayoutManager;
    private FrameLayout frameLayout;
    private boolean isBottom;
    private RecyclerView mRvHomeTab;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private HomeTabAdapter homeTabAdapter;
    /**
     * 标记首页Tab个数，是否为单个
     */
    private TextView tvTitle;
    private ImageView ivBannerNoData;

    public static PlatformFragment newInstance() {
        Bundle bundle = new Bundle();
        PlatformFragment platformFragment = new PlatformFragment();
        platformFragment.setArguments(bundle);
        return platformFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partner_fragment_platform;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        appBarLayout = rootView.findViewById(R.id.appbar_platform_home);
        layoutTitle = rootView.findViewById(R.id.ll_platform_home_titlebar);
        layoutIndex = rootView.findViewById(R.id.ll_home_banner_index);
        recyclerView = rootView.findViewById(R.id.rv_platform_home);
        mRvHomeTab = rootView.findViewById(R.id.rv_home_tab);
        tvTitle = rootView.findViewById(R.id.tv_title);
        frameLayout = rootView.findViewById(R.id.fl_platform);
        ivBannerNoData = rootView.findViewById(R.id.iv_banner_no_data);
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new HomeAdapter(_mActivity);
        homeTabAdapter = new HomeTabAdapter(R.layout.item_home_tab, new ArrayList<PlanTypeList>());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRvHomeTab.setLayoutManager(staggeredGridLayoutManager);
        mRvHomeTab.setAdapter(homeTabAdapter);
        banner = rootView.findViewById(R.id.banner_plarform);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // recylerView滚动，显示所有的tab按钮
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //获取第一个可见view的位置
                int firstItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                mPresenter.changeTabLayout(recyclerView, firstItemPosition, homeTabAdapter);
            }
        });
        // 动态设置banner的高度
        setBannerHeight();
        appBarLayout.addOnOffsetChangedListener(this);
        mPresenter.getData();
        if (isBottom) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            params.bottomMargin = 0;
            frameLayout.setLayoutParams(params);
        }
        // 导航按钮被点击，跳转对应位置
        homeTabAdapter.setItemClickListener(new HomeTabAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                mLinearLayoutManager.scrollToPositionWithOffset((Integer) homeTabAdapter.getData().get(position).tabIndex, 0);
                appBarLayout.setExpanded(false); // 点击关闭AppBarlayout
            }
        });
    }

    @Override
    public void setData(List<Object> datas) {
        adapter.setData(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setBanner(final List<HomeBanner> banners) {
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new HomeBannerAdapter();
            }
        }, banners);
        banner.startTurning(3900);
        if (banners.size() == 1) {
            layoutIndex.setVisibility(View.GONE);
            // 一张图片隐藏banner
            ivBannerNoData.setVisibility(View.VISIBLE);
            banner.setVisibility(View.GONE);
            // 直接使用图片显示
            Glide.with(_mActivity).load(banners.get(0).banner_image)
                    .apply(new RequestOptions().error(R.drawable.icon_banner))
                    .into(ivBannerNoData);
            ivBannerNoData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(banners.get(0).banner_url)) { // 链接不为空
                        if (banners.get(0).banner_url.contains("friend")) { // 合伙人页面
                            PartnerActivity.startAction(_mActivity);
                        } else { // 非合伙人
                            Intent intent = new Intent(_mActivity, PlatformWebViewActivity.class);
                            intent.putExtra(PlatformWebViewActivity.WEBVIEW_URL, banners.get(0).banner_url);
                            _mActivity.startActivity(intent);
                        }
                    }
                }
            });
        }
        if (banners.size() <= 0) { // 无数据
            ivBannerNoData.setVisibility(View.VISIBLE);
            banner.setVisibility(View.GONE);
        }
        banner.setOnPageChangeListener(new HomeBannerIndexAdapter(getContext(), banners.size(), layoutIndex));
    }

    @Override
    public void stopBanner() {
        if (banner != null) {
            banner.stopTurning();
        }
    }

    @Override
    public void startBanner() {
        if (banner != null) {
            banner.startTurning(3900);
        }
    }

    @Override
    public void setHomeTab(List<PlanTypeList> planTypeLists) {
        List<PlanTypeList> mData = new ArrayList<>();
        for (PlanTypeList planTypeList : planTypeLists) { // 去除方案为空的标题,去除方案名为其他包装的数据
            if (planTypeList.type_solutions != null && planTypeList.type_solutions.size() > 0 && !planTypeList.solution_typename.equals("其他包装")) // 方案不为空，添加标题
                mData.add(planTypeList);
        }
        if (mData.size() > 3) { // 超过三个使用横向滚动LayoutManager
            mRvHomeTab.setLayoutManager(new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false));
            homeTabAdapter.setLinearLayout();
//            staggeredGridLayoutManager.setSpanCount(mData.size()); // 重新设置网格的列数，根据服务器返回的数据
        } else {
            staggeredGridLayoutManager.setSpanCount(mData.size()); // 重新设置网格的列数，根据服务器返回的数据
        }
        homeTabAdapter.setNewData(mData);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int px = banner.getHeight() - DisplayUtil.dip2px(75); // 滑动到banner刚好不可见，标题栏变为不透明
        float f = (float) -verticalOffset / px;
        if (f > 1) {
            f = 1;
        }
        layoutTitle.getBackground().setAlpha((int) (f * 255));
        tvTitle.setAlpha(1.0f * f);
    }

    /**
     * 屏幕适配banner高度
     */
    private void setBannerHeight() {
        int screenWidth = DisplayUtil.getScreenWidth(getActivity()); // 屏幕宽度
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) banner.getLayoutParams();
        layoutParams.height = (int) (screenWidth / 1.68f); // 根据设计图上的比例，宽高比为1.68 : 1 求出Banner的高，屏幕适配
        banner.setLayoutParams(layoutParams); // 设置给Banner
        ivBannerNoData.setLayoutParams(layoutParams);
    }

    @Override
    protected void reLoadData() {
        mPresenter.getData();
    }
}
