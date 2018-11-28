package com.horen.service.bean;

/**
 * @author :ChenYangYi
 * @date :2018/08/15/14:19
 * @description :出入口产品集合
 * @github :https://github.com/chenyy0708
 */
public class StorageSubmitBean {

    /**
     * 分配数量
     */
    private String allot_qty;
    /**
     * 产品ID
     */
    private String product_id;
    /**
     * 维修数量
     */
    private String repair_qty;

    public StorageSubmitBean(String allot_qty, String product_id, String repair_qty) {
        this.allot_qty = allot_qty;
        this.product_id = product_id;
        this.repair_qty = repair_qty;
    }

    public String getAllot_qty() {
        return allot_qty;
    }

    public void setAllot_qty(String allot_qty) {
        this.allot_qty = allot_qty;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getRepair_qty() {
        return repair_qty;
    }

    public void setRepair_qty(String repair_qty) {
        this.repair_qty = repair_qty;
    }
}
