package com.horen.partner.bean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/17 13:12
 * Description:This 业务中心曲线数据bean
 */
public class BusinessLineBean {

    private List<RentBean> rent;
    private List<RecycleBean> recycle;

    public List<RentBean> getRent() {
        return rent;
    }

    public void setRent(List<RentBean> rent) {
        this.rent = rent;
    }

    public List<RecycleBean> getRecycle() {
        return recycle;
    }

    public void setRecycle(List<RecycleBean> recycle) {
        this.recycle = recycle;
    }

    public static class RentBean {
        /**
         * months : 测试内容nvl6
         * total_receiveqty : 51677
         */

        private String months;
        private int total_receiveqty;

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

        public int getTotal_receiveqty() {
            return total_receiveqty;
        }

        public void setTotal_receiveqty(int total_receiveqty) {
            this.total_receiveqty = total_receiveqty;
        }
    }

    public static class RecycleBean {
        /**
         * total_receiveqty : 61466
         * months : 测试内容y7h7
         */

        private int total_receiveqty;
        private String months;

        public int getTotal_receiveqty() {
            return total_receiveqty;
        }

        public void setTotal_receiveqty(int total_receiveqty) {
            this.total_receiveqty = total_receiveqty;
        }

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }
    }

}
