package com.horen.partner.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.DisplayUtil;
import com.horen.partner.R;
import com.horen.partner.bean.PlanTypeList;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:Steven
 * Time:2018/8/27 15:46
 * Description:This isHomeTabAdapter
 */
public class HomeTabAdapter extends BaseQuickAdapter<PlanTypeList, BaseViewHolder> {
    /**
     * 是否横向排布
     */
    private boolean isLinaerLayout = false;
    /**
     * 上一个点击的Postion
     */
    private int previousPostion = -1;

    private List<View> views;

    public HomeTabAdapter(int layoutResId, @Nullable List<PlanTypeList> data) {
        super(layoutResId, data);
        views = new ArrayList<>();
    }

    @Override
    protected void convert(final BaseViewHolder helper, PlanTypeList item) {
        views.add(helper.getView(R.id.ll_container));
        // 默认放大第一个view
        if (helper.getLayoutPosition() == 0) {
            AnimationUtils.scaleBigView(views.get(0), 1);
            previousPostion = 0; // 记录上一个变大的View
        }
        FrameLayout frameLayout = helper.getView(R.id.fl_tab_container);
        if (isLinaerLayout) { // 动态计算item的宽度
            int screenWidth = DisplayUtil.getScreenWidth(mContext); // 屏幕宽度
            int itemWidth = (screenWidth - DisplayUtil.dip2px(15) * 2) / 3;
            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) frameLayout.getLayoutParams();
            lp.width = itemWidth;
            frameLayout.setLayoutParams(lp);
        }
        //图片
        Glide.with(mContext)
                .load(item.banner_image)
                .apply(new RequestOptions().dontAnimate())
                .apply(new RequestOptions().placeholder(R.drawable.icon_normal_user))
                .apply(new RequestOptions().error(R.drawable.icon_normal_user))
                .into((CircleImageView) helper.getView(R.id.iv_home_tab));
        helper.setText(R.id.tv_home_tab, item.solution_typename);
        helper.setOnClickListener(R.id.ll_container, new View.OnClickListener() { // 图片被点击
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onItemClickListener(helper.getLayoutPosition());
                if (previousPostion == helper.getLayoutPosition()) return; // 点击当前位置，不需要动画
                View currentView = views.get(helper.getLayoutPosition()); // 当前点击的View
                View previousView = null; // 上一个View
                if (previousPostion != -1) {
                    previousView = views.get(previousPostion);
                    AnimationUtils.scaleSmallView(previousView, 300);
                }
                AnimationUtils.scaleBigView(currentView, 300);
                previousPostion = helper.getLayoutPosition();
            }
        });
    }

    public void changePostion(int position) {
        if (previousPostion == position) return; // 点击当前位置，不需要动画
        View currentView = views.get(position);
        View previousView = null;
        if (previousPostion != -1) {
            previousView = views.get(previousPostion);
            AnimationUtils.scaleSmallView(previousView, 300);
        }
        // 记录上一个点击的position
        AnimationUtils.scaleBigView(currentView, 300);
        previousPostion = position;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setLinearLayout() {
        isLinaerLayout = true;
    }

    public interface ItemClickListener {
        void onItemClickListener(int position);
    }
}
