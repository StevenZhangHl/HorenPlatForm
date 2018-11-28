package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/12/09:16
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class AnalysisTotal {


    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * empty_qty : 1
         * en_route_qty : 4
         * full_qty : 9
         * org_address : 王帅的下游
         * org_id : CN02171K01
         * org_name : 王帅的下游
         * product_type : OF330
         * total_qty : 10
         */

        private int empty_qty;
        private int en_route_qty;
        private int full_qty;
        private String org_address;
        private String org_id;
        private String org_name;
        private String product_type;
        private int total_qty;

        public int getEmpty_qty() {
            return empty_qty;
        }

        public void setEmpty_qty(int empty_qty) {
            this.empty_qty = empty_qty;
        }

        public int getEn_route_qty() {
            return en_route_qty;
        }

        public void setEn_route_qty(int en_route_qty) {
            this.en_route_qty = en_route_qty;
        }

        public int getFull_qty() {
            return full_qty;
        }

        public void setFull_qty(int full_qty) {
            this.full_qty = full_qty;
        }

        public String getOrg_address() {
            return org_address;
        }

        public void setOrg_address(String org_address) {
            this.org_address = org_address;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public int getTotal_qty() {
            return total_qty;
        }

        public void setTotal_qty(int total_qty) {
            this.total_qty = total_qty;
        }
    }
}
