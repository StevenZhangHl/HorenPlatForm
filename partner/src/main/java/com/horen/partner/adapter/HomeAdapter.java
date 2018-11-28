package com.horen.partner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.partner.R;
import com.horen.partner.bean.Partner;
import com.horen.partner.bean.Plan;
import com.horen.partner.bean.PlanTypeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/27 16:55
 * Description:This isHomeAdapter
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_NAME_HOT = 0;
    private final int VIEW_TYPE_ONE_IMG = 1;
    private final int VIEW_TYPE_TWO_IMG = 2;
    private final int VIEW_TYPE_THREE_IMG = 3;
    private List<Object> datas = new ArrayList<>();
    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Object> datas) {
        this.datas = datas;
    }

    public void addPartner(List<Partner> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NAME_HOT:
                View viewHot = LayoutInflater.from(context).inflate(R.layout.fragment_platform_item_allhot, parent, false);
                NameHotViewHolder nameHotViewHolder = new NameHotViewHolder(viewHot);
                return nameHotViewHolder;
            case VIEW_TYPE_ONE_IMG:
                View viewItemHot = LayoutInflater.from(context).inflate(R.layout.fragment_platform_item_one_img, parent, false);
                return new OneImgHolder(viewItemHot);
            case VIEW_TYPE_TWO_IMG: // 两张图片
                View towViewHot = LayoutInflater.from(context).inflate(R.layout.fragment_platform_item_two_img, parent, false);
                return new TwoImgHolder(towViewHot);
            case VIEW_TYPE_THREE_IMG: // 三张图片
                View threeViewHot = LayoutInflater.from(context).inflate(R.layout.fragment_platform_item_three_img, parent, false);
                return new ThreeImgHolder(threeViewHot);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NameHotViewHolder) {
            final PlanTypeList typeList = (PlanTypeList) datas.get(position);
            NameHotViewHolder holderItem = (NameHotViewHolder) holder;
            holderItem.textView.setText(typeList.solution_typename);
        } else if (holder instanceof OneImgHolder) {
            final Plan plan = (Plan) datas.get(position);
            OneImgHolder holderItem = (OneImgHolder) holder;
            holderItem.setData(context, plan);
        } else if (holder instanceof TwoImgBaseHolder) {
            final Plan plan = (Plan) datas.get(position);
            TwoImgBaseHolder holderItem = (TwoImgBaseHolder) holder;
            holderItem.setData(context, plan);
        } else if (holder instanceof ThreeImgHolder) {
            final Plan plan = (Plan) datas.get(position);
            ThreeImgHolder holderItem = (ThreeImgHolder) holder;
            holderItem.setData(context, plan);
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object data = datas.get(position);
        if (data instanceof PlanTypeList) { // 标题
            return VIEW_TYPE_NAME_HOT;
        } else {
            if (data instanceof Plan && ((Plan) data).getSolutions().size() == 1) { // 一张图片
                return VIEW_TYPE_ONE_IMG;
            } else if (data instanceof Plan && ((Plan) data).getSolutions().size() == 2) { // 两张图片
                return VIEW_TYPE_TWO_IMG;
            } else if (data instanceof Plan && ((Plan) data).getSolutions().size() >= 3) { // 三张图片
                return VIEW_TYPE_THREE_IMG;
            }
        }
        return -1;
    }

    /**
     * 方案标题Holder
     */
    class NameHotViewHolder extends RecyclerView.ViewHolder {
        LinearLayout frameLayout;
        TextView textView;

        private NameHotViewHolder(View itemView) {
            super(itemView);
            frameLayout = (LinearLayout) itemView.findViewById(R.id.ll_to_allhot);
            textView = itemView.findViewById(R.id.tv_platform_item_hot);
        }
    }

    /**
     * 单张图片方案holder
     */
    class OneImgHolder extends OneImgBaseHolder {
        public OneImgHolder(View itemView) {
            super(itemView);
            ivBgSingle = itemView.findViewById(R.id.iv_platform_item_bg_single);
        }
    }

    /**
     * 两张图片方案holder
     */
    class TwoImgHolder extends TwoImgBaseHolder {
        public TwoImgHolder(View itemView) {
            super(itemView);
            ivDoubleOne = itemView.findViewById(R.id.iv_platform_item_doubue_1);
            ivDoubleTwo = itemView.findViewById(R.id.iv_platform_item_doubue_2);
        }
    }

    /**
     * 三张图片轮播图方案holder
     */
    class ThreeImgHolder extends ThreeBaseHolder {
        public ThreeImgHolder(View itemView) {
            super(itemView);
            hic_view_pager = itemView.findViewById(R.id.hic_view_pager);
        }
    }
}
