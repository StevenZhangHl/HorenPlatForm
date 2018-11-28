package com.horen.service.mvp.presenter;

import android.text.TextUtils;

import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.MultipleSelectBean;
import com.horen.base.bean.UploadBean;
import com.horen.base.listener.MultipleSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.MultipleSelectDialog;
import com.horen.service.R;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.AddRepairBean;
import com.horen.service.bean.PositionListBean;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.bean.RtpInfo;
import com.horen.service.mvp.contract.RepairAddContract;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:06
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class RepairAddPresenter extends RepairAddContract.Presenter implements MultipleSelectListener {

    /**
     * 损坏位置信息多选框
     */
    private MultipleSelectDialog dialog;

    /**
     * 箱子信息
     */
    private RtpInfo rtpInfo;

    /**
     * 报修获取扫码产品信息
     *
     * @param ctnr_sn 产品箱号
     */
    @Override
    public void getRtpInfo(String ctnr_sn, final String damageLocation) {
        if (TextUtils.isEmpty(ctnr_sn)) {
            ToastUitl.showShort("箱号为必填项");
            return;
        }
        if (rtpInfo == null) {
            mRxManager.add(mModel.getRtpInfo(ServiceParams.getRtpInfo(ctnr_sn))
                    .subscribeWith(new BaseObserver<RtpInfo>() {
                        @Override
                        protected void onSuccess(RtpInfo info) {
                            // 保存箱号信息，并且抽取损坏位置集合
                            rtpInfo = info;
                            showDialog(info, damageLocation);
                        }

                        @Override
                        protected void onError(String message) {
                            ToastUitl.showShort(message);
                        }
                    }));
        } else {
            dialog.show();
        }
    }

    @Override
    public void addRtpRepair(final String ctnr, final String remark, List<String> imgStr, final RepairDetailBean.ServiceListBean repairBean) {
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
                    .subscribeWith(new BaseObserver<UploadBean>(mContext, false) {
                        @Override
                        protected void onSuccess(UploadBean uploadBean) {
                            // 上传图片成功，提交报修单
                            saveRtpRepair(ctnr, remark, uploadBean.getData(), netWorkUrl, repairBean);
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
            saveRtpRepair(ctnr, remark, new ArrayList<UploadBean.DataBean>(), netWorkUrl, repairBean);
        }
    }

    /**
     * 删除维修单
     */
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
    public void saveRtpRepair(final String ctnr, final String remark, List<UploadBean.DataBean> imgStr, List<String> netWorkUrl, RepairDetailBean.ServiceListBean repairBean) {
        // 用来整合本地和网络图片地址
        List<String> updatePhotoUrl = new ArrayList<>();
        updatePhotoUrl.addAll(netWorkUrl);
        for (UploadBean.DataBean dataBean : imgStr) {
            updatePhotoUrl.add(dataBean.getWatchUrl());
        }

        List<PositionListBean> positionList = new ArrayList<>();
        // 新增报修
        if (repairBean == null) {
            // 得到损坏位置集合
            for (Integer integer : dialog.getSelect()) {
                positionList.add(rtpInfo.getPositionList().get(integer));
            }
        } else {
            // 编辑报修
            for (RepairDetailBean.ServiceListBean.PositionListBean positionListBean : repairBean.getPositionList()) {
                positionList.add(new PositionListBean(positionListBean.getPosition_id()));
            }
        }
        // 上传
        mRxManager.add(mModel.addRtpRepair(ServiceParams.addRtpRepair(ctnr, remark, updatePhotoUrl, positionList, repairBean == null ? "" : repairBean.getService_id()))
                .subscribeWith(new BaseObserver<AddRepairBean>() {
                    @Override
                    protected void onSuccess(AddRepairBean addRepairBean) {
                        mView.saveSuccess(addRepairBean.getServiceMap().getService_id());
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 显示dialog
     */
    private void showDialog(RtpInfo info, String damageLocation) {
        if (dialog == null) {
            dialog = new MultipleSelectDialog(mContext, initDialogData(info.getPositionList(), damageLocation), mContext.getString(R.string.service_damage_location_tip));
            dialog.setMultipleSelectListener(RepairAddPresenter.this);
        } else {
            dialog.setData(initDialogData(info.getPositionList(), damageLocation));
        }
        dialog.show();
    }

    /**
     * 创建损坏位置弹框数据
     *
     * @param positionList 服务器返回箱子对应损坏位置信息
     */
    private List<MultipleSelectBean> initDialogData(List<PositionListBean> positionList, String damageLocation) {
        // 获取已选择损坏位置集合
        List<String> damageList = new ArrayList<>(Arrays.asList(damageLocation.split("、")));
        List<MultipleSelectBean> mData = new ArrayList<>();
        for (PositionListBean positionListBean : positionList) {
            mData.add(new MultipleSelectBean(positionListBean.getPosition(), damageList.contains(positionListBean.getPosition())));
        }
        return mData;
    }

    /**
     * 选择损坏位置
     */
    @Override
    public void onMultipleSelect(List<Integer> mSelect) {
        StringBuilder builder = new StringBuilder();
        // 用分号隔开
        for (int i = 0; i < mSelect.size(); i++) {
            builder.append(rtpInfo.getPositionList().get(mSelect.get(i)).getPosition());
            // 集合的长度大于1并且不是最后一条 拼接;
            if (mSelect.size() > 1 && mSelect.size() - 1 != i) {
                builder.append("、");
            }
        }
        mView.onSelectDamageLocation(builder.toString());
    }

    /**
     * 清空箱子信息
     */
    public void clear() {
        this.rtpInfo = null;
    }
}
