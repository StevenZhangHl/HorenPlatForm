package com.horen.partner.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/3 15:48
 * Description:This isWalletBillAdapter
 */
public class WalletBillBean {

    /**
     * list : [{"amount":"测试内容6f1u","date":"测试内容9vk8","remak":"测试内容6d5u","type":"测试内容66t1","type_title":"测试内容30o2"}]
     * pageNum : 62361
     * pageSize : 24171
     * pages : 76261
     * total : 20123
     */

    private int pageNum;
    private int pageSize;
    private int pages;
    private int total;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * amount : 测试内容6f1u
         * date : 测试内容9vk8
         * remak : 测试内容6d5u
         * type : 测试内容66t1
         * type_title : 测试内容30o2
         */

        private double amount;
        private String date;
        private String remak;
        private String type;
        private String type_title;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRemak() {
            return remak;
        }

        public void setRemak(String remak) {
            this.remak = remak;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType_title() {
            return type_title;
        }

        public void setType_title(String type_title) {
            this.type_title = type_title;
        }
    }

}
