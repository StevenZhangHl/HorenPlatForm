package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/17:38
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class DownStreamBean {


    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * ctnr_type : IF1040
         * date_month : 2018-09
         * org_id : CN02720401
         * org_name : 武汉龙安集团有限责任公司
         * owner_id : CN102101
         * qty : 23
         * sum_qty : 0
         */

        private String ctnr_type;
        private String date_month;
        private String org_id;
        private String org_name;
        private String owner_id;
        private int qty;
        private int sum_qty;
        /**
         * 破损率 百分比
         */
        private float avg_qty;

        public float getAvg_qty() {
            return avg_qty;
        }

        public void setAvg_qty(float avg_qty) {
            this.avg_qty = avg_qty;
        }

        /**
         * 柱状图显示数值
         */
        private float number;

        public float getNumber() {
            return number;
        }

        public void setNumber(float number) {
            this.number = number;
        }

        /**
         * 是否执行过动画
         */
        private boolean isAnimation = false;

        public boolean isAnimation() {
            return isAnimation;
        }

        public void setAnimation(boolean animation) {
            isAnimation = animation;
        }

        public String getCtnr_type() {
            return ctnr_type;
        }

        public void setCtnr_type(String ctnr_type) {
            this.ctnr_type = ctnr_type;
        }

        public String getDate_month() {
            return date_month;
        }

        public void setDate_month(String date_month) {
            this.date_month = date_month;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(String owner_id) {
            this.owner_id = owner_id;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getSum_qty() {
            return sum_qty;
        }

        public void setSum_qty(int sum_qty) {
            this.sum_qty = sum_qty;
        }
    }
}
