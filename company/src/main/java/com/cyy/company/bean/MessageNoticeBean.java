package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/20/13:48
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class MessageNoticeBean {

    /**
     * pdList : {"endRow":10,"firstPage":1,"hasNextPage":true,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":false,"lastPage":8,"list":[{"STATUS":"1","msg_content":"企业 天津弗英认证已被批准.","msg_date":"2018-04-02 09:11:02","msg_title":"","msg_type":"108","receiver":"1212@qq.com","receiver_id":"HU00001667"}],"navigateFirstPage":1,"navigateLastPage":8,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"nextPage":2,"pageNum":1,"pageSize":10,"pages":2678,"prePage":0,"size":10,"startRow":1,"total":26772}
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
         * endRow : 10
         * firstPage : 1
         * hasNextPage : true
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : false
         * lastPage : 8
         * list : [{"STATUS":"1","msg_content":"企业 天津弗英认证已被批准.","msg_date":"2018-04-02 09:11:02","msg_title":"","msg_type":"108","receiver":"1212@qq.com","receiver_id":"HU00001667"}]
         * navigateFirstPage : 1
         * navigateLastPage : 8
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6,7,8]
         * nextPage : 2
         * pageNum : 1
         * pageSize : 10
         * pages : 2678
         * prePage : 0
         * size : 10
         * startRow : 1
         * total : 26772
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
             * STATUS : 1
             * msg_content : 企业 天津弗英认证已被批准.
             * msg_date : 2018-04-02 09:11:02
             * msg_title :
             * msg_type : 108
             * receiver : 1212@qq.com
             * receiver_id : HU00001667
             */

            private String STATUS;
            private String msg_content;
            private String msg_date;
            private String msg_title;
            private String msg_type;
            private String receiver;
            private String receiver_id;
            private String msg_id;

            public String getMsg_id() {
                return msg_id;
            }

            public void setMsg_id(String msg_id) {
                this.msg_id = msg_id;
            }

            public String getSTATUS() {
                return STATUS;
            }

            public void setSTATUS(String STATUS) {
                this.STATUS = STATUS;
            }

            public String getMsg_content() {
                return msg_content;
            }

            public void setMsg_content(String msg_content) {
                this.msg_content = msg_content;
            }

            public String getMsg_date() {
                return msg_date;
            }

            public void setMsg_date(String msg_date) {
                this.msg_date = msg_date;
            }

            public String getMsg_title() {
                return msg_title;
            }

            public void setMsg_title(String msg_title) {
                this.msg_title = msg_title;
            }

            public String getMsg_type() {
                return msg_type;
            }

            public void setMsg_type(String msg_type) {
                this.msg_type = msg_type;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getReceiver_id() {
                return receiver_id;
            }

            public void setReceiver_id(String receiver_id) {
                this.receiver_id = receiver_id;
            }
        }
    }
}
