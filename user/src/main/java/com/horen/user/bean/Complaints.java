package com.horen.user.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/14:30
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class Complaints {

    private List<ComplainTypeListBean> complainTypeList;

    public List<ComplainTypeListBean> getComplainTypeList() {
        return complainTypeList;
    }

    public void setComplainTypeList(List<ComplainTypeListBean> complainTypeList) {
        this.complainTypeList = complainTypeList;
    }

    public static class ComplainTypeListBean {
        /**
         * complain_type : 平台
         * complain_typeid : 0
         */

        private String complain_type;
        private String complain_typeid;

        public String getComplain_type() {
            return complain_type;
        }

        public void setComplain_type(String complain_type) {
            this.complain_type = complain_type;
        }

        public String getComplain_typeid() {
            return complain_typeid;
        }

        public void setComplain_typeid(String complain_typeid) {
            this.complain_typeid = complain_typeid;
        }
    }
}
