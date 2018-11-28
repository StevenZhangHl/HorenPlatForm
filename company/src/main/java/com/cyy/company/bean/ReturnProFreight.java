package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/24/14:25
 * @description :还箱物品运费
 * @github :https://github.com/chenyy0708
 */
public class ReturnProFreight {

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * company_id : CM00000001
         * org_id : 107100010002
         * product_typeid : 01
         * trans_price : 10
         */

        private String company_id;
        private String org_id;
        private String product_typeid;
        private double trans_price;

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public String getProduct_typeid() {
            return product_typeid;
        }

        public void setProduct_typeid(String product_typeid) {
            this.product_typeid = product_typeid;
        }

        public double getTrans_price() {
            return trans_price;
        }

        public void setTrans_price(double trans_price) {
            this.trans_price = trans_price;
        }
    }
}
