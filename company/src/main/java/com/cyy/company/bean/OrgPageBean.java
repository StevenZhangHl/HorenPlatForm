package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/12/09:53
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrgPageBean {

    /**
     * pdList : {"endRow":4,"firstPage":1,"hasNextPage":true,"hasPreviousPage":false,"isFirstPage":false,"isLastPage":false,"lastPage":1,"list":[{"flag_road":"0","list":[{"empty_qty":3,"en_route_qty":9,"full_qty":24,"product_type":"KF975","total_qty":27}],"org_id":"HC00001632","org_latitude":34.482432,"org_longitude":109.020458,"org_name":"陕西通汇汽车物流有限公司"}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":1,"pageNum":0,"pageSize":10,"pages":1,"prePage":0,"size":4,"startRow":1,"total":4}
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
         * endRow : 4
         * firstPage : 1
         * hasNextPage : true
         * hasPreviousPage : false
         * isFirstPage : false
         * isLastPage : false
         * lastPage : 1
         * list : [{"flag_road":"0","list":[{"empty_qty":3,"en_route_qty":9,"full_qty":24,"product_type":"KF975","total_qty":27}],"org_id":"HC00001632","org_latitude":34.482432,"org_longitude":109.020458,"org_name":"陕西通汇汽车物流有限公司"}]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * navigatePages : 8
         * navigatepageNums : [1]
         * nextPage : 1
         * pageNum : 0
         * pageSize : 10
         * pages : 1
         * prePage : 0
         * size : 4
         * startRow : 1
         * total : 4
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
        private List<ListBeanX> list;

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

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public static class ListBeanX {
            /**
             * flag_road : 0
             * list : [{"empty_qty":3,"en_route_qty":9,"full_qty":24,"product_type":"KF975","total_qty":27}]
             * org_id : HC00001632
             * org_latitude : 34.482432
             * org_longitude : 109.020458
             * org_name : 陕西通汇汽车物流有限公司
             */

            private String flag_road;
            private String org_id;
            private double org_latitude;
            private double org_longitude;
            private String org_name;
            private List<ListBean> list;

            private boolean isExpendle;

            public boolean isExpendle() {
                return isExpendle;
            }

            public void setExpendle(boolean expendle) {
                isExpendle = expendle;
            }

            public String getFlag_road() {
                return flag_road;
            }

            public void setFlag_road(String flag_road) {
                this.flag_road = flag_road;
            }

            public String getOrg_id() {
                return org_id;
            }

            public void setOrg_id(String org_id) {
                this.org_id = org_id;
            }

            public double getOrg_latitude() {
                return org_latitude;
            }

            public void setOrg_latitude(double org_latitude) {
                this.org_latitude = org_latitude;
            }

            public double getOrg_longitude() {
                return org_longitude;
            }

            public void setOrg_longitude(double org_longitude) {
                this.org_longitude = org_longitude;
            }

            public String getOrg_name() {
                return org_name;
            }

            public void setOrg_name(String org_name) {
                this.org_name = org_name;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * empty_qty : 3
                 * en_route_qty : 9
                 * full_qty : 24
                 * product_type : KF975
                 * total_qty : 27
                 */

                private int empty_qty;
                private int en_route_qty;
                private int full_qty;
                private String product_type;
                private int total_qty;

                public int getEmpty_qty() {
                    return empty_qty;
                }

                public void setEmpty_qty(int empty_qty) {
                    this.empty_qty = empty_qty;
                }

                public int getEn_route_qty() {
                    return en_route_qty;
                }

                public void setEn_route_qty(int en_route_qty) {
                    this.en_route_qty = en_route_qty;
                }

                public int getFull_qty() {
                    return full_qty;
                }

                public void setFull_qty(int full_qty) {
                    this.full_qty = full_qty;
                }

                public String getProduct_type() {
                    return product_type;
                }

                public void setProduct_type(String product_type) {
                    this.product_type = product_type;
                }

                public int getTotal_qty() {
                    return total_qty;
                }

                public void setTotal_qty(int total_qty) {
                    this.total_qty = total_qty;
                }
            }
        }
    }
}
