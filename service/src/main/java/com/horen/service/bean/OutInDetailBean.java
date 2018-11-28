package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/29/13:21
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OutInDetailBean {

    private List<OutInStorageListBean> enterStorageList;
    private List<OutInStorageListBean> outStorageList;

    public List<OutInStorageListBean> getEnterStorageList() {
        return enterStorageList;
    }

    public void setEnterStorageList(List<OutInStorageListBean> enterStorageList) {
        this.enterStorageList = enterStorageList;
    }

    public List<OutInStorageListBean> getOutStorageList() {
        return outStorageList;
    }

    public void setOutStorageList(List<OutInStorageListBean> outStorageList) {
        this.outStorageList = outStorageList;
    }

    public static class OutInStorageListBean {
        /**
         * createTime : 2018-08-28
         * product_id : 01.0101.014
         * product_name : 吨立方IF1040食品
         * product_photo : http://61.153.224.202:9096/upload/photo/IF1040-green.png
         * product_type : IF1040
         * sto_type : 12
         * storage_qty : 0
         */

        private String createTime;
        private String product_id;
        private String product_name;
        private String product_photo;
        private String product_type;
        private String sto_type;
        private String storage_qty;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getSto_type() {
            return sto_type;
        }

        public void setSto_type(String sto_type) {
            this.sto_type = sto_type;
        }

        public String getStorage_qty() {
            return storage_qty;
        }

        public void setStorage_qty(String storage_qty) {
            this.storage_qty = storage_qty;
        }
    }
}
