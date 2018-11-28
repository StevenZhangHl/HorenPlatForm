package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/20/16:27
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class ReturnIntegra {

    /**
     * count : 60
     * pdList : [{"company_id":"CN021701","count_point":60,"create_date":"2018-09-09 17:28:47","point":-10,"point_movement":"奖励","total_point":300}]
     */

    private int count_point;
    private List<PdListBean> pdList;

    public int getCount() {
        return count_point;
    }

    public void setCount(int count) {
        this.count_point = count;
    }

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * company_id : CN021701
         * count_point : 60
         * create_date : 2018-09-09 17:28:47
         * point : -10
         * point_movement : 奖励
         * total_point : 300
         */

        private String company_id;
        private int count_point;
        private String create_date;
        private int point;
        private String point_movement;
        private int total_point;

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public int getCount_point() {
            return count_point;
        }

        public void setCount_point(int count_point) {
            this.count_point = count_point;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getPoint_movement() {
            return point_movement;
        }

        public void setPoint_movement(String point_movement) {
            this.point_movement = point_movement;
        }

        public int getTotal_point() {
            return total_point;
        }

        public void setTotal_point(int total_point) {
            this.total_point = total_point;
        }
    }
}
