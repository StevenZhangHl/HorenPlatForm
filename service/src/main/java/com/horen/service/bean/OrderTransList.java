package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/15:29
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderTransList {

    /**
     * pageInfo : {"endRow":3,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"audit_time":"2018-08-14 02:08:00","end_time":"0000-00-00 12:00:00","order_type":"11","orderallot_id":"1","time_difference":"0"},{"audit_time":"2018-08-14 03:08:09","end_time":"0000-00-00 12:00:00","order_type":"99","orderallot_id":"11111","time_difference":"0"},{"audit_time":"2018-08-14 03:08:09","end_time":"0000-00-00 12:00:00","order_type":"99","orderallot_id":"22222","time_difference":"0"}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":3,"startRow":1,"total":3}
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
         * endRow : 3
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"audit_time":"2018-08-14 02:08:00","end_time":"0000-00-00 12:00:00","order_type":"11","orderallot_id":"1","time_difference":"0"},{"audit_time":"2018-08-14 03:08:09","end_time":"0000-00-00 12:00:00","order_type":"99","orderallot_id":"11111","time_difference":"0"},{"audit_time":"2018-08-14 03:08:09","end_time":"0000-00-00 12:00:00","order_type":"99","orderallot_id":"22222","time_difference":"0"}]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * navigatePages : 8
         * navigatepageNums : [1]
         * nextPage : 0
         * pageNum : 1
         * pageSize : 10
         * pages : 1
         * prePage : 0
         * size : 3
         * startRow : 1
         * total : 3
         */

        private int endRow;
        private int firstPage;
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private int lastPage;
        private int navigateFirstPage;
        private int navigateLastPage;
        private int navigatePages;
        private int nextPage;
        private int pageNum;
        private int pageSize;
        private int pages;
        private int prePage;
        private int size;
        private int startRow;
        private int total;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * audit_time : 2018-08-14 02:08:00
             * end_time : 0000-00-00 12:00:00
             * order_type : 11
             * orderallot_id : 1
             * time_difference : 0
             */

            private String audit_time;
            private String end_time;
            private String order_type;
            private String orderallot_id;
            private String time_difference;

            public String getAudit_time() {
                return audit_time;
            }

            public void setAudit_time(String audit_time) {
                this.audit_time = audit_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getOrderallot_id() {
                return orderallot_id;
            }

            public void setOrderallot_id(String orderallot_id) {
                this.orderallot_id = orderallot_id;
            }

            /**
             * 0天 默认返回1天
             */
            public String getTime_difference() {
                return Integer.valueOf(time_difference) == 0 ? "1" : time_difference;
            }

            public void setTime_difference(String time_difference) {
                this.time_difference = time_difference;
            }
        }
    }
}
