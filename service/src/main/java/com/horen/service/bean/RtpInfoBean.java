package com.horen.service.bean;

/**
 * @author :ChenYangYi
 * @date :2018/08/22/13:10
 * @description :箱子信息
 * @github :https://github.com/chenyy0708
 */
public class RtpInfoBean {
    /**
     * ctnr_sn : 010102006 001173
     * product_id : 01.0102.006
     * product_name : 超立方OF330食品
     * product_photo : http://61.153.224.202:9096/upload/photo/OF330-green.png
     * product_type : OF330
     */

    private String ctnr_sn;
    private String product_id;
    private String product_name;
    private String product_photo;
    private String product_type;

    public String getCtnr_sn() {
        return ctnr_sn;
    }

    public void setCtnr_sn(String ctnr_sn) {
        this.ctnr_sn = ctnr_sn;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
}
