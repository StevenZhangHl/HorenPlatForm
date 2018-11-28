package com.horen.partner.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.launcher.ARouter;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.util.DisplayUtil;
import com.horen.base.widget.MessageDialog;
import com.horen.base.widget.ScrollWebView;
import com.horen.partner.R;
import com.horen.partner.event.EventConstans;
import com.horen.base.constant.MsgEvent;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class PartnerWebViewActivity extends BaseActivity {
    private ScrollWebView webView;
    public static String WEBVIEW_URL = "WEBVIEW_URL";
    private String url;

    public static void startAction(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, PartnerWebViewActivity.class);
        intent.putExtra(WEBVIEW_URL, url);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_partner_web_view;
    }

    @Override
    public void initPresenter() {

    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        showWhiteTitle(""); // 白色状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getTitleBar().setElevation(0.0f);
        }
        webView = (ScrollWebView) findViewById(R.id.wv_platform);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });//播放视频
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
        webView.addJavascriptInterface(this, "android");
        url = getIntent().getStringExtra(WEBVIEW_URL);
        if (url == null) {
            return;
        }
        webView.loadUrl(url);
        webView.setOnScrollChangeListener(new ScrollWebView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (t > DisplayUtil.dip2px(50)) {
                    getTitleBar().setTitle(webView.getTitle());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getTitleBar().setElevation(2.0f);
                    }
                } else {
                    getTitleBar().setTitle("");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getTitleBar().setElevation(0.0f);
                    }
                }
            }
        });
    }

    @Override
    protected void reLoadData() {
        super.reLoadData();
        webView.loadUrl(url);
    }

    /**
     * 当webview正在播放MP3或MP4时，关闭webview会继续播放声音
     * 加上此代码会解决这一问题
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        try {
            webView.getClass().getMethod("onPause").invoke(webView, (Object[]) null);
            webView.removeAllViews();
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击物理返回键，若网页能够回退则回退，不能回退则关闭当前webview
     */
    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return;
        }
        new MessageDialog(mContext)
                .showTitle("退出申请")
                .showContent("您确定需要退出申请吗？")
                .setButtonTexts("放弃申请", "继续申请")
                .setOnClickListene(new MessageDialog.OnClickListener() {
                    @Override
                    public void onLeftClick() {
                        PartnerWebViewActivity.super.onBackPressed();
                    }

                    @Override
                    public void onRightClick() {

                    }
                }).show();
    }

    /**
     * 网页调用此方法，关闭当前webview
     */
    @JavascriptInterface
    public void backAction() {
        finish();
    }

    /**
     * js调用此方法，跳转到下单页面
     */
    @JavascriptInterface
    public void order() {
        ARouter.getInstance().build(ARouterPath.CREATE_ORDER_FRAME_ACTIVITY).navigation();
    }

    @JavascriptInterface
    public void gohome() { // 通知关闭所有webview
        EventBus.getDefault().post(new MsgEvent(EventConstans.FINISH_WEBVIEW));
    }

    @Subscriber
    public void onMessageEvent(MsgEvent event) {
        switch (event.getEvent()) {
            case EventConstans.FINISH_WEBVIEW://万箱通知关闭webview
                finish();
                break;
        }
    }

    /**
     * 申请合伙人成功回调
     */
    @JavascriptInterface
    public void friendSuccess() { // 主线程更新UI
        EventBus.getDefault().post(new MsgEvent(EventConstans.FINISH_WEBVIEW));
        //申请成功
//        ARouter.getInstance().build(ARouterPath.PLATFORM_ACTIVITY_SUCCESS).navigation();
    }

    /**
     * 显示
     */
    @JavascriptInterface
    public void showDialog() {
        finish();
    }
}
