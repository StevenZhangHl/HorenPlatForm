package com.cyy.company.utils;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyy.company.R;

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
        adapter.setEmptyView(R.layout.widget_empty_page, (ViewGroup) recyclerView.getParent());
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

    /**
     * 动态设置文字的空View
     *
     * @param adapter      adapter
     * @param recyclerView 列表
     * @param msg          暂无数据提示信息
     */
    public static void setEmptyView(BaseQuickAdapter adapter, RecyclerView recyclerView, String msg) {
        View view = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.widget_empty_page, null);
        TextView textView = view.findViewById(R.id.empty_text);
        textView.setText(msg);
        adapter.setNewData(null);
        adapter.setEmptyView(view);
    }
}
