package com.cyy.company.api;

import com.horen.base.net.NetManager;

/**
 * @author :ChenYangYi
 * @date :2018/10/11/13:00
 * @description :用户端Api
 * @github :https://github.com/chenyy0708
 */
public class ApiCompany {
    private static CompanyService api;

    private ApiCompany() {
    }

    public static CompanyService getInstance() {
        if (api == null) {
            synchronized (CompanyService.class) {
                if (api == null) {
                    api = NetManager.getInstance().getRetrofit().create(CompanyService.class);
                }
            }
        }
        return api;
    }
}
