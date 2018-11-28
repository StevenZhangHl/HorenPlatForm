package com.horen.cortp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.horen.base.app.HRConstant;
import com.horen.base.base.AppManager;
import com.horen.base.bean.LoginBean;
import com.horen.base.constant.ARouterPath;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.rx.RxManager;
import com.horen.base.util.ToastUitl;
import com.horen.base.util.UserHelper;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/15:31
 * @description :启动页
 * @github :https://github.com/chenyy0708
 */
public class LaunchActivity extends Activity {
    /**
     * 启动页时间-暂定1s
     */
    public int DURATION = 1;
    private RxManager mRxManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 把actvity放到application栈中管理
        mRxManager = new RxManager();
        AppManager.getAppManager().addActivity(this);
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        jumpActivity();
    }

    private void jumpActivity() {
        if (UserHelper.isLogin()) {
            // 刷新token，和登陆信息
            mRxManager.add(ApiUser.getInstance().getMobileLoginInfoByToken(UserApiPram.getDefaultPram())
                    .compose(RxHelper.<LoginBean>getResult())
                    .subscribeWith(new BaseObserver<LoginBean>() {
                        @Override
                        protected void onSuccess(LoginBean loginBean) {
                            UserHelper.saveUserInfo(loginBean);
                        }

                        @Override
                        protected void onError(String message) {
                            ToastUitl.showShort(message);
//                        UserHelper.logOut();
                        }
                    }));
        }
        // 延时一秒进入app
        mRxManager.add(Observable.timer(DURATION, TimeUnit.SECONDS)
                .compose(RxHelper.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        // 登陆页面
                        if (UserHelper.isLogin()) {
                            // 会员分类（2-客户 3-合伙人4-服务商5-合伙人+服务商）
                            boolean booleanExtra = getIntent().getBooleanExtra(HRConstant.IS_OPEN_PUSH, false);
                            switch (UserHelper.getUserInfo().getLoginInfo().getCompany_class()) {
                                case "2":
                                    ARouter.getInstance().build(ARouterPath.COMPANY_MAIN_ACTIVITY)
                                            .withBoolean(HRConstant.IS_OPEN_PUSH, booleanExtra) // 是否是点击推送消息打开页面
                                            .withString(HRConstant.PUSH_EXTRA, getIntent().getStringExtra(HRConstant.PUSH_EXTRA)) // 推送消息
                                            .navigation();
                                    break;
                                case "7":
                                    ARouter.getInstance().build(ARouterPath.COMPANY_MAIN_ACTIVITY)
                                            .withBoolean(HRConstant.IS_OPEN_PUSH, booleanExtra) // 是否是点击推送消息打开页面
                                            .withString(HRConstant.PUSH_EXTRA, getIntent().getStringExtra(HRConstant.PUSH_EXTRA)) // 推送消息
                                            .navigation();
                                    break;
                                case "3":
                                    ARouter.getInstance().build(ARouterPath.PARNNER_MAIN_ACTIVITY).navigation();
                                    break;
                                case "4":
                                    ARouter.getInstance().build(ARouterPath.SERVICE_MAIN_ACTIVITY).navigation();
                                    break;
                                case "5":
                                    ARouter.getInstance().build(ARouterPath.USER_PLATFORM).navigation();
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            // 没有登录，跳转未登录首页
                            ARouter.getInstance().build(ARouterPath.UN_LOGIN_MAIN_ACTIVITY).navigation();
                        }
                        finish();
                    }
                }));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mRxManager.clear();
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }
}
