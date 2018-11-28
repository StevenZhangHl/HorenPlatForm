package com.cyy.company.api;

import com.cyy.company.bean.AccountManager;
import com.cyy.company.bean.AddressBook;
import com.cyy.company.bean.AnalysisHistogram;
import com.cyy.company.bean.AnalysisLineChart;
import com.cyy.company.bean.AnalysisTotal;
import com.cyy.company.bean.AssetCheckBean;
import com.cyy.company.bean.AssetMapListBean;
import com.cyy.company.bean.BillDetailBean;
import com.cyy.company.bean.BillListBean;
import com.cyy.company.bean.DefaultOrgBean;
import com.cyy.company.bean.DownStreamBean;
import com.cyy.company.bean.EvaluationBean;
import com.cyy.company.bean.HomeCoor;
import com.cyy.company.bean.HomeOkuraDetail;
import com.cyy.company.bean.HomeOrgDetail;
import com.cyy.company.bean.MessageLog;
import com.cyy.company.bean.MessageNoticeBean;
import com.cyy.company.bean.MsgLogisticsBean;
import com.cyy.company.bean.OrderDetailBean;
import com.cyy.company.bean.OrderListBean;
import com.cyy.company.bean.OrderLogs;
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.bean.OrgPageBean;
import com.cyy.company.bean.RenewalAddress;
import com.cyy.company.bean.RenewalRtp;
import com.cyy.company.bean.RentDays;
import com.cyy.company.bean.ReturnIntegra;
import com.cyy.company.bean.ReturnOrderPD;
import com.cyy.company.bean.ReturnProFreight;
import com.cyy.company.bean.SubmitOrder;
import com.horen.base.bean.BaseEntry;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author :ChenYangYi
 * @date :2018/10/11/13:01
 * @description :
 * @github :https://github.com/chenyy0708
 */
public interface CompanyService {
    /**
     * 扫码登录，请求信息
     */
    @POST("entry/qrCodeLog")
    Observable<BaseEntry> scanloginApplyInfo(@Body RequestBody body);

    /**
     * 扫码登录，请求登录
     */
    @POST("entry/qrCodeAccredit")
    Observable<BaseEntry> scanloginApplyLogin(@Body RequestBody body);

    /**
     * 上游订单列表查询
     */
    @POST("custOrderApp/getOrderList")
    Observable<BaseEntry<OrderListBean>> getOrderList(@Body RequestBody body);

    /**
     * 订单评价提交
     */
    @POST("custOrder/saveOrderEvaluation")
    Observable<BaseEntry> saveOrderEvaluation(@Body RequestBody body);

    /**
     * 订单评价查询
     */
    @POST("custOrder/getOrderEvaluationInfo")
    Observable<BaseEntry<EvaluationBean>> getOrderEvaluationInfo(@Body RequestBody body);

    /**
     * 上游订单详情列表查询
     */
    @POST("custOrderApp/getOrderLineList")
    Observable<BaseEntry<OrderDetailBean>> getOrderLineList(@Body RequestBody body);

    /**
     * 订单取消
     */
    @POST("custOrder/cancelOrderInfo")
    Observable<BaseEntry> cancelOrderInfo(@Body RequestBody body);

    /**
     * 操作员列表查询
     */
    @POST("custSys/getCustomerById")
    Observable<BaseEntry<AccountManager>> getCustomerById(@Body RequestBody body);

    /**
     * 操作员是否停用
     */
    @POST("custSys/updateUserStatusCust")
    Observable<BaseEntry> updateUserStatusCust(@Body RequestBody body);

    /**
     * 操作员保存
     */
    @POST("custSys/saveUserCust")
    Observable<BaseEntry> saveUserCust(@Body RequestBody body);

    /**
     * 地址薄列表查询
     */
    @POST("custSys/getOrgAddressList")
    Observable<BaseEntry<AddressBook>> getOrgAddressList(@Body RequestBody body);

    /**
     * 地址薄网点地址联系人和电话修改
     */
    @POST("custOrder/upOrderConsignee")
    Observable<BaseEntry> upOrderConsignee(@Body RequestBody body);

    /**
     * 订单消息列表查询,可分页
     */
    @POST("eyeMessageApp/getMessageList")
    Observable<BaseEntry<MsgLogisticsBean>> getMessageList(@Body RequestBody body);

    /**
     * 消息更新为已读状态
     */
    @POST("eyeMessage/upMessageInfo")
    Observable<BaseEntry> upMessageInfo(@Body RequestBody body);

    /**
     * 订单消息删除
     */
    @POST("eyeMessage/delMessageInfo")
    Observable<BaseEntry> delMessageInfo(@Body RequestBody body);

    /**
     * 上游默认租箱网点地址查询
     */
    @POST("custOrder/getOrgsIdOkura")
    Observable<BaseEntry<DefaultOrgBean>> getOrgsIdOkura(@Body RequestBody body);

    /**
     * 获取物品列表
     */
    @POST("custOrder/getProductsList")
    Observable<BaseEntry<OrderProducts>> getProductsList(@Body RequestBody body);

    /**
     * 订单租赁天数查询
     */
    @POST("custOrder/getRentdaysList")
    Observable<BaseEntry<RentDays>> getRentdaysList(@Body RequestBody body);

    /**
     * 上游租、还箱订单提交(添加)
     */
    @POST("custOrder/saveOrderInfo")
    Observable<BaseEntry<SubmitOrder>> saveOrderInfo(@Body RequestBody body);

    /**
     * 上游还箱产品信息查询
     */
    @POST("custOrder/getOrderProNumber")
    Observable<BaseEntry<ReturnOrderPD>> getOrderProNumber(@Body RequestBody body);

    /**
     * 下游还箱产品信息查询
     */
    @POST("custOrder/getUnderOrderProNumber")
    Observable<BaseEntry<ReturnOrderPD>> getUnderOrderProNumber(@Body RequestBody body);

    /**
     * 上游还箱运费单价查询
     */
    @POST("custOrder/getOrderProFreight")
    Observable<BaseEntry<ReturnProFreight>> getOrderProFreight(@Body RequestBody body);

    /**
     * 上游续租产品信息查询
     */
    @POST("custOrderApp/getContProductsList")
    Observable<BaseEntry<RenewalRtp>> getContProductsList(@Body RequestBody body);

    /**
     * 上游默认租箱网点地址查询
     */
    @POST("custOrder/getDefaultOrgsList")
    Observable<BaseEntry<RenewalAddress>> getDefaultOrgsList(@Body RequestBody body);

    /**
     * 上游续租订单提交(添加)
     */
    @POST("custOrder/saveContLeaOrderInfo")
    Observable<BaseEntry<SubmitOrder>> saveContLeaOrderInfo(@Body RequestBody body);

    /**
     * 下游还箱订单提交(添加)
     */
    @POST("custOrder/saveUnderOrderInfo")
    Observable<BaseEntry<SubmitOrder>> saveUnderOrderInfo(@Body RequestBody body);

    /**
     * 首页:百网千驿坐标查询
     */
    @POST("eyeAssets/getHomeCoorList")
    Observable<BaseEntry<HomeCoor>> getHomeCoorList(@Body RequestBody body);

    /**
     * 首页:根据百网ID,网点
     */
    @POST("eyeAssets/getOrgByIdsProType")
    Observable<BaseEntry<HomeOrgDetail>> getOrgByIdsProType(@Body RequestBody body);

    /**
     * 首页:根据百网ID,大仓
     */
    @POST("eyeAssets/getHomeOkuraList")
    Observable<BaseEntry<HomeOkuraDetail>> getHomeOkuraList(@Body RequestBody body);

    /**
     * 客户盘点命令接口
     */
    @POST("rtpCheckHeader/sendRtpCheckCmd")
    Observable<BaseEntry> sendRtpCheckCmd(@Body RequestBody body);

    /**
     * 分布:地图模式查询
     */
    @POST("eyeAssets/getOrgMapList")
    Observable<BaseEntry<AssetMapListBean>> getOrgMapList(@Body RequestBody body);

    /**
     * 客户盘点今日列表查询
     */
    @POST("rtpCheckHeader/getTodayCheckList")
    Observable<BaseEntry<AssetCheckBean>> getTodayCheckList(@Body RequestBody body);

    /**
     * 分析:资产总数查询
     */
    @POST("eyeAssets/getAnalysisTotalList")
    Observable<BaseEntry<AnalysisTotal>> getAnalysisTotalList(@Body RequestBody body);

    /**
     * 分析:资产在租量统计
     */
    @POST("eyeAssetsApp/getAnalysisLineChart")
    Observable<BaseEntry<AnalysisLineChart>> getAnalysisLineChart(@Body RequestBody body);

    /**
     * 分析:损坏数统计-柱状图
     */
    @POST("eyeAssetsApp/getAnalysisHistogram")
    Observable<BaseEntry<AnalysisHistogram>> getAnalysisHistogram(@Body RequestBody body);

    /**
     * 分布:列表模式查询
     */
    @POST("eyeAssets/getOrgPageList")
    Observable<BaseEntry<OrgPageBean>> getOrgPageList(@Body RequestBody body);

    /**
     * 分布:下游地图模式网点坐标查询
     */
    @POST("eyeAssetsApp/getOrgDownMapList")
    Observable<BaseEntry<AssetMapListBean>> getOrgDownMapList(@Body RequestBody body);

    /**
     * 账单列表查询
     */
    @POST("custSys/getCheckBillList")
    Observable<BaseEntry<BillListBean>> getCheckBillList(@Body RequestBody body);

    /**
     * 账单详情列表查询
     */
    @POST("custSys/getCheckBillDetailsList")
    Observable<BaseEntry<BillDetailBean>> getCheckBillDetailsList(@Body RequestBody body);

    /**
     * 下游概况:网点使用箱数
     */
    @POST("eyeAssetsApp/getSlaveOrgHistogram")
    Observable<BaseEntry<DownStreamBean>> getSlaveOrgHistogram(@Body RequestBody body);

    /**
     * 下游概况:损坏率查询
     */
    @POST("eyeAssetsApp/getOrgDamageRateList")
    Observable<BaseEntry<DownStreamBean>> getOrgDamageRateList(@Body RequestBody body);

    /**
     * APP订单日志查询
     */
    @POST("custOrderApp/getOrderLogsList")
    Observable<BaseEntry<OrderLogs>> getOrderLogsList(@Body RequestBody body);

    /**
     * 物流消息、通知消息未读数量
     */
    @POST("eyeMessageApp/geMessageLogList")
    Observable<BaseEntry<MessageLog>> geMessageLogList(@Body RequestBody body);

    /**
     * 通知类消息列表查询,可分页
     */
    @POST("eyeMessageApp/getMessageNoticeList")
    Observable<BaseEntry<MessageNoticeBean>> getMessageNoticeList(@Body RequestBody body);

    /**
     * 通知类消息列表查询,可分页
     */
    @POST("eyeMessageApp/delMessageNoticeInfo")
    Observable<BaseEntry> delMessageNoticeInfo(@Body RequestBody body);

    /**
     * 通知类消息更新为已读状态
     */
    @POST("eyeMessageApp/upMessageNoticeInfo")
    Observable<BaseEntry> upMessageNoticeInfo(@Body RequestBody body);

    /**
     * 还箱点列表查询
     */
    @POST("custSys/getCusLinesList")
    Observable<BaseEntry<ReturnIntegra>> getCusLinesList(@Body RequestBody body);
}
