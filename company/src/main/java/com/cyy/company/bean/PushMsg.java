package com.cyy.company.bean;

/**
 * @author :ChenYangYi
 * @date :2018/11/15/11:04
 * @description :消息推送
 * @github :https://github.com/chenyy0708
 */
public class PushMsg {

    /**
     * order_id : SCN02171K1811150Y-S1
     * type : 1
     * type：1：物流信息推送，2：消息通知推送
     */

    private String order_id;
    private String type;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
