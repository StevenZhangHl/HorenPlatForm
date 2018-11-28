package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/16/11:20
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderListBean {


    /**
     * pdList : {"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"box_orderqty":1,"box_receiveqty":0,"eval_status":"0","list":[{"order_companyid":"CN02121R","order_companyname":"京东A","order_id":"ZCN02121R18102301","order_qty":1,"order_type":"11","product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_type":"IF1040","receive_qty":0,"rent_price":75,"tms_price":20,"wash_price":0}],"material_orderqty":0,"material_receiveqty":0,"order_companyid":"CN02121R","order_companyname":"京东A","order_date":"2018-10-23 09:02:41","order_id":"ZCN02121R18102301","order_status":"0","order_type":"11","total_orderqty":1}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}
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
         * list : [{"box_orderqty":1,"box_receiveqty":0,"eval_status":"0","list":[{"order_companyid":"CN02121R","order_companyname":"京东A","order_id":"ZCN02121R18102301","order_qty":1,"order_type":"11","product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_type":"IF1040","receive_qty":0,"rent_price":75,"tms_price":20,"wash_price":0}],"material_orderqty":0,"material_receiveqty":0,"order_companyid":"CN02121R","order_companyname":"京东A","order_date":"2018-10-23 09:02:41","order_id":"ZCN02121R18102301","order_status":"0","order_type":"11","total_orderqty":1}]
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
        private List<ListBeanX> list;
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

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBeanX {
            /**
             * box_orderqty : 1
             * box_receiveqty : 0
             * eval_status : 0
             * list : [{"order_companyid":"CN02121R","order_companyname":"京东A","order_id":"ZCN02121R18102301","order_qty":1,"order_type":"11","product_id":"01.0101.013","product_name":"吨立方IF1040非食品","product_photo":"http://61.153.224.202:9096/upload/photo/IF1040-blue.png","product_type":"IF1040","receive_qty":0,"rent_price":75,"tms_price":20,"wash_price":0}]
             * material_orderqty : 0
             * material_receiveqty : 0
             * order_companyid : CN02121R
             * order_companyname : 京东A
             * order_date : 2018-10-23 09:02:41
             * order_id : ZCN02121R18102301
             * order_status : 0
             * order_type : 11
             * total_orderqty : 1
             */

            private int box_orderqty;
            private int box_receiveqty;
            private String eval_status;
            private int material_orderqty;
            private int material_receiveqty;
            private String order_companyid;
            private String order_companyname;
            private String order_date;
            private String order_id;
            private String order_status;
            private String order_type;
            private int total_orderqty;
            private List<ListBean> list;

            /**
             * 是否评价
             * 评价状态(0:未评价, 1:已评价)
             */
            public boolean isEvalStatus() {
                return eval_status.equals("1");
            }

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

            public String getEval_status() {
                return eval_status;
            }

            public void setEval_status(String eval_status) {
                this.eval_status = eval_status;
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

            public String getOrder_companyid() {
                return order_companyid;
            }

            public void setOrder_companyid(String order_companyid) {
                this.order_companyid = order_companyid;
            }

            public String getOrder_companyname() {
                return order_companyname;
            }

            public void setOrder_companyname(String order_companyname) {
                this.order_companyname = order_companyname;
            }

            public String getOrder_date() {
                return order_date;
            }

            public void setOrder_date(String order_date) {
                this.order_date = order_date;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public int getTotal_orderqty() {
                return total_orderqty;
            }

            public void setTotal_orderqty(int total_orderqty) {
                this.total_orderqty = total_orderqty;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * order_companyid : CN02121R
                 * order_companyname : 京东A
                 * order_id : ZCN02121R18102301
                 * order_qty : 1
                 * order_type : 11
                 * product_id : 01.0101.013
                 * product_name : 吨立方IF1040非食品
                 * product_photo : http://61.153.224.202:9096/upload/photo/IF1040-blue.png
                 * product_type : IF1040
                 * receive_qty : 0
                 * rent_price : 75.0
                 * tms_price : 20.0
                 * wash_price : 0.0
                 */

                private String order_companyid;
                private String order_companyname;
                private String order_id;
                private int order_qty;
                private String order_type;
                private String product_id;
                private String product_name;
                private String product_photo;
                private String product_type;
                private int receive_qty;
                private double rent_price;
                private double tms_price;
                private double wash_price;

                public String getOrder_companyid() {
                    return order_companyid;
                }

                public void setOrder_companyid(String order_companyid) {
                    this.order_companyid = order_companyid;
                }

                public String getOrder_companyname() {
                    return order_companyname;
                }

                public void setOrder_companyname(String order_companyname) {
                    this.order_companyname = order_companyname;
                }

                public String getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(String order_id) {
                    this.order_id = order_id;
                }

                public int getOrder_qty() {
                    return order_qty;
                }

                public void setOrder_qty(int order_qty) {
                    this.order_qty = order_qty;
                }

                public String getOrder_type() {
                    return order_type;
                }

                public void setOrder_type(String order_type) {
                    this.order_type = order_type;
                }

                public String getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(String product_id) {
                    this.product_id = product_id;
                }

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }

                public String getProduct_photo() {
                    return product_photo;
                }

                public void setProduct_photo(String product_photo) {
                    this.product_photo = product_photo;
                }

                public String getProduct_type() {
                    return product_type;
                }

                public void setProduct_type(String product_type) {
                    this.product_type = product_type;
                }

                public int getReceive_qty() {
                    return receive_qty;
                }

                public void setReceive_qty(int receive_qty) {
                    this.receive_qty = receive_qty;
                }

                public double getRent_price() {
                    return rent_price;
                }

                public void setRent_price(double rent_price) {
                    this.rent_price = rent_price;
                }

                public double getTms_price() {
                    return tms_price;
                }

                public void setTms_price(double tms_price) {
                    this.tms_price = tms_price;
                }

                public double getWash_price() {
                    return wash_price;
                }

                public void setWash_price(double wash_price) {
                    this.wash_price = wash_price;
                }
            }
        }
    }
}
