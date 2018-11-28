package com.cyy.company.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/24/17:03
 * @description :续租默认地址
 * @github :https://github.com/chenyy0708
 */
public class RenewalAddress implements Serializable{

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean implements Serializable {
        /**
         * km : 23084
         * org_address : 淘宝B详细地址
         * org_address1 : B区服务商详细地址
         * org_area : 云南省丽江市古城区
         * org_area1 : 内蒙古自治区乌海市海勃湾区
         * org_consignee : 姚亚楠
         * org_consignee1 : 李雷
         * org_consigneetel : 13512323132
         * org_consigneetel1 : 18175541582
         * org_id : CN88820H01
         * org_id1 : CN47340401
         * org_latitude : 39.892193
         * org_latitude1 : 39.777416
         * org_longitude : 116.76628
         * org_longitude1 : 116.54137
         * org_name : 淘宝B
         * org_name1 : B区服务商
         */

        private double km;
        private String org_address;
        private String org_address1;
        private String org_area;
        private String org_area1;
        private String org_consignee;
        private String org_consignee1;
        private String org_consigneetel;
        private String org_consigneetel1;
        private String org_id;
        private String org_id1;
        private double org_latitude;
        private double org_latitude1;
        private double org_longitude;
        private double org_longitude1;
        private String org_name;
        private String org_name1;

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

        public String getOrg_address1() {
            return org_address1;
        }

        public void setOrg_address1(String org_address1) {
            this.org_address1 = org_address1;
        }

        public String getOrg_area() {
            return org_area;
        }

        public void setOrg_area(String org_area) {
            this.org_area = org_area;
        }

        public String getOrg_area1() {
            return org_area1;
        }

        public void setOrg_area1(String org_area1) {
            this.org_area1 = org_area1;
        }

        public String getOrg_consignee() {
            return org_consignee;
        }

        public void setOrg_consignee(String org_consignee) {
            this.org_consignee = org_consignee;
        }

        public String getOrg_consignee1() {
            return org_consignee1;
        }

        public void setOrg_consignee1(String org_consignee1) {
            this.org_consignee1 = org_consignee1;
        }

        public String getOrg_consigneetel() {
            return org_consigneetel;
        }

        public void setOrg_consigneetel(String org_consigneetel) {
            this.org_consigneetel = org_consigneetel;
        }

        public String getOrg_consigneetel1() {
            return org_consigneetel1;
        }

        public void setOrg_consigneetel1(String org_consigneetel1) {
            this.org_consigneetel1 = org_consigneetel1;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public String getOrg_id1() {
            return org_id1;
        }

        public void setOrg_id1(String org_id1) {
            this.org_id1 = org_id1;
        }

        public double getOrg_latitude() {
            return org_latitude;
        }

        public void setOrg_latitude(double org_latitude) {
            this.org_latitude = org_latitude;
        }

        public double getOrg_latitude1() {
            return org_latitude1;
        }

        public void setOrg_latitude1(double org_latitude1) {
            this.org_latitude1 = org_latitude1;
        }

        public double getOrg_longitude() {
            return org_longitude;
        }

        public void setOrg_longitude(double org_longitude) {
            this.org_longitude = org_longitude;
        }

        public double getOrg_longitude1() {
            return org_longitude1;
        }

        public void setOrg_longitude1(double org_longitude1) {
            this.org_longitude1 = org_longitude1;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getOrg_name1() {
            return org_name1;
        }

        public void setOrg_name1(String org_name1) {
            this.org_name1 = org_name1;
        }
    }
}
