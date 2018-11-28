package com.cyy.company.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/15:23
 * @description :账号管理
 * @github :https://github.com/chenyy0708
 */
public class AccountManager implements Serializable {

    /**
     * customer_users : {"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"company_class":"2","company_id":"CN875203","company_name":"京东A","create_date":"2018-09-26 14:32:11","facility_type":"2","flag_admin":"0","flag_data":"1","lock_status":"0","org_id":"CN87520301","org_name":"京东A","role_id":"EOS_USER","role_name":"企业操作员","status":"2","user_id":"25749959802373340","user_mail":"","user_mobile":"18274728888","user_name":"JDCZY001","user_nickname":"京东操作员"}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}
     */

    private CustomerUsersBean customer_users;

    public CustomerUsersBean getCustomer_users() {
        return customer_users;
    }

    public void setCustomer_users(CustomerUsersBean customer_users) {
        this.customer_users = customer_users;
    }

    public static class CustomerUsersBean implements Serializable{
        /**
         * endRow : 1
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"company_class":"2","company_id":"CN875203","company_name":"京东A","create_date":"2018-09-26 14:32:11","facility_type":"2","flag_admin":"0","flag_data":"1","lock_status":"0","org_id":"CN87520301","org_name":"京东A","role_id":"EOS_USER","role_name":"企业操作员","status":"2","user_id":"25749959802373340","user_mail":"","user_mobile":"18274728888","user_name":"JDCZY001","user_nickname":"京东操作员"}]
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
             * company_class : 2
             * company_id : CN875203
             * company_name : 京东A
             * create_date : 2018-09-26 14:32:11
             * facility_type : 2
             * flag_admin : 0
             * flag_data : 1
             * lock_status : 0
             * org_id : CN87520301
             * org_name : 京东A
             * role_id : EOS_USER
             * role_name : 企业操作员
             * status : 2
             * user_id : 25749959802373340
             * user_mail :
             * user_mobile : 18274728888
             * user_name : JDCZY001
             * user_nickname : 京东操作员
             */

            private String company_class;
            private String company_id;
            private String company_name;
            private String create_date;
            private String facility_type;
            private String flag_admin;
            private String flag_data;
            private String lock_status;
            private String org_id;
            private String org_name;
            private String role_id;
            private String role_name;
            private String status;
            private String user_id;
            private String user_mail;
            private String user_mobile;
            private String user_name;
            private String user_nickname;

            public String getCompany_class() {
                return company_class;
            }

            public void setCompany_class(String company_class) {
                this.company_class = company_class;
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

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getFacility_type() {
                return facility_type;
            }

            public void setFacility_type(String facility_type) {
                this.facility_type = facility_type;
            }

            public String getFlag_admin() {
                return flag_admin;
            }

            public void setFlag_admin(String flag_admin) {
                this.flag_admin = flag_admin;
            }

            public String getFlag_data() {
                return flag_data;
            }

            public void setFlag_data(String flag_data) {
                this.flag_data = flag_data;
            }

            public String getLock_status() {
                return lock_status;
            }

            public void setLock_status(String lock_status) {
                this.lock_status = lock_status;
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

            public String getRole_id() {
                return role_id;
            }

            public void setRole_id(String role_id) {
                this.role_id = role_id;
            }

            public String getRole_name() {
                return role_name;
            }

            public void setRole_name(String role_name) {
                this.role_name = role_name;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_mail() {
                return user_mail;
            }

            public void setUser_mail(String user_mail) {
                this.user_mail = user_mail;
            }

            public String getUser_mobile() {
                return user_mobile;
            }

            public void setUser_mobile(String user_mobile) {
                this.user_mobile = user_mobile;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }
        }
    }
}
