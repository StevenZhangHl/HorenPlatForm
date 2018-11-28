package com.horen.user.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.user.R;
import com.horen.user.bean.PlatFormSupportBean;

import java.util.List;

/**
 * Created by David-Notebook on 2017/7/10.
 */

public class PlatformSupportAdapter extends BaseQuickAdapter<PlatFormSupportBean, BaseViewHolder> {

    public PlatformSupportAdapter(int layoutResId, List<PlatFormSupportBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PlatFormSupportBean item) {
        //昵称
        helper.setText(R.id.tv_item_user, item.getFlag() + "：" + item.getName());
        //mobile
        String mobile = TextUtils.isEmpty(item.getTel()) ? "" : item.getTel();
        helper.setText(R.id.tv_item_phone, mobile);
        //mail
        String mail = TextUtils.isEmpty(item.getMail()) ? "" : item.getMail();
        helper.setText(R.id.tv_item_email, mail);
        helper.addOnClickListener(R.id.tv_item_phone);
    }
}
