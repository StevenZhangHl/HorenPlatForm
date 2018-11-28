package com.horen.partner.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.horen.partner.R;

/**
 * @author :ChenYangYi
 * @date :2018/08/29/10:16
 * @description :统一RecycleView设置空数据页面
 * @github :https://github.com/chenyy0708
 */
public class RvEmptyUtils {

    /**
     * 统一设置空数据View
     *
     * @param adapter      adapter
     * @param recyclerView 列表
     */
    public static void setEmptyView(BaseQuickAdapter adapter, RecyclerView recyclerView) {
        adapter.setNewData(null);
        adapter.setEmptyView(R.layout.include_data_empty, (ViewGroup) recyclerView.getParent());
    }

    /**
     * 统一设置空数据View
     *
     * @param adapter      adapter
     * @param recyclerView 列表
     * @param emptyViewId  空数据id
     */
    public static void setEmptyView(BaseQuickAdapter adapter, RecyclerView recyclerView, @LayoutRes int emptyViewId) {
        adapter.setNewData(null);
        adapter.setEmptyView(emptyViewId, (ViewGroup) recyclerView.getParent());
    }
}
