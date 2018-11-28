package com.horen.partner.api;

import com.horen.base.net.NetManager;

/**
 * @author :ChenYangYi
 * @date :2018/07/18/10:43
 * @description :合伙人Api
 * @github :https://github.com/chenyy0708
 */
public class ApiPartner {
    private static PartnerApi api;

    private ApiPartner() {
    }

    public static PartnerApi getInstance() {
        if (api == null) {
            synchronized (ApiPartner.class) {
                if (api == null) {
                    api = NetManager.getInstance().getRetrofit().create(PartnerApi.class);
                }
            }
        }
        return api;

    }
}