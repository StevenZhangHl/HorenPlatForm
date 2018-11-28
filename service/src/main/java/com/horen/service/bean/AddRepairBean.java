package com.horen.service.bean;

/**
 * @author :ChenYangYi
 * @date :2018/08/22/14:27
 * @description :新增报修单
 * @github :https://github.com/chenyy0708
 */
public class AddRepairBean {

    /**
     * serviceMap : {"service_id":"01"}
     */

    private ServiceMapBean serviceMap;

    public ServiceMapBean getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(ServiceMapBean serviceMap) {
        this.serviceMap = serviceMap;
    }

    public static class ServiceMapBean {
        /**
         * service_id : 01
         */

        private String service_id;

        public String getService_id() {
            return service_id;
        }

        public void setService_id(String service_id) {
            this.service_id = service_id;
        }
    }
}
