package com.horen.base.widget;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.horen.base.R;
import com.horen.base.bean.OrderPopupBean;
import com.horen.base.listener.OnTagClickListener;
import com.horen.base.widget.flowlayout.FlowTagLayout;
import com.horen.base.widget.flowlayout.TagAdapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by HOREN on 2017/10/30.
 */

public class SelectDialog extends BasePopupWindow {

    private List<OrderPopupBean> list;
    private onSelectLinstener onSelectLinstener;
    private FlowTagLayout flowTagLayout;
    private int currentPostion;
    private final LinearLayout mLLContent;


    /**
     * @param context
     * @param list
     * @param postion 选中位置
     */
    public SelectDialog(Activity context, List<OrderPopupBean> list, int postion) {
        super(context);
        this.list = list;
        this.currentPostion = postion;
        flowTagLayout = findViewById(R.id.flow_tag);
        mLLContent = findViewById(R.id.ll_content);
        View view = findViewById(R.id.view_dismiss);
        setViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        }, view);
        setAdapter(list);
    }

    private void setAdapter(final List<OrderPopupBean> list) {

        TagAdapter<OrderPopupBean> tagAdapter = new TagAdapter<>(getContext());
        flowTagLayout.setAdapter(tagAdapter);
        tagAdapter.setSelectPosition(currentPostion);
        tagAdapter.onlyAddAll(list);

        flowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                if (onSelectLinstener != null)
                    onSelectLinstener.onItemSelectLinstener(position, list.get(position).name, list.get(position).type);
                dismiss();
            }
        });
    }




    public SelectDialog setSelectLinstener(onSelectLinstener onSelectLinstener) {
        this.onSelectLinstener = onSelectLinstener;
        return this;
    }

//    @Override
//    protected Animation onCreateShowAnimation() {
//        AnimationSet set = new AnimationSet(true);
//        set.setInterpolator(new DecelerateInterpolator());
//        set.addAnimation(getScaleAnimation(1, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
//        set.addAnimation(getDefaultAlphaAnimation());
//        return set;
//    }


    @Override
    public boolean isAllowDismissWhenTouchOutside() {
        return true;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.dialog_select);
    }

    public interface onSelectLinstener {
        void onItemSelectLinstener(int position, String name, String type);
    }

    @Override
    public void showPopupWindow(View v) {
        super.showPopupWindow(v);
    }
}
