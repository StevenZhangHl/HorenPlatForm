package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/16/15:10
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class EvaluationBean  {

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * eval_attitude : 5
         * eval_logistics : 5
         * eval_product : 5
         * eval_remarks :
         * eval_status : 1
         * order_id : ZCN8752031810120J-2
         */

        private String eval_attitude;
        private String eval_logistics;
        private String eval_product;
        private String eval_remarks;
        private String eval_status;
        private String order_id;

        public String getEval_attitude() {
            return eval_attitude;
        }

        public void setEval_attitude(String eval_attitude) {
            this.eval_attitude = eval_attitude;
        }

        public String getEval_logistics() {
            return eval_logistics;
        }

        public void setEval_logistics(String eval_logistics) {
            this.eval_logistics = eval_logistics;
        }

        public String getEval_product() {
            return eval_product;
        }

        public void setEval_product(String eval_product) {
            this.eval_product = eval_product;
        }

        public String getEval_remarks() {
            return eval_remarks;
        }

        public void setEval_remarks(String eval_remarks) {
            this.eval_remarks = eval_remarks;
        }

        public String getEval_status() {
            return eval_status;
        }

        public void setEval_status(String eval_status) {
            this.eval_status = eval_status;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }
}
