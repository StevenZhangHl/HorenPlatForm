package com.cyy.company.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/14:56
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class DefaultOrgBean implements Serializable {


    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean implements Serializable {
        /**
         * km : 0
         * org_address : 上海市
         * org_area : 云南省临沧市云县
         * org_consignee : 综合
         * org_consigneetel : 15921026958
         * org_contact : 综合
         * org_id : CN88340101
         * org_latitude : 31.24916
         * org_longitude : 121.4879
         * org_name : 综合服务商ABC
         * org_tel : 15921026958
         * pro_org_address : 上海
         * pro_org_consignee : 京东
         * pro_org_consigneetel : 18721585530
         * pro_org_id : CN02121R01
         * pro_org_latitude : 31.24916
         * pro_org_longitude : 121.4879
         * pro_org_name : 京东A
         */

        private double km;
        private String org_address;
        private String org_area;
        private String org_consignee;
        private String org_consigneetel;
        private String org_contact;
        private String org_id;
        private double org_latitude;
        private double org_longitude;
        private String org_name;
        private String org_tel;
        private String pro_org_address;
        private String pro_org_consignee;
        private String pro_org_consigneetel;
        private String pro_org_id;
        private double pro_org_latitude;
        private double pro_org_longitude;
        private String pro_org_name;

        public double getKm() {
            return km;
        }

        public void setKm(double km) {
            this.km = km;
        }

        public String getOrg_address() {
            return org_address;
        }

        public void setOrg_address(String org_address) {
            this.org_address = org_address;
        }

        public String getOrg_area() {
            return org_area;
        }

        public void setOrg_area(String org_area) {
            this.org_area = org_area;
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

        public String getOrg_tel() {
            return org_tel;
        }

        public void setOrg_tel(String org_tel) {
            this.org_tel = org_tel;
        }

        public String getPro_org_address() {
            return pro_org_address;
        }

        public void setPro_org_address(String pro_org_address) {
            this.pro_org_address = pro_org_address;
        }

        public String getPro_org_consignee() {
            return pro_org_consignee;
        }

        public void setPro_org_consignee(String pro_org_consignee) {
            this.pro_org_consignee = pro_org_consignee;
        }

        public String getPro_org_consigneetel() {
            return pro_org_consigneetel;
        }

        public void setPro_org_consigneetel(String pro_org_consigneetel) {
            this.pro_org_consigneetel = pro_org_consigneetel;
        }

        public String getPro_org_id() {
            return pro_org_id;
        }

        public void setPro_org_id(String pro_org_id) {
            this.pro_org_id = pro_org_id;
        }

        public double getPro_org_latitude() {
            return pro_org_latitude;
        }

        public void setPro_org_latitude(double pro_org_latitude) {
            this.pro_org_latitude = pro_org_latitude;
        }

        public double getPro_org_longitude() {
            return pro_org_longitude;
        }

        public void setPro_org_longitude(double pro_org_longitude) {
            this.pro_org_longitude = pro_org_longitude;
        }

        public String getPro_org_name() {
            return pro_org_name;
        }

        public void setPro_org_name(String pro_org_name) {
            this.pro_org_name = pro_org_name;
        }
    }
}
