package com.horen.service.bean;

import com.horen.chart.piechart.IPieData;
import com.horen.service.utils.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/22/09:40
 * @description :服务列表
 * @github :https://github.com/chenyy0708
 */
public class ServiceListBean implements Serializable {

    private List<ServiceBean> serviceList;

    public List<ServiceBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceBean> serviceList) {
        this.serviceList = serviceList;
    }

    public static class ServiceBean implements IPieData,Serializable {
        /**
         * product_id : 01.0101.013
         * product_name : 测试内容81ke
         * product_type : IF1040
         * service_qty : 2
         * service_type : 0
         */

        private String product_id;
        private String product_name;
        private String product_type;
        private String product_photo;
        private int service_qty;
        private String service_type;

        public String getProduct_photo() {
            return product_photo;
        }

        public void setProduct_photo(String product_photo) {
            this.product_photo = product_photo;
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

        @Override
        public float getValue() {
            return service_qty;
        }

        /**
         * 超过四位显示...
         */
        @Override
        public String getLabelName() {
            return StringUtils.substring(product_name);
        }
    }
}
