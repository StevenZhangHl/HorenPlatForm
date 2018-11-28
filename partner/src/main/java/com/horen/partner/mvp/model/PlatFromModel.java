package com.horen.partner.mvp.model;

import com.horen.base.rx.RxHelper;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.ApiResultHomeData;
import com.horen.partner.bean.ApiResultPartnerList;
import com.horen.partner.mvp.contract.PlatformHomeContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Author:Steven
 * Time:2018/8/27 15:41
 * Description:This isPlatFromModel
 */
public class PlatFromModel implements PlatformHomeContract.Model {
    @Override
    public Observable<ApiResultHomeData> getData() {
        return ApiPartner.getInstance().getHomeData(ApiRequest.getPlatFormHomeData()).compose(RxHelper.<ApiResultHomeData>handleResult());
    }
}
