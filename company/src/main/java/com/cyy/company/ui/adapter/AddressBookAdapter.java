package com.cyy.company.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.AddressBook;
import com.cyy.company.ui.activity.me.AddressEditActivity;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.SpanUtils;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/15/16:23
 * @description :地址簿
 * @github :https://github.com/chenyy0708
 */
public class AddressBookAdapter extends BaseQuickAdapter<AddressBook.PdListBean.ListBean, BaseViewHolder> {

    public AddressBookAdapter(@Nullable List<AddressBook.PdListBean.ListBean> data) {
        super(R.layout.item_address_book, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AddressBook.PdListBean.ListBean item) {
        TextView tvAddress = helper.getView(R.id.tv_address);
        SuperButton sbtName = helper.getView(R.id.sbt_name);
        // 姓名
        sbtName.setText(item.getOrg_consignee().substring(0, 1));
        helper.setText(R.id.tv_name, item.getOrg_consignee());
        // 联系电话
        helper.setText(R.id.tv_phone, FormatUtil.phoneSetMiddleEmpty(item.getOrg_consigneetel()));
        // 默认 网点地址：(0:非默认地址, 1:默认地址)
        if (item.getFlag_defaultorg().equals("1")) {
            tvAddress.setText(new SpanUtils()
                    .append(" 默认 ")
                    .setFontSize(12, true)
                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.base_text_color_light))
                    .setBackgroundColor(Color.parseColor("#336fba2c"))
                    .append("  ")
                    .setBackgroundColor(ContextCompat.getColor(mContext, R.color.white))
                    .append("" + item.getOrg_name() + "")
                    .setFontSize(12, true)
                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.base_text_color_light))
                    .setBackgroundColor(Color.parseColor("#336fba2c"))
                    .append("  " + item.getOrg_address())
                    .setFontSize(13, true)
                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333)).create());
            sbtName.setShapeGradientStartColor(ContextCompat.getColor(mContext, R.color.color_main))
                    .setShapeGradientEndColor(ContextCompat.getColor(mContext, R.color.base_text_color_light))
                    .setUseShape();
        } else {
            tvAddress.setText(new SpanUtils()
                    .append("" + item.getOrg_name() + "")
                    .setFontSize(12, true)
                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.base_text_color_light))
                    .setBackgroundColor(Color.parseColor("#336fba2c"))
                    .append("  " + item.getOrg_address())
                    .setFontSize(13, true)
                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.color_333)).create());
            sbtName.setShapeGradientStartColor(ContextCompat.getColor(mContext, R.color.DCDCDC))
                    .setShapeGradientEndColor(ContextCompat.getColor(mContext, R.color.color_999))
                    .setUseShape();
        }
        helper.addOnClickListener(R.id.tv_edit);
    }
}
