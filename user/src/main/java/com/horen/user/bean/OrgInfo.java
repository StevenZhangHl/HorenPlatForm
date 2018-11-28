package com.horen.user.bean;

/**
 * @author :ChenYangYi
 * @date :2018/09/14/09:45
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrgInfo {

    /**
     * orgInfo : {"company_id":"CN021501","company_name":"箱箱综合合伙人-松江区洞泾","org_address":"上海市松江区洞泾镇洞塔路","org_contact":"陈振辉","org_id":"CN02150101","org_latitude":0,"org_longitude":0,"org_mail":"chenzh@cortp.com","org_name":"箱箱综合类合伙人有限公司-ChenZhengHui","org_tel":"15601648699"}
     */

    private OrgInfoBean orgInfo;

    public OrgInfoBean getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfoBean orgInfo) {
        this.orgInfo = orgInfo;
    }

    public static class OrgInfoBean {
        /**
         * company_id : CN021501
         * company_name : 箱箱综合合伙人-松江区洞泾
         * org_address : 上海市松江区洞泾镇洞塔路
         * org_contact : 陈振辉
         * org_id : CN02150101
         * org_latitude : 0.0
         * org_longitude : 0.0
         * org_mail : chenzh@cortp.com
         * org_name : 箱箱综合类合伙人有限公司-ChenZhengHui
         * org_tel : 15601648699
         */

        private String company_id;
        private String company_name;
        private String org_address;
        private String org_contact;
        private String org_id;
        private double org_latitude;
        private double org_longitude;
        private String org_mail;
        private String org_name;
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

        public String getOrg_mail() {
            return org_mail;
        }

        public void setOrg_mail(String org_mail) {
            this.org_mail = org_mail;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getOrg_tel() {
            return org_tel;
        }

        public void setOrg_tel(String org_tel) {
            this.org_tel = org_tel;
        }
    }
}
