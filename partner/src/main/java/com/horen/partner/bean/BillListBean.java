package com.horen.partner.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/29 10:03
 * Description:This isBillListBean
 */
public class BillListBean implements Serializable {


    private List<AwardsBean> awards;
    private List<OrdersBean> orders;
    /**
     * 返利奖
     */
    private Commission commission;
    private Performance performance;
    /**
     * 突破奖
     */
    private Topaward topaward;

    public Topaward getTopaward() {
        return topaward;
    }

    public void setTopaward(Topaward topaward) {
        this.topaward = topaward;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public List<AwardsBean> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardsBean> awards) {
        this.awards = awards;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public static class AwardsBean implements Serializable {

        /**
         * amount : 20
         * months : 2018-08
         * company_id : CN027204
         * company_name : 武汉龙安集团有限责任公司
         */

        private int amount;
        private String months;
        private String company_id;
        private String company_name;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

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
    }

    public static class OrdersBean implements Serializable {
        /**
         * company_id : CM00000321
         * company_name : 上海TEST7分公司
         * months : 2018-08
         * amount : 2000
         */

        private String company_id;
        private String company_name;
        private String months;
        private double amount;

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

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    public static class Commission {
        private double commission_amount;

        public double getCommission_amount() {
            return commission_amount;
        }

        public void setCommission_amount(double commission_amount) {
            this.commission_amount = commission_amount;
        }
    }

    public static class Performance {
        private double amount;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

    public static class Topaward {
        /**
         * award_amount : 20
         * award_type : 突破奖
         */

        private int award_amount;
        private String award_type;

        public int getAward_amount() {
            return award_amount;
        }

        public void setAward_amount(int award_amount) {
            this.award_amount = award_amount;
        }

        public String getAward_type() {
            return award_type;
        }

        public void setAward_type(String award_type) {
            this.award_type = award_type;
        }
    }
}
