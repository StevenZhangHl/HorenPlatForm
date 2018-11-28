package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/12/16:11
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillDetailBean  {

    private List<BillListBean> billList;
    private List<BillLogListBean> billLogList;

    public List<BillListBean> getBillList() {
        return billList;
    }

    public void setBillList(List<BillListBean> billList) {
        this.billList = billList;
    }

    public List<BillLogListBean> getBillLogList() {
        return billLogList;
    }

    public void setBillLogList(List<BillLogListBean> billLogList) {
        this.billLogList = billLogList;
    }

    public static class BillListBean {
        /**
         * create_time : 2018-09-07
         * product_id : 01.0102.006
         * product_name : 超立方OF330食品
         * product_type : OF330
         * storage_qty : 0
         */

        private String create_time;
        private String product_id;
        private String product_name;
        private String product_type;
        private String storage_qty;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getStorage_qty() {
            return storage_qty;
        }

        public void setStorage_qty(String storage_qty) {
            this.storage_qty = storage_qty;
        }
    }

    public static class BillLogListBean {
        /**
         * confirm_amt : 1000
         * create_time : 2018-09-07
         */

        private int confirm_amt;
        private String create_time;

        public int getConfirm_amt() {
            return confirm_amt;
        }

        public void setConfirm_amt(int confirm_amt) {
            this.confirm_amt = confirm_amt;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
