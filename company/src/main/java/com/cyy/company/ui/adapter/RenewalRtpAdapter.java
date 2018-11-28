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
import com.cyy.company.bean.RenewalRtp;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.ToastUitl;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/10:51
 * @description :续租物品
 * @github :https://github.com/chenyy0708
 */
public class RenewalRtpAdapter extends BaseQuickAdapter<RenewalRtp.PdListBean, BaseViewHolder> {

    List<EditText> mEditTexts = new ArrayList<>();

    private DeleteListener deleteListener;

    public RenewalRtpAdapter(@Nullable List<RenewalRtp.PdListBean> data, DeleteListener deleteListener) {
        super(R.layout.item_rent_box_product, data);
        this.deleteListener = deleteListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RenewalRtp.PdListBean item) {
        final SwipeMenuLayout menuLayout = helper.getView(R.id.menu_layout);
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_photo));
        helper.setText(R.id.tv_name, item.getProduct_name())
                .setText(R.id.tv_type, item.getProduct_type())
                .setText(R.id.tv_price, item.getRent_price() + "元/个");

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
                if (count > item.getQty()) { // 超过限租数
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
                if (deleteListener != null)
                    deleteListener.onNumberChangeListener(helper.getLayoutPosition());
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
                        mEtName.setText("1");
                    }
                    if (Integer.valueOf(mEtName.getText().toString()) > item.getQty()) { // 超过最大数
                        mEtName.setText(Integer.valueOf(item.getQty()) + "");
                        ToastUitl.showShort("数量超出范围");
                    }
                }
            }
        });
        helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuLayout.smoothClose();
                remove(helper.getLayoutPosition());
                if (deleteListener != null)
                    deleteListener.onDeleteListener(helper.getLayoutPosition());
            }
        });
        mEtName.setText(item.getQty() + "");
        // 耗材不显示限制个数
//        helper.setVisible(R.id.tv_limit_count, item.getFlag_product().equals("1"));
        helper.setVisible(R.id.tv_limit_count, false);
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
    public interface DeleteListener {
        void onDeleteListener(int position);

        /**
         * 输入框数量发生变化
         */
        void onNumberChangeListener(int position);
    }
}
