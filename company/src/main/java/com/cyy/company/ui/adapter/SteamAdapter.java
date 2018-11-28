package com.cyy.company.ui.adapter;

import android.animation.Animator;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.DownStreamBean;
import com.horen.base.app.HRConstant;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.NumberUtil;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/17:38
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class SteamAdapter extends BaseQuickAdapter<DownStreamBean.PdListBean, BaseViewHolder> {

    /**
     * 宽度对应数量的比例，用于计算宽度
     */
    private float widthProportion;

    private int type;

    public SteamAdapter(@Nullable List<DownStreamBean.PdListBean> data, int type) {
        super(R.layout.item_eye_down_steam, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, DownStreamBean.PdListBean item) {
        View viewPerCent = helper.getView(R.id.view_percent);
        helper.setText(R.id.tv_serial, "NO." + (helper.getLayoutPosition() + 1))
                .setText(R.id.tv_company_name, item.getOrg_name())
                .setText(R.id.tv_number, type == HRConstant.BOX_USED ? String.valueOf((int) item.getNumber()) :
                        NumberUtil.formitNewNumberTwoPoint(item.getNumber()) + "%");
        // 设置View的宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPerCent.getLayoutParams();
        // 如果数字过于小，最少显示1dp宽度柱状体,0还是不显示
        if (widthProportion * item.getNumber() < DisplayUtil.dip2px(1) && widthProportion * item.getNumber() != 0) {
            lp.width = DisplayUtil.dip2px(2);
        } else {
            lp.width = (int) (widthProportion * item.getNumber());
        }
        viewPerCent.setLayoutParams(lp);
        if (type == HRConstant.BOX_USED) {
            viewPerCent.setBackgroundResource(R.drawable.shape_eye_green);
        } else if (type == HRConstant.BREAKAGE_RATE) {
            viewPerCent.setBackgroundResource(R.drawable.shape_eye_orange);
        }
        // 执行过动画直接显示
        if (item.isAnimation()) {
            helper.getView(R.id.ll_content).setAlpha(1f);
        }
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        if (index < 11) {
            anim.setStartDelay(index * 50);
        }
        mData.get(index).setAnimation(true);
        super.startAnim(anim, index);
    }

    /**
     * 根据最大值，计算出比例
     */
    public void setMaxNumber(int screenWidth, List<DownStreamBean.PdListBean> mData) {
        // 损坏率需要计算百分比
        if (type == HRConstant.BREAKAGE_RATE) {
            for (DownStreamBean.PdListBean mDatum : mData) { // 百分比需要 * 100 得出 百分比占比
                mDatum.setNumber(mDatum.getAvg_qty() * 100f);
            }
        } else {
            for (DownStreamBean.PdListBean mDatum : mData) {
                mDatum.setNumber(mDatum.getQty());
            }
        }
        float maxNumber = 0;
        // 计算出最大值
        for (DownStreamBean.PdListBean mDatum : mData) {
            if (mDatum.getNumber() > maxNumber) maxNumber = mDatum.getNumber();
        }
        if (maxNumber != 0) {
            // 比例 = 屏幕宽度 - 左右边距 - 左右文字View宽度 / 最大值
            widthProportion = (screenWidth
                    - DisplayUtil.dip2px(25 + 20)
                    - DisplayUtil.dip2px(50 + 60)) * 1.0f / maxNumber * 1.0f;
        } else {
            widthProportion = 0;
        }
        setNewData(mData);
    }
}
