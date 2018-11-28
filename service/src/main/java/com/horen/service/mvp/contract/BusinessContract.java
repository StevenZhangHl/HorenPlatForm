package com.horen.service.mvp.contract;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import com.horen.base.bean.BaseEntry;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.service.bean.OrderDetail;
import com.horen.service.bean.StorageSubmitBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/13:59
 * @description :业务中心契约类
 * @github :https://github.com/chenyy0708
 */
public interface BusinessContract {
    interface Model extends BaseModel {
        /**
         * 任务单详情查询
         */
        Observable<OrderDetail> getAllotAndTransInfo(RequestBody body);

        /**
         * 任务单确认
         */
        Observable<BaseEntry> addStorage(RequestBody body);
    }

    interface View extends BaseView {
        /**
         * 获取任务单详情成功
         */
        void getInfoSuccess(OrderDetail orderDetail);

        /**
         * 提交成功
         */
        void submitSuccess();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 任务单详情查询
         *
         * @param order_type    任务单类型
         * @param orderallot_id 任务单ID
         * @param status        任务单状态  待处理、已完成
         */
        public abstract void getAllotAndTransInfo(String order_type, String orderallot_id, String status);

        /**
         * 任务单确认
         */
        public abstract void addStorage(RequestBody body);

        /**
         * 点击空白处，隐藏软键盘。
         *
         * @param view    隐藏键盘View
         * @param context 上下文
         */
        public abstract void inputClose(android.view.View view, Context context);

        /**
         * 安全隐藏软键盘
         *
         * @param activity Activity
         * @param editText 需要隐藏键盘的EditText
         */
        public abstract void showSoftInputFromWindow(Activity activity, EditText editText);

        /**
         * 提交，交割
         *
         * @param order_type    任务单类型
         * @param orderallot_id 任务单ID
         * @param mImageUrls    图片集合
         * @param reamke        备注
         * @param mData         上传参数
         */
        public abstract void submit(String order_type, String orderallot_id, List<String> mImageUrls, String reamke, List<StorageSubmitBean> mData);
    }
}
