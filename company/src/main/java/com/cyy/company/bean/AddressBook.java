package com.cyy.company.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/17:06
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class AddressBook implements Serializable {

    /**
     * pdList : {"endRow":2,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"flag_defaultorg":"0","master_companyid":"CN875203","master_companyname":"京东A","org_address":"京东A京东A京东A","org_consignee":"京东","org_consigneetel":"13111111111","org_id":"CN87520301","org_name":"京东A","partner_relation":"1"},{"flag_defaultorg":"1","master_companyid":"CN875203","master_companyname":"京东A","org_address":"京东A的上游","org_consignee":"京东A的上游","org_consigneetel":"13821221111","org_id":"CN87520302","org_name":"京东A的上游","partner_relation":"1"}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":2,"startRow":1,"total":2}
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
         * endRow : 2
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"flag_defaultorg":"0","master_companyid":"CN875203","master_companyname":"京东A","org_address":"京东A京东A京东A","org_consignee":"京东","org_consigneetel":"13111111111","org_id":"CN87520301","org_name":"京东A","partner_relation":"1"},{"flag_defaultorg":"1","master_companyid":"CN875203","master_companyname":"京东A","org_address":"京东A的上游","org_consignee":"京东A的上游","org_consigneetel":"13821221111","org_id":"CN87520302","org_name":"京东A的上游","partner_relation":"1"}]
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
        private List<ListBean> list;

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


        public static class ListBean implements Serializable{
            /**
             * flag_defaultorg : 0
             * master_companyid : CN875203
             * master_companyname : 京东A
             * org_address : 京东A京东A京东A
             * org_consignee : 京东
             * org_consigneetel : 13111111111
             * org_id : CN87520301
             * org_name : 京东A
             * partner_relation : 1
             */

            private String flag_defaultorg;
            private String master_companyid;
            private String master_companyname;
            private String org_address;
            private String org_consignee;
            private String org_consigneetel;
            private String org_id;
            private String org_name;
            private String partner_relation;

            public String getFlag_defaultorg() {
                return flag_defaultorg;
            }

            public void setFlag_defaultorg(String flag_defaultorg) {
                this.flag_defaultorg = flag_defaultorg;
            }

            public String getMaster_companyid() {
                return master_companyid;
            }

            public void setMaster_companyid(String master_companyid) {
                this.master_companyid = master_companyid;
            }

            public String getMaster_companyname() {
                return master_companyname;
            }

            public void setMaster_companyname(String master_companyname) {
                this.master_companyname = master_companyname;
            }

            public String getOrg_address() {
                return org_address;
            }

            public void setOrg_address(String org_address) {
                this.org_address = org_address;
            }

            public String getOrg_consignee() {
                return org_consignee;
            }

            public void setOrg_consignee(String org_consignee) {
                this.org_consignee = org_consignee;
            }

            public String getOrg_consigneetel() {
                return org_consigneetel;
            }

            public void setOrg_consigneetel(String org_consigneetel) {
                this.org_consigneetel = org_consigneetel;
            }

            public String getOrg_id() {
                return org_id;
            }

            public void setOrg_id(String org_id) {
                this.org_id = org_id;
            }

            public String getOrg_name() {
                return org_name;
            }

            public void setOrg_name(String org_name) {
                this.org_name = org_name;
            }

            public String getPartner_relation() {
                return partner_relation;
            }

            public void setPartner_relation(String partner_relation) {
                this.partner_relation = partner_relation;
            }
        }
    }
}
