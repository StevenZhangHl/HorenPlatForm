package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.ReturnOrderPD;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.ToastUitl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/10:51
 * @description :还箱物品
 * @github :https://github.com/chenyy0708
 */
public class OrderReturnPDAdapter extends BaseQuickAdapter<ReturnOrderPD.PdListBean, BaseViewHolder> {

    List<EditText> mEditTexts = new ArrayList<>();

    private ChangeListener listener;

    public OrderReturnPDAdapter(@Nullable List<ReturnOrderPD.PdListBean> data, ChangeListener listener) {
        super(R.layout.item_return_box_product, data);
        this.listener = listener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ReturnOrderPD.PdListBean item) {
        // 计算最大输入数量 = 最大还箱数量 + 10%
        final int maxNumber = item.getQty() + (int) (Math.ceil(0.1f * item.getQty()));
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_photo));
        helper.setText(R.id.tv_name, item.getProduct_name())
                .setText(R.id.tv_type, item.getProduct_type())
                .setText(R.id.tv_return_box_number, "剩余还箱数: " + item.getQty());

        final EditText mEtName = helper.getView(R.id.et_number);
        mEditTexts.add(mEtName);
        // 减
        helper.setOnClickListener(R.id.fl_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(mEtName.getText().toString()) != 0) {
                    mEtName.setText((Integer.valueOf(mEtName.getText().toString()) - 1) + "");
                    mEtName.setSelection(mEtName.getText().toString().length());
                }
            }
        });
        // 加
        helper.setOnClickListener(R.id.fl_add, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = (Integer.valueOf(mEtName.getText().toString()) + 1);
                if (count > maxNumber) { // 超过限租数
                    ToastUitl.showShort("数量超出范围");
                } else {
                    mEtName.setText((Integer.valueOf(mEtName.getText().toString()) + 1) + "");
                    mEtName.setSelection(mEtName.getText().toString().length());
                }
            }
        });
        // 输入监听
        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //不能输入0开始的两位数
                if (editable.toString().length() > 1) {
                    if (editable.toString().startsWith("0")) {
                        mEtName.setText("0");
                        item.setOrder_qty(0);
                        mEtName.setSelection(mEtName.getText().toString().length());
                    }
                }
                if (TextUtils.isEmpty(editable.toString())) {
                    // 记录数量
                    item.setOrder_qty(0);
                } else {
                    // 记录数量
                    item.setOrder_qty(Integer.valueOf(editable.toString()));
                }
                if (listener != null)
                    listener.onNumberChangeListener(helper.getLayoutPosition());
            }
        });
        // 获取焦点
        mEtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) { // 获取焦点
                    mEtName.setSelection(mEtName.getText().toString().length());
                } else { // 失去焦点
                    if (TextUtils.isEmpty(mEtName.getText().toString())) { // 为空
                        mEtName.setText("0");
                    }
                    if (Integer.valueOf(mEtName.getText().toString()) > maxNumber) { // 超过最大数
                        mEtName.setText(Integer.valueOf(item.getQty()) + "");
                        ToastUitl.showShort("数量超出范围");
                    }
                }
            }
        });
        // 设置最大还箱数
        mEtName.setText(0 + "");
    }

    public void setonKeyboardClose() {
        for (EditText mEditText : mEditTexts) {
            if (mEditText.isFocused()) {
                mEditText.clearFocus();
            }
        }
    }

    /**
     * 删除信息
     */
    public interface ChangeListener {
        /**
         * 输入框数量发生变化
         */
        void onNumberChangeListener(int position);
    }
}
