package com.horen.user.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.bean.TypeBean;
import com.horen.base.listener.MultipleSelectListener;
import com.horen.base.listener.TypeSelectListener;
import com.horen.user.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/31 15:44
 * Description:This isCustomDialog
 */
public class CustomDialog extends Dialog {
    private RecyclerView recyclerView;
    private List<TypeBean> mData;
    private MultipleSelectListener listener;
    private TextView tvTitle;
    private TextView tv_ok;
    /**
     * 单选
     */
    private List<Integer> mSelectedIds = new ArrayList<>();
    private Context mContext;
    private String mTitle;

    public CustomDialog(@NonNull Context context, List<TypeBean> data, String title) {
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
        this.mData = data;
        this.mTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        initView();
        tvTitle.setText(mTitle);
        setData(mData);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        tv_ok = (TextView) findViewById(R.id.tv_submit_select_type);
    }

    private void setData(final List<TypeBean> mData) {
        final BaseQuickAdapter<TypeBean, BaseViewHolder> quickAdapter = new BaseQuickAdapter<TypeBean, BaseViewHolder>(R.layout.item_checkbox_type, mData) {
            @Override
            protected void convert(final BaseViewHolder helper, final TypeBean item) {
                final CheckBox radioButton = helper.getView(R.id.radiobutton);
                helper.setText(R.id.tv_type, item.getTypeName());
                radioButton.setChecked(item.isChecked());
                helper.setOnClickListener(R.id.ll_industy_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mData.get(helper.getLayoutPosition()).isChecked()) {
                            mData.get(helper.getLayoutPosition()).setChecked(false);
                        } else {
                            mData.get(helper.getLayoutPosition()).setChecked(true);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        };
        recyclerView.setAdapter(quickAdapter);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    for (int i = 0; i < mData.size(); i++) {
                        if (mData.get(i).isChecked()) {
                            mSelectedIds.add(Integer.parseInt(mData.get(i).getTypeId()));
                        }
                    }
                    listener.onMultipleSelect(mSelectedIds);
                }
                dismiss();
            }
        });
    }

    public void setSelectListener(MultipleSelectListener listener) {
        this.listener = listener;
    }
}
