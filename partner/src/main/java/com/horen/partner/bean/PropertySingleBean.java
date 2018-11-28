package com.horen.partner.bean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/17 14:59
 * Description:This isPropertySingleBean
 */
public class PropertySingleBean {

    /**
     * lng : 53666
     * tag_id : 测试内容7sw3
     * loss : 测试内容2284
     * open_status : 测试内容34u8
     * lat : 65343
     * ctnr_sn : 测试内容1z23
     */
    List<SingleBean> list;

    public List<SingleBean> getList() {
        return list;
    }

    public void setList(List<SingleBean> list) {
        this.list = list;
    }

    public class SingleBean {
        private double lng;
        private int num;
        private double lat;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }


}
