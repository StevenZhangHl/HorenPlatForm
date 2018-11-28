package com.cyy.company.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.OrgPageBean;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.DisplayUtil;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/30/13:28
 * @description :资产列表
 * @github :https://github.com/chenyy0708
 */
public class AssetsListAdapter extends BaseQuickAdapter<OrgPageBean.PdListBean.ListBeanX, BaseViewHolder> {
    public AssetsListAdapter(@Nullable List<OrgPageBean.PdListBean.ListBeanX> data) {
        super(R.layout.item_eye_assets_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final OrgPageBean.PdListBean.ListBeanX item) {
        if (item.getFlag_road().equals("2")) { // 疑似网点
            helper.setText(R.id.tv_org_name, "疑似网点");
            helper.setBackgroundColor(R.id.view, ContextCompat.getColor(mContext, R.color.color_F15));
        } else {
            helper.setText(R.id.tv_org_name, "网点: " + item.getOrg_name());
            helper.setBackgroundColor(R.id.view, ContextCompat.getColor(mContext, R.color.color_main));
        }
        // 地址
        helper.setText(R.id.tv_address, item.getOrg_name());
        final RecyclerView mRecyclerView = helper.getView(R.id.recycler_view);
        final TextView mTvExpendle = helper.getView(R.id.tv_expendle);
        mTvExpendle.setText(item.isExpendle() ? "缩回" : "展开");
        setDrawableRightIcon(mContext, mTvExpendle, item.isExpendle() ? R.drawable.icon_eye_up_arrow_gray : R.drawable.icon_eye_down_arrow_gray);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        final AnalysisListChildAdapter orgAdapter = new AnalysisListChildAdapter(item.getList());
        mRecyclerView.setAdapter(orgAdapter);
        if (item.isExpendle()) { // 当前状态展开
            setRecyclerViewHeight(mRecyclerView, LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            setRecyclerViewHeight(mRecyclerView, DisplayUtil.dip2px(90));
        }
//        if (item.getList().size() < 2) {
//            mTvExpendle.setVisibility(View.GONE);
//        } else {
//            mTvExpendle.setVisibility(View.VISIBLE);
//        }
        // 点击展开
        helper.setOnClickListener(R.id.tv_expendle, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isExpendle()) { // 展开状态，收起列表
                    setDrawableRightIcon(mContext, mTvExpendle, R.drawable.icon_eye_down_arrow_gray);
                    mTvExpendle.setText("展开");
                    AnimationUtils.scaleRecyclerView(mRecyclerView, 300, DisplayUtil.dip2px(orgAdapter.getData().size() * 90),
                            DisplayUtil.dip2px(90));
                    setRecyclerViewHeight(mRecyclerView, DisplayUtil.dip2px(90));
                    item.setExpendle(false);
                } else { // 收起状态，展开列表
                    setDrawableRightIcon(mContext, mTvExpendle, R.drawable.icon_eye_up_arrow_gray);
                    AnimationUtils.scaleRecyclerView(mRecyclerView, 300, DisplayUtil.dip2px(90),
                            DisplayUtil.dip2px(orgAdapter.getData().size() * 90));
                    mTvExpendle.setText("缩回");
                    item.setExpendle(true);
                }
            }
        });
    }

    public void setDrawableRightIcon(Context mContext, TextView mTvExpendle, @DrawableRes int icon) {
        Drawable drawable = mContext.getResources().getDrawable(icon);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvExpendle.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置RecycleView高度
     */
    private void setRecyclerViewHeight(RecyclerView mRecyclerView, int height) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        lp.height = height;
        mRecyclerView.setLayoutParams(lp);
    }
}
