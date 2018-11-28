package com.horen.service.listener;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/14:16
 * @description : 仓储中心总个数监听
 * @github :https://github.com/chenyy0708
 */
public interface IStorageTotalCount {
    /**
     * 设置业务中心总个数
     *
     * @param notWashed 未清洗
     * @param notRepair 未维修
     */
    void setTotalCount(String notWashed, String notRepair);
}
