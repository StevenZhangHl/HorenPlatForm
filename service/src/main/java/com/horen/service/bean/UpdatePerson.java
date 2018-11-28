package com.horen.service.bean;

/**
 * @author :ChenYangYi
 * @date :2018/08/23/10:58
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class UpdatePerson {
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 服务ID
     */
    private String serviceId;

    public UpdatePerson(String productId, String serviceId) {
        this.productId = productId;
        this.serviceId = serviceId;
    }
}
