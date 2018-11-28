package com.horen.partner.mvp.presenter;

import android.support.v7.widget.RecyclerView;

import com.horen.base.rx.BaseObserver;
import com.horen.partner.adapter.HomeTabAdapter;
import com.horen.partner.bean.ApiResultHomeData;
import com.horen.partner.bean.Plan;
import com.horen.partner.bean.PlanTypeList;
import com.horen.partner.mvp.contract.PlatformHomeContract;


import java.util.LinkedList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/6 9:02
 * Description:This isPlatformPresenter
 */
public class PlatformPresenter extends PlatformHomeContract.Presenter {
    private List<Object> datas = new LinkedList<>();

    @Override
    public void getData() {
        mRxManager.add(mModel.getData().subscribeWith(new BaseObserver<ApiResultHomeData>(mContext, true) {
            @Override
            protected void onSuccess(ApiResultHomeData homeData) {
                if (homeData.solution_homebanners != null && homeData.solution_homebanners.size() > 0) {
                    mView.setBanner(homeData.solution_homebanners);
                }
                if (homeData.solution_homehotsolutions != null && homeData.solution_homehotsolutions.size() > 0) {
                    List<PlanTypeList> sol = homeData.solution_homehotsolutions;
                    for (int i = 0; i < sol.size(); i++) {
                        PlanTypeList typeList = sol.get(i);
                        if (typeList.type_solutions != null && typeList.type_solutions.size() > 0) // 方案不为空，添加标题
                            datas.add(typeList);
                        List<Plan> plans = typeList.type_solutions;
                        for (int j = 0; j < plans.size(); j++) {
                            datas.add(plans.get(j));
                        }
                    }
                    mView.setData(datas); // 初始化RecycleView
                    for (int d = 0; d < datas.size(); d++) {
                        Object o = datas.get(d);
                        if (o instanceof PlanTypeList) {
                            PlanTypeList p = (PlanTypeList) o;
                            p.tabIndex = d; // 记录当前数据对应RecycleView的位置，用于点击跳转RecycleView位置使用
                        }
                    }
                    mView.setHomeTab(homeData.solution_homehotsolutions); // 初始化顶部tab按钮
                }
            }

            @Override
            protected void onError(String s) {
                mView.onError(s);
            }
        }));
    }

    @Override
    public void changeTabLayout(RecyclerView recyclerView, int firstItemPosition, HomeTabAdapter homeTabAdapter) {
        Object o = datas.get(firstItemPosition);
        if (o instanceof PlanTypeList) {
            PlanTypeList planTypeList = (PlanTypeList) o;
            Integer tag = (Integer) recyclerView.getTag();
            if (tag == null || tag != planTypeList.tabIndex) {
                recyclerView.setTag(planTypeList.tabIndex);
                // 放大图标
                for (int i = 0; i < homeTabAdapter.getData().size(); i++) { // 判断是第几个位置
                    if (homeTabAdapter.getData().get(i).tabIndex == planTypeList.tabIndex) {
                        homeTabAdapter.changePostion(i);
                        return;
                    }
                }
            }
        }
    }
}
