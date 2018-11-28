package com.horen.user.ui.adapter;

import android.media.Image;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.user.R;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/10 10:34
 * Description:This isUserLevelTipAdapter
 */
public class UserLevelTipAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String mType;

    public UserLevelTipAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public void getSetType(String type) {
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_level_tip, item);
        int level = helper.getLayoutPosition();
        ImageView iv_level = helper.getView(R.id.iv_level);
        if ("液体".equals(mType)) {
            switch (level) {
                case 0:
                    iv_level.setImageResource(R.mipmap.icon_large_fluid_one);
                    break;
                case 1:
                    iv_level.setImageResource(R.mipmap.icon_large_fluid_two);
                    break;
                case 2:
                    iv_level.setImageResource(R.mipmap.icon_large_fluid_three);
                    break;
                case 3:
                    iv_level.setImageResource(R.mipmap.icon_large_fluid_four);
                    break;
            }
        }
        if ("生鲜".equals(mType)) {
            switch (level) {
                case 0:
                    iv_level.setImageResource(R.mipmap.icon_large_fresh_one);
                    break;
                case 1:
                    iv_level.setImageResource(R.mipmap.icon_large_fresh_two);
                    break;
                case 2:
                    iv_level.setImageResource(R.mipmap.icon_large_fresh_three);
                    break;
                case 3:
                    iv_level.setImageResource(R.mipmap.icon_large_fresh_four);
                    break;
            }
        }
        if ("汽配".equals(mType)) {
            switch (level) {
                case 0:
                    iv_level.setImageResource(R.mipmap.icon_large_automobiellevel_one);
                    break;
                case 1:
                    iv_level.setImageResource(R.mipmap.icon_large_automobiellevel_two);
                    break;
                case 2:
                    iv_level.setImageResource(R.mipmap.icon_large_automobiellevel_three);
                    break;
                case 3:
                    iv_level.setImageResource(R.mipmap.icon_large_automobiellevel_four);
                    break;
            }
        }

    }
}
