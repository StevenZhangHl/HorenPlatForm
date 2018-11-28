package com.horen.service.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/22/16:44
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class RepairDetailBean implements Serializable {


    private List<ServiceListBean> serviceList;

    public List<ServiceListBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceListBean> serviceList) {
        this.serviceList = serviceList;
    }

    public static class ServiceListBean implements Serializable {
        /**
         * consumablesList : [{"product_id":"12.0401.001","product_name":"33333","product_type":"IF1040","qty":"1","warehouse_qty":55552}]
         * create_time : 2018-08-22
         * ctnr_sn : PI50103
         * positionList : [{"img":"http://baidu.com","is_agree":"0","position_id":"333","remark":"","service_lineid":"4714e1408ea244b88c645043f28fa1d0"}]
         * product_id : 01.0101.013
         * product_name : 吨立方IF1040非食品
         * product_photo : http://61.153.224.202:9096/upload/photo/IF1040-blue.png
         * product_type : IF1040
         * remark : 500
         * service_id : 01
         * service_qty : 1
         * service_type : 0
         * update_time : 2018-08-22
         */

        private String create_time;
        private String ctnr_sn;
        private String product_id;
        private String product_name;
        private String product_photo;
        private String product_type;
        private String remark;
        private String service_id;
        private int service_qty;
        private String service_type;
        private String update_time;
        private String is_person;
        private String service_status;
        private List<ConsumablesListBean> consumablesList;
        private List<PositionListBean> positionList;

        public String getService_status() {
            return service_status;
        }

        public void setService_status(String service_status) {
            this.service_status = service_status;
        }

        public String getIs_person() {
            return is_person;
        }

        public void setIs_person(String is_person) {
            this.is_person = is_person;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCtnr_sn() {
            return ctnr_sn;
        }

        public void setCtnr_sn(String ctnr_sn) {
            this.ctnr_sn = ctnr_sn;
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

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getService_id() {
            return service_id;
        }

        public void setService_id(String service_id) {
            this.service_id = service_id;
        }

        public int getService_qty() {
            return service_qty;
        }

        public void setService_qty(int service_qty) {
            this.service_qty = service_qty;
        }

        public String getService_type() {
            return service_type;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public List<ConsumablesListBean> getConsumablesList() {
            return consumablesList;
        }

        public void setConsumablesList(List<ConsumablesListBean> consumablesList) {
            this.consumablesList = consumablesList;
        }

        public List<PositionListBean> getPositionList() {
            return positionList;
        }

        public void setPositionList(List<PositionListBean> positionList) {
            this.positionList = positionList;
        }

        public static class ConsumablesListBean implements Serializable {
            /**
             * product_id : 12.0401.001
             * product_name : 33333
             * product_type : IF1040
             * qty : 1
             * warehouse_qty : 55552
             */

            private String product_id;
            private String product_name;
            private String product_type;
            private String qty;
            private int warehouse_qty;

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

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

            public int getWarehouse_qty() {
                return warehouse_qty;
            }

            public void setWarehouse_qty(int warehouse_qty) {
                this.warehouse_qty = warehouse_qty;
            }
        }

        public static class PositionListBean implements Serializable {
            /**
             * img : http://baidu.com
             * is_agree : 0
             * position_id : 333
             * remark :
             * service_lineid : 4714e1408ea244b88c645043f28fa1d0
             */

            private String img;
            private List<String> imgList;
            private String is_agree;
            private String position_id;
            private String position;
            private String remark;
            private String service_lineid;

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public List<String> getImgList() {
                return imgList;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getIs_agree() {
                return is_agree;
            }

            /**
             * 是否同意维修损坏位置
             * 0:同意 1:拒绝
             */
            public boolean isAgree() {
                return is_agree.equals("0");
            }

            public void setIs_agree(String is_agree) {
                this.is_agree = is_agree;
            }

            public String getPosition_id() {
                return position_id;
            }

            public void setPosition_id(String position_id) {
                this.position_id = position_id;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getService_lineid() {
                return service_lineid;
            }

            public void setService_lineid(String service_lineid) {
                this.service_lineid = service_lineid;
            }
        }
    }
}
