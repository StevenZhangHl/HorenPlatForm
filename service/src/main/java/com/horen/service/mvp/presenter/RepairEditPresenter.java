package com.horen.service.mvp.presenter;

import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.UploadBean;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.AddRepairBean;
import com.horen.service.bean.PositionListBean;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.mvp.contract.RepairEditContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:06
 * @description :维修编辑
 * @github :https://github.com/chenyy0708
 */
public class RepairEditPresenter extends RepairEditContract.Presenter {

    @Override
    public void addRtpRepair(final String is_person, final String remark, List<String> imgStr, final RepairDetailBean.ServiceListBean repairBean) {
        // 区分本地图片和远程图片地址，使用http 和 https开头来区别
        List<String> localUrl = new ArrayList<>();
        final List<String> netWorkUrl = new ArrayList<>();
        for (String s : imgStr) {
            if (s.startsWith("http:") || s.startsWith("https")) {
                netWorkUrl.add(s);
            } else {
                localUrl.add(s);
            }
        }
        if (!CollectionUtils.isNullOrEmpty(localUrl)) {
            // 上传本地图片
            final List<File> mFiles = new ArrayList<>();
            mRxManager.add(Observable.just(localUrl)
                    .compose(RxHelper.uploadFile(mFiles))
                    .subscribeWith(new BaseObserver<UploadBean>(mContext, true) {
                        @Override
                        protected void onSuccess(UploadBean uploadBean) {
                            // 上传图片成功，提交报修单
                            saveRtpRepair(is_person, remark, uploadBean.getData(), netWorkUrl, repairBean);
                            // 删除本地压缩图片
                            for (File mFile : mFiles) {
                                mFile.delete();
                            }
                        }

                        @Override
                        protected void onError(String message) {
                            mView.onError(message);
                        }
                    }));
        } else {
            // 不需要上传本地图片
            saveRtpRepair(is_person, remark, new ArrayList<UploadBean.DataBean>(), netWorkUrl, repairBean);
        }
    }

    @Override
    public void delRepair(final String service_id) {
        mRxManager.add(mModel.delRepair(ServiceParams.delRepair(service_id))
                .subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        mView.delRepairSuccess(service_id);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 保存报修单
     */
    private void saveRtpRepair(String is_person, final String remark, List<UploadBean.DataBean> imgStr, List<String> netWorkUrl, RepairDetailBean.ServiceListBean repairBean) {
        // 用来整合本地和网络图片地址
        List<String> updatePhotoUrl = new ArrayList<>();
        updatePhotoUrl.addAll(netWorkUrl);
        for (UploadBean.DataBean dataBean : imgStr) {
            updatePhotoUrl.add(dataBean.getWatchUrl());
        }
        List<PositionListBean> positionList = new ArrayList<>();
        // 编辑报修
        for (RepairDetailBean.ServiceListBean.PositionListBean positionListBean : repairBean.getPositionList()) {
            positionList.add(new PositionListBean(positionListBean.getPosition_id()));
        }
        // 上传
        mRxManager.add(mModel.addRtpRepair(ServiceParams.addRtpRepair(is_person, repairBean.getCtnr_sn(), remark, updatePhotoUrl, positionList, repairBean == null ? "" : repairBean.getService_id()))
                .subscribeWith(new BaseObserver<AddRepairBean>() {
                    @Override
                    protected void onSuccess(AddRepairBean addRepairBean) {
                        mView.saveSuccess();
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }
}
