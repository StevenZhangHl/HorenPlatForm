package com.horen.base.listener;


import com.horen.base.widget.flowlayout.FlowTagLayout;

import java.util.List;

/**
 * Created by HanHailong on 15/10/20.
 */
public interface OnTagSelectListener {
    void onItemSelect(FlowTagLayout parent, List<Integer> selectedList);
}
