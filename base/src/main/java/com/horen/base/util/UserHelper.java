package com.horen.base.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.horen.base.app.BaseApp;
import com.horen.base.app.HRConstant;
import com.horen.base.bean.LoginBean;

import java.util.Arrays;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/05/15/17:26
 * @description :用户信息工具类
 * @github :https://github.com/chenyy0708
 */
public class UserHelper {

    private static LoginBean mLoginBean = null;
    /**
     * 只有符合该权限的用户才能登陆 会员分类（2-客户 3-合伙人4-服务商5-合伙人+服务商 7下游）
     */
    private static List<String> mUserPermission = Arrays.asList("2", "3", "4", "5", "7");

    /**
     * 保存用户信息到SP
     *
     * @param loginBean 用户信息
     */
    public static void saveUserInfo(LoginBean loginBean) {
        if (loginBean != null && loginBean.getLoginInfo() != null) {
            mLoginBean = loginBean;
            // 用户登陆信息，整个json保存到sp中
            SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.LOGIN_INFO, GsonUtil.getGson().toJson(loginBean));
            SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.TOKEN, loginBean.getLoginInfo().getApp_token());
            SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.USER_ID, loginBean.getLoginInfo().getUser_id());
        }
    }

    /**
     * 保存登陆账号信息
     *
     * @param loginBean 用户信息
     */
    public static void saveUserInfo(LoginBean loginBean, String userAccount) {
        SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.ACCOUNT, userAccount);
        // 保存登陆账号，用于登陆
        saveUserInfo(loginBean);
    }

    /**
     * 退出登陆
     * 清除用户信息，退出登陆使用
     */
    public static void logOut() {
        // 用户企业信息
        SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.TOKEN, "");
        SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.LOGIN_INFO, "");
        SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.USER_ID, "");
        // 清空内存保存的登陆信息
        mLoginBean = null;
    }

    /**
     * 获取登陆信息
     *
     * @return 登陆信息
     */
    public static LoginBean getUserInfo() {
        if (mLoginBean == null) {
            synchronized (Gson.class) {
                if (mLoginBean == null) {
                    if (!TextUtils.isEmpty(SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.LOGIN_INFO))) { // 用户登陆信息不为空
                        mLoginBean = GsonUtil.getGson().fromJson(SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.LOGIN_INFO), LoginBean.class);
                    } else { // 用户退出登陆或未登录
                        mLoginBean = new LoginBean();
                    }
                }
            }
        }
        return mLoginBean;
    }

    /**
     * app是否登录,通过Token来判断
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.TOKEN));
    }

    /**
     * 是否既是合伙人又是服务商
     */
    public static boolean isPartnerAndService() {
        return getUserInfo().getLoginInfo().getCompany_class().equals("5");
    }

    /**
     * 检查用户是否是合伙人
     */
    public static boolean checkUser(String company_class) {
        return mUserPermission.contains(company_class);
    }

    /**
     * 下游账号
     */
    public static boolean checkDownStream() {
        return getUserInfo().getLoginInfo().getFlag_data().equals("2");
    }
}
