package com.horen.user.api;

import com.horen.base.app.BaseApp;
import com.horen.base.app.HRConstant;
import com.horen.base.util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/07/18/10:43
 * @description :个人中心请求参数
 * @github :https://github.com/chenyy0708
 */
public class UserApiPram {

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
     * 默认上传的Body,适用于只需要app_token的请求
     */
    public static RequestBody getDefaultPram() {
        object = getDefaultJSONObject();
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    private static String getUser_id() {
        return SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.USER_ID);
    }

    /**
     * 获取通用token
     *
     * @return token
     */
    private static String getToken() {
        return SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.TOKEN);
    }

    /**
     * 手机账号密码登陆
     *
     * @param loginName 用户名
     * @param password  密码
     */
    public static RequestBody mobileUserLogin(String loginName, String password) {
        object = new JSONObject();
        try {
            object.put("device_token", SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.REGISTRATION_ID));
            object.put("loginName", loginName);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 手机账号验证码登陆
     *
     * @param user_mobile 手机号
     * @param valid_code  验证码
     */
    public static RequestBody mobileUserCodeLogin(String user_mobile, String valid_code) {
        object = new JSONObject();
        try {
            object.put("user_mobile", user_mobile);
            object.put("valid_code", valid_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 绑定邮箱获取验证码
     *
     * @return
     */
    public static RequestBody getBindingCode(String user_mail) {
        object = getDefaultJSONObject();
        try {
            object.put("type", "2");//1手机 2 邮箱
            object.put("user_mail", user_mail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 获取合作意向数据
     *
     * @return
     */
    public static RequestBody getPurposeData() {
        object = new JSONObject();
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 预约注册
     *
     * @param mobile
     * @param consult_uname
     * @param company_name
     * @param address
     * @param user_type
     * @return
     */
    public static RequestBody submitRegister(String mobile, String consult_uname, String company_name, String address, String user_type) {
        object = new JSONObject();
        try {
            object.put("company_tel", mobile);
            object.put("company_contact", consult_uname);
            object.put("company_name", company_name);
            object.put("company_address", address);
            object.put("company_class", user_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 预约咨询师注册
     *
     * @param company_contact 联系人（必须）
     * @param company_tel     电话（必须）
     * @param consult_workday 1：工作日 2：周末 4：工作日+周末
     * @return consult_worktime  1：9:00-13:00（工作日上午） 2:13:00-18:00 （工作日下午） 4 18:00-21:00 （工作日下班）
     */
    public static RequestBody submitRegister(String company_contact, String company_tel, String consult_workday, String consult_worktime) {
        object = new JSONObject();
        try {
            object.put("company_contact", company_contact);
            object.put("company_tel", company_tel);
            object.put("consult_workday", consult_workday);
            object.put("consult_worktime", consult_worktime);
            object.put("company_name", company_contact);
            object.put("company_class", "2");
            object.put("company_industry", "00");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 用户头像保存接口
     */
    public static RequestBody savePhotoUrl(String photo) {
        object = getDefaultJSONObject();
        try {
            object.put("photo", photo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 用户密码修改
     */
    public static RequestBody updatePwd(String user_id, String user_name, String user_password) {
        object = getDefaultJSONObject();
        try {
            object.put("user_id", user_id);
            object.put("user_name", user_name);
            object.put("user_password", user_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 获取手机验证码
     */
    public static RequestBody getMobileValidCode(String user_mobile) {
        object = getDefaultJSONObject();
        try {
            object.put("user_mobile", user_mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 验证手机验证码
     */
    public static RequestBody validMobile(String user_mobile, String valid_code) {
        object = getDefaultJSONObject();
        try {
            object.put("user_mobile", user_mobile);
            object.put("valid_code", valid_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 手机绑定认证公用
     */
    public static RequestBody bindMobile(String user_mobile, String valid_code) {
        object = getDefaultJSONObject();
        try {
            object.put("user_mobile", user_mobile);
            object.put("valid_code", valid_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 激活手机新密码
     */
    public static RequestBody activeMobilePwd(String user_mobile, String user_password) {
        object = getDefaultJSONObject();
        try {
            object.put("user_mobile", user_mobile);
            object.put("user_password", user_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 用户昵称修改
     */
    public static RequestBody updateNickName(String user_nickname) {
        object = getDefaultJSONObject();
        try {
            object.put("user_nickname", user_nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 获取平台支援数据
     *
     * @return
     */
    public static RequestBody getPlatFormSupport(String user_id) {
        object = getDefaultJSONObject();
        try {
            object.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 个人中心-修改网点联系人
     */
    public static RequestBody updateOrgContact(String org_contact, String org_id) {
        object = getDefaultJSONObject();
        try {
            object.put("org_contact", org_contact);
            object.put("org_id", org_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 个人中心-修改网点联系电话
     */
    public static RequestBody updateOrgTel(String org_tel, String org_id) {
        object = getDefaultJSONObject();
        try {
            object.put("org_tel", org_tel);
            object.put("org_id", org_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 个人中心-修改联系地址
     */
    public static RequestBody updateOrgAddress(String org_address, String org_id, double org_latitude, double org_longitude) {
        object = getDefaultJSONObject();
        try {
            object.put("org_address", org_address);
            object.put("org_id", org_id);
            object.put("org_latitude", org_latitude);
            object.put("org_longitude", org_longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    /**
     * 投诉建议信息添加
     */
    public static RequestBody createComplain(String company_id, String complain_content, String complain_typeid) {
        object = getDefaultJSONObject();
        try {
            object.put("company_id", company_id);
            object.put("complain_content", complain_content);
            object.put("complain_typeid", complain_typeid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }
}
