package com.cyy.company.ui.fragment.main;

import android.os.Bundle;
import android.view.View;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AssetCheckBean;
import com.cyy.company.ui.activity.eye.AssetsListActivity;
import com.cyy.company.ui.fragment.eye.asset.AssetAnalysisFragment;
import com.cyy.company.ui.fragment.eye.asset.AssetMapFragment;
import com.cyy.company.ui.fragment.eye.downstream.DownstreamFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragment;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.SPUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;
import com.horen.base.widget.HRViewPager;
import com.horen.base.widget.MessageDialog;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/10:50
 * @description :天眼
 * @github :https://github.com/chenyy0708
 */
public class EyeFragment extends BaseFragment implements OnTabSelectListener, View.OnClickListener {

    private HRTitle mToolBar;
    private SlidingTabLayout mTabLayout;
    private HRViewPager mViewPager;

    private List<SupportFragment> mFragments = new ArrayList<>();

    private String[] mTitles;

    private boolean isInit;

    public static EyeFragment newInstance() {
        Bundle args = new Bundle();
        EyeFragment fragment = new EyeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_eye;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) rootView.findViewById(R.id.tool_bar);
        mTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.tab_layout);
        mViewPager = (HRViewPager) rootView.findViewById(R.id.view_pager);
        if (UserHelper.checkDownStream()) { // 下游只需要显示一个地图Fragment
            mFragments.add(AssetMapFragment.newInstance());
            mTitles = new String[]{"资产分布"};
            mTabLayout.setVisibility(View.GONE);
            mToolBar.getTvLeft().setVisibility(View.INVISIBLE);
        } else {
            mFragments.add(AssetMapFragment.newInstance());
//            mFragments.add(MsgNoticeFragment.newInstance());
//            mFragments.add(MsgNoticeFragment.newInstance());
            mFragments.add(AssetAnalysisFragment.newInstance());
            mFragments.add(DownstreamFragment.newInstance());
            mTitles = new String[]{"资产分布", "资产分析", "下游概况"};
        }
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        // viewpager和tablayout切换监听
        mTabLayout.setViewPager(mViewPager, mTitles);
        mTabLayout.setOnTabSelectListener(this);
        mToolBar.setOnRightTextListener(this);
        mToolBar.setOnLeftTextListener(this);
        getToadyCheckStatus();
        isInit = true;
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
        if (view.getId() == HRTitle.TITLE_LEFT_TEXT) { // 一键盘点
            new MessageDialog(_mActivity)
                    .showTitle("一键盘点")
                    .showContent("箱子信号将在半小时内发射一次 （每天只能盘点一次，当天24点刷新）")
                    .setButtonTexts("取消", "确认")
                    .setOnClickListene(new MessageDialog.OnClickListener() {
                        @Override
                        public void onLeftClick() {

                        }

                        @Override
                        public void onRightClick() {
                            // 发送一键盘点
                            sendRtpCheckCmd();
                        }
                    })
                    .show();
        } else if (view.getId() == HRTitle.TITLE_RIGHT_TEXT) { // 资产列表
            AssetsListActivity.startAction(_mActivity);
        }
    }

    /**
     * 一键盘点
     */
    private void sendRtpCheckCmd() {
        mRxManager.add(ApiCompany.getInstance().sendRtpCheckCmd(CompanyParams.sendRtpCheckCmd())
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>(_mActivity, true) {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        mToolBar.setLeftTextColor(R.color.color_999);
                        mToolBar.getTvLeft().setEnabled(false);
                        // 开始倒计时
                        AssetMapFragment mapFragment = (AssetMapFragment) mFragments.get(0);
                        mapFragment.countdown();
                        // 保存一键盘点时间戳
                        SPUtils.setSharedLongData(_mActivity, HRConstant.INVENTORY_TIME, System.currentTimeMillis());
                        // 保存一键盘点的用户名id
                        SPUtils.setSharedStringData(_mActivity, HRConstant.INVENTORY_ACCOUNT, UserHelper.getUserInfo().getLoginInfo().getUser_id());
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isInit)
            getToadyCheckStatus();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInit && !isHidden())
            getToadyCheckStatus();
    }

    /**
     * 获取今日一键盘点状态
     */
    public void getToadyCheckStatus() {
        mRxManager.add(ApiCompany.getInstance().getTodayCheckList(CompanyParams.getDefaultPram())
                .compose(RxHelper.<AssetCheckBean>getResult())
                .subscribeWith(new BaseObserver<AssetCheckBean>() {
                    @Override
                    protected void onSuccess(AssetCheckBean orgDetail) {
                        if (CollectionUtils.isNullOrEmpty(orgDetail.getCheckList())) { // 今日没有盘点，可以盘点
                            mToolBar.getTvLeft().setEnabled(true);
                            mToolBar.setLeftTextColor(R.color.base_text_color_light);
                        } else { // 今日已经盘点，盘点按钮灰色
                            mToolBar.getTvLeft().setEnabled(false);
                            mToolBar.setLeftTextColor(R.color.color_999);
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }
}
