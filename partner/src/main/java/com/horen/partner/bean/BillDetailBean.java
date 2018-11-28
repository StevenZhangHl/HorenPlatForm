package com.horen.partner.bean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/30 8:52
 * Description:This isBillDetalBean
 */
public class BillDetailBean {

    /**
     * pay_date : 2018-08-31
     * prodcuts : [{"product_id":"010.110.120","qty":50,"product_name":"吨立方OF330非食品","product_flag":1,"order_amount":30,"product_type":"OF330非食品"}]
     * order_id : SCM0000000118081609
     * orders_amount : 100
     */

    private String pay_date;
    private String order_id;
    private int orders_amount;
    private List<ProdcutsBean> products;

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getOrders_amount() {
        return orders_amount;
    }

    public void setOrders_amount(int orders_amount) {
        this.orders_amount = orders_amount;
    }

    public List<ProdcutsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProdcutsBean> products) {
        this.products = products;
    }

    public static class ProdcutsBean {
        /**
         * product_id : 010.110.120
         * qty : 50
         * product_name : 吨立方OF330非食品
         * product_flag : 1
         * order_amount : 30
         * product_type : OF330非食品
         */

        private String product_id;
        private int qty;
        private String product_name;
        private int product_flag;
        private int order_amount;
        private String product_type;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getProduct_flag() {
            return product_flag;
        }

        public void setProduct_flag(int product_flag) {
            this.product_flag = product_flag;
        }

        public int getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(int order_amount) {
            this.order_amount = order_amount;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }
    }
}
