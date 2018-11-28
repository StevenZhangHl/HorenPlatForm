package com.horen.base.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.horen.base.R;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * @author :ChenYangYi
 * @date :2018/09/21/12:58
 * @description :通用1dp分割线
 * @github :https://github.com/chenyy0708
 */
public class DividerItemDecoration_1 extends Y_DividerItemDecoration {

    private Context context;

    public DividerItemDecoration_1(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        // 第一个item 不添加线
        if (itemPosition == 0) {
            divider = new Y_DividerBuilder()
                    .setTopSideLine(true, ContextCompat.getColor(context, R.color.transparent), 1, 15, 15)
                    .create();
        } else { // 其他item 添加顶部线
            divider = new Y_DividerBuilder()
                    .setTopSideLine(true, ContextCompat.getColor(context, R.color.color_f1), 1, 15, 15)
                    .create();
        }
        return divider;
    }
}
