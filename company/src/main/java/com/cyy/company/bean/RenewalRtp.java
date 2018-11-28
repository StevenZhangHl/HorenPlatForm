package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/24/16:27
 * @description :续租产品
 * @github :https://github.com/chenyy0708
 */
public class RenewalRtp {

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * org_id : CN02170K01
         * owner_id : CN02121R
         * product_id : 01.0101.013
         * product_name : 吨立方IF1040非食品
         * product_photo : http://61.153.224.202:9096/upload/photo/IF1040-blue.png
         * product_type : IF1040
         * product_typeid : 01
         * qty : 3
         */

        private String org_id;
        private String owner_id;
        private String product_id;
        private String product_name;
        private String product_photo;
        private String product_type;
        private String product_typeid;
        private String product_flag;

        public String getProduct_flag() {
            return product_flag;
        }

        public void setProduct_flag(String product_flag) {
            this.product_flag = product_flag;
        }

        private int qty;

        private double rent_price;
        private double tms_price;

        public double getTms_price() {
            return tms_price;
        }

        public void setTms_price(double tms_price) {
            this.tms_price = tms_price;
        }

        /**
         * 默认数量为1
         */
        private int order_qty = 1;

        public double getRent_price() {
            return rent_price;
        }

        public void setRent_price(double rent_price) {
            this.rent_price = rent_price;
        }

        public int getOrder_qty() {
            return order_qty;
        }

        public void setOrder_qty(int order_qty) {
            this.order_qty = order_qty;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public String getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(String owner_id) {
            this.owner_id = owner_id;
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

        public String getProduct_typeid() {
            return product_typeid;
        }

        public void setProduct_typeid(String product_typeid) {
            this.product_typeid = product_typeid;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }
    }
}
