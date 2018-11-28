package com.horen.service.bean;

import android.text.TextUtils;

import com.horen.base.util.NumberUtil;
import com.horen.chart.piechart.IPieData;
import com.horen.service.utils.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/12/14:26
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillMainBean implements Serializable {

    private List<BillListBean> billList;

    public List<BillListBean> getBillList() {
        return billList;
    }

    public void setBillList(List<BillListBean> billList) {
        this.billList = billList;
    }

    public static class BillListBean implements IPieData, Serializable {
        /**
         * account_status : 1
         * confirm_amt : 0.0
         * cost_type : 2
         * diff_amt : 0.0
         * relative_month : 2018-08
         * total_ar : 0.0
         * udpater_time : 0000-00-00
         */

        private String account_status;
        private String confirm_amt;
        private String cost_type;
        private String diff_amt;
        private String relative_month;
        private String total_ar;
        private String udpater_time;

        public String getAccount_status() {
            return account_status;
        }

        public void setAccount_status(String account_status) {
            this.account_status = account_status;
        }

        public String getConfirm_amt() {
            return TextUtils.isEmpty(confirm_amt) ? "0" : confirm_amt;
        }

        public void setConfirm_amt(String confirm_amt) {
            this.confirm_amt = confirm_amt;
        }

        public String getCost_type() {
            return cost_type;
        }

        public void setCost_type(String cost_type) {
            this.cost_type = cost_type;
        }

        public String getDiff_amt() {
            return TextUtils.isEmpty(diff_amt) ? "0" : diff_amt;
        }

        public void setDiff_amt(String diff_amt) {
            this.diff_amt = diff_amt;
        }

        public String getRelative_month() {
            return relative_month;
        }

        public void setRelative_month(String relative_month) {
            this.relative_month = relative_month;
        }

        public String getTotal_ar() {
            return total_ar;
        }

        public void setTotal_ar(String total_ar) {
            this.total_ar = total_ar;
        }

        public String getUdpater_time() {
            return udpater_time;
        }

        public void setUdpater_time(String udpater_time) {
            this.udpater_time = udpater_time;
        }

        @Override
        public float getValue() {
            return Float.valueOf(total_ar);
        }

        @Override
        public String getLabelName() {
            return StringUtils.checkBillType(cost_type) + " " + NumberUtil.formitNumberTenthousand(Double.valueOf(total_ar));
        }
    }
}
