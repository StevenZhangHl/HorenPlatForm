package com.cyy.company.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/09:44
 * @description :订单详情
 * @github :https://github.com/chenyy0708
 */
public class OrderDetailBean implements Serializable {

    /**
     * pageInfo : {"create_date":"2018-10-23 14:00:15.0","creator":"jda","end_address":"上海","end_area":"上海上海市徐汇区","end_contact":"京东","end_orgid":"CN02121R01","end_orgname":"京东A","end_tel":"18721585530","expect_arrivedate":"2018-10-23 00:00:00.0","flag_relet":"0","flag_renttype":"2","flag_send":"0","list":[{"load_qty":"0","order_qty":"12","plan_qty":"0","product_flag":"1","product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_spec":"蓝287C","product_type":"IF1040","receive_qty":"0","return_qty":"0","service_qty":"0"}],"order_id":"ZCN02121R18102306","order_note":"","order_status":"0","order_type":"11","rent_days":"30","start_address":"上海市徐家汇古美路","start_area":"上海上海市嘉定区","start_contact":"建彬","start_orgid":"CN02140601","start_orgname":"综合服务商B","start_tel":"18651527780","total_ar":"1140.00","total_discountar":"0.00","total_foregiftar":"0.00","total_otherar":"0.00","total_rentar":"900.00","total_salear":"0.00","total_tmsar":"240.00","total_washar":"0.00"}
     */

    private PageInfoBean pageInfo;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class PageInfoBean implements Serializable {
        /**
         * create_date : 2018-10-23 14:00:15.0
         * creator : jda
         * end_address : 上海
         * end_area : 上海上海市徐汇区
         * end_contact : 京东
         * end_orgid : CN02121R01
         * end_orgname : 京东A
         * end_tel : 18721585530
         * expect_arrivedate : 2018-10-23 00:00:00.0
         * flag_relet : 0
         * flag_renttype : 2
         * flag_send : 0
         * list : [{"load_qty":"0","order_qty":"12","plan_qty":"0","product_flag":"1","product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_spec":"蓝287C","product_type":"IF1040","receive_qty":"0","return_qty":"0","service_qty":"0"}]
         * order_id : ZCN02121R18102306
         * order_note :
         * order_status : 0
         * order_type : 11
         * rent_days : 30
         * start_address : 上海市徐家汇古美路
         * start_area : 上海上海市嘉定区
         * start_contact : 建彬
         * start_orgid : CN02140601
         * start_orgname : 综合服务商B
         * start_tel : 18651527780
         * total_ar : 1140.00
         * total_discountar : 0.00
         * total_foregiftar : 0.00
         * total_otherar : 0.00
         * total_rentar : 900.00
         * total_salear : 0.00
         * total_tmsar : 240.00
         * total_washar : 0.00
         */

        private String eval_status;


        public String getEval_status() {
            return eval_status;
        }

        public void setEval_status(String eval_status) {
            this.eval_status = eval_status;
        }

        private String create_date;
        private String creator;
        private String end_address;
        private String end_area;
        private String end_contact;
        private String end_orgid;
        private String end_orgname;
        private String end_tel;
        private String expect_arrivedate;
        private String flag_relet;
        private String flag_renttype;
        private String flag_send;
        private String order_id;
        private String order_note;
        private String order_status;
        private String order_type;
        private String rent_days;
        private String start_address;
        private String start_area;
        private String start_contact;
        private String start_orgid;
        private String start_orgname;
        private String start_tel;
        private double total_ar;
        private double total_discountar;
        private double total_foregiftar;
        private double total_otherar;
        private double total_rentar;
        private double total_salear;
        private double total_tmsar;
        private double total_washar;
        /**
         * 总超期费
         */
        private double total_overprice;
        private List<ListBean> list;
        /**
         * 超期物品列表
         */
        private List<OverListBean> overList;

        private String start_longitude;
        private String start_latitude;
        private String end_longitude;
        private String end_latitude;

        public double getTotal_overprice() {
            return total_overprice;
        }

        public void setTotal_overprice(double total_overprice) {
            this.total_overprice = total_overprice;
        }

        public List<OverListBean> getOverList() {
            return overList;
        }

        public void setOverList(List<OverListBean> overList) {
            this.overList = overList;
        }

        public String getStart_longitude() {
            return start_longitude;
        }

        public void setStart_longitude(String start_longitude) {
            this.start_longitude = start_longitude;
        }

        public String getStart_latitude() {
            return start_latitude;
        }

        public void setStart_latitude(String start_latitude) {
            this.start_latitude = start_latitude;
        }

        public String getEnd_longitude() {
            return end_longitude;
        }

        public void setEnd_longitude(String end_longitude) {
            this.end_longitude = end_longitude;
        }

        public String getEnd_latitude() {
            return end_latitude;
        }

        public void setEnd_latitude(String end_latitude) {
            this.end_latitude = end_latitude;
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

        public String getEnd_address() {
            return end_address;
        }

        public void setEnd_address(String end_address) {
            this.end_address = end_address;
        }

        public String getEnd_area() {
            return end_area;
        }

        public void setEnd_area(String end_area) {
            this.end_area = end_area;
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

        public String getExpect_arrivedate() {
            return expect_arrivedate;
        }

        public void setExpect_arrivedate(String expect_arrivedate) {
            this.expect_arrivedate = expect_arrivedate;
        }

        public String getFlag_relet() {
            return flag_relet;
        }

        public void setFlag_relet(String flag_relet) {
            this.flag_relet = flag_relet;
        }

        public String getFlag_renttype() {
            return flag_renttype;
        }

        public void setFlag_renttype(String flag_renttype) {
            this.flag_renttype = flag_renttype;
        }

        public String getFlag_send() {
            return flag_send;
        }

        public void setFlag_send(String flag_send) {
            this.flag_send = flag_send;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_note() {
            return order_note;
        }

        public void setOrder_note(String order_note) {
            this.order_note = order_note;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getRent_days() {
            return rent_days;
        }

        public void setRent_days(String rent_days) {
            this.rent_days = rent_days;
        }

        public String getStart_address() {
            return start_address;
        }

        public void setStart_address(String start_address) {
            this.start_address = start_address;
        }

        public String getStart_area() {
            return start_area;
        }

        public void setStart_area(String start_area) {
            this.start_area = start_area;
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

        public double getTotal_ar() {
            return total_ar;
        }

        public void setTotal_ar(double total_ar) {
            this.total_ar = total_ar;
        }

        public double getTotal_discountar() {
            return total_discountar;
        }

        public void setTotal_discountar(double total_discountar) {
            this.total_discountar = total_discountar;
        }

        public double getTotal_foregiftar() {
            return total_foregiftar;
        }

        public void setTotal_foregiftar(double total_foregiftar) {
            this.total_foregiftar = total_foregiftar;
        }

        public double getTotal_otherar() {
            return total_otherar;
        }

        public void setTotal_otherar(double total_otherar) {
            this.total_otherar = total_otherar;
        }

        public double getTotal_rentar() {
            return total_rentar;
        }

        public void setTotal_rentar(double total_rentar) {
            this.total_rentar = total_rentar;
        }

        public double getTotal_salear() {
            return total_salear;
        }

        public void setTotal_salear(double total_salear) {
            this.total_salear = total_salear;
        }

        public double getTotal_tmsar() {
            return total_tmsar;
        }

        public void setTotal_tmsar(double total_tmsar) {
            this.total_tmsar = total_tmsar;
        }

        public double getTotal_washar() {
            return total_washar;
        }

        public void setTotal_washar(double total_washar) {
            this.total_washar = total_washar;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * load_qty : 0
             * order_qty : 12
             * plan_qty : 0
             * product_flag : 1
             * product_id : 01.0101.013
             * product_name : 吨立方IF1040非食品
             * product_photo : http://61.153.224.202:9096/upload/photo/IF1040-blue.png
             * product_spec : 蓝287C
             * product_type : IF1040
             * receive_qty : 0
             * return_qty : 0
             * service_qty : 0
             */

            private String load_qty;
            private String order_qty;
            private String plan_qty;
            private String product_flag;
            private String product_id;
            private String product_name;
            private String product_photo;
            private String product_spec;
            private String product_type;
            private String receive_qty;
            private String return_qty;
            private String service_qty;

            public String getLoad_qty() {
                return load_qty;
            }

            public void setLoad_qty(String load_qty) {
                this.load_qty = load_qty;
            }

            public String getOrder_qty() {
                return order_qty;
            }

            public void setOrder_qty(String order_qty) {
                this.order_qty = order_qty;
            }

            public String getPlan_qty() {
                return plan_qty;
            }

            public void setPlan_qty(String plan_qty) {
                this.plan_qty = plan_qty;
            }

            public String getProduct_flag() {
                return product_flag;
            }

            public void setProduct_flag(String product_flag) {
                this.product_flag = product_flag;
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

            public String getProduct_photo() {
                return product_photo;
            }

            public void setProduct_photo(String product_photo) {
                this.product_photo = product_photo;
            }

            public String getProduct_spec() {
                return product_spec;
            }

            public void setProduct_spec(String product_spec) {
                this.product_spec = product_spec;
            }

            public String getProduct_type() {
                return product_type;
            }

            public void setProduct_type(String product_type) {
                this.product_type = product_type;
            }

            public String getReceive_qty() {
                return receive_qty;
            }

            public void setReceive_qty(String receive_qty) {
                this.receive_qty = receive_qty;
            }

            public String getReturn_qty() {
                return return_qty;
            }

            public void setReturn_qty(String return_qty) {
                this.return_qty = return_qty;
            }

            public String getService_qty() {
                return service_qty;
            }

            public void setService_qty(String service_qty) {
                this.service_qty = service_qty;
            }
        }

        public static class OverListBean implements Serializable {
            private String order_id;
            private String product_id;
            private String box_orderqty;
            private String rent_days;
            private String rent_days_actual;
            private String expired_days;
            private String product_name;

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getBox_orderqty() {
                return box_orderqty;
            }

            public void setBox_orderqty(String box_orderqty) {
                this.box_orderqty = box_orderqty;
            }

            public String getRent_days() {
                return rent_days;
            }

            public void setRent_days(String rent_days) {
                this.rent_days = rent_days;
            }

            public String getRent_days_actual() {
                return rent_days_actual;
            }

            public void setRent_days_actual(String rent_days_actual) {
                this.rent_days_actual = rent_days_actual;
            }

            public String getExpired_days() {
                return expired_days;
            }

            public void setExpired_days(String expired_days) {
                this.expired_days = expired_days;
            }
        }
    }
}
