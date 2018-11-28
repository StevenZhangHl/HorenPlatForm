package com.cyy.company.ui.fragment.eye.downstream;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.DownStreamBean;
import com.cyy.company.ui.adapter.SteamAdapter;
import com.cyy.company.utils.DownStreamPickerViewHelper;
import com.cyy.company.utils.RvEmptyUtils;
import com.cyy.company.widget.SlideLeftAnimation;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseFragment;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.OrderPopupBean;
import com.horen.base.listener.TimePickerListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.DisplayUtil;
import com.horen.base.widget.SelectDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/16:18
 * @description :下游使用箱数Fragment
 * @github :https://github.com/chenyy0708
 */
public class StreamBoxFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener {

    private TextView mTvBoxType;
    private TextView mTvSelectTime;
    private RecyclerView mRecyclerView;
    private DownStreamPickerViewHelper timePickerViewHelper;


    /**
     * 箱型
     */
    private String ctnr_type = "all";
    /**
     * 月份
     */
    private String date_month = "all";

    private int type;

    private List<OrderPopupBean> statusList = new ArrayList<>();
    private int typePosition = 0;
    private SmartRefreshLayout mRefreshLayout;
    private FrameLayout mFlContainer;
    private SteamAdapter steamAdapter;
    private int screenWidth;

    public static StreamBoxFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        StreamBoxFragment fragment = new StreamBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_down_stream_box;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        screenWidth = DisplayUtil.getScreenWidth(_mActivity);
        mTvBoxType = (TextView) rootView.findViewById(R.id.tv_box_type);
        mTvSelectTime = (TextView) rootView.findViewById(R.id.tv_select_time);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mFlContainer = (FrameLayout) rootView.findViewById(R.id.fl_container);
        mTvBoxType.setOnClickListener(this);
        mTvSelectTime.setOnClickListener(this);
        timePickerViewHelper = new DownStreamPickerViewHelper(_mActivity, "时间");
        timePickerViewHelper.setTimePickerListener(new TimePickerListener() {
            @Override
            public void onTimePicker(String time) {
                if (time.equals("全部时间")) {
                    date_month = "all";
                } else {
                    date_month = time;
                }
                mTvSelectTime.setText(time);
                getData();
            }
        });
        statusList.add(new OrderPopupBean("全部", "all"));
        statusList.add(new OrderPopupBean("IF1040", "IF1040"));
        statusList.add(new OrderPopupBean("KF975", "KF975"));
        statusList.add(new OrderPopupBean("OF330", "OF330"));
        statusList.add(new OrderPopupBean("RPC6411", "RPC6411"));
        statusList.add(new OrderPopupBean("RPC6419", "RPC6419"));
        statusList.add(new OrderPopupBean("RPC6422", "RPC6422"));
        // 下游数据
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        steamAdapter = new SteamAdapter(new ArrayList<DownStreamBean.PdListBean>(), type);
        steamAdapter.openLoadAnimation(new SlideLeftAnimation());
        mRecyclerView.setAdapter(steamAdapter);
        // 获取数据
        getData();
    }

    private void getData() {
        Observable<BaseEntry<DownStreamBean>> slaveOrgHistogram = type == HRConstant.BOX_USED ?
                ApiCompany.getInstance() // 使用箱数
                        .getSlaveOrgHistogram(CompanyParams.getSlaveOrgHistogram(ctnr_type, date_month)) :
                ApiCompany.getInstance() // 损坏率
                        .getOrgDamageRateList(CompanyParams.getSlaveOrgHistogram(ctnr_type, date_month));
        mRxManager.add(slaveOrgHistogram
                .compose(RxHelper.<DownStreamBean>getResult())
                .subscribeWith(new BaseObserver<DownStreamBean>() {
                    @Override
                    protected void onSuccess(DownStreamBean downStreamBean) {
                        if (!CollectionUtils.isNullOrEmpty(downStreamBean.getPdList())) {
                            // 设置新数据
                            steamAdapter.setMaxNumber(screenWidth, downStreamBean.getPdList());
                        } else {
                            RvEmptyUtils.setEmptyView(steamAdapter, mRecyclerView);
                        }
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
    public void onClick(View view) {
        if (view.getId() == R.id.tv_box_type) { // 全部箱型
            new SelectDialog(getActivity(), statusList, typePosition)
                    .setSelectLinstener(new SelectDialog.onSelectLinstener() {
                        @Override
                        public void onItemSelectLinstener(int position, String name, String type) {
                            if (!mRefreshLayout.isRefreshing()) {
                                typePosition = position;
                                mTvBoxType.setText(name.equals("全部") ? "全部箱型" : name);
                                ctnr_type = type;
                                getData();
                            }
                        }
                    }).showPopupWindow(mFlContainer);
        } else if (view.getId() == R.id.tv_select_time) { // 全部时间
            timePickerViewHelper.show();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getData();
    }
}
