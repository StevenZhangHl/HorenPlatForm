package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/20/12:51
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderLogs {

    private List<OrderIdBean> order_id;
    private List<PartListBean> partList;

    public List<OrderIdBean> getOrder_id() {
        return order_id;
    }

    public void setOrder_id(List<OrderIdBean> order_id) {
        this.order_id = order_id;
    }

    public List<PartListBean> getPartList() {
        return partList;
    }

    public void setPartList(List<PartListBean> partList) {
        this.partList = partList;
    }

    public static class OrderIdBean {
        /**
         * company_id :
         * company_name : Horen
         * create_date : 2018-11-02 12:49:29
         * creator :
         * log : 执行中
         * log_id : 25806926151764470
         * movement : 4
         * order_id : ZCN02122U18110201
         * order_type : 11
         * phase : 1
         */

        private String company_id;
        private String company_name;
        private String create_date;
        private String creator;
        private String log;
        private String log_id;
        private String movement;
        private String order_id;
        private String order_type;
        private String phase;

        /**
         * 执行中物品列表
         */
        private List<PartListBean> partList;


        public List<PartListBean> getPartList() {
            return partList;
        }

        public void setPartList(List<PartListBean> partList) {
            this.partList = partList;
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

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getMovement() {
            return movement;
        }

        public void setMovement(String movement) {
            this.movement = movement;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }
    }

    public static class PartListBean {
        /**
         * create_time : 2018-11-02 13:04:31
         * creater : ll
         * is_clean : 2
         * order_id : ZCN02122U18110201
         * order_type : 11
         * orderallot_status : 4
         * ordertrans_id : PZCN02122U18110201
         * product_id : 01.0101.020
         * product_type : IF1040防窃启锁扣
         * repair_qty : 0
         * storage_qty : 100
         */

        private String create_time;
        private String creater;
        private String is_clean;
        private String order_id;
        private String order_type;
        private String orderallot_status;
        private String ordertrans_id;
        private String product_id;
        private String product_type;
        private int repair_qty;
        private int storage_qty;


        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public String getIs_clean() {
            return is_clean;
        }

        public void setIs_clean(String is_clean) {
            this.is_clean = is_clean;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getOrderallot_status() {
            return orderallot_status;
        }

        public void setOrderallot_status(String orderallot_status) {
            this.orderallot_status = orderallot_status;
        }

        public String getOrdertrans_id() {
            return ordertrans_id;
        }

        public void setOrdertrans_id(String ordertrans_id) {
            this.ordertrans_id = ordertrans_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public int getRepair_qty() {
            return repair_qty;
        }

        public void setRepair_qty(int repair_qty) {
            this.repair_qty = repair_qty;
        }

        public int getStorage_qty() {
            return storage_qty;
        }

        public void setStorage_qty(int storage_qty) {
            this.storage_qty = storage_qty;
        }
    }
}
