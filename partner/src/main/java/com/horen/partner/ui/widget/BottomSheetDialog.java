package com.horen.partner.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.partner.R;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.PropertyBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/4 17:09
 * Description:This isBottomSheetDialog
 */
public class BottomSheetDialog extends Dialog {
    private RecyclerView recyclerView;
    private Context mContext;
    private List<PropertyBean> list;

    public BottomSheetDialog(@NonNull Context context, List<PropertyBean> info) {
        super(context, R.style.Theme_Design_BottomSheetDialog);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
        this.mContext = context;
        this.list = info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_bottom_sheet);
        initView();
        setData(list);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.asset_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void setData(List<PropertyBean> mData) {
        final BaseQuickAdapter<PropertyBean, BaseViewHolder> quickAdapter = new BaseQuickAdapter<PropertyBean, BaseViewHolder>(R.layout.partner_item_property, mData) {
            @Override
            protected void convert(final BaseViewHolder helper, final PropertyBean item) {
                helper.setText(R.id.tv_good_name, item.getCtnr_name());
                helper.setText(R.id.tv_good_type, item.getCtnr_type());
                helper.setText(R.id.tv_product_total, item.getTotal_num() + "");
                helper.setText(R.id.tv_empty_count, item.getTotal_empty() + "");
                helper.setText(R.id.tv_sign_count, item.getTotal_loss() + item.getTotal_normal() + "");
            }
        };
        recyclerView.setAdapter(quickAdapter);
    }
}
