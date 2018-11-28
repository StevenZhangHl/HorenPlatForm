package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/06/09:48
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class AssetMapListBean {

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * flag_road : 0
         * org_id : HC00001663
         * org_latitude : 34.487104
         * org_longitude : 109.026168
         * org_name : 陕西通汇-北三物流
         */

        private String flag_road;
        private String org_id;
        private double org_latitude;
        private double org_longitude;
        private String org_name;

        public String getFlag_road() {
            return flag_road;
        }

        public void setFlag_road(String flag_road) {
            this.flag_road = flag_road;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public double getOrg_latitude() {
            return org_latitude;
        }

        public void setOrg_latitude(double org_latitude) {
            this.org_latitude = org_latitude;
        }

        public double getOrg_longitude() {
            return org_longitude;
        }

        public void setOrg_longitude(double org_longitude) {
            this.org_longitude = org_longitude;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }
    }
}
