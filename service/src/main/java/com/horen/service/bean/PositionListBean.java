package com.horen.service.bean;

/**
 * @author :ChenYangYi
 * @date :2018/08/22/13:10
 * @description :损坏位置
 * @github :https://github.com/chenyy0708
 */
public class PositionListBean {
    public PositionListBean(String position_id) {
        this.position_id = position_id;
    }

    /**
     * position : 111
     * position_id : 555
     * product_type : OF330
     */



    private String position;
    private String position_id;
    private String product_type;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition_id() {
        return position_id;
    }

    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
}
