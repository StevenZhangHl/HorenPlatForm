package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/19/10:50
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillDetailBean {

    /**
     * pdList : {"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"account_bill_id":"1234567890qwerty","account_bill_lineid":"1234567890qwerty0001","beyond_amt":0,"bonus_amt":0,"cash_amt":0,"confirm_amt":100,"creater":"创建人","creater_time":"2018-10-15 18:23:09","cycle_amt1":0,"cycle_amt2":0,"cycle_date":"0","line_discount":0,"line_foregiftar":0,"line_rentar":0,"line_salear":0,"line_tmsar":0,"line_washar":0,"order_id":"21321321321","orderallot_id":"123412","remaid_amt":0,"repair_amt":0,"total_ar":100}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}
     */

    private PdListBean pdList;

    public PdListBean getPdList() {
        return pdList;
    }

    public void setPdList(PdListBean pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * endRow : 1
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"account_bill_id":"1234567890qwerty","account_bill_lineid":"1234567890qwerty0001","beyond_amt":0,"bonus_amt":0,"cash_amt":0,"confirm_amt":100,"creater":"创建人","creater_time":"2018-10-15 18:23:09","cycle_amt1":0,"cycle_amt2":0,"cycle_date":"0","line_discount":0,"line_foregiftar":0,"line_rentar":0,"line_salear":0,"line_tmsar":0,"line_washar":0,"order_id":"21321321321","orderallot_id":"123412","remaid_amt":0,"repair_amt":0,"total_ar":100}]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * navigatePages : 8
         * navigatepageNums : [1]
         * nextPage : 0
         * pageNum : 1
         * pageSize : 10
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

        public static class ListBean {
            /**
             * account_bill_id : 1234567890qwerty
             * account_bill_lineid : 1234567890qwerty0001
             * beyond_amt : 0
             * bonus_amt : 0
             * cash_amt : 0
             * confirm_amt : 100
             * creater : 创建人
             * creater_time : 2018-10-15 18:23:09
             * cycle_amt1 : 0
             * cycle_amt2 : 0
             * cycle_date : 0
             * line_discount : 0
             * line_foregiftar : 0
             * line_rentar : 0
             * line_salear : 0
             * line_tmsar : 0
             * line_washar : 0
             * order_id : 21321321321
             * orderallot_id : 123412
             * remaid_amt : 0
             * repair_amt : 0
             * total_ar : 100
             */

            private String account_bill_id;
            private String account_bill_lineid;
            private int beyond_amt;
            private int bonus_amt;
            private int cash_amt;
            private int confirm_amt;
            private String creater;
            private String creater_time;
            private int cycle_amt1;
            private int cycle_amt2;
            private String cycle_date;
            private int line_discount;
            private int line_foregiftar;
            private int line_rentar;
            private int line_salear;
            private int line_tmsar;
            private int line_washar;
            private String order_id;
            private String orderallot_id;
            private int remaid_amt;
            private int repair_amt;
            private int total_ar;

            public String getAccount_bill_id() {
                return account_bill_id;
            }

            public void setAccount_bill_id(String account_bill_id) {
                this.account_bill_id = account_bill_id;
            }

            public String getAccount_bill_lineid() {
                return account_bill_lineid;
            }

            public void setAccount_bill_lineid(String account_bill_lineid) {
                this.account_bill_lineid = account_bill_lineid;
            }

            public int getBeyond_amt() {
                return beyond_amt;
            }

            public void setBeyond_amt(int beyond_amt) {
                this.beyond_amt = beyond_amt;
            }

            public int getBonus_amt() {
                return bonus_amt;
            }

            public void setBonus_amt(int bonus_amt) {
                this.bonus_amt = bonus_amt;
            }

            public int getCash_amt() {
                return cash_amt;
            }

            public void setCash_amt(int cash_amt) {
                this.cash_amt = cash_amt;
            }

            public int getConfirm_amt() {
                return confirm_amt;
            }

            public void setConfirm_amt(int confirm_amt) {
                this.confirm_amt = confirm_amt;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
            }

            public String getCreater_time() {
                return creater_time;
            }

            public void setCreater_time(String creater_time) {
                this.creater_time = creater_time;
            }

            public int getCycle_amt1() {
                return cycle_amt1;
            }

            public void setCycle_amt1(int cycle_amt1) {
                this.cycle_amt1 = cycle_amt1;
            }

            public int getCycle_amt2() {
                return cycle_amt2;
            }

            public void setCycle_amt2(int cycle_amt2) {
                this.cycle_amt2 = cycle_amt2;
            }

            public String getCycle_date() {
                return cycle_date;
            }

            public void setCycle_date(String cycle_date) {
                this.cycle_date = cycle_date;
            }

            public int getLine_discount() {
                return line_discount;
            }

            public void setLine_discount(int line_discount) {
                this.line_discount = line_discount;
            }

            public int getLine_foregiftar() {
                return line_foregiftar;
            }

            public void setLine_foregiftar(int line_foregiftar) {
                this.line_foregiftar = line_foregiftar;
            }

            public int getLine_rentar() {
                return line_rentar;
            }

            public void setLine_rentar(int line_rentar) {
                this.line_rentar = line_rentar;
            }

            public int getLine_salear() {
                return line_salear;
            }

            public void setLine_salear(int line_salear) {
                this.line_salear = line_salear;
            }

            public int getLine_tmsar() {
                return line_tmsar;
            }

            public void setLine_tmsar(int line_tmsar) {
                this.line_tmsar = line_tmsar;
            }

            public int getLine_washar() {
                return line_washar;
            }

            public void setLine_washar(int line_washar) {
                this.line_washar = line_washar;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrderallot_id() {
                return orderallot_id;
            }

            public void setOrderallot_id(String orderallot_id) {
                this.orderallot_id = orderallot_id;
            }

            public int getRemaid_amt() {
                return remaid_amt;
            }

            public void setRemaid_amt(int remaid_amt) {
                this.remaid_amt = remaid_amt;
            }

            public int getRepair_amt() {
                return repair_amt;
            }

            public void setRepair_amt(int repair_amt) {
                this.repair_amt = repair_amt;
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
