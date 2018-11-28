package com.horen.service.listener;

/**
 * @author :ChenYangYi
 * @date :2018/08/09/14:16
 * @description : 业务中心总个数监听
 * @github :https://github.com/chenyy0708
 */
public interface IBusinessTotalCount {
    /**
     * 设置业务中心总个数
     *
     * @param totalCount 个数
     * @param type       来自哪个页面
     */
    void setTotalCount(String totalCount, String type);
}
