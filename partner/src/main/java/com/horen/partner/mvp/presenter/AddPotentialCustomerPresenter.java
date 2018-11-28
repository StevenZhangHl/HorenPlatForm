package com.horen.partner.mvp.presenter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.TypeBean;
import com.horen.base.bean.UploadBean;
import com.horen.base.listener.TypeSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.HRException;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.AssetUtil;
import com.horen.base.util.GsonUtil;
import com.horen.base.util.LogUtils;
import com.horen.base.widget.LoadingDialog;
import com.horen.base.widget.TranslateLoadingDialog;
import com.horen.partner.R;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.CityBaseBean;
import com.horen.partner.bean.IndustryBean;
import com.horen.partner.mvp.contract.AddPotentialCustomerContract;
import com.horen.partner.ui.widget.CustomDialog;
import com.horen.partner.ui.widget.SelectCustomDialog;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import io.reactivex.Observable;

/**
 * Author:Steven
 * Time:2018/8/7 17:41
 * Description:This isCustomerPresenter
 */
public class AddPotentialCustomerPresenter extends AddPotentialCustomerContract.Presenter {
    private List<CityBaseBean> options1Items = new ArrayList<>();
    private List<ArrayList<String>> options2Items = new ArrayList<>();
    private List<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private List<IndustryBean> industriesBeanList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;
    private TranslateLoadingDialog loadingDialog;
    private List<TypeBean> typeBeanList = new ArrayList<>();

    @Override
    public void getIndustryData() {
        mRxManager.add(mModel.getIndustryData(ApiRequest.getIndustryData()).subscribeWith(new BaseObserver<List<IndustryBean>>(mContext, false) {
            @Override
            protected void onSuccess(List<IndustryBean> industryBean) {
                industriesBeanList.addAll(industryBean);
                getTypeData(industryBean);
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    public ArrayList<CityBaseBean> parseData(String result) {//Gson 解析
        ArrayList<CityBaseBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityBaseBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityBaseBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private void initJsonData() {
        String JsonData = new AssetUtil().getJson(mContext, "province.json");//获取assets目录下的json文件数据
        List<CityBaseBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    @Override
    public void showSelectCityPicker() {
        if (isLoaded) {
            OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    mView.setSelectCityInfo(options1Items.get(options1).getPickerViewText(), options2Items.get(options1).get(options2), options3Items.get(options1).get(options2).get(options3));

                }
            })
                    .setTitleText("选择城市地区")
                    .setDividerColor(Color.BLACK)
                    .setCancelColor(mContext.getResources().getColor(R.color.color_333))
                    .setSubmitColor(mContext.getResources().getColor(R.color.color_333))
                    .setTextColorCenter(mContext.getResources().getColor(R.color.color_666)) //设置选中项文字颜色
                    .setContentTextSize(18)
                    .build();
            pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
            pvOptions.show();
        }
    }

    @Override
    public void getCityData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    public void removeHandler() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void showSelectIndustryDialog(BaseActivity activity, String selectId) {
        refreshCheckStatesById(selectId);
        CustomDialog dialog = new CustomDialog(activity, typeBeanList, "行业", selectId);
        dialog.setSelectListener(new TypeSelectListener() {
            @Override
            public void onSubmitClick(TypeBean type, int position) {
                mView.getSeclectIndustryData(type);
                refreshCheckStates(position);
            }
        });
        dialog.show();
    }

    private void refreshCheckStatesById(String selectId) {
        for (int i = 0; i < typeBeanList.size(); i++) {
            if (typeBeanList.get(i).getTypeId() == selectId) {
                typeBeanList.get(i).setChecked(true);
            } else {
                typeBeanList.get(i).setChecked(false);
            }
        }
    }

    private void refreshCheckStates(int position) {
        for (int i = 0; i < typeBeanList.size(); i++) {
            if (i == position) {
                typeBeanList.get(i).setChecked(true);
            } else {
                typeBeanList.get(i).setChecked(false);
            }
        }
    }

    /**
     * 转换数据
     *
     * @param list
     * @return
     */
    private void getTypeData(List<IndustryBean> list) {
        for (int i = 0; i < list.size(); i++) {
            TypeBean bean = new TypeBean(list.get(i).getIndustry_id(), list.get(i).getIndustry_name(), false);
            typeBeanList.add(bean);
        }
    }

    @Override
    public void uploadPotentialInfo(List<String> imageLists, final String state_id, final String city_id, final String country_id, final String county_id, final String customer_address, final String customer_contact, final String customer_industry, final String customer_mail, final String customer_name, final String customer_tel, final String photo_desc, final String requirements) {
        loadingDialog = new TranslateLoadingDialog();
        loadingDialog.showDialogForLoading(mContext, false);
        if (imageLists.size() != 0) {
            final List<File> files = new ArrayList<>();
            // 上传图片带压缩
            mRxManager.add(Observable.just(imageLists)
                    // 压缩图片
                    .compose(RxHelper.uploadFile(files))
                    // 返回地址
                    .subscribeWith(new BaseObserver<UploadBean>() {
                        @Override
                        protected void onSuccess(UploadBean uploadBean) {
                            LogUtils.d("Thread:" + Thread.currentThread().getName());
                            // 删除上传成功的本地压缩图片
                            if (uploadBean.getCode().equals("000000")) {
                                List<String> resultImages = new ArrayList<>();
                                for (int i = 0; i < uploadBean.getData().size(); i++) {
                                    resultImages.add(uploadBean.getData().get(i).getWatchUrl());
                                }
                                mRxManager.add(ApiPartner.getInstance().uploadPotentialData(ApiRequest.uploadPotentialCustomerData(city_id, state_id, country_id, county_id, customer_address, customer_contact, customer_industry, customer_mail, customer_name, customer_tel, photo_desc, requirements, resultImages)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {

                                    @Override
                                    protected void onSuccess(BaseEntry o) {
                                        if (o.success()) {
                                            mView.submitSuccess("客户新增成功");
                                            loadingDialog.cancelDialogForLoading();
                                        }
                                    }

                                    @Override
                                    protected void onError(String message) {
                                        mView.onError(message);
                                        mView.submitFail(message);
                                        loadingDialog.cancelDialogForLoading();
                                    }
                                }));

                            } else {
                                Observable.error(new HRException(uploadBean.getCode()));
                                loadingDialog.cancelDialogForLoading();
                            }
                            for (File file : files) {
                                file.delete();
                            }

                        }

                        @Override
                        protected void onError(String message) {
                            System.out.println(message);
                            mView.submitFail(message);
                            loadingDialog.cancelDialogForLoading();
                        }
                    }));
        } else {
            mRxManager.add(ApiPartner.getInstance().uploadPotentialData(ApiRequest.uploadPotentialCustomerData(city_id, state_id, country_id, county_id, customer_address, customer_contact, customer_industry, customer_mail, customer_name, customer_tel, photo_desc, requirements, imageLists)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {

                @Override
                protected void onSuccess(BaseEntry o) {
                    if (o.success()) {
                        mView.submitSuccess("客户新增成功");
                        loadingDialog.cancelDialogForLoading();
                    }
                }

                @Override
                protected void onError(String message) {
                    mView.onError(message);
                    mView.submitFail(message);
                    loadingDialog.cancelDialogForLoading();
                }
            }));
        }
    }

    @Override
    public void editCustomerInfo(final String customer_id, final String state_id, final String city_id, final String county_id, final String customer_address, final String customer_contact, final String customer_industry, final String customer_name, final String customer_mail, final String customer_tel, final String requirements, final List<String> imageLists) {
        List<String> uploadFiles = new ArrayList<>();
        final List<String> serverFiles = new ArrayList<>();
        for (int i = 0; i < imageLists.size(); i++) {
            if (imageLists.get(i).startsWith("http://")) {
                serverFiles.add(imageLists.get(i));
            } else {
                uploadFiles.add(imageLists.get(i));
            }
        }
        loadingDialog = new TranslateLoadingDialog();
        loadingDialog.showDialogForLoading(mContext, false);
        if (uploadFiles.size() != 0) {
            final List<File> files = new ArrayList<>();
            // 上传图片带压缩
            mRxManager.add(Observable.just(uploadFiles)
                    // 压缩图片
                    .compose(RxHelper.uploadFile(files))
                    // 返回地址
                    .subscribeWith(new BaseObserver<UploadBean>() {
                        @Override
                        protected void onSuccess(UploadBean uploadBean) {
                            LogUtils.d("Thread:" + Thread.currentThread().getName());
                            // 删除上传成功的本地压缩图片
                            if (uploadBean.getCode().equals("000000")) {
                                List<String> resultImages = new ArrayList<>();
                                for (int i = 0; i < uploadBean.getData().size(); i++) {
                                    resultImages.add(uploadBean.getData().get(i).getWatchUrl());
                                }
                                resultImages.addAll(serverFiles);
                                mRxManager.add(mModel.editCustomerInfo(ApiRequest.updateCustomerInfo(customer_id, state_id, city_id, county_id, customer_address, customer_contact, customer_industry, customer_mail, customer_name, customer_tel, requirements, resultImages)).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
                                    @Override
                                    protected void onSuccess(BaseEntry baseEntry) {
                                        mView.updateSuccess("客户修改成功");
                                        loadingDialog.cancelDialogForLoading();
                                    }

                                    @Override
                                    protected void onError(String message) {
                                        mView.onError(message);
                                        mView.submitFail(message);
                                        loadingDialog.cancelDialogForLoading();
                                    }
                                }));

                            } else {
                                Observable.error(new HRException(uploadBean.getCode()));
                                loadingDialog.cancelDialogForLoading();
                            }
                            for (File file : files) {
                                file.delete();
                            }

                        }

                        @Override
                        protected void onError(String message) {
                            System.out.println(message);
                            mView.submitFail(message);
                            loadingDialog.cancelDialogForLoading();
                        }
                    }));
        } else {
            mRxManager.add(mModel.editCustomerInfo(ApiRequest.updateCustomerInfo(customer_id, state_id, city_id, county_id, customer_address, customer_contact, customer_industry, customer_mail, customer_name, customer_tel, requirements, serverFiles)).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
                @Override
                protected void onSuccess(BaseEntry baseEntry) {
                    mView.updateSuccess("客户修改成功");
                    loadingDialog.cancelDialogForLoading();
                }

                @Override
                protected void onError(String message) {
                    mView.submitFail(message);
                    loadingDialog.cancelDialogForLoading();
                }
            }));
        }

    }

}
