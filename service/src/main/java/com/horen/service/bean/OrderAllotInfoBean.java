package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/16:36
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderAllotInfoBean {
    /**
     * audit_time : 2018-08-14 04:08:31
     * end_address : 333
     * end_contact : 333
     * end_orgid : HC00000734
     * end_orgname : 333
     * end_tel : 33
     * end_time : 2018-08-14 04:08:31
     * order_type : 11
     * orderallot_id : 1111
     * proList : [{"is_clean":"2","order_qty":155,"orderallot_id":"1111","orderallot_lineid":"33333","perform_qty":5,"product_id":"01.0101.013","product_photo":"http://61.153.224.202:9106/upload/photo/2018-06-21-b2f681ac-e3bb-49dd-a3cc-beb0e5979c03.jpg","product_type":"IF1040"},{"is_clean":"2","order_qty":23,"orderallot_id":"1111","orderallot_lineid":"33334","perform_qty":4,"product_id":"01.0101.014","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-green.png","product_type":"IF1040"}]
     * start_address : 22
     * start_contact : 22
     * start_orgid : 22
     * start_orgname : 22
     * start_tel : 22
     */

    private String audit_time;
    private String end_address;
    private String end_contact;
    private String end_orgid;
    private String end_orgname;
    private String end_tel;
    private String end_time;
    private String exp_time;
    private String order_type;
    private String orderallot_id;
    private String start_address;
    private String start_contact;
    private String start_orgid;
    private String start_orgname;
    private String start_tel;
    private String time_difference;

    public String getExp_time() {
        return exp_time;
    }

    public void setExp_time(String exp_time) {
        this.exp_time = exp_time;
    }

    private List<ProListBean> proList;


    public String getTime_difference() {
        return Integer.valueOf(time_difference) == 0 ? "1" : time_difference;
    }

    public String getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(String audit_time) {
        this.audit_time = audit_time;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public String getEnd_contact() {
        return end_contact;
    }

    public void setEnd_contact(String end_contact) {
        this.end_contact = end_contact;
    }

    public String getEnd_orgid() {
        return end_orgid;
    }

    public void setEnd_orgid(String end_orgid) {
        this.end_orgid = end_orgid;
    }

    public String getEnd_orgname() {
        return end_orgname;
    }

    public void setEnd_orgname(String end_orgname) {
        this.end_orgname = end_orgname;
    }

    public String getEnd_tel() {
        return end_tel;
    }

    public void setEnd_tel(String end_tel) {
        this.end_tel = end_tel;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrderallot_id() {
        return orderallot_id;
    }

    public void setOrderallot_id(String orderallot_id) {
        this.orderallot_id = orderallot_id;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getStart_contact() {
        return start_contact;
    }

    public void setStart_contact(String start_contact) {
        this.start_contact = start_contact;
    }

    public String getStart_orgid() {
        return start_orgid;
    }

    public void setStart_orgid(String start_orgid) {
        this.start_orgid = start_orgid;
    }

    public String getStart_orgname() {
        return start_orgname;
    }

    public void setStart_orgname(String start_orgname) {
        this.start_orgname = start_orgname;
    }

    public String getStart_tel() {
        return start_tel;
    }

    public void setStart_tel(String start_tel) {
        this.start_tel = start_tel;
    }

    public List<ProListBean> getProList() {
        return proList;
    }

    public void setProList(List<ProListBean> proList) {
        this.proList = proList;
    }

    public static class ProListBean {
        /**
         * is_clean : 2
         * order_qty : 155
         * orderallot_id : 1111
         * orderallot_lineid : 33333
         * perform_qty : 5
         * product_id : 01.0101.013
         * product_photo : http://61.153.224.202:9106/upload/photo/2018-06-21-b2f681ac-e3bb-49dd-a3cc-beb0e5979c03.jpg
         * product_type : IF1040
         */

        private String is_clean;
        private int order_qty;
        private String orderallot_id;
        private String orderallot_lineid;
        private int perform_qty;
        /**
         * 配送数量
         */
        private int distribution_qty;
        /**
         * 损坏量
         */
        private int repair_qty;
        /**
         * 实际回收量
         */
        private int actual_recovery_qty;
        /**
         * 未执行量
         */
        private int not_perform_qty;
        /**
         * 总库存数量
         */
        private int total_qty;
        /**
         * 可用数量
         */
        private int available_qty;
        private String product_id;
        private String product_photo;
        private String product_type;
        private String product_name;

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getRepair_qty() {
            return repair_qty;
        }

        public void setRepair_qty(int repair_qty) {
            this.repair_qty = repair_qty;
        }

        public int getDistribution_qty() {
            return distribution_qty;
        }

        public void setDistribution_qty(int distribution_qty) {
            this.distribution_qty = distribution_qty;
        }

        public int getActual_recovery_qty() {
            return actual_recovery_qty;
        }

        public void setActual_recovery_qty(int actual_recovery_qty) {
            this.actual_recovery_qty = actual_recovery_qty;
        }

        public int getNot_perform_qty() {
            return not_perform_qty;
        }

        public void setNot_perform_qty(int not_perform_qty) {
            this.not_perform_qty = not_perform_qty;
        }

        public int getTotal_qty() {
            return total_qty;
        }

        public void setTotal_qty(int total_qty) {
            this.total_qty = total_qty;
        }

        public int getAvailable_qty() {
            return available_qty;
        }

        public void setAvailable_qty(int available_qty) {
            this.available_qty = available_qty;
        }

        public String getIs_clean() {
            return is_clean;
        }

        public void setIs_clean(String is_clean) {
            this.is_clean = is_clean;
        }

        public int getOrder_qty() {
            return order_qty;
        }

        public void setOrder_qty(int order_qty) {
            this.order_qty = order_qty;
        }

        public String getOrderallot_id() {
            return orderallot_id;
        }

        public void setOrderallot_id(String orderallot_id) {
            this.orderallot_id = orderallot_id;
        }

        public String getOrderallot_lineid() {
            return orderallot_lineid;
        }

        public void setOrderallot_lineid(String orderallot_lineid) {
            this.orderallot_lineid = orderallot_lineid;
        }

        public int getPerform_qty() {
            return perform_qty;
        }

        public void setPerform_qty(int perform_qty) {
            this.perform_qty = perform_qty;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_photo() {
            return product_photo;
        }

        public void setProduct_photo(String product_photo) {
            this.product_photo = product_photo;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }
    }
}