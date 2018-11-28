package com.cyy.company.ui.adapter;

import android.animation.Animator;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/15/16:23
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public static List<String> mData = Arrays.asList("", "", "");
    public static List<String> mData2 = Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");

    public TestAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }

    @Override
    protected void startAnim(Animator anim, int index) {
        if (index < 12)
            anim.setStartDelay(index * 50);
        super.startAnim(anim, index);
    }
}
