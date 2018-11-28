package com.horen.service.ui.fragment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.NumberUtil;
import com.horen.service.R;
import com.horen.service.bean.BillMainBean;
import com.horen.service.utils.StringUtils;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/03/13:24
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillMainCompleteAdapter extends BaseQuickAdapter<BillMainBean.BillListBean, BaseViewHolder> {
    public BillMainCompleteAdapter(@Nullable List<BillMainBean.BillListBean> data) {
        super(R.layout.service_item_bill_main_complete, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillMainBean.BillListBean item) {
        // 隐藏最后一个条目的分割线
        if (helper.getLayoutPosition() == mData.size() - 1) {
            helper.setVisible(R.id.divider, false);
        } else {
            helper.setVisible(R.id.divider, true);
        }
        // 单号
//        helper.setText(R.id.tv_order_number,"");
        // 账单类型
        helper.setText(R.id.tv_status, StringUtils.checkBillType(item.getCost_type()));
        // 总费用
        helper.setText(R.id.tv_money,  NumberUtil.formitNumberTwoPoint(Double.valueOf(item.getTotal_ar())));
        // 时间
        helper.setText(R.id.tv_time, String.valueOf(item.getRelative_month()));
    }
}
