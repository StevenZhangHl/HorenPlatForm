package com.horen.service.api;

import com.horen.base.bean.BaseEntry;
import com.horen.service.bean.AddRepairBean;
import com.horen.service.bean.BillDetailBean;
import com.horen.service.bean.BillMainBean;
import com.horen.service.bean.BillTransportBean;
import com.horen.service.bean.BillTransportDetail;
import com.horen.service.bean.OrderDetail;
import com.horen.service.bean.OrderTransList;
import com.horen.service.bean.OutInDetailBean;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.bean.RepairListDetailBean;
import com.horen.service.bean.RtpInfo;
import com.horen.service.bean.ServiceListBean;
import com.horen.service.bean.StorageCenterBean;

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
public interface ServiceApi {

    /**
     * 待处理||已完成列表
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/orderAllotAndTransList")
    Observable<BaseEntry<OrderTransList>> orderAllotAndTransList(@Body RequestBody body);

    /**
     * 任务单详情查询
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/getAllotAndTransInfo")
    Observable<BaseEntry<OrderDetail>> getAllotAndTransInfo(@Body RequestBody body);

    /**
     * 任务单确认
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/addStorage")
    Observable<BaseEntry> addStorage(@Body RequestBody body);

    /**
     * 服务中心列表
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/getRtpServiceList")
    Observable<BaseEntry<ServiceListBean>> getRtpServiceList(@Body RequestBody body);

    /**
     * 服务中心列表
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/addRtpWash")
    Observable<BaseEntry> addRtpWash(@Body RequestBody body);

    /**
     * 报修获取扫码产品信息
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/getRtpInfo")
    Observable<BaseEntry<RtpInfo>> getRtpInfo(@Body RequestBody body);

    /**
     * 提交报修
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/addRtpRepair")
    Observable<BaseEntry<AddRepairBean>> addRtpRepair(@Body RequestBody body);

    /**
     * 维修列表信息
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/getServiceRtpInfo")
    Observable<BaseEntry<RepairListDetailBean>> getServiceRtpInfo(@Body RequestBody body);

    /**
     * 根据服务id获取报修列表
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/getRepairList")
    Observable<BaseEntry<RepairDetailBean>> getRepairList(@Body RequestBody body);

    /**
     * 更新维修申报人
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/updateServicePerson")
    Observable<BaseEntry> updateServicePerson(@Body RequestBody body);

    /**
     * 维修删除
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/delRepair")
    Observable<BaseEntry> delRepair(@Body RequestBody body);

    /**
     * 维修完成
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/repairComplete")
    Observable<BaseEntry> repairComplete(@Body RequestBody body);

    /**
     * 仓储中心
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/storageList")
    Observable<BaseEntry<StorageCenterBean>> storageList(@Body RequestBody body);

    /**
     * 仓储中心-出入库详情
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("storage/getStoreDeliverDetails")
    Observable<BaseEntry<OutInDetailBean>> getStoreDeliverDetails(@Body RequestBody body);

    /**
     * 仓储中心-出入库详情
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("bill/getAllBillList")
    Observable<BaseEntry<BillMainBean>> getAllBillList(@Body RequestBody body);


    /**
     * 账单详情
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("bill/getBillList")
    Observable<BaseEntry<BillDetailBean>> getbillDetail(@Body RequestBody body);

    /**
     * 账单运费列表
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("bill/getFreightBillList")
    Observable<BaseEntry<BillTransportBean>> getFreightBillList(@Body RequestBody body);

    /**
     * 账单运费明细
     *
     * @param body 请求参数
     * @return Observable
     */
    @POST("bill/getFreightBillInfo")
    Observable<BaseEntry<BillTransportDetail>> getFreightBillInfo(@Body RequestBody body);
}
