package com.horen.service.api;

import com.horen.base.net.NetManager;

/**
 * @author :ChenYangYi
 * @date :2018/07/18/10:43
 * @description :合伙人Api
 * @github :https://github.com/chenyy0708
 */
public class Api {
    private static ServiceApi api;

    private Api() {
    }

    public static ServiceApi getInstance() {
        if (api == null) {
            synchronized (Api.class) {
                if (api == null) {
                    api = NetManager.getInstance().getRetrofit().create(ServiceApi.class);
                }
            }
        }
        return api;
    }
}