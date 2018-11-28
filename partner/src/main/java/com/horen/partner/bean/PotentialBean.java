package com.horen.partner.bean;

import com.horen.base.bean.BaseEntry;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/8 13:26
 * Description:This isPotentialBean
 */
public class PotentialBean  {

    /**
     * pages : 67754
     * pageSize : 63637
     * list : [{"customer_mail":"测试内容c89k","customer_address":"测试内容3977","end_date":"测试内容x5qa","days":88082,"warning":"测试内容2otd","start_date":"测试内容368v","customer_id":"测试内容t721","customer_contact":"测试内容2by5","customer_tel":"测试内容53q3","customer_name":"测试内容al57"},{"customer_mail":"测试内容c89k","customer_address":"测试内容3977","end_date":"测试内容x5qa","days":88082,"warning":"测试内容2otd","start_date":"测试内容368v","customer_id":"测试内容t721","customer_contact":"测试内容2by5","customer_tel":"测试内容53q3","customer_name":"测试内容al57"},{"customer_mail":"测试内容c89k","customer_address":"测试内容3977","end_date":"测试内容x5qa","days":88082,"warning":"测试内容2otd","start_date":"测试内容368v","customer_id":"测试内容t721","customer_contact":"测试内容2by5","customer_tel":"测试内容53q3","customer_name":"测试内容al57"},{"customer_mail":"测试内容c89k","customer_address":"测试内容3977","end_date":"测试内容x5qa","days":88082,"warning":"测试内容2otd","start_date":"测试内容368v","customer_id":"测试内容t721","customer_contact":"测试内容2by5","customer_tel":"测试内容53q3","customer_name":"测试内容al57"},{"customer_mail":"测试内容c89k","customer_address":"测试内容3977","end_date":"测试内容x5qa","days":88082,"warning":"测试内容2otd","start_date":"测试内容368v","customer_id":"测试内容t721","customer_contact":"测试内容2by5","customer_tel":"测试内容53q3","customer_name":"测试内容al57"},{"customer_mail":"测试内容c89k","customer_address":"测试内容3977","end_date":"测试内容x5qa","days":88082,"warning":"测试内容2otd","start_date":"测试内容368v","customer_id":"测试内容t721","customer_contact":"测试内容2by5","customer_tel":"测试内容53q3","customer_name":"测试内容al57"},{"customer_mail":"测试内容c89k","customer_address":"测试内容3977","end_date":"测试内容x5qa","days":88082,"warning":"测试内容2otd","start_date":"测试内容368v","customer_id":"测试内容t721","customer_contact":"测试内容2by5","customer_tel":"测试内容53q3","customer_name":"测试内容al57"}]
     * total : 16028
     * pageNum : 34856
     */

    private int pages;
    private int pageSize;
    private int total;
    private int pageNum;
    private List<ListBean> list;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * customer_mail : 测试内容c89k
         * customer_address : 测试内容3977
         * end_date : 测试内容x5qa
         * days : 88082
         * warning : 测试内容2otd
         * start_date : 测试内容368v
         * customer_id : 测试内容t721
         * customer_contact : 测试内容2by5
         * customer_tel : 测试内容53q3
         * customer_name : 测试内容al57
         */

        private String customer_mail;
        private String customer_address;
        private String end_date;
        private int days;
        private String warning;
        private String start_date;
        private String customer_id;
        private String customer_contact;
        private String customer_tel;
        private String customer_name;

        public String getCustomer_mail() {
            return customer_mail;
        }

        public void setCustomer_mail(String customer_mail) {
            this.customer_mail = customer_mail;
        }

        public String getCustomer_address() {
            return customer_address;
        }

        public void setCustomer_address(String customer_address) {
            this.customer_address = customer_address;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getWarning() {
            return warning;
        }

        public void setWarning(String warning) {
            this.warning = warning;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getCustomer_contact() {
            return customer_contact;
        }

        public void setCustomer_contact(String customer_contact) {
            this.customer_contact = customer_contact;
        }

        public String getCustomer_tel() {
            return customer_tel;
        }

        public void setCustomer_tel(String customer_tel) {
            this.customer_tel = customer_tel;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }
    }

}
