package com.horen.service.utils;

import com.google.gson.reflect.TypeToken;
import com.horen.base.app.BaseApp;
import com.horen.base.app.HRConstant;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.GsonUtil;
import com.horen.base.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/22/12:49
 * @description :服务单号id 工具类
 * @github :https://github.com/chenyy0708
 */
public class ServiceIdUtils {

    /**
     * 保存服务id到集合中
     *
     * @param service_id 服务id
     */
    public static void addData(String service_id) {
        List<String> mData = getServiceIdList();
        // 集合中不存在该条数据，新增
        if (!mData.contains(service_id)) {
            mData.add(service_id);
        }
        // 保存新数据到本地
        SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.SERVICE_ID_LIST, GsonUtil.getGson().toJson(mData));
    }

    /**
     * 删除服务id
     *
     * @param service_id 服务id
     */
    public static void deleteData(String service_id) {
        List<String> mData = getServiceIdList();
        // 移除该服务id
        if (mData.remove(service_id)) {
            // 保存新数据到本地
            SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.SERVICE_ID_LIST, GsonUtil.getGson().toJson(mData));
        }
    }

    /**
     * 清空服务id集合
     */
    public static void clear() {
        List<String> mData = getServiceIdList();
        mData.clear();
        // 保存新数据到本地
        SPUtils.setSharedStringData(BaseApp.getAppContext(), HRConstant.SERVICE_ID_LIST, GsonUtil.getGson().toJson(mData));
    }

    /**
     * 获取所有服务id集合
     *
     * @return
     */
    public static List<String> getServiceIdList() {
        String data = SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.SERVICE_ID_LIST);
        // 解析本地保存数据，得到保存的服务单号id集合
        List<String> serviceList = GsonUtil.getGson().fromJson(data, new TypeToken<List<String>>() {
        }.getType());
        // 保存数据为空，返回空数据
        if (CollectionUtils.isNullOrEmpty(serviceList)) {
            return new ArrayList<>();
        } else {
            return serviceList;
        }
    }
}
