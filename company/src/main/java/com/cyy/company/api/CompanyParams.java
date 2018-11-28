package com.cyy.company.api;

import android.support.annotation.NonNull;

import com.cyy.company.bean.DeleteMsgBean;
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.bean.RenewalRtp;
import com.cyy.company.bean.ReturnOrderPD;
import com.cyy.company.utils.DateUtils;
import com.horen.base.app.BaseApp;
import com.horen.base.app.HRConstant;
import com.horen.base.util.GsonUtil;
import com.horen.base.util.SPUtils;
import com.horen.base.util.UserHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
public class CompanyParams {

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

    public static RequestBody checkLoginInfo(String uid) {
        object = getDefaultJSONObject();
        try {
            object.put("codeUuid", uid);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    public static RequestBody getOrderId(String order_id) {
        object = getDefaultJSONObject();
        try {
            object.put("order_id", order_id);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    public static RequestBody getOrgId(String org_id) {
        object = getDefaultJSONObject();
        try {
            object.put("org_id", org_id);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    public static RequestBody getCompanyId() {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    public static RequestBody getAssetMapData() {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            if (UserHelper.checkDownStream()) { // 下游需要传org_id
                object.put("org_id", UserHelper.getUserInfo().getLoginInfo().getOrg_id());
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 上游订单列表查询
     */
    public static RequestBody getOrderList(String order_type, String order_status, int pageNum, int pageSize) {
        object = getDefaultJSONObject();
        try {
            Calendar endTime = Calendar.getInstance();
            // 开始时间为一年前
            Calendar startTime = Calendar.getInstance();
            startTime.add(Calendar.YEAR, -1);
            object.put("company_id", UserHelper.checkDownStream() ? UserHelper.getUserInfo().getLoginInfo().getOrg_id() :
                    UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("home_status", UserHelper.getUserInfo().getLoginInfo().getFlag_data());
            object.put("start_date", DateUtils.getDateMonthYear(startTime.getTime()));
            object.put("end_date", DateUtils.getDateMonthYear(endTime.getTime()));
            object.put("order_type", order_type);
            object.put("order_status", order_status);
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 订单评价提交
     */
    public static RequestBody saveOrderEvaluation(String order_id, int eval_product, int eval_logistics
            , int eval_attitude, String eval_remarks, String eval_status) {
        object = getDefaultJSONObject();
        try {
            object.put("order_id", order_id);
            object.put("eval_product", eval_product);
            object.put("eval_logistics", eval_logistics);
            object.put("eval_attitude", eval_attitude);
            object.put("eval_remarks", eval_remarks);
            object.put("eval_status", eval_status);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 上游订单详情列表查询
     */
    public static RequestBody getOrderLineList(String order_id) {
        object = getDefaultJSONObject();
        try {
            object.put("order_id", order_id);
            object.put("company_id", UserHelper.checkDownStream() ? UserHelper.getUserInfo().getLoginInfo().getOrg_id() :
                    UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("home_status", UserHelper.getUserInfo().getLoginInfo().getFlag_data());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 操作员列表查询
     */
    public static RequestBody getCustomerById(String company_id, int pageNum, int pageSize) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", company_id);
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
            // 默认传递1，查询操作员信息列表
            object.put("role_id", "1");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 操作员是否停用
     *
     * @param status  启用状态1-待启用（停用）2-启用
     * @param user_id 操作员用户ID(编号)
     * @return
     */
    public static RequestBody updateUserStatusCust(String status, String user_id) {
        object = getDefaultJSONObject();
        try {
            object.put("status", status);
            object.put("user_id", user_id);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 操作员保存
     *
     * @return
     */
    public static RequestBody saveUserCust(String user_id, String user_mail
            , String user_mobile, String user_name, String user_nickname, String user_password) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("org_id", UserHelper.getUserInfo().getLoginInfo().getOrg_id());
            object.put("org_name", UserHelper.getUserInfo().getLoginInfo().getOrg_name());
            object.put("role_id", "EOS_USER");
            object.put("user_id", user_id);
            object.put("user_mail", user_mail);
            object.put("user_mobile", user_mobile);
            object.put("user_name", user_name);
            object.put("user_nickname", user_nickname);
            object.put("user_password", user_password);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 地址薄列表查询
     *
     * @return
     */
    public static RequestBody getOrgAddressList(String org_type, int pageNum, int pageSize) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("org_type", org_type);
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 网点地址联系人和电话修改
     *
     * @return
     */
    public static RequestBody upOrderConsignee(String org_id, String flag_defaultorg,
                                               String org_consignee, String org_consigneetel) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("org_id", org_id);
            object.put("flag_defaultorg", flag_defaultorg);
            object.put("org_consignee", org_consignee);
            object.put("org_consigneetel", org_consigneetel);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 地址薄列表查询
     *
     * @return
     */
    public static RequestBody getMessageList(int pageNum, int pageSize) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 消息更新为已读状态
     *
     * @return
     */
    public static RequestBody upMessageInfo(String log_id) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("log_id", log_id);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 消息更新为已读状态
     *
     * @return
     */
    public static RequestBody delMessageInfo(String log_id) {
        List<DeleteMsgBean> list = new ArrayList<>();
        list.add(new DeleteMsgBean(log_id));
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
        map.put("list", list);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }

    /**
     * 消息更新为已读状态
     *
     * @return
     */
    public static RequestBody getDefaultOrgsList(String org_id, String partner_relation) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("org_id", org_id);
            object.put("partner_relation", partner_relation);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 上游租箱产品信息查询
     *
     * @return
     */
    public static RequestBody getProductsList(String org_id, String set_rentdays) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("org_id", org_id);
            object.put("set_rentdays", set_rentdays);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 上游租、还箱订单提交(添加)
     *
     * @return
     */
    public static RequestBody saveOrderInfo(String end_orgid, String expect_arrivedate, String flag_relet, String flag_send, List<OrderProducts.PdListBean> list,
                                            String order_companyid, String order_companyname, String order_note, String order_type, String rent_days, String start_orgid) {
        for (OrderProducts.PdListBean pdListBean : list) {
            pdListBean.setProduct_flag(pdListBean.getFlag_product());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("end_orgid", end_orgid);
        map.put("expect_arrivedate", expect_arrivedate);
        map.put("flag_relet", flag_relet);
        map.put("flag_send", flag_send);
        map.put("list", list);
        map.put("order_companyid", order_companyid);
        map.put("order_companyname", order_companyname);
        map.put("order_note", order_note);
        map.put("order_type", order_type);
        map.put("rent_days", rent_days);
        map.put("start_orgid", start_orgid);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }

    /**
     * 上游租、还箱订单提交(添加)
     *
     * @return
     */
    public static RequestBody saveReturnOrderInfo(String end_orgid, String expect_arrivedate, String flag_relet, String flag_send, List<ReturnOrderPD.PdListBean> list,
                                                  String order_companyid, String order_companyname, String order_note, String order_type, String start_orgid) {
        // 过滤掉 还箱数量为0的数据
        List<ReturnOrderPD.PdListBean> mData = new ArrayList<>();
        for (ReturnOrderPD.PdListBean pdListBean : list) {
            // 数量不为0
            if (pdListBean.getOrder_qty() != 0) mData.add(pdListBean);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("end_orgid", end_orgid);
        map.put("expect_arrivedate", expect_arrivedate);
        map.put("flag_relet", flag_relet);
        map.put("flag_send", flag_send);
        map.put("list", mData);
        map.put("order_companyid", order_companyid);
        map.put("order_companyname", order_companyname);
        map.put("order_note", order_note);
        map.put("order_type", order_type);
        map.put("rent_days", 0);
        map.put("start_orgid", start_orgid);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }


    /**
     * 上游续租订单提交(添加)
     *
     * @return
     */
    public static RequestBody saveContLeaOrderInfo(String end_orgid, String flag_send, List<RenewalRtp.PdListBean> list,
                                                   String order_companyid, String order_companyname, String order_note, String rent_days, String start_orgid) {
        // 过滤掉 还箱数量为0的数据
        List<RenewalRtp.PdListBean> mData = new ArrayList<>();
        for (RenewalRtp.PdListBean pdListBean : list) {
            // 数量不为0
            if (pdListBean.getOrder_qty() != 0) {
                pdListBean.setProduct_flag("1"); // 续租只能是箱子
                mData.add(pdListBean);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("end_orgid", end_orgid);
        map.put("flag_send", flag_send);
        map.put("list", mData);
        map.put("order_companyid", order_companyid);
        map.put("order_companyname", order_companyname);
        map.put("order_note", order_note);
        map.put("rent_days", rent_days);
        map.put("start_orgid", start_orgid);
        String content = GsonUtil.getGson().toJson(map);
        return getRequestBody(content);
    }

    /**
     * 上游还箱产品信息查询
     */
    public static RequestBody getOrderProNumber(String org_id) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("org_id", org_id);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 上游默认租箱网点地址查询
     */
    public static RequestBody getDefaultOrgsList() {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("org_id", UserHelper.getUserInfo().getLoginInfo().getOrg_id());
            object.put("partner_relation", "1");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 上游续租产品信息查询
     */
    public static RequestBody getContProductsList(String org_id, String set_rentdays) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("org_id", org_id);
            object.put("set_rentdays", set_rentdays);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 首页:百网千驿坐标查询
     */
    public static RequestBody getHomeCoorList() {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            // 	1:上游，2：下游
            object.put("home_status", UserHelper.getUserInfo().getLoginInfo().getFlag_data());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 客户盘点命令接口
     */
    public static RequestBody sendRtpCheckCmd() {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 账单列表
     */
    public static RequestBody getCheckBillList(String company_id, int pageNum, int pageSize) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", company_id);
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
            object.put("pay_type", "all");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 账单详情列表查询
     */
    public static RequestBody getCheckBillDetailsList(String account_bill_id, int pageNum, int pageSize) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("account_bill_id", account_bill_id);
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }

    /**
     * 下游概况:网点使用箱数
     */
    public static RequestBody getSlaveOrgHistogram(String ctnr_type, String date_month) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
            object.put("ctnr_type", ctnr_type);
            object.put("date_month", date_month);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return getRequestBody(object.toString());
    }
}
