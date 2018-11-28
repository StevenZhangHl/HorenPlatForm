package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/16:36
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderDetail {

    /**
     * 任务单信息
     */
    private OrderAllotInfoBean orderAllotInfo;
    /**
     * 出入库集合
     */
    private List<StorageBean> storageList;
    /**
     * 仓库详情
     */
    private List<WareHousesBean> warehousesList;

    public OrderAllotInfoBean getOrderAllotInfo() {
        return orderAllotInfo;
    }

    public void setOrderAllotInfo(OrderAllotInfoBean orderAllotInfo) {
        this.orderAllotInfo = orderAllotInfo;
    }

    public List<StorageBean> getStorageList() {
        return storageList;
    }

    public void setStorageList(List<StorageBean> storageList) {
        this.storageList = storageList;
    }

    public List<WareHousesBean> getWarehousesList() {
        return warehousesList;
    }

    public void setWarehousesList(List<WareHousesBean> warehousesList) {
        this.warehousesList = warehousesList;
    }

}
