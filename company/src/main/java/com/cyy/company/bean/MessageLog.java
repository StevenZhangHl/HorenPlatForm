package com.cyy.company.bean;

/**
 * @author :ChenYangYi
 * @date :2018/11/20/13:45
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class MessageLog {

    /**
     * pageInfo : {"mobile":5,"msg":26772}
     */

    private PageInfoBean pageInfo;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class PageInfoBean {
        /**
         * mobile : 5
         * msg : 26772
         */

        private int mobile;
        private int msg;
        private int count_point = 0;

        public int getCount() {
            return count_point;
        }


        public void setCount(int count) {
            this.count_point = count;
        }

        public int getMobile() {
            return mobile;
        }

        public void setMobile(int mobile) {
            this.mobile = mobile;
        }

        public int getMsg() {
            return msg;
        }

        public void setMsg(int msg) {
            this.msg = msg;
        }
    }
}
