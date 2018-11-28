package com.horen.service.ui.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.ui.BigImagePagerActivity;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.ImageLoader;
import com.horen.service.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/09:13
 * @description : 图片展示
 * @github :https://github.com/chenyy0708
 */
public class PhotoPickerAdapter extends RecyclerView.Adapter<PhotoPickerAdapter.ViewHolder> {
    /**
     * 图片路径url
     */
    private List<String> mImageUrlList = new ArrayList<>();
    private Context mContext;
    /**
     * 默认最大选择图片的数量
     */
    public static final int MAX_NUMBER = 2;
    /**
     * 选择图片文字
     */
    private String select_text;
    /**
     * 最大选择图片的数量
     */
    public int maxSelect;

    private OnPickerListener mOnPickerListener;
    private onDeleteListener onDeleteListener;

    public PhotoPickerAdapter(Context context) {
        this(context, MAX_NUMBER, "");
    }

    /**
     * 指定最大选择图片个数
     *
     * @param context     上下文
     * @param maxSelect   最多选择图片个数
     * @param select_text 选择图片展示文字
     */
    public PhotoPickerAdapter(Context context, int maxSelect, String select_text) {
        this.mContext = context;
        this.maxSelect = maxSelect;
        this.select_text = select_text;
        // 如果为空，设置默认文字
        if (TextUtils.isEmpty(this.select_text)) {
            this.select_text = context.getString(R.string.service_photo_delivery);
        }
    }

    /**
     * 追加图片
     *
     * @param imageUrlList 图片集合
     */
    public void addData(List<String> imageUrlList) {
        this.mImageUrlList.addAll(imageUrlList);
        this.notifyDataSetChanged();
    }

    /**
     * 添加单张图片
     *
     * @param imaPath 图片地址
     */
    public void addData(String imaPath) {
        this.mImageUrlList.add(imaPath);
        this.notifyDataSetChanged();
    }

    /**
     * 设置新图片集合
     *
     * @param imageUrlList 图片集合
     */
    public void setNewData(List<String> imageUrlList) {
        this.mImageUrlList = imageUrlList == null ? new ArrayList<String>() : imageUrlList;
        this.notifyDataSetChanged();
    }

    public void setOnPickerListener(OnPickerListener onPickerListener) {
        mOnPickerListener = onPickerListener;
    }

    public void setOnDeleteListener(onDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item_photo_picker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 屏幕宽减去左右15dp边距，减去3个分割5dp线宽度 / 4 = 单个图片宽高
        ViewGroup.LayoutParams layoutParams = holder.flContainer.getLayoutParams();
        int width = (DisplayUtil.getScreenWidth(mContext) - (DisplayUtil.dip2px(15) * 2)) / 4;
        layoutParams.width = width;
        layoutParams.height = width;
        holder.flContainer.setLayoutParams(layoutParams);
        // 如果当前图片的数量是0，或者大于所选择图片的数量则显示一个带选择图片的image，同时隐藏删除按钮
        if (mImageUrlList.size() == 0 || mImageUrlList.size() == position) {
            holder.flDelete.setVisibility(View.INVISIBLE);
            holder.llCamera.setVisibility(View.VISIBLE);
            holder.mImageView.setVisibility(View.INVISIBLE);
            // 没有图片集合，显示图片交割
            if (mImageUrlList.size() == 0) {
                holder.tvCurrentPhoto.setText(select_text);
            } else {
                // 有图片数据，显示当时选择 / 最大选择个数
                holder.tvCurrentPhoto.setText(mImageUrlList.size() + " / " + maxSelect);
            }
        } else {
            holder.llCamera.setVisibility(View.INVISIBLE);
            holder.mImageView.setVisibility(View.VISIBLE);
            //否则每个图片直接显示删除按钮，并加载显示图片
            holder.flDelete.setVisibility(View.VISIBLE);
            //glide加载图片
            ImageLoader.loadCenter(mContext, mImageUrlList.get(position), holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mImageUrlList.size() < maxSelect ? mImageUrlList.size() + 1 : mImageUrlList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        LinearLayout llCamera;
        TextView tvCurrentPhoto;
        FrameLayout flContainer, flDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_photo);
            llCamera = itemView.findViewById(R.id.ll_camera);
            tvCurrentPhoto = itemView.findViewById(R.id.tv_current_photo);
            flContainer = itemView.findViewById(R.id.fl_container);
            flDelete = itemView.findViewById(R.id.fl_delete);
            flDelete.setOnClickListener(this);
            mImageView.setOnClickListener(this);
            llCamera.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // 点击相片，预览图片
            if (v.getId() == R.id.iv_photo) {
                BigImagePagerActivity.startImagePagerActivity((Activity) mContext, mImageUrlList, getLayoutPosition());
            } else if (v.getId() == R.id.fl_delete) {
                mImageUrlList.remove(getLayoutPosition());
                notifyDataSetChanged();
                if (onDeleteListener != null) {
                    onDeleteListener.onDeletePhoto();
                }
            } else if (v.getId() == R.id.ll_camera) {
                if (mOnPickerListener != null) {
                    mOnPickerListener.onPicker(getLayoutPosition());
                }
            }
        }
    }

    public List<String> getmImageUrlList() {
        return mImageUrlList;
    }

    /**
     * recyclerView设置的监听接口
     */
    public interface OnPickerListener {
        void onPicker(int position);
    }

    /**
     * 删除监听
     */
    public interface onDeleteListener {
        void onDeletePhoto();
    }
}