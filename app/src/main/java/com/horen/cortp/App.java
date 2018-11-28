package com.horen.cortp;

import com.horen.base.app.BaseApp;

import cn.jpush.android.api.JPushInterface;

/**
 * @author :ChenYangYi
 * @date :2018/10/09/12:00
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化 JPush
        JPushInterface.init(this);
    }
}
