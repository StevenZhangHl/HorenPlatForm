package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/18/17:37
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class MsgLogisticsBean {

    /**
     * pdList : {"endRow":10,"firstPage":1,"hasNextPage":true,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":false,"lastPage":8,"list":[{"box_orderqty":1,"box_receiveqty":0,"create_date":"2018-10-26","create_date_time":"2018-10-26 09:48:56","log":"取消租还箱订单","log_id":"25803931720352170","material_orderqty":0,"material_receiveqty":0,"movement":"8","order_id":"ZCN02121R1810250D","order_type":"","status":"1"},{"box_orderqty":2,"box_receiveqty":2,"create_date":"2018-10-25","create_date_time":"2018-10-25 12:51:09","log":"创建续租还箱订单","log_id":"25803931720352153","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"SCN02121R1810250J-1","order_type":"12","status":"1"},{"box_orderqty":2,"box_receiveqty":2,"create_date":"2018-10-25","create_date_time":"2018-10-25 12:51:09","log":"创建续租租箱订单","log_id":"25803931720352155","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"ZCN02121R1810250G-2","order_type":"11","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:31:14","log":"创建续租还箱订单","log_id":"25803931720352148","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"SCN02121R1810250I-1","order_type":"12","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:31:14","log":"创建续租租箱订单","log_id":"25803931720352150","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"ZCN02121R1810250F-2","order_type":"11","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:30:52","log":"创建续租还箱订单","log_id":"25803931720352143","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"SCN02121R1810250H-1","order_type":"12","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:30:52","log":"创建续租租箱订单","log_id":"25803931720352145","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"ZCN02121R1810250E-2","order_type":"11","status":"1"},{"box_orderqty":1,"box_receiveqty":0,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:30:39","log":"创建租还箱订单","log_id":"25803931720352141","material_orderqty":0,"material_receiveqty":0,"movement":"0","order_id":"SCN02121R1810250G","order_type":"12","status":"1"},{"box_orderqty":1,"box_receiveqty":0,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:30:25","log":"创建租还箱订单","log_id":"25803931720352139","material_orderqty":0,"material_receiveqty":0,"movement":"0","order_id":"ZCN02121R1810250D","order_type":"11","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:27:44","log":"创建续租还箱订单","log_id":"25803931720352134","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"SCN02121R1810250F-1","order_type":"12","status":"1"}],"navigateFirstPage":1,"navigateLastPage":8,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"nextPage":2,"pageNum":1,"pageSize":10,"pages":8,"prePage":0,"size":10,"startRow":1,"total":72}
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
         * list : [{"box_orderqty":1,"box_receiveqty":0,"create_date":"2018-10-26","create_date_time":"2018-10-26 09:48:56","log":"取消租还箱订单","log_id":"25803931720352170","material_orderqty":0,"material_receiveqty":0,"movement":"8","order_id":"ZCN02121R1810250D","order_type":"","status":"1"},{"box_orderqty":2,"box_receiveqty":2,"create_date":"2018-10-25","create_date_time":"2018-10-25 12:51:09","log":"创建续租还箱订单","log_id":"25803931720352153","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"SCN02121R1810250J-1","order_type":"12","status":"1"},{"box_orderqty":2,"box_receiveqty":2,"create_date":"2018-10-25","create_date_time":"2018-10-25 12:51:09","log":"创建续租租箱订单","log_id":"25803931720352155","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"ZCN02121R1810250G-2","order_type":"11","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:31:14","log":"创建续租还箱订单","log_id":"25803931720352148","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"SCN02121R1810250I-1","order_type":"12","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:31:14","log":"创建续租租箱订单","log_id":"25803931720352150","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"ZCN02121R1810250F-2","order_type":"11","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:30:52","log":"创建续租还箱订单","log_id":"25803931720352143","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"SCN02121R1810250H-1","order_type":"12","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:30:52","log":"创建续租租箱订单","log_id":"25803931720352145","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"ZCN02121R1810250E-2","order_type":"11","status":"1"},{"box_orderqty":1,"box_receiveqty":0,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:30:39","log":"创建租还箱订单","log_id":"25803931720352141","material_orderqty":0,"material_receiveqty":0,"movement":"0","order_id":"SCN02121R1810250G","order_type":"12","status":"1"},{"box_orderqty":1,"box_receiveqty":0,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:30:25","log":"创建租还箱订单","log_id":"25803931720352139","material_orderqty":0,"material_receiveqty":0,"movement":"0","order_id":"ZCN02121R1810250D","order_type":"11","status":"1"},{"box_orderqty":1,"box_receiveqty":1,"create_date":"2018-10-25","create_date_time":"2018-10-25 11:27:44","log":"创建续租还箱订单","log_id":"25803931720352134","material_orderqty":0,"material_receiveqty":0,"movement":"32","order_id":"SCN02121R1810250F-1","order_type":"12","status":"1"}]
         * navigateFirstPage : 1
         * navigateLastPage : 8
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6,7,8]
         * nextPage : 2
         * pageNum : 1
         * pageSize : 10
         * pages : 8
         * prePage : 0
         * size : 10
         * startRow : 1
         * total : 72
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
             * box_orderqty : 1
             * box_receiveqty : 0
             * create_date : 2018-10-26
             * create_date_time : 2018-10-26 09:48:56
             * log : 取消租还箱订单
             * log_id : 25803931720352170
             * material_orderqty : 0
             * material_receiveqty : 0
             * movement : 8
             * order_id : ZCN02121R1810250D
             * order_type :
             * status : 1
             */


            private String product_photo;

            public String getProduct_photo() {
                return product_photo;
            }

            public void setProduct_photo(String product_photo) {
                this.product_photo = product_photo;
            }

            private int box_orderqty;
            private int box_receiveqty;
            private String create_date;
            private String create_date_time;
            private String log;
            private String log_id;
            private int material_orderqty;
            private int material_receiveqty;
            private String movement;
            private String order_id;
            private String order_type;
            private String status;

            public int getBox_orderqty() {
                return box_orderqty;
            }

            public void setBox_orderqty(int box_orderqty) {
                this.box_orderqty = box_orderqty;
            }

            public int getBox_receiveqty() {
                return box_receiveqty;
            }

            public void setBox_receiveqty(int box_receiveqty) {
                this.box_receiveqty = box_receiveqty;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getCreate_date_time() {
                return create_date_time;
            }

            public void setCreate_date_time(String create_date_time) {
                this.create_date_time = create_date_time;
            }

            public String getLog() {
                return log;
            }

            public void setLog(String log) {
                this.log = log;
            }

            public String getLog_id() {
                return log_id;
            }

            public void setLog_id(String log_id) {
                this.log_id = log_id;
            }

            public int getMaterial_orderqty() {
                return material_orderqty;
            }

            public void setMaterial_orderqty(int material_orderqty) {
                this.material_orderqty = material_orderqty;
            }

            public int getMaterial_receiveqty() {
                return material_receiveqty;
            }

            public void setMaterial_receiveqty(int material_receiveqty) {
                this.material_receiveqty = material_receiveqty;
            }

            public String getMovement() {
                return movement;
            }

            public void setMovement(String movement) {
                this.movement = movement;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
