package com.cyy.company.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/15:25
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderProducts implements Serializable {

    public OrderProducts(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean implements Serializable {
        /**
         * days : 30
         * flag_product : 1
         * product_id : 01.0101.013
         * product_name : 吨立方IF1040非食品
         * product_photo : http://61.153.224.202:9096/upload/photo/IF1040-blue.png
         * product_spec : 蓝287C
         * product_type : IF1040
         * rent_price : 75.0
         * tms_price : 20
         */

        private String days;
        private String flag_product;
        private String product_id;
        private String product_name;
        private String product_photo;
        private String product_spec;
        private String product_type;
        private double rent_price;
        private String tms_price;
        private boolean isSelect;
        private String product_flag;

        public String getProduct_flag() {
            return product_flag;
        }

        public void setProduct_flag(String product_flag) {
            this.product_flag = product_flag;
        }

        /**
         * 默认数量为1
         */
        private int order_qty = 1;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getOrder_qty() {
            return order_qty;
        }

        public void setOrder_qty(int order_qty) {
            this.order_qty = order_qty;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getFlag_product() {
            return flag_product;
        }

        public void setFlag_product(String flag_product) {
            this.flag_product = flag_product;
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

        public String getProduct_spec() {
            return product_spec;
        }

        public void setProduct_spec(String product_spec) {
            this.product_spec = product_spec;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public double getRent_price() {
            return rent_price;
        }

        public void setRent_price(double rent_price) {
            this.rent_price = rent_price;
        }

        public String getTms_price() {
            return tms_price;
        }

        public void setTms_price(String tms_price) {
            this.tms_price = tms_price;
        }
    }
}
