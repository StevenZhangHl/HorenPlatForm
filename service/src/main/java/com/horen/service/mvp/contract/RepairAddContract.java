package com.horen.service.mvp.contract;

import com.horen.base.bean.BaseEntry;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.service.bean.AddRepairBean;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.bean.RtpInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/13:59
 * @description :新增报修
 * @github :https://github.com/chenyy0708
 */
public interface RepairAddContract {
    interface Model extends BaseModel {
        /**
         * 报修获取扫码产品信息
         */
        Observable<RtpInfo> getRtpInfo(RequestBody body);

        /**
         * 新增报修单
         */
        Observable<AddRepairBean> addRtpRepair(RequestBody body);

        /**
         * 维修删除
         */
        Observable<BaseEntry> delRepair(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 选择损坏位置
         *
         * @param location 损坏位置
         */
        void onSelectDamageLocation(String location);

        /**
         * 保存成功
         *
         * @param service_id 服务id，需要保存本地
         */
        void saveSuccess(String service_id);

        /**
         * 维修删除成功
         */
        void delRepairSuccess(String service_id);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 报修获取扫码产品信息
         *
         * @param ctnr_sn        产品箱号
         * @param damageLocation 损坏位置
         */
        public abstract void getRtpInfo(String ctnr_sn, String damageLocation);

        /**
         * 提交报修
         *
         * @param ctnr   箱子ID
         * @param remark 备注
         * @param imgStr 图片json字符串
         */
        public abstract void addRtpRepair(String ctnr, String remark, List<String> imgStr, RepairDetailBean.ServiceListBean repairBean);

        /**
         * 维修删除
         */
        public abstract void delRepair(String service_id);
    }
}
