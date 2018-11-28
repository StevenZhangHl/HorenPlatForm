package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/12/09:38
 * @description :分析:资产在租量统计
 * @github :https://github.com/chenyy0708
 */
public class AnalysisLineChart {



    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * list : [{"date":"2018-06","number":0},{"date":"2018-07","number":0},{"date":"2018-08","number":0},{"date":"2018-09","number":0},{"date":"2018-10","number":2},{"date":"2018-11","number":21}]
         * type : IF1040
         */

        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        private String type;
        private List<ListBean> list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
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
}
