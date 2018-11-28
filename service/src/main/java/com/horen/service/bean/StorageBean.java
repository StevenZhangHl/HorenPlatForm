package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/17:24
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class StorageBean {

    /**
     * create_time : 2018-08-14 05:08:26
     * img1 : http://kkjk
     * img2 : http://kkjk
     * ordertrans_id : AO000000000009
     * proList : [{"is_clean":"2","ordertrans_id":"AO000000000009","product_id":"01.0101.013","product_type":"2","repair_qty":2,"storage_id":"1","storage_lineid":"1","storage_qty":2},{"is_clean":"2","ordertrans_id":"AO000000000009","product_id":"01.0101.014","product_type":"2","repair_qty":2,"storage_id":"1","storage_lineid":"2","storage_qty":2}]
     * remark : 500
     * storage_id : 1
     * storage_status : 2
     * storage_type : 2
     * warehouse_id : HC00000734
     * warehouse_name : 2
     */

    private String create_time;
    private String img1;
    private String img2;
    private String ordertrans_id;
    private String remark;
    private String storage_id;
    private String storage_status;
    private String storage_type;
    private String warehouse_id;
    private String warehouse_name;
    private List<ProListBean> proList;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getOrdertrans_id() {
        return ordertrans_id;
    }

    public void setOrdertrans_id(String ordertrans_id) {
        this.ordertrans_id = ordertrans_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getStorage_status() {
        return storage_status;
    }

    public void setStorage_status(String storage_status) {
        this.storage_status = storage_status;
    }

    public String getStorage_type() {
        return storage_type;
    }

    public void setStorage_type(String storage_type) {
        this.storage_type = storage_type;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public List<ProListBean> getProList() {
        return proList;
    }

    public void setProList(List<ProListBean> proList) {
        this.proList = proList;
    }

    public static class ProListBean {
        /**
         * is_clean : 2
         * ordertrans_id : AO000000000009
         * product_id : 01.0101.013
         * product_type : 2
         * repair_qty : 2
         * storage_id : 1
         * storage_lineid : 1
         * storage_qty : 2
         */

        private String is_clean;
        private String ordertrans_id;
        private String product_id;
        private String product_type;
        private String product_name;
        private int repair_qty;
        private String storage_id;
        private String storage_lineid;
        private int storage_qty;

        public String getIs_clean() {
            return is_clean;
        }

        public void setIs_clean(String is_clean) {
            this.is_clean = is_clean;
        }

        public String getOrdertrans_id() {
            return ordertrans_id;
        }

        public void setOrdertrans_id(String ordertrans_id) {
            this.ordertrans_id = ordertrans_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_type() {
            return product_type;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public int getRepair_qty() {
            return repair_qty;
        }

        public void setRepair_qty(int repair_qty) {
            this.repair_qty = repair_qty;
        }

        public String getStorage_id() {
            return storage_id;
        }

        public void setStorage_id(String storage_id) {
            this.storage_id = storage_id;
        }

        public String getStorage_lineid() {
            return storage_lineid;
        }

        public void setStorage_lineid(String storage_lineid) {
            this.storage_lineid = storage_lineid;
        }

        public int getStorage_qty() {
            return storage_qty;
        }

        public void setStorage_qty(int storage_qty) {
            this.storage_qty = storage_qty;
        }
    }
}
