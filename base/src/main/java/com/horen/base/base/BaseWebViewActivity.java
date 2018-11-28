package com.horen.base.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.horen.base.R;
import com.horen.base.widget.HRTitle;

public class BaseWebViewActivity extends BaseActivity {
    public static String WEBVIEW_URL = "WEBVIEW_URL";
    private HRTitle mToolBar;
    private WebView webView;
    private String url;

    public static void startAction(Context context, String url) {
        Intent intent = new Intent();
        intent.putExtra(BaseWebViewActivity.WEBVIEW_URL, url);
        intent.setClass(context, BaseWebViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web_view;
    }

    @Override
    public void initPresenter() {

    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mToolBar.bindActivity(this, R.color.white);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mToolBar.setTitle(title);
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
    }

}
