package com.horen.service.api;

import android.support.annotation.NonNull;

import com.horen.base.app.BaseApp;
import com.horen.base.app.HRConstant;
import com.horen.base.util.GsonUtil;
import com.horen.base.util.SPUtils;
import com.horen.service.bean.PositionListBean;
import com.horen.service.bean.StorageSubmitBean;
import com.horen.service.bean.UpdatePerson;
import com.horen.service.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/09:55
 * @description :请求参数
 * @github :https://github.com/chenyy0708
 */
public class ServiceParams {

    private static JSONObject object;

    /**
     * 默认需要上传的参数
     *
     * @return 通用参数
     */
    public static JSONObject getDefaultJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("app_token", getToken());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 获取通用token
     *
     * @return token
     */
    private static String getToken() {
        return SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.TOKEN);
//        return "dbf425965480790379cd27807191599e1282224e";
    }

    @NonNull
    private static RequestBody getRequestBody(String body) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
    }

    /**
     * 默认上传的Body,适用于只需要app_token的请求
     */
    public static RequestBody getDefaultPram() {
        object = getDefaultJSONObject();
        return getRequestBody(object.toString());
    }

    /**
     * 业务中心 列表数据
     *
     * @param pageNum  第几页
     * @param pageSize 每页行数
     * @param status   任务单状态：3已审核，4已完成
     */
    public static RequestBody orderAllotAndTransList(int pageNum, int pageSize, String status) {
        object = getDefaultJSONObject();
        try {
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
            object.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 任务单详情查询
     *
     * @param order_type    任务单类型
     * @param orderallot_id 任务单ID
     */
    public static RequestBody getAllotAndTransInfo(String order_type, String orderallot_id, String status) {
        object = getDefaultJSONObject();
        try {
            object.put("order_type", order_type);
            object.put("orderallot_id", orderallot_id);
            object.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 任务单确认
     *
     * @param img1          上传图片1
     * @param img2          上传图片2
     * @param order_type    任务单类型
     * @param orderallot_id 任务单ID
     * @param mData         产品集合
     * @param remark        备注
     */
    public static RequestBody addStorage(String img1, String img2, String order_type, String orderallot_id, List<StorageSubmitBean> mData, String remark) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("img1", img1);
        map.put("img2", img2);
        map.put("order_type", order_type);
        map.put("orderallot_id", orderallot_id);
        map.put("proList", mData);
        map.put("remark", remark);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }

    /**
     * 服务中心列表
     *
     * @param service_status 维修状态值 0:未清洗|未维修，4:已清洗已维修
     * @param service_type   服务状态值 0:维修 1:清洗，=====已完成不用传
     */
    public static RequestBody getRtpServiceList(String service_status, String service_type) {
        object = getDefaultJSONObject();
        try {
            Calendar endTime = Calendar.getInstance();
            // 开始时间为一年前
            Calendar startTime = Calendar.getInstance();
            startTime.add(Calendar.YEAR, -1);
            // 开始时间结束时间为一年  	开始时间2017-08-22   	结束时间2018-08-22
            object.put("start_time", StringUtils.getDateMonthYear(startTime.getTime()));
            object.put("end_time", StringUtils.getDateMonthYear(endTime.getTime()));
            object.put("service_status", service_status);
            object.put("service_type", service_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 添加清洗
     *
     * @param product_id  产品ID
     * @param service_qty 清洗数量
     */
    public static RequestBody addRtpWash(String product_id, String service_qty) {
        object = getDefaultJSONObject();
        try {
            object.put("product_id", product_id);
            object.put("service_qty", service_qty);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 服务列表信息
     *
     * @param pageNum        页数
     * @param pageSize       条数
     * @param product_id     产品ID
     * @param service_status 维修状态值 0:未清洗|未维修，4:已清洗已维修
     * @param service_type   服务状态值 0:维修 1:清洗，=====已完成不用传
     */
    public static RequestBody getServiceRtpInfo(int pageNum, int pageSize, String product_id, String service_status, String service_type) {
        object = getDefaultJSONObject();
        try {
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
            object.put("product_id", product_id);
            object.put("service_status", service_status);
            object.put("service_type", service_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 报修获取扫码产品信息
     *
     * @param ctnr_sn 产品箱号
     */
    public static RequestBody getRtpInfo(String ctnr_sn) {
        object = getDefaultJSONObject();
        try {
            object.put("ctnr_sn", ctnr_sn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 提交报修
     *
     * @param ctnr_sn 产品箱号
     */
    public static RequestBody addRtpRepair(String ctnr_sn, String remark, List<String> imgList, List<PositionListBean> rtpDamageList, String service_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("ctnr_sn", ctnr_sn);
        map.put("remark", remark);
        map.put("imgStr", imgList);
        map.put("rtpDamageList", rtpDamageList);
        map.put("service_id", service_id);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }

    /**
     * 维修编辑
     */
    public static RequestBody addRtpRepair(String is_person, String ctnr_sn, String remark, List<String> imgList, List<PositionListBean> rtpDamageList, String service_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("is_person", is_person);
        map.put("ctnr_sn", ctnr_sn);
        map.put("remark", remark);
        map.put("imgStr", imgList);
        map.put("rtpDamageList", rtpDamageList);
        map.put("service_id", service_id);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }

    /**
     * 提交报修
     *
     * @param serviceIdList 服务id集合
     */
    public static RequestBody getRepairList(List<String> serviceIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("serviceIdList", serviceIdList);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }

    /**
     * 更新维修申报人
     *
     * @param serviceList 维修json
     */
    public static RequestBody updateServicePerson(String orderallot_id, List<UpdatePerson> serviceList) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("orderallot_id", orderallot_id);
        map.put("serviceList", serviceList);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }

    /**
     * 维修删除
     */
    public static RequestBody delRepair(String service_id) {
        object = getDefaultJSONObject();
        try {
            object.put("service_id", service_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 维修完成
     */
    public static RequestBody repairComplete(String service_id, String remark) {
        object = getDefaultJSONObject();
        try {
            object.put("service_id", service_id);
            object.put("remark", remark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 仓储中心列表
     */
    public static RequestBody storageList(String type) {
        object = getDefaultJSONObject();
        try {
            object.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 出入库详情
     */
    public static RequestBody getStoreDeliverDetails(String product_id) {
        object = getDefaultJSONObject();
        try {
            object.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 账单中心首页
     */
    public static RequestBody getAllBillList(String relativeMonth, String accountStatus) {
        object = getDefaultJSONObject();
        try {
            object.put("relativeMonth", relativeMonth);
            object.put("accountStatus", accountStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 账单详情
     */
    public static RequestBody getBillDetail(String cost_type, String relativeMonth) {
        object = getDefaultJSONObject();
        try {
            object.put("cost_type", cost_type);
            object.put("relativeMonth", relativeMonth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 账单运费明细
     */
    public static RequestBody getFreightBillInfo(String account_id) {
        object = getDefaultJSONObject();
        try {
            object.put("account_id", account_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object.toString());
    }
}
