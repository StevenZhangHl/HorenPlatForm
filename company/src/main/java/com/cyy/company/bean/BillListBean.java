package com.cyy.company.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/19/10:50
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillListBean  implements Serializable {

    /**
     * pdList : {"endRow":0,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"account_bill_id":"1111121321sdsdsf","account_time":"2018-10-15 17:10:36","account_type":"1","begin_last_month":"2018-09-01","company_id":"CN512201","company_name":"箱箱物流","confirm_amt":100,"creater_time":"2018-10-15 17:10:42","end_last_month":"2018-09-30","total_ar":100}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":1,"pages":1,"prePage":0,"size":1,"startRow":0,"total":1}
     */

    private PdListBean pdList;

    public PdListBean getPdList() {
        return pdList;
    }

    public void setPdList(PdListBean pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean implements Serializable{
        /**
         * endRow : 0
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"account_bill_id":"1111121321sdsdsf","account_time":"2018-10-15 17:10:36","account_type":"1","begin_last_month":"2018-09-01","company_id":"CN512201","company_name":"箱箱物流","confirm_amt":100,"creater_time":"2018-10-15 17:10:42","end_last_month":"2018-09-30","total_ar":100}]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * navigatePages : 8
         * navigatepageNums : [1]
         * nextPage : 0
         * pageNum : 1
         * pageSize : 1
         * pages : 1
         * prePage : 0
         * size : 1
         * startRow : 0
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
             * account_bill_id : 1111121321sdsdsf
             * account_time : 2018-10-15 17:10:36
             * account_type : 1
             * begin_last_month : 2018-09-01
             * company_id : CN512201
             * company_name : 箱箱物流
             * confirm_amt : 100
             * creater_time : 2018-10-15 17:10:42
             * end_last_month : 2018-09-30
             * total_ar : 100
             */

            private String account_bill_id;
            private String account_time;
            private String account_type;
            private String begin_last_month;
            private String company_id;
            private String company_name;
            private int confirm_amt;
            private String creater_time;
            private String end_last_month;
            private int total_ar;

            public String getAccount_bill_id() {
                return account_bill_id;
            }

            public void setAccount_bill_id(String account_bill_id) {
                this.account_bill_id = account_bill_id;
            }

            public String getAccount_time() {
                return account_time;
            }

            public void setAccount_time(String account_time) {
                this.account_time = account_time;
            }

            public String getAccount_type() {
                return account_type;
            }

            public void setAccount_type(String account_type) {
                this.account_type = account_type;
            }

            public String getBegin_last_month() {
                return begin_last_month;
            }

            public void setBegin_last_month(String begin_last_month) {
                this.begin_last_month = begin_last_month;
            }

            public String getCompany_id() {
                return company_id;
            }

            public void setCompany_id(String company_id) {
                this.company_id = company_id;
            }

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public int getConfirm_amt() {
                return confirm_amt;
            }

            public void setConfirm_amt(int confirm_amt) {
                this.confirm_amt = confirm_amt;
            }

            public String getCreater_time() {
                return creater_time;
            }

            public void setCreater_time(String creater_time) {
                this.creater_time = creater_time;
            }

            public String getEnd_last_month() {
                return end_last_month;
            }

            public void setEnd_last_month(String end_last_month) {
                this.end_last_month = end_last_month;
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
