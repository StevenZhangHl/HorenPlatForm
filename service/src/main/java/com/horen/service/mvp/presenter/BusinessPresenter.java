package com.horen.service.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.UploadBean;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.OrderAllotInfoBean;
import com.horen.service.bean.OrderDetail;
import com.horen.service.bean.StorageSubmitBean;
import com.horen.service.bean.WareHousesBean;
import com.horen.service.mvp.contract.BusinessContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:06
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BusinessPresenter extends BusinessContract.Presenter {

    /**
     * 任务单详情查询
     *
     * @param order_type    任务单类型
     * @param orderallot_id 任务单ID
     */
    @Override
    public void getAllotAndTransInfo(String order_type, String orderallot_id, String status) {
        mRxManager.add(mModel.getAllotAndTransInfo(ServiceParams.getAllotAndTransInfo(order_type, orderallot_id, status))
                .subscribeWith(new BaseObserver<OrderDetail>(mContext, true) {
                    @Override
                    protected void onSuccess(OrderDetail orderDetail) {
                        // 预先处理数据，得到总库存、可用数量
                        for (OrderAllotInfoBean.ProListBean proListBean : orderDetail.getOrderAllotInfo().getProList()) {
                            for (WareHousesBean wareHousesBean : orderDetail.getWarehousesList()) {
                                // 产品id相同，取出总库存、可用数量
                                if (proListBean.getProduct_id().equals(wareHousesBean.getProduct_id())) {
                                    // 总库存量
                                    proListBean.setTotal_qty(wareHousesBean.getWarehouse_qty());
                                    // 可用数量
                                    proListBean.setAvailable_qty(wareHousesBean.getAvailable_qty());
                                }
                            }
                            // 设置未执行量  未执行 = 需求量 - 已执行
                            proListBean.setNot_perform_qty(proListBean.getOrder_qty() - proListBean.getPerform_qty());
                        }
                        mView.getInfoSuccess(orderDetail);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 提交任务单
     */
    @Override
    public void addStorage(RequestBody body) {
        mRxManager.add(mModel.addStorage(body).subscribeWith(new BaseObserver<BaseEntry>() {
            @Override
            protected void onSuccess(BaseEntry baseEntry) {
                mView.submitSuccess();
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

    /**
     * 提交，交割
     *
     * @param mImageUrls 图片集合
     * @param reamke     备注
     * @param mData      上传参数
     */
    @Override
    public void submit(final String order_type, final String orderallot_id, List<String> mImageUrls, final String reamke, final List<StorageSubmitBean> mData) {
        final List<File> mFiles = new ArrayList<>();
        // 上传图片
        mRxManager.add(Observable.just(mImageUrls)
                .compose(RxHelper.uploadFile(mFiles))
                .subscribeWith(new BaseObserver<UploadBean>(mContext, true) {
                    @Override
                    protected void onSuccess(UploadBean uploadBean) {
                        // 上传图片成功，提交任务单
                        addStorage(ServiceParams.addStorage(uploadBean.getData().size() >= 1 ? uploadBean.getData().get(0).getWatchUrl() : "", uploadBean.getData().size() == 2 ? uploadBean.getData().get(1).getWatchUrl() : "",
                                order_type, orderallot_id, mData, reamke));
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
    }

    /**
     * 点击空白处，隐藏软键盘。
     *
     * @param view    隐藏键盘View
     * @param context 上下文
     */
    @Override
    public void inputClose(View view, Context context) {
        if (view instanceof EditText) {
            view.clearFocus();
        }
        try {
            InputMethodManager im = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全隐藏软键盘
     *
     * @param activity Activity
     * @param editText 需要隐藏键盘的EditText
     */
    @Override
    public void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.findFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 显示输入法
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        // 光标设置在最后
        editText.setSelection(editText.getText().toString().length());
    }

}
