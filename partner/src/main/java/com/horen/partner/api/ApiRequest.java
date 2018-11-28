package com.horen.partner.api;

import com.horen.base.app.BaseApp;
import com.horen.base.app.HRConstant;
import com.horen.base.util.GsonUtil;
import com.horen.base.util.SPUtils;
import com.horen.base.util.UserHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/8 9:33
 * Description:This isApiRequest
 */
public class ApiRequest {
    private static JSONObject object;

    private static RequestBody getRequestBody(JSONObject object) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 获取通用token
     *
     * @return token
     */
    private static String getToken() {
        return SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.TOKEN);
    }

    private static String getUser_id() {
        return SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.USER_ID);
    }

    /**
     * 默认需要上传的参数
     * app_token
     * locale
     *
     * @return
     */
    public static JSONObject getDefaultJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("app_token", getToken());
            jsonObject.put("user_id", getUser_id());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 新增潜在客户
     *
     * @param city_id
     * @param country_id
     * @param county_id
     * @param customer_address
     * @param customer_contact
     * @param customer_industry
     * @param customer_mail
     * @param customer_name
     * @param customer_tel
     * @param photo_desc
     * @param requirements
     * @param photo_urls
     * @return
     */
    public static RequestBody uploadPotentialCustomerData(String city_id, String state_id, String country_id, String county_id, String customer_address, String customer_contact, String customer_industry, String customer_mail, String customer_name, String customer_tel, String photo_desc, String requirements, List<String> photo_urls) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("user_id", getUser_id());
        map.put("city_id", city_id);
        map.put("state_id", state_id);
        map.put("country_id", country_id);
        map.put("county_id", county_id);
        map.put("street", customer_address);
        map.put("customer_contact", customer_contact);
        map.put("customer_industry", customer_industry);
        map.put("customer_mail", customer_mail);
        map.put("customer_name", customer_name);
        map.put("customer_tel", customer_tel);
        map.put("photo_desc", photo_desc);
        map.put("photo_urls", photo_urls);
        map.put("requirements", requirements);
        String result = GsonUtil.getGson().toJson(map);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), result);
    }

    /**
     * 获取潜在用户列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static RequestBody getPotentialData(String pageNum, String pageSize) {
        object = getDefaultJSONObject();
        try {
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取行业
     *
     * @return
     */
    public static RequestBody getIndustryData() {
        object = new JSONObject();
        return getRequestBody(object);
    }

    /**
     * 获取客户详情
     *
     * @param customerId
     * @return
     */
    public static RequestBody getCustomerDetail(String customerId) {
        object = new JSONObject();
        try {
            object.put("app_token", getToken());
            object.put("customer_id", customerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 更新客户信息
     *
     * @param customer_id
     * @param customer_address
     * @param customer_contact
     * @param customer_industry
     * @param customer_mail
     * @param customer_name
     * @param customer_tel
     * @param requirements
     * @param photo_urls
     * @return
     */
    public static RequestBody updateCustomerInfo(String customer_id, String state_id, String city_id, String county_id, String customer_address, String customer_contact, String customer_industry, String customer_mail, String customer_name, String customer_tel, String requirements, List<String> photo_urls) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_token", getToken());
        map.put("customer_id", customer_id);
        map.put("city_id", city_id);
        map.put("state_id", state_id);
        map.put("county_id", county_id);
        map.put("street", customer_address);
        map.put("customer_contact", customer_contact);
        map.put("customer_industry", customer_industry);
        map.put("customer_mail", customer_mail);
        map.put("customer_name", customer_name);
        map.put("customer_tel", customer_tel);
        map.put("photo_urls", photo_urls);
        map.put("requirements", requirements);
        String result = GsonUtil.getGson().toJson(map);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), result);
    }

    /**
     * 获取正式客户列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static RequestBody getOfficalCustomerData(String pageNum, String pageSize) {
        object = getDefaultJSONObject();
        try {
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取拜访记录列表
     *
     * @param customerId
     * @return
     */
    public static RequestBody getVisiteNoteData(String customerId) {
        object = new JSONObject();
        try {
            object.put("user_id", getUser_id());
            object.put("customer_id", customerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 增加拜访记录
     *
     * @param customer_id
     * @param visit_addr
     * @param visit_content
     * @param visit_name
     * @param visit_tel
     * @param photoUrls
     * @return
     */
    public static RequestBody addVisiteNoteInfo(String customer_id, String visit_addr, String visit_content, String visit_name, String visit_tel, List<String> photoUrls) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", getUser_id());
        map.put("app_token", getToken());
        map.put("customer_id", customer_id);
        map.put("visit_addr", visit_addr);
        map.put("visit_content", visit_content);
        map.put("visit_name", visit_name);
        map.put("photo_urls", photoUrls);
        map.put("visit_tel", visit_tel);
        String result = GsonUtil.getGson().toJson(map);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), result);
    }

    /**
     * 更新拜访记录
     *
     * @param visit_id
     * @param visit_addr
     * @param visit_content
     * @param visit_name
     * @param visit_tel
     * @param photoUrls
     * @return
     */
    public static RequestBody editVisiteNoteInfo(String visit_id, String visit_addr, String visit_content, String visit_name, String visit_tel, List<String> photoUrls) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", getUser_id());
        map.put("app_token", getToken());
        map.put("visit_id", visit_id);
        map.put("visit_addr", visit_addr);
        map.put("visit_content", visit_content);
        map.put("visit_name", visit_name);
        map.put("photo_urls", photoUrls);
        map.put("visit_tel", visit_tel);
        String result = GsonUtil.getGson().toJson(map);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), result);
    }

    /**
     * 获取业务中心公司列表
     *
     * @return
     */
    public static RequestBody getCompanyList() {
        object = getDefaultJSONObject();
        return getRequestBody(object);
    }

    /**
     * 业务中心曲线数据
     *
     * @param order_companyid
     * @return
     */
    public static RequestBody getBusinessLineData(String order_companyid) {
        object = getDefaultJSONObject();
        try {
            object.put("order_companyid", order_companyid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取租赁资产数据
     *
     * @param company_id
     * @return
     */
    public static RequestBody getPropertyData(String company_id) {
        object = new JSONObject();
        try {
            object.put("app_token", getToken());
            object.put("company_id", company_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取租赁资产信号数据
     *
     * @param company_id
     * @return
     */
    public static RequestBody getPropertySingleData(String company_id) {
        object = new JSONObject();
        try {
            object.put("app_token", getToken());
            object.put("company_id", company_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 每月RTP订单明细
     *
     * @param months
     * @param order_companyid
     * @param order_type
     * @return
     */
    public static RequestBody getRTpOrderInfoByMonth(String months, String order_companyid, String order_type, int pageNum) {
        object = new JSONObject();
        try {
            object.put("app_token", getToken());
            object.put("order_companyid", order_companyid);
            object.put("months", months);
            object.put("order_type", order_type);
            object.put("pageNum", "");
            object.put("pageSize", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 每月耗材订单明细
     *
     * @param months
     * @param order_companyid
     * @param order_type
     * @return
     */
    public static RequestBody getSellOrderInfoByMonth(String months, String order_companyid, String order_type, int pageNum) {
        object = new JSONObject();
        try {
            object.put("app_token", getToken());
            object.put("order_companyid", order_companyid);
            object.put("months", months);
            object.put("order_type", order_type);
            object.put("pageNum", "");
            object.put("pageSize", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取rtp和耗材
     *
     * @param months
     * @param order_companyid
     * @return
     */
    public static RequestBody getRTpSellOrderData(String months, String order_companyid, int pageNum) {
        object = new JSONObject();
        try {
            object.put("app_token", getToken());
            object.put("order_companyid", order_companyid);
            object.put("months", months);
            object.put("pageNum", "");
            object.put("pageSize", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取万箱主页数据
     *
     * @return
     */
    public static RequestBody getPlatFormHomeData() {
        object = new JSONObject();
        try {
            object.put("page", "1");
            object.put("rows", "100");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取账单中心折现数据
     *
     * @return
     */
    public static RequestBody getBillChartData() {
        object = getDefaultJSONObject();
        return getRequestBody(object);
    }

    /**
     * 账单中心列表数据
     *
     * @param month
     * @return
     */
    public static RequestBody getBillListByMonth(String month) {
        object = getDefaultJSONObject();
        try {
            object.put("months", month);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 账单中心列表详情
     *
     * @param company_id
     * @param months
     * @return
     */
    public static RequestBody getBillDetail(String company_id, String months) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", company_id);
            object.put("months", months);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取钱包列表数据
     *
     * @param pageNum
     * @param pageSize
     * @return
     */

    public static RequestBody getWalletList(int pageNum, int pageSize) {
        object = getDefaultJSONObject();
        try {
            object.put("pageNum", "");
            object.put("pageSize", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 提现申请
     *
     * @param amount
     * @return
     */
    public static RequestBody submitCashApply(String amount) {
        object = getDefaultJSONObject();
        try {
            object.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 得到钱包信息
     *
     * @return
     */
    public static RequestBody getWalletInfo() {
        object = getDefaultJSONObject();
        return getRequestBody(object);
    }

    /**
     * 获取用户等级
     *
     * @return
     */
    public static RequestBody getUserLevelInfo() {
        object = getDefaultJSONObject();
        try {
            object.put("user_id", getUser_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取个人信息
     *
     * @return
     */
    public static RequestBody getUserInfo() {
        object = new JSONObject();
        try {
            object.put("app_token", getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }

    /**
     * 获取突破奖详情
     *
     * @param company_id
     * @param months
     * @return
     */
    public static RequestBody getAwardsDetail(String company_id, String months) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", company_id);
            object.put("months", months);
            object.put("consulting_companyid", UserHelper.getUserInfo().getLoginInfo().getCompany_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getRequestBody(object);
    }
}
