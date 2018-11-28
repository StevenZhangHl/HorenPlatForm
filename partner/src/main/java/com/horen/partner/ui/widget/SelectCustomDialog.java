package com.horen.partner.ui.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.app.BaseApp;
import com.horen.base.bean.TypeBean;
import com.horen.base.listener.TypeSelectListener;
import com.horen.partner.R;

import java.util.ArrayList;

/**
 * Author:Steven
 * Time:2018/8/13 15:11
 * Description:This isSelectCustomDialog
 */
public class SelectCustomDialog extends BottomSheetDialogFragment {
    private RecyclerView recyclerView;
    private ArrayList<TypeBean> mData;
    private TypeSelectListener listener;
    private TextView tvTitle;
    /**
     * 业务类型
     */
    private TypeBean type;

    //    private SuperButton stb_submit;
    private String selectedId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_type, null);
        recyclerView = view.findViewById(R.id.recycler_view);
        tvTitle = view.findViewById(R.id.tv_title);
//        stb_submit = view.findViewById(R.id.stb_submit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initView();
        dialog.setContentView(view);
        return dialog;
    }

    private void initView() {
        mData = getArguments().getParcelableArrayList("mData");
        tvTitle.setText(getArguments().getString("title"));
        selectedId = getArguments().getString("selectedId");
        setData(mData);
    }

    public void setData(ArrayList<TypeBean> mData) {
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
