package com.horen.partner.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.bean.TypeBean;
import com.horen.base.listener.TypeSelectListener;
import com.horen.partner.R;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/31 15:44
 * Description:This isCustomDialog
 */
public class CustomDialog extends Dialog {
    private RecyclerView recyclerView;
    private List<TypeBean> mData;
    private TypeSelectListener listener;
    private TextView tvTitle;
    /**
     * 业务类型
     */
    private TypeBean type;

    /**
     * 单选
     */
    private String mSelectedId;
    private Context mContext;
    private String mTitle;

    public CustomDialog(@NonNull Context context, List<TypeBean> data, String title, String selectedId) {
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
        this.mSelectedId = selectedId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_custom);
        initView();
        setData(mData);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void setData(List<TypeBean> mData) {
        tvTitle.setText(mTitle);
        final BaseQuickAdapter<TypeBean, BaseViewHolder> quickAdapter = new BaseQuickAdapter<TypeBean, BaseViewHolder>(R.layout.item_type, mData) {
            @Override
            protected void convert(final BaseViewHolder helper, final TypeBean item) {
                final CheckBox radioButton = helper.getView(R.id.radiobutton);
                helper.setText(R.id.tv_type, item.getTypeName());
                radioButton.setChecked(item.isChecked());
                helper.setOnClickListener(R.id.ll_industy_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        type = item;
                        if (listener != null) {
                            listener.onSubmitClick(type, helper.getLayoutPosition());
                            dismiss();
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(quickAdapter);
    }

    public void setSelectListener(TypeSelectListener listener) {
        this.listener = listener;
    }
}
