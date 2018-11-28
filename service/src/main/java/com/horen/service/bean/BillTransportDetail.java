package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/14/16:18
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillTransportDetail {

    /**
     * billLogList : {"confirm_amt":"测试内容fcu4","create_time":"测试内容2rxq"}
     * freightBillInfo : {"account_id":"CN512201180914S0103","account_status":"1","confirm_amt":0,"create_time":"0000-00-00","diff_amt":33,"end_address":"ddd","end_companyid":"CN512201","end_companyname":"333","end_orgid":"CN512201","end_orgname":"ddded","orderallot_id":"Z18091410","proList":[{"perform_qty":0,"product_id":"01.0102.006","product_name":"超立方OF330食品","product_type":"OF330"}],"start_address":"山东省济南市天桥区二环西路北首路西一号（天顺物流市场）","start_orgid":"10710002","start_orgname":"3333","total_ar":33}
     */

    private List<BillLogListBean> billLogList;
    private FreightBillInfoBean freightBillInfo;

    public List<BillLogListBean> getBillLogList() {
        return billLogList;
    }

    public void setBillLogList(List<BillLogListBean> billLogList) {
        this.billLogList = billLogList;
    }

    public FreightBillInfoBean getFreightBillInfo() {
        return freightBillInfo;
    }

    public void setFreightBillInfo(FreightBillInfoBean freightBillInfo) {
        this.freightBillInfo = freightBillInfo;
    }

    public static class BillLogListBean {
        /**
         * confirm_amt : 测试内容fcu4
         * create_time : 测试内容2rxq
         */

        private String confirm_amt;
        private String create_time;

        public String getConfirm_amt() {
            return confirm_amt;
        }

        public void setConfirm_amt(String confirm_amt) {
            this.confirm_amt = confirm_amt;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class FreightBillInfoBean {
        /**
         * account_id : CN512201180914S0103
         * account_status : 1
         * confirm_amt : 0
         * create_time : 0000-00-00
         * diff_amt : 33
         * end_address : ddd
         * end_companyid : CN512201
         * end_companyname : 333
         * end_orgid : CN512201
         * end_orgname : ddded
         * orderallot_id : Z18091410
         * proList : [{"perform_qty":0,"product_id":"01.0102.006","product_name":"超立方OF330食品","product_type":"OF330"}]
         * start_address : 山东省济南市天桥区二环西路北首路西一号（天顺物流市场）
         * start_orgid : 10710002
         * start_orgname : 3333
         * total_ar : 33
         */

        private String account_id;
        private String account_status;
        private int confirm_amt;
        private String create_time;
        private int diff_amt;
        private String end_address;
        private String end_companyid;
        private String end_companyname;
        private String end_orgid;
        private String end_orgname;
        private String orderallot_id;
        private String start_address;
        private String start_orgid;
        private String start_orgname;
        private int total_ar;
        private List<ProListBean> proList;

        public String getAccount_id() {
            return account_id;
        }

        public void setAccount_id(String account_id) {
            this.account_id = account_id;
        }

        public String getAccount_status() {
            return account_status;
        }

        public void setAccount_status(String account_status) {
            this.account_status = account_status;
        }

        public int getConfirm_amt() {
            return confirm_amt;
        }

        public void setConfirm_amt(int confirm_amt) {
            this.confirm_amt = confirm_amt;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getDiff_amt() {
            return diff_amt;
        }

        public void setDiff_amt(int diff_amt) {
            this.diff_amt = diff_amt;
        }

        public String getEnd_address() {
            return end_address;
        }

        public void setEnd_address(String end_address) {
            this.end_address = end_address;
        }

        public String getEnd_companyid() {
            return end_companyid;
        }

        public void setEnd_companyid(String end_companyid) {
            this.end_companyid = end_companyid;
        }

        public String getEnd_companyname() {
            return end_companyname;
        }

        public void setEnd_companyname(String end_companyname) {
            this.end_companyname = end_companyname;
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

        public int getTotal_ar() {
            return total_ar;
        }

        public void setTotal_ar(int total_ar) {
            this.total_ar = total_ar;
        }

        public List<ProListBean> getProList() {
            return proList;
        }

        public void setProList(List<ProListBean> proList) {
            this.proList = proList;
        }

        public static class ProListBean {
            /**
             * perform_qty : 0
             * product_id : 01.0102.006
             * product_name : 超立方OF330食品
             * product_type : OF330
             */

            private int perform_qty;
            private String product_id;
            private String product_name;
            private String product_type;

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

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_type() {
                return product_type;
            }

            public void setProduct_type(String product_type) {
                this.product_type = product_type;
            }
        }
    }
}
