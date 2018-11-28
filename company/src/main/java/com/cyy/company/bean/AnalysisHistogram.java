package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/12/09:45
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class AnalysisHistogram {

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * date : 2018-06
         * number : 0
         */

        private String date;
        private int number;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
