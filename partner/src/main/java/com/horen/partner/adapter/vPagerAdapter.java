package com.horen.partner.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.horen.base.util.DisplayUtil;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.PlatformUtil;
import com.horen.partner.R;
import com.horen.partner.bean.PlanBean;
import com.horen.partner.ui.activity.PlatformWebViewActivity;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/27 17:22
 * Description:This isvPagerAdapter
 */
public class vPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<PlanBean> mDatas;
    private LayoutInflater mLayoutInflater;

    public vPagerAdapter(Context mContext, List<PlanBean> mDatas) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        if (mDatas.size() == 3) { // 当数据只有三个时，多次滑动轮播图会出现item变少的情况，复制一份数据，个数超过三
            this.mDatas.addAll(mDatas);
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(final View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.item_plat_three_img_iv, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_platform_vp);
        // 设置ImageView的宽高，动态计算高度
        int ivHeight = DisplayUtil.dip2px(100);  // ImageView高
        CardView.LayoutParams layoutParams = (CardView.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = (int) (ivHeight * 1.68f); // 根据设计图上的比例，宽高比为1.68 : 1 求出ImageView的高，屏幕适配
        layoutParams.height = ivHeight;
        imageView.setLayoutParams(layoutParams); // 设置给Imageview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //  5.0以上不需要加载圆角图片，防止出现cardView和imageview有间隙的情况
            ImageLoader.load(mContext, mDatas.get(position).getSolution_bglogo(), imageView);
        } else { // 5.0以下图片加载圆角Glide
            ImageLoader.loadRoundIV(mContext, mDatas.get(position).getSolution_bglogo(), imageView, 6);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                if ("2".equals(mDatas.get(position).getH5_mode())) {
                    url = mDatas.get(position).getSolution_pc_url();//海外版URL不需要拼接
                } else {
                    url = PlatformUtil.getPlanUrlH5(mDatas.get(position).getSolution_h5_url());//国内版URL需要拼接
                }
                Intent intent = new Intent(mContext, PlatformWebViewActivity.class);
                intent.putExtra(PlatformWebViewActivity.WEBVIEW_URL, url);
                mContext.startActivity(intent);
            }
        });
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        //添加数据源元素
        container.addView(view);
        //返回数据源中position下标的元素
        return view;
    }

    /**
     * 当ViewPager滚动出某页时，需要销毁position所对应的Item信息
     * 在此方法中需要将数据源中的元素从container中移除
     */
    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }
}
