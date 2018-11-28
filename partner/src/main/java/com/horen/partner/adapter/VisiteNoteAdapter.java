package com.horen.partner.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.ui.BigImagePagerActivity;
import com.horen.partner.R;
import com.horen.partner.bean.VisiteNoteBaseBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/14 14:40
 * Description:This isVisiteNoteAdapter
 */
public class VisiteNoteAdapter extends BaseQuickAdapter<VisiteNoteBaseBean, BaseViewHolder> {
    public VisiteNoteAdapter(int layoutResId, @Nullable List<VisiteNoteBaseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VisiteNoteBaseBean item) {
        helper.setText(R.id.tv_visite_time, item.getVisit_date());
        helper.setText(R.id.tv_visite_people, item.getVisit_name());
        helper.setText(R.id.tv_phone, item.getVisit_tel());
        helper.setText(R.id.tv_needs_info, item.getVisit_content());
        RecyclerView recyclerView = helper.getView(R.id.recyclerview_visite_images);
        VisiteNotePhotoAdapter visiteNotePhotoAdapter = new VisiteNotePhotoAdapter(R.layout.partner_item_visitenote_photo, item.getPhotosUrl());
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(visiteNotePhotoAdapter);
        helper.addOnClickListener(R.id.iv_edit_visite);
    }
}
