package com.horen.service.utils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.util.CollectionUtils;
import com.horen.service.bean.RepairDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/19/09:27
 * @description :维修设置损坏位置
 * @github :https://github.com/chenyy0708
 */
public class RepairUtils {

    /**
     * 服务中心 维修  统一区分设置损坏位置和拒接损坏位置
     *
     * @param mLlNormalLocation 损坏位置根布局
     * @param mTvNormal         损坏位置
     * @param mLlRefuseLocation 拒绝维修位置根布局
     * @param mTvRefuse         拒绝维修位置
     * @param mData             损坏位置集合
     */
    public static void setLocation(LinearLayout mLlNormalLocation, TextView mTvNormal,
                                   LinearLayout mLlRefuseLocation, TextView mTvRefuse, List<RepairDetailBean.ServiceListBean.PositionListBean> mData) {
        // 区分两种损坏位置
        List<RepairDetailBean.ServiceListBean.PositionListBean> mNormal = new ArrayList<>();
        List<RepairDetailBean.ServiceListBean.PositionListBean> mRefuse = new ArrayList<>();
        for (RepairDetailBean.ServiceListBean.PositionListBean positionListBean : mData) {
            if (positionListBean.isAgree()) {
                mNormal.add(positionListBean);
            } else {
                mRefuse.add(positionListBean);
            }
        }
        // 正常损坏位置
        if (!CollectionUtils.isNullOrEmpty(mNormal)) {
            StringBuilder builder1 = new StringBuilder();
            for (int i = 0; i < mNormal.size(); i++) {
                builder1.append(mNormal.get(i).getPosition());
                if (mNormal.size() > 1 && mNormal.size() - 1 != i) {
                    builder1.append("; ");
                }
            }
            mLlNormalLocation.setVisibility(View.VISIBLE);
            mTvNormal.setText(builder1.toString());
        } else {
            mLlNormalLocation.setVisibility(View.GONE);
        }
        // 拒绝维修损坏位置
        if (!CollectionUtils.isNullOrEmpty(mRefuse)) {
            StringBuilder builder2 = new StringBuilder();
            for (int i = 0; i < mRefuse.size(); i++) {
                builder2.append(mRefuse.get(i).getPosition());
                if (mRefuse.size() > 1 && mRefuse.size() - 1 != i) {
                    builder2.append("; ");
                }
            }
            mLlRefuseLocation.setVisibility(View.VISIBLE);
            mTvRefuse.setText(builder2.toString());
        } else {
            mLlRefuseLocation.setVisibility(View.GONE);
        }
    }
}
