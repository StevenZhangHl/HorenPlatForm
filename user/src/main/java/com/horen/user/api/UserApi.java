package com.horen.user.api;

import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.LoginBean;
import com.horen.user.bean.CompanyInfo;
import com.horen.user.bean.Complaints;
import com.horen.user.bean.OrgInfo;
import com.horen.user.bean.PlatFormSupportBean;
import com.horen.user.bean.PurposeBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author :ChenYangYi
 * @date :2018/07/31/10:42
 * @description :服务端 请求
 * @github :https://github.com/chenyy0708
 */
public interface UserApi {
    /**
     * 用户登录信息
     */
    @POST("entry/mobileLogin")
    Observable<BaseEntry<LoginBean>> mobileUserLogin(@Body RequestBody body);

    /**
     * 用户验证码登录信息
     */
    @POST("entry/smsLogin")
    Observable<BaseEntry<LoginBean>> mobileUserCodeLogin(@Body RequestBody body);

    /**
     * 用户登录信息
     */
    @POST("entry/getMobileLoginInfoByToken")
    Observable<BaseEntry<LoginBean>> getMobileLoginInfoByToken(@Body RequestBody body);

    /**
     * 合作意向数据
     *
     * @return
     */
    @POST("presale/userTypes")
    Observable<BaseEntry<List<PurposeBean>>> getPurposeData(@Body RequestBody body);

    /**
     * 预约注册
     *
     * @param body
     * @return
     */
    @POST("presale/insert")
    Observable<BaseEntry> submitRegister(@Body RequestBody body);

    /**
     * 用户头像保存接口
     */
    @POST("entry/savePhotoUrl")
    Observable<BaseEntry> savePhotoUrl(@Body RequestBody body);

    /**
     * 用户密码修改
     */
    @POST("entry/updatePwd")
    Observable<BaseEntry> updatePwd(@Body RequestBody body);

    /**
     * 获取手机验证码
     */
    @POST("entry/getValidCodeMobile")
    Observable<BaseEntry> getMobileValidCode(@Body RequestBody body);

    /**
     * 验证手机验证码
     */
    @POST("entry/validMobile")
    Observable<BaseEntry> ValidMobile(@Body RequestBody body);

    /**
     * 手机绑定认证
     */
    @POST("entry/bindMobile")
    Observable<BaseEntry> bindMobile(@Body RequestBody body);

    /**
     * 激活手机新密码  修改密码
     */
    @POST("entry/updateMobilePwd")
    Observable<BaseEntry> activeMobilePwd(@Body RequestBody body);

    /**
     * 用户昵称修改
     */
    @POST("entry/updateNickName")
    Observable<BaseEntry> updateNickName(@Body RequestBody body);

    /**
     * 获取平台支援数据
     */
    @POST("exptOrder/getSupports")
    Observable<BaseEntry<List<PlatFormSupportBean>>> getPlatFormSupport(@Body RequestBody body);

    /**
     * 手机忘记密码-设置新密码
     */
    @POST("entry/updateForgetPwdMobile")
    Observable<BaseEntry> updateForgetPwdMobile(@Body RequestBody body);

    /**
     * 个人中心----业务范围
     */
    @POST("entry/userOrgInfo")
    Observable<BaseEntry<OrgInfo>> userOrgInfo(@Body RequestBody body);

    /**
     * 业务范围----修改网点联系人
     */
    @POST("org/updateOrgContact")
    Observable<BaseEntry> updateOrgContact(@Body RequestBody body);

    /**
     * 业务范围----修改网点联系地址
     */
    @POST("org/updateOrgAddress")
    Observable<BaseEntry> updateOrgAddress(@Body RequestBody body);

    /**
     * 业务范围----修改网点联系电话
     */
    @POST("org/updateOrgTel")
    Observable<BaseEntry> updateOrgTel(@Body RequestBody body);

    /**
     * 投诉建议对象列表查询
     */
    @POST("dictionary/getComplainTypeList")
    Observable<BaseEntry<Complaints>> getComplainTypeList(@Body RequestBody body);

    /**
     * 投诉建议信息添加
     */
    @POST("complain/createComplain")
    Observable<BaseEntry> createComplain(@Body RequestBody body);

    /**
     * 用户信息查询
     */
    @POST("custSys/getPCSysUserInfo")
    Observable<BaseEntry<CompanyInfo>> getPCSysUserInfo(@Body RequestBody body);
}
