package com.horen.partner.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.horen.base.util.ImageLoader;
import com.horen.partner.bean.HomeBanner;
import com.horen.partner.ui.activity.PartnerActivity;
import com.horen.partner.ui.activity.PlatformWebViewActivity;

/**
 * Author:Steven
 * Time:2018/8/27 17:32
 * Description:This isHomeBannerAdapter
 */
public class HomeBannerAdapter implements Holder<HomeBanner> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
    @Override
    public void UpdateUI(final Context context, final int position, final HomeBanner data) {
        ImageLoader.loadBanner(context,data.banner_image,imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(data.banner_url)){ // 链接不为空
                    if(data.banner_url.contains("friend")) { // 合伙人页面
                        PartnerActivity.startAction(context);
                    }else { // 非合伙人
                        Intent intent = new Intent(context, PlatformWebViewActivity.class);
                        intent.putExtra(PlatformWebViewActivity.WEBVIEW_URL, data.banner_url);
                        context.startActivity(intent);
                    }
                }
            }
        });
    }
}
