package com.horen.service.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/14/16:09
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillTransportBean implements Serializable{

    /**
     * pageInfo : {"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"account_id":"CN512201180914S0103","account_status":"1","confirm_amt":0,"create_time":"0000-00-00","diff_amt":33,"orderallot_id":"Z18091410","total_ar":33}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":100,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}
     */

    private PageInfoBean pageInfo;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class PageInfoBean implements Serializable{
        /**
         * endRow : 1
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"account_id":"CN512201180914S0103","account_status":"1","confirm_amt":0,"create_time":"0000-00-00","diff_amt":33,"orderallot_id":"Z18091410","total_ar":33}]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * navigatePages : 8
         * navigatepageNums : [1]
         * nextPage : 0
         * pageNum : 1
         * pageSize : 100
         * pages : 1
         * prePage : 0
         * size : 1
         * startRow : 1
         * total : 1
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

        public static class ListBean implements Serializable{
            /**
             * account_id : CN512201180914S0103
             * account_status : 1
             * confirm_amt : 0
             * create_time : 0000-00-00
             * diff_amt : 33
             * orderallot_id : Z18091410
             * total_ar : 33
             */

            private String account_id;
            private String account_status;
            private int confirm_amt;
            private String create_time;
            private int diff_amt;
            private String orderallot_id;
            private int total_ar;

            public String getAccount_id() {
                return account_id;
            }

            public void setAccount_id(String account_id) {
                this.account_id = account_id;
            }

            public String getAccount_status() {
                return account_status;
            }

            public void setAccount_status(String account_status) {
                this.account_status = account_status;
            }

            public int getConfirm_amt() {
                return confirm_amt;
            }

            public void setConfirm_amt(int confirm_amt) {
                this.confirm_amt = confirm_amt;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getDiff_amt() {
                return diff_amt;
            }

            public void setDiff_amt(int diff_amt) {
                this.diff_amt = diff_amt;
            }

            public String getOrderallot_id() {
                return orderallot_id;
            }

            public void setOrderallot_id(String orderallot_id) {
                this.orderallot_id = orderallot_id;
            }

            public int getTotal_ar() {
                return total_ar;
            }

            public void setTotal_ar(int total_ar) {
                this.total_ar = total_ar;
            }
        }
    }
}
