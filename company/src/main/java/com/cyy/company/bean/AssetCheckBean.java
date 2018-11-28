package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/06/10:48
 * @description :资产分布一键盘点
 * @github :https://github.com/chenyy0708
 */
public class AssetCheckBean {

    /**
     * checkList : [{"check_content":"智能RTP企业盘点命令","check_endtime":"2018-11-06 12:05:10","check_id":"CK000000000022","check_starttime":"2018-11-06 10:05:10","check_status":"0","check_type":"1","create_date":"2018-11-06 10:05:10","owner_id":"CN02122N"}]
     * nums_check : 1
     */

    private int nums_check;
    private List<CheckListBean> checkList;

    public int getNums_check() {
        return nums_check;
    }

    public void setNums_check(int nums_check) {
        this.nums_check = nums_check;
    }

    public List<CheckListBean> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<CheckListBean> checkList) {
        this.checkList = checkList;
    }

    public static class CheckListBean {
        /**
         * check_content : 智能RTP企业盘点命令
         * check_endtime : 2018-11-06 12:05:10
         * check_id : CK000000000022
         * check_starttime : 2018-11-06 10:05:10
         * check_status : 0
         * check_type : 1
         * create_date : 2018-11-06 10:05:10
         * owner_id : CN02122N
         */

        private String check_content;
        private String check_endtime;
        private String check_id;
        private String check_starttime;
        private String check_status;
        private String check_type;
        private String create_date;
        private String owner_id;

        public String getCheck_content() {
            return check_content;
        }

        public void setCheck_content(String check_content) {
            this.check_content = check_content;
        }

        public String getCheck_endtime() {
            return check_endtime;
        }

        public void setCheck_endtime(String check_endtime) {
            this.check_endtime = check_endtime;
        }

        public String getCheck_id() {
            return check_id;
        }

        public void setCheck_id(String check_id) {
            this.check_id = check_id;
        }

        public String getCheck_starttime() {
            return check_starttime;
        }

        public void setCheck_starttime(String check_starttime) {
            this.check_starttime = check_starttime;
        }

        public String getCheck_status() {
            return check_status;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }

        public String getCheck_type() {
            return check_type;
        }

        public void setCheck_type(String check_type) {
            this.check_type = check_type;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(String owner_id) {
            this.owner_id = owner_id;
        }
    }
}
