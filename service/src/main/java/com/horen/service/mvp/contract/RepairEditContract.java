package com.horen.service.mvp.contract;

import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.service.bean.RepairDetailBean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/13:59
 * @description :维修编辑
 * @github :https://github.com/chenyy0708
 */
public interface RepairEditContract {
    interface View extends BaseView {
        /**
         * 保存成功
         */
        void saveSuccess();
        /**
         * 维修删除成功
         */
        void delRepairSuccess(String service_id);
    }

    abstract class Presenter extends BasePresenter<View, RepairAddContract.Model> {
        /**
         * 提交报修
         *
         * @param is_person 责任方
         * @param remark    备注
         * @param imgStr    图片json字符串
         */
        public abstract void addRtpRepair(String is_person, String remark, List<String> imgStr, RepairDetailBean.ServiceListBean repairBean);

        /**
         * 维修删除
         */
        public abstract void delRepair(String service_id);
    }
}
