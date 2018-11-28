package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/23/13:09
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class RepairListDetailBean {


    /**
     * pageInfo : {"endRow":2,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"consumablesList":[{"product_id":"12.0401.001","product_name":"33333","product_type":"IF1040","qty":"1","warehouse_qty":55552}],"create_time":"2018-08-22","ctnr_sn":"PI50103","is_person":"0","positionList":[{"img":"http://61.153.224.202:9106/upload/photo/2018-08-22-6c7e4f28-54c8-46ea-a447-46c97e45f76b.jpeg","is_agree":"0","position":"222","position_id":"333","remark":"","service_lineid":"4714e1408ea244b88c645043f28fa1d0"}],"product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_type":"IF1040","remark":"500","service_id":"01","service_qty":1,"service_status":"4","service_type":"0","update_time":"2018-08-22"},{"consumablesList":[{"product_id":"12.0401.001","product_name":"33333","product_type":"IF1040","qty":"1","warehouse_qty":55552}],"create_time":"2018-08-22","ctnr_sn":"PI50103","is_person":"1","positionList":[{"img":"http://61.153.224.202:9106/upload/photo/2018-08-22-6c7e4f28-54c8-46ea-a447-46c97e45f76b.jpeg","is_agree":"0","position":"222","position_id":"333","remark":"","service_lineid":"82b75bd80b31488c86d9e0fdff069d04"}],"product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_type":"IF1040","remark":"500","service_id":"02","service_qty":1,"service_status":"0","service_type":"0","update_time":"0000-00-00"}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":2,"startRow":1,"total":2}
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
         * endRow : 2
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"consumablesList":[{"product_id":"12.0401.001","product_name":"33333","product_type":"IF1040","qty":"1","warehouse_qty":55552}],"create_time":"2018-08-22","ctnr_sn":"PI50103","is_person":"0","positionList":[{"img":"http://61.153.224.202:9106/upload/photo/2018-08-22-6c7e4f28-54c8-46ea-a447-46c97e45f76b.jpeg","is_agree":"0","position":"222","position_id":"333","remark":"","service_lineid":"4714e1408ea244b88c645043f28fa1d0"}],"product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_type":"IF1040","remark":"500","service_id":"01","service_qty":1,"service_status":"4","service_type":"0","update_time":"2018-08-22"},{"consumablesList":[{"product_id":"12.0401.001","product_name":"33333","product_type":"IF1040","qty":"1","warehouse_qty":55552}],"create_time":"2018-08-22","ctnr_sn":"PI50103","is_person":"1","positionList":[{"img":"http://61.153.224.202:9106/upload/photo/2018-08-22-6c7e4f28-54c8-46ea-a447-46c97e45f76b.jpeg","is_agree":"0","position":"222","position_id":"333","remark":"","service_lineid":"82b75bd80b31488c86d9e0fdff069d04"}],"product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_type":"IF1040","remark":"500","service_id":"02","service_qty":1,"service_status":"0","service_type":"0","update_time":"0000-00-00"}]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * navigatePages : 8
         * navigatepageNums : [1]
         * nextPage : 0
         * pageNum : 1
         * pageSize : 10
         * pages : 1
         * prePage : 0
         * size : 2
         * startRow : 1
         * total : 2
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
        private List<RepairDetailBean.ServiceListBean> list;
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

        public List<RepairDetailBean.ServiceListBean> getList() {
            return list;
        }

        public void setList(List<RepairDetailBean.ServiceListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }
    }
}
