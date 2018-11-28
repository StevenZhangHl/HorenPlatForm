package com.horen.service.ui.activity.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.ImageLoader;
import com.horen.service.R;
import com.horen.service.bean.OrderAllotInfoBean;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/10:30
 * @description :入库订单物品
 * @github :https://github.com/chenyy0708
 */
public class StorageGoodsAdapter extends BaseQuickAdapter<OrderAllotInfoBean.ProListBean, BaseViewHolder> {
    public StorageGoodsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderAllotInfoBean.ProListBean item) {
        // 实际回收量默认 = 回收量
        final EditText etActualRecovery = helper.getView(R.id.et_actual_recovery);
        // 损坏量
//        final EditText etAmountDamage = helper.getView(R.id.et_amount_damage);
        if (helper.getLayoutPosition() == mData.size() - 1) {
            helper.setGone(R.id.divider, false);
        }
        // 产品图片
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_pic));
        helper.setText(R.id.tv_goods_type, item.getProduct_name() + "   " + item.getProduct_type());
        // 计划量
        helper.setText(R.id.tv_recycling_plan, String.format(mContext.getString(R.string.service_recycling_plan), String.valueOf(item.getOrder_qty())));
        // 已执行
        helper.setText(R.id.tv_executed, String.format(mContext.getString(R.string.service_executed), String.valueOf(item.getPerform_qty())));
        // 未执行 = 需求量 - 已执行
        helper.setText(R.id.tv_not_performed, String.format(mContext.getString(R.string.service_not_performed), String.valueOf(item.getNot_perform_qty())));
        // 实际回收量输入框监听
        etActualRecovery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //不能输入0开始的两位数
                if (s.toString().length() > 1) {
                    if (s.toString().startsWith("0")) {
                        etActualRecovery.setText("0");
                    }
                }
                // 输入空
                if (TextUtils.isEmpty(s.toString())) {
                    item.setActual_recovery_qty(0);
                } else {
                    // 保存实际回收量
                    item.setActual_recovery_qty(Integer.valueOf(s.toString()));
                }
                // 光标设置在最后
                etActualRecovery.setSelection(etActualRecovery.getText().toString().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // 损坏量输入框监听
//        etAmountDamage.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //不能输入0开始的两位数
//                if (s.toString().length() > 1) {
//                    if (s.toString().startsWith("0")) {
//                        etAmountDamage.setText("0");
//                    }
//                }
//                // 输入空
//                if (TextUtils.isEmpty(s.toString())) {
//                    item.setRepair_qty(0);
//                    return;
//                }
//                // 损坏量不能大于实际回收量
//                if (Integer.valueOf(s.toString()) > item.getActual_recovery_qty()) {
//                    etAmountDamage.setText(String.valueOf(item.getActual_recovery_qty()));
//                    item.setRepair_qty(item.getActual_recovery_qty());
//                } else {
//                    // 保存损坏量
//                    item.setRepair_qty(Integer.valueOf(s.toString()));
//                }
//                // 光标设置在最后
//                etAmountDamage.setSelection(etAmountDamage.getText().toString().length());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
        // 实际回收量默认 = 回收量
        etActualRecovery.setText(String.valueOf(item.getOrder_qty()));
        // 损坏量默认为空
//        etAmountDamage.setText("");
    }
}