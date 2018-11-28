package com.horen.partner.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.horen.base.base.BaseActivity;
import com.horen.base.constant.MsgEvent;
import com.horen.base.util.DisplayUtil;
import com.horen.base.widget.ScrollWebView;
import com.horen.partner.R;
import com.horen.partner.event.EventConstans;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class PlatformWebViewActivity extends BaseActivity {
    public static String WEBVIEW_URL = "WEBVIEW_URL";
    private ScrollWebView webView;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_platform_web_view;
    }

    @Override
    public void initPresenter() {

    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        showWhiteTitle(""); // 白色状态栏
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
                getTitleBar().setTitle(title);
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
}
