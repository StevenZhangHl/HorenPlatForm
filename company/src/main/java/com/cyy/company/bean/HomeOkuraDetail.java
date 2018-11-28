package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/14:35
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class HomeOkuraDetail {


    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * create_time : 2018-10-25 15:52:10
         * product_id : 01.0101.013
         * product_industry : 液体包装
         * product_name : 吨立方IF1040非食品
         * product_spec : 蓝287C
         * product_type : IF1040
         * qty : 998
         */

        private String create_time;
        private String product_id;
        private String product_industry;
        private String product_name;
        private String product_spec;
        private String product_type;
        private int qty;

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

        public String getProduct_industry() {
            return product_industry;
        }

        public void setProduct_industry(String product_industry) {
            this.product_industry = product_industry;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
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

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }
    }
}
