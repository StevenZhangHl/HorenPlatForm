package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/12:43
 * @description :百网迁移
 * @github :https://github.com/chenyy0708
 */
public class HomeCoor {

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * company_id : CN102101
         * company_name : 上海箱箱物流科技有限公司
         * org_address : 成都市新都区货运大道5888号川宏产业园
         * org_consignee : 杜明明
         * org_consigneetel :
         * org_contact : 杜明明
         * org_id : 107100010002
         * org_latitude : 30.851802
         * org_longitude : 104.243297
         * org_name : 成都仓
         * org_status : 测试内容1i4l
         * org_tel :
         */

        private String company_id;
        private String company_name;
        private String org_address;
        private String org_consignee;
        private String org_consigneetel;
        private String org_contact;
        private String org_id;
        private double org_latitude;
        private double org_longitude;
        private String org_name;
        private String org_status;
        private String org_tel;

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getOrg_address() {
            return org_address;
        }

        public void setOrg_address(String org_address) {
            this.org_address = org_address;
        }

        public String getOrg_consignee() {
            return org_consignee;
        }

        public void setOrg_consignee(String org_consignee) {
            this.org_consignee = org_consignee;
        }

        public String getOrg_consigneetel() {
            return org_consigneetel;
        }

        public void setOrg_consigneetel(String org_consigneetel) {
            this.org_consigneetel = org_consigneetel;
        }

        public String getOrg_contact() {
            return org_contact;
        }

        public void setOrg_contact(String org_contact) {
            this.org_contact = org_contact;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public double getOrg_latitude() {
            return org_latitude;
        }

        public void setOrg_latitude(double org_latitude) {
            this.org_latitude = org_latitude;
        }

        public double getOrg_longitude() {
            return org_longitude;
        }

        public void setOrg_longitude(double org_longitude) {
            this.org_longitude = org_longitude;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getOrg_status() {
            return org_status;
        }

        public void setOrg_status(String org_status) {
            this.org_status = org_status;
        }

        public String getOrg_tel() {
            return org_tel;
        }

        public void setOrg_tel(String org_tel) {
            this.org_tel = org_tel;
        }
    }
}
