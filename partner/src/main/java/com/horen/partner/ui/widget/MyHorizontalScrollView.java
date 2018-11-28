package com.horen.partner.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Author:Steven
 * Time:2018/9/5 14:13
 * Description:This isMyHorizontalScrollView
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
    //另一个HorizontalScrollView
    MyHorizontalScrollView otherHsv;

    public MyHorizontalScrollView(Context context) {
        this(context, null);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //滑动时调用
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //不为空，实例不是自己是调用
        if (otherHsv != null && otherHsv != this) {
            otherHsv.scrollTo(l, t);
        }

    }

    public void setOtherHsv(MyHorizontalScrollView otherHsv) {
        this.otherHsv = otherHsv;
    }
}
