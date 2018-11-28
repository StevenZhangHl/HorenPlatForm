package com.horen.service.utils;

import com.horen.base.app.BaseApp;
import com.horen.base.bean.RadioSelectBean;
import com.horen.service.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/15/16:14
 * @description :订单id获取对应的类型
 * @github :https://github.com/chenyy0708
 */
public class OrderUtils {

    /**
     * 获取调度对应类型
     *
     * @param order_type 订单类型
     * @return 订单中文类型
     */
    public static String checkSchedulingStatus(String order_type) {
        if (order_type.equals("99") || order_type.equals("11")) {
            // 出库
            return BaseApp.getAppResources().getString(R.string.service_outbound);
        } else if (order_type.equals("98") || order_type.equals("12")) {
            // 入库
            return BaseApp.getAppResources().getString(R.string.service_storage);
        }
        return "";
    }

    /**
     * 根据服务器返回code 返回对应中文责任方
     *
     * @param is_person 责任方 责任人0仓储，1服务商
     */
    public static String checkPerson(String is_person) {
        return "0".equals(is_person) ? "仓储" : "服务商";
    }

    /**
     * 根据中文责任方返回对应 code
     *
     * @param is_person 责任方 责任人0仓储，1服务商
     */
    public static String getPerson(String is_person) {
        return "仓储".equals(is_person) ? "0" : "1";
    }

    /**
     * 维修订单状态，平台确认状态
     *
     * @param service_status 待平台确认状态  0：提交 1:待审核 2：维修中 3：客户待确认 4：已完成   0、1未确认
     */
    public static boolean checkServiceStatus(String service_status) {
        return ("0".equals(service_status) || "1".equals(service_status));
    }

    /**
     * 责任方
     */
    public static List<RadioSelectBean> getPerson() {
        List<RadioSelectBean> mData = new ArrayList<>();
        mData.add(new RadioSelectBean("仓储"));
        mData.add(new RadioSelectBean("服务商"));
        return mData;
    }
}
