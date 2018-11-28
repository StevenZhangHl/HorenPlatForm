package com.horen.base.listener;

import android.view.View;

import com.horen.base.widget.flowlayout.FlowTagLayout;


/**
 * Created by HanHailong on 15/10/20.
 */
public interface OnTagClickListener {
    void onItemClick(FlowTagLayout parent, View view, int position);
}
