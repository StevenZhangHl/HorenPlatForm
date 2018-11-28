package com.horen.base.app;

/**
 * @author :ChenYangYi
 * @date :2018/08/06/13:35
 * @description : 常量管理类
 * @github :https://github.com/chenyy0708
 */
public class HRConstant {
    /**
     * Provider路径
     */
    public static final String FILE_PROVIDER = "com.horen.cortp.fileprovider";
    /**
     * 用户登陆token
     */
    public static final String TOKEN = "app_token";
    /**
     * 登陆返回json信息
     */
    public static final String LOGIN_INFO = "login_info";
    /**
     * 登陆账号
     */
    public static final String ACCOUNT = "account";
    /**
     * 服务单号id 集合
     */
    public static final String SERVICE_ID_LIST = "service_id_list";
    /**
     * user_id
     */
    public static String USER_ID = "user_id";
    /**
     * 多个身份的用户选择进入合伙人
     */
    public static String USER_SELET_PARTNER = "user_partner";
    /**
     * 极光推送ID
     */
    public static String REGISTRATION_ID = "registrationID";
    /**
     * 一键盘点时间
     */
    public static String INVENTORY_TIME = "inventory_time";
    /**
     * 一键盘点账号
     */
    public static String INVENTORY_ACCOUNT = "inventory_account";

    public static final String HUNDRED_KILOMETERS = "百网";
    public static final String EYE = "天眼";
    /**
     * 资产在租量统计
     */
    public static final int RENT = 0;
    /**
     * 资产损坏数统计
     */
    public static final int DAMAGE = 1;
    /**
     * 是否点击通知栏打开Activity
     */
    public static final String IS_OPEN_PUSH = "isOpenPush";
    /**
     * 推送额外信息
     */
    public static final String PUSH_EXTRA = "push_extra";
    /**
     * 使用箱数
     */
    public static final int BOX_USED = 0;
    /**
     * 破损率排名
     */
    public static final int BREAKAGE_RATE = 1;
}
