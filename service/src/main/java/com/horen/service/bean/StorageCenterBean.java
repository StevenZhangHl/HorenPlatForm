package com.horen.service.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/29/09:59
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class StorageCenterBean implements Serializable {

    private List<ListBean> list;

    private WarehousesInfoBean warehousesInfo;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public WarehousesInfoBean getWarehousesInfo() {
        return warehousesInfo == null ? new WarehousesInfoBean() : warehousesInfo;
    }

    public void setWarehousesInfo(WarehousesInfoBean warehousesInfo) {
        this.warehousesInfo = warehousesInfo;
    }

    public static class ListBean implements Serializable{
        /**
         * product_id : 01.0101.013
         * product_name : 吨立方IF1040非食品
         * product_type : IF1040
         * sto_type : 11
         * storage_qty : 317
         */

        private String product_id;
        private String product_name;
        private String product_type;
        private String sto_type;
        private int storage_qty;
        private int enterQty;
        private int available_qty;
        private int clean_qty;
        private int repair_qty;
        private int warehouse_qty;
        private String product_unitname;
        private String product_photo;

        public int getEnterQty() {
            return enterQty;
        }

        public void setEnterQty(int enterQty) {
            this.enterQty = enterQty;
        }

        public String getProduct_photo() {
            return product_photo;
        }

        public void setProduct_photo(String product_photo) {
            this.product_photo = product_photo;
        }

        public int getAvailable_qty() {
            return available_qty;
        }

        public void setAvailable_qty(int available_qty) {
            this.available_qty = available_qty;
        }

        public int getClean_qty() {
            return clean_qty;
        }

        public void setClean_qty(int clean_qty) {
            this.clean_qty = clean_qty;
        }

        public int getRepair_qty() {
            return repair_qty;
        }

        public void setRepair_qty(int repair_qty) {
            this.repair_qty = repair_qty;
        }

        public int getWarehouse_qty() {
            return warehouse_qty;
        }

        public void setWarehouse_qty(int warehouse_qty) {
            this.warehouse_qty = warehouse_qty;
        }

        public String getProduct_unitname() {
            return product_unitname;
        }

        public void setProduct_unitname(String product_unitname) {
            this.product_unitname = product_unitname;
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

        public int getStorage_qty() {
            return storage_qty;
        }

        public void setStorage_qty(int storage_qty) {
            this.storage_qty = storage_qty;
        }
    }

    public static class WarehousesInfoBean implements Serializable{

        /**
         * available_qty : 985912
         * clean_qty : 199529
         * repair_qty : 0
         * warehouse_qty : 1185441
         */

        private int available_qty;
        private int clean_qty;
        private int repair_qty;
        private int warehouse_qty;

        public int getAvailable_qty() {
            return available_qty;
        }

        public void setAvailable_qty(int available_qty) {
            this.available_qty = available_qty;
        }

        public int getClean_qty() {
            return clean_qty == 0 ? 0 : clean_qty;
        }

        public void setClean_qty(int clean_qty) {
            this.clean_qty = clean_qty;
        }

        public int getRepair_qty() {
            return repair_qty == 0 ? 0 : repair_qty;
        }

        public void setRepair_qty(int repair_qty) {
            this.repair_qty = repair_qty;
        }

        public int getWarehouse_qty() {
            return warehouse_qty;
        }

        public void setWarehouse_qty(int warehouse_qty) {
            this.warehouse_qty = warehouse_qty;
        }
    }
}
