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
 * @description :出库订单物品
 * @github :https://github.com/chenyy0708
 */
public class OutBoundGoodsAdapter extends BaseQuickAdapter<OrderAllotInfoBean.ProListBean, BaseViewHolder> {
    public OutBoundGoodsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderAllotInfoBean.ProListBean item) {
        final EditText etOutBound = helper.getView(R.id.et_outBound);
        if (helper.getLayoutPosition() == mData.size() - 1) {
            helper.setGone(R.id.divider, false);
        }
        // 产品图片
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_pic));
        helper.setText(R.id.tv_goods_type, item.getProduct_name() + "   " + item.getProduct_type());
        // 需求量
        helper.setText(R.id.tv_demand, String.format(mContext.getString(R.string.service_demand), String.valueOf(item.getOrder_qty())));
        // 已执行
        helper.setText(R.id.tv_executed, String.format(mContext.getString(R.string.service_executed), String.valueOf(item.getPerform_qty())));
        // 未执行 = 需求量 - 已执行
        helper.setText(R.id.tv_not_performed, String.format(mContext.getString(R.string.service_not_performed), String.valueOf(item.getNot_perform_qty())));
        // 总库存
        helper.setText(R.id.tv_total_inventory, String.format(mContext.getString(R.string.service_total_inventory), String.valueOf(item.getTotal_qty())));
        // 可用
        helper.setText(R.id.tv_available, String.format(mContext.getString(R.string.service_available), String.valueOf(item.getAvailable_qty())));
        if (item.getNot_perform_qty() <= item.getAvailable_qty()) {
            // 当未执行数量小于库存可用数量时，配送数量默认读取未执行量
            etOutBound.setText(String.valueOf(item.getNot_perform_qty()));
            item.setDistribution_qty(item.getNot_perform_qty());
        } else if (item.getNot_perform_qty() >= item.getAvailable_qty()) {
            //当未执行量大于库存可用数量时，配送数量默认读取库存可用数量，并红色显示
            etOutBound.setText(String.valueOf(item.getAvailable_qty()));
            item.setDistribution_qty(item.getAvailable_qty());
        }
        // 输入框监听
        etOutBound.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //不能输入0开始的两位数
                if (s.toString().length() > 1) {
                    if (s.toString().startsWith("0")) {
                        etOutBound.setText("0");
                    }
                }
                // 输入空
                if (TextUtils.isEmpty(s.toString())) {
                    item.setDistribution_qty(0);
                    return;
                }
                // 1.配送数量可以编辑，但是不能大于库存可用数量。
                if (Integer.valueOf(s.toString()) > item.getAvailable_qty()) {
                    etOutBound.setText(String.valueOf(item.getAvailable_qty()));
                    item.setDistribution_qty(item.getAvailable_qty());
                } else {
                    // 保存输入的配送数量
                    item.setDistribution_qty(Integer.valueOf(s.toString()));
                }
                // 2.配送数量要小于未执行数
                if (Integer.valueOf(s.toString()) > item.getNot_perform_qty()) {
                    etOutBound.setText(String.valueOf(item.getNot_perform_qty()));
                    item.setDistribution_qty(item.getNot_perform_qty());
                } else {
                    // 保存输入的配送数量
                    item.setDistribution_qty(Integer.valueOf(s.toString()));
                }

                // 光标设置在最后
                etOutBound.setSelection(etOutBound.getText().toString().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
