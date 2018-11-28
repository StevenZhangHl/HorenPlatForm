package com.horen.partner.api;

import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.LoginBean;
import com.horen.base.bean.UserLevelBean;
import com.horen.base.net.Url;
import com.horen.partner.bean.ApiResultHomeData;
import com.horen.partner.bean.AwardsDetailBean;
import com.horen.partner.bean.BillChartBean;
import com.horen.partner.bean.BillDetailBean;
import com.horen.partner.bean.BillListBean;
import com.horen.partner.bean.BusinessLineBean;
import com.horen.partner.bean.CompanyBean;
import com.horen.partner.bean.CustomerBean;
import com.horen.partner.bean.IndustryBean;
import com.horen.partner.bean.OrderBean;
import com.horen.partner.bean.PotentialBean;
import com.horen.partner.bean.PropertyBean;
import com.horen.partner.bean.PropertySingleBean;
import com.horen.partner.bean.UserInfoBean;
import com.horen.partner.bean.VisiteNoteBaseBean;
import com.horen.partner.bean.WalletBillBean;
import com.horen.partner.bean.WalletInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * @author :ChenYangYi
 * @date :2018/07/31/10:42
 * @description :合伙人 接口
 * @github :https://github.com/chenyy0708
 */
public interface PartnerApi {

    /**
     * 新增潜在客户
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/save")
    Observable<BaseEntry> uploadPotentialData(@Body RequestBody body);

    /**
     * 获取潜在客户列表
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/queryListProfile")
    Observable<BaseEntry<PotentialBean>> getPotentialData(@Body RequestBody body);

    /**
     * 获取行业条目
     *
     * @return
     */
    @POST("exploitCustomer/queryIndustries")
    Observable<BaseEntry<List<IndustryBean>>> getIndustryData(@Body RequestBody body);

    /**
     * 根据id获取客户详情
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/queryById")
    Observable<BaseEntry<CustomerBean>> getCustomerDetail(@Body RequestBody body);

    /**
     * 获取正式客户列表
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/queryListFormal")
    Observable<BaseEntry<PotentialBean>> getOfficalData(@Body RequestBody body);

    /**
     * 获取拜访记录列表
     *
     * @param body
     * @return
     */
    @POST("visit/queryVisits")
    Observable<BaseEntry<List<VisiteNoteBaseBean>>> getVisiteNoteData(@Body RequestBody body);

    /**
     * 新增拜访记录
     *
     * @param body
     * @return
     */
    @POST("visit/save")
    Observable<BaseEntry> addVisiteNoteInfo(@Body RequestBody body);

    /**
     * 更新某条拜访记录
     *
     * @param body
     * @return
     */
    @POST("visit/updateVisit")
    Observable<BaseEntry> eidtVisiteNoteInfo(@Body RequestBody body);

    /**
     * 编辑客户
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/update")
    Observable<BaseEntry> editCustomerInfo(@Body RequestBody body);

    /**
     * 获取业务中心公司列表
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/queryComItems")
    Observable<BaseEntry<List<CompanyBean>>> getCompanyList(@Body RequestBody body);

    /**
     * 业务中心曲线数据
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getStatsByMonths")
    Observable<BaseEntry<BusinessLineBean>> getBusinessLineData(@Body RequestBody body);

    /**
     * 获取业务中心租赁资产数据
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/queryStatAssets")
    Observable<BaseEntry<List<PropertyBean>>> getPropertyData(@Body RequestBody body);

    /**
     * 获取租赁资产信号数据
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/getSignalDist")
    Observable<BaseEntry<PropertySingleBean>> getPropertySingleData(@Body RequestBody body);

    /**
     * 每月收箱
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getStatsOrdersRtp")
    Observable<BaseEntry<OrderBean>> getRTpOrderInfoByMonth(@Body RequestBody body);

    /**
     * 每月发箱
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getStatsOrdersMa")
    Observable<BaseEntry<OrderBean>> getSellOrderInfoByMonth(@Body RequestBody body);

    /**
     * 获取rtp和耗材数据
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getRentAndRecycleV2")
    Observable<BaseEntry<OrderBean>> getRTpSellOrderData(@Body RequestBody body);

    /**
     * 获取主页数据
     */
    @Headers({DOMAIN_NAME_HEADER + Url.BASE_PALTFORM})
    @POST("solution/gethomedata/v4")
    Observable<ApiResultHomeData> getHomeData(@Body RequestBody body);

    /**
     * 获取账单中心折现图数据
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getBillsTend")
    Observable<BaseEntry<List<BillChartBean>>> getBillChartData(@Body RequestBody body);

    /**
     * 账单中心列表数据
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getBillsTendData")
    Observable<BaseEntry<BillListBean>> getBillListByMonth(@Body RequestBody body);

    /**
     * 账单中心详情
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getBillsTendDataDetail")
    Observable<BaseEntry<List<BillDetailBean>>> getBillDetail(@Body RequestBody body);

    /**
     * 获取钱包列表数据
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getWalletLogs")
    Observable<BaseEntry<WalletBillBean>> getWalletListData(@Body RequestBody body);

    /**
     * 提现申请
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getWalletWithdraw")
    Observable<BaseEntry> submitCashApply(@Body RequestBody body);

    /**
     * 获取钱包金额
     *
     * @param body
     * @return
     */
    @POST("exptOrder/getWallet")
    Observable<BaseEntry<WalletInfo>> getWalletInfo(@Body RequestBody body);

    /**
     * 等级数据
     *
     * @param body
     * @return
     */
    @POST("exploitCustomer/starLevel")
    Observable<BaseEntry<UserLevelBean>> getUserLevelInfo(@Body RequestBody body);

    /**
     * 用户登录信息
     */
    @POST("entry/getMobileLoginInfoByToken")
    Observable<BaseEntry<LoginBean>> getMobileLoginInfoByToken(@Body RequestBody body);

    /**
     * 获取突破奖详情
     *
     * @param body
     * @return
     */
    @POST("productskip/getProductsKips")
    Observable<BaseEntry<List<AwardsDetailBean>>> getAwardsDetail(@Body RequestBody body);
}
