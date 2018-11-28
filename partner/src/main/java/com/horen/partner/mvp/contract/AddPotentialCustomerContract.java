package com.horen.partner.mvp.contract;

import android.webkit.SafeBrowsingResponse;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.TypeBean;
import com.horen.base.bean.UploadBean;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.partner.bean.IndustryBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Author:Steven
 * Time:2018/8/7 10:16
 * Description:This isCustomerContract
 */
public interface AddPotentialCustomerContract {
    interface Model extends BaseModel {
        Observable<List<IndustryBean>> getIndustryData(RequestBody body);

        Observable<BaseEntry> editCustomerInfo(RequestBody body);

    }

    interface View extends BaseView {
        void submitSuccess(String result);

        void updateSuccess(String result);

        void getSeclectIndustryData(TypeBean typeBean);

        void setSelectCityInfo(String province, String city, String area);

        void submitFail(String result);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getIndustryData();

        public abstract void showSelectCityPicker();

        public abstract void getCityData();

        public abstract void removeHandler();

        public abstract void showSelectIndustryDialog(BaseActivity activity, String selectedId);

        public abstract void uploadPotentialInfo(List<String> imageLists, String state_id, String city_id, String country_id, String county_id, String customer_address, String customer_contact, String customer_industry, String customer_mail, String customer_name, String customer_tel, String photo_desc, String requirements);

        public abstract void editCustomerInfo(String customer_id, String state_id, String city_id, String county_id, String customer_address, String customer_contact, String customer_industry, String customer_name, String customer_mail, String customer_tel, String requirements, List<String> imageLists);
    }
}
