package com.horen.user.mvp.model;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.RxHelper;
import com.horen.user.api.ApiUser;
import com.horen.user.bean.OrgInfo;
import com.horen.user.mvp.contract.BusinessScopeContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/14:05
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BusinessScopeModel implements BusinessScopeContract.Model {

    @Override
    public Observable<OrgInfo> getOrgInfo(RequestBody body) {
        return ApiUser.getInstance().userOrgInfo(body)
                .compose(RxHelper.<OrgInfo>getResult());
    }

    @Override
    public Observable<BaseEntry> updateOrgAddress(RequestBody body) {
        return ApiUser.getInstance().updateOrgAddress(body)
                .compose(RxHelper.handleResult());
    }
}
