package com.horen.user.api;

import com.horen.base.net.NetManager;

/**
 * @author :ChenYangYi
 * @date :2018/07/18/10:43
 * @description :个人中心
 * @github :https://github.com/chenyy0708
 */
public class ApiUser {
    private static UserApi api;

    private ApiUser() {
    }

    public static UserApi getInstance() {
        if (api == null) {
            synchronized (UserApi.class) {
                if (api == null) {
                    api = NetManager.getInstance().getRetrofit().create(UserApi.class);
                }
            }
        }
        return api;
    }
}