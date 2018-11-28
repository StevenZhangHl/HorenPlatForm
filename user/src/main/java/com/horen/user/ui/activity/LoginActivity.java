package com.horen.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.horen.base.app.HRConstant;
import com.horen.base.base.AppManager;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.LoginBean;
import com.horen.base.constant.ARouterPath;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.EditTextHelper;
import com.horen.base.util.SPUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;
import com.horen.base.widget.PWEditText;
import com.horen.base.widget.RippleButton;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.ui.activity.accout.ResetpsdActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/15:31
 * @description :登陆页
 * @github :https://github.com/chenyy0708
 */
@Route(path = ARouterPath.USER_LOGIN)
public class LoginActivity extends BaseActivity implements View.OnClickListener, EditTextHelper.EditTextInputListener {
    private String password;

    private LinearLayout llLoginView;
    private LinearLayout llContent;
    private TextView tvPhone;
    private PWEditText etAccount;
    private PWEditText etPassword;
    private RippleButton rbtLogin;
    private TextView tv_code_login;
    private TextView tv_forget_password;
    private String phone;
    private HRTitle mToolbar;

    public static void startActivity(Context context, String phone, String password) {
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setWhiteStatusBar(R.color.white);
        EventBus.getDefault().register(this);
        mToolbar = (HRTitle) findViewById(R.id.tool_bar);
        llLoginView = (LinearLayout) findViewById(R.id.ll_loginView);
        llContent = (LinearLayout) findViewById(R.id.ll_content);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        etAccount = (PWEditText) findViewById(R.id.et_account);
        etPassword = (PWEditText) findViewById(R.id.et_password);
        rbtLogin = (RippleButton) findViewById(R.id.rbt_login);
        tv_code_login = (TextView) findViewById(R.id.tv_code_login);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        mToolbar.bindActivity(this, R.color.white, "返回万箱");
        mToolbar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterSelectActivity.startActivity(mContext);
            }
        });
        new EditTextHelper.Builder()
                .addEditTexts(etAccount, etPassword)
                .addOnInputListener(this)
                .build();
        rbtLogin.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintKbTwo();
                loginClick();
            }
        });
        tv_forget_password.setOnClickListener(this);
        tv_code_login.setOnClickListener(this);
        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        if (TextUtils.isEmpty(phone)) {
            phone = SPUtils.getSharedStringData(mContext, HRConstant.ACCOUNT);
        }
        etAccount.setText(phone);
        etPassword.setText(password);
        // 光标最后
        etAccount.setSelection(etAccount.getText().toString().length());
        //弹出软键盘时滚动视图
        autoScrollView(llLoginView, rbtLogin);
    }

    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition = 0;

    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += (scrollHeight + DisplayUtil.dip2px(10));

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }

    /**
     * 普通账号密码登录
     */
    private void loginClick() {
        phone = etAccount.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        mRxManager.add(ApiUser.getInstance().mobileUserLogin(UserApiPram.mobileUserLogin(phone, password))
                .compose(RxHelper.<LoginBean>getResult())
                .subscribeWith(new BaseObserver<LoginBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        rbtLogin.showLoadingButton();
                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        // 检查用户是否是合伙人账号
                        if (UserHelper.checkUser(loginBean.getLoginInfo().getCompany_class())) {
                            // 保存用户信息和token
                            UserHelper.saveUserInfo(loginBean, phone);
                            loginSuccess(loginBean);
                        } else {
                            LoginFail("账号或密码错误");
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        LoginFail(message);
                    }
                }));
    }

    /**
     * 登陆成功跳转页面
     *
     * @param loginBean 登陆
     */
    private void loginSuccess(LoginBean loginBean) {
        // 会员分类（2-客户 3-合伙人4-服务商5-合伙人+服务商）
        switch (loginBean.getLoginInfo().getCompany_class()) {
            case "2":
                ARouter.getInstance().build(ARouterPath.COMPANY_MAIN_ACTIVITY).navigation();
                break;
            case "7":
                ARouter.getInstance().build(ARouterPath.COMPANY_MAIN_ACTIVITY).navigation();
                break;
            case "3":
                ARouter.getInstance().build(ARouterPath.PARNNER_MAIN_ACTIVITY).navigation();
                break;
            case "4":
                ARouter.getInstance().build(ARouterPath.SERVICE_MAIN_ACTIVITY).navigation();
                break;
            case "5":
                startActivity(PlatformActivity.class);
                break;
            default:
                startActivity(PlatformActivity.class);
                break;
        }
        // 关闭所有页面
        AppManager.getAppManager().finishAllActivity();
    }

    /**
     * 登录失败之后的操作
     */
    private void LoginFail(String msg) {
        rbtLogin.showRedButton(msg);
    }

    /**
     * 此方法只是关闭软键盘
     */
    protected void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onSuccess() {
        rbtLogin.showGreenButton("登录");
    }

    @Override
    public void onError() {
        rbtLogin.showGrayButton();
    }


    @Override
    public void onClick(View view) {
        if (view == tv_code_login) {
            VerificationCodeActivity.startAction(LoginActivity.this);
        }
        if (view == tv_forget_password) {
            ResetpsdActivity.startAction(LoginActivity.this, "找回登录密码");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber
    public void onEvent(String event) {
        if ("loginSuccess".equals(event)) {
            finish();
        }
    }
}
