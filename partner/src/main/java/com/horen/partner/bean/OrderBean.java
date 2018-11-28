package com.horen.partner.bean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/17 14:00
 * Description:This isOrderInfoBean
 */
public class OrderBean {

    /**
     * products : [{"product_type":"测试内容r461","product_name":"测试内容c89p","product_unit":"测试内容46d4","product_id":"测试内容koe0","receive_qty":84201}]
     * order_id : 测试内容wd9g
     * months : 测试内容itbk
     * finish_date : 35835
     */
    private List<Order> list;
    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class Order {
        private String order_id;
        private String months;
        private String finish_date;
        private String order_type;
        private List<ProductsBean> products;

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

        public String getFinish_date() {
            return finish_date;
        }

        public void setFinish_date(String finish_date) {
            this.finish_date = finish_date;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * product_type : 测试内容r461
             * product_name : 测试内容c89p
             * product_unit : 测试内容46d4
             * product_id : 测试内容koe0
             * receive_qty : 84201
             */

            private String product_type;
            private String product_name;
            private String product_unit;
            private String product_id;
            private int receive_qty;

            public String getProduct_type() {
                return product_type;
            }

            public void setProduct_type(String product_type) {
                this.product_type = product_type;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_unit() {
                return product_unit;
            }

            public void setProduct_unit(String product_unit) {
                this.product_unit = product_unit;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public int getReceive_qty() {
                return receive_qty;
            }

            public void setReceive_qty(int receive_qty) {
                this.receive_qty = receive_qty;
            }
        }
    }
}
