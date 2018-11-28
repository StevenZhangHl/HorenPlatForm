package com.horen.service.listener;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/14:56
 * @description :确认修改清洗数
 * @github :https://github.com/chenyy0708
 */
public interface ModifyCleanListener {
    /**
     * 修改清洗数
     *
     * @param clean_qty 修改数量
     */
    void onModifyCleanCount(int clean_qty);
}
