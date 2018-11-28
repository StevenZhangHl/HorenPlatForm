package com.horen.user.mvp.presenter;

import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.user.api.UserApiPram;
import com.horen.user.bean.OrgInfo;
import com.horen.user.mvp.contract.BusinessScopeContract;

/**
 * Created by HOREN on 2018/2/5.
 */
public class BusinessScopePresenter extends BusinessScopeContract.Presenter {

    /**
     * 获取网点信息
     */
    @Override
    public void getOrgInfo() {
        mRxManager.add(mModel.getOrgInfo(UserApiPram.getDefaultPram())
                .subscribeWith(new BaseObserver<OrgInfo>(mContext, true) {
                    @Override
                    protected void onSuccess(OrgInfo orgInfo) {
                        mView.getOrgInfoSuccess(orgInfo);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }

    /**
     * 个人中心-修改联系地址
     *
     * @param org_address   网点地址
     * @param org_id        网点ID
     * @param org_latitude  纬度
     * @param org_longitude 经度
     */
    @Override
    public void updateOrgAddress(final String org_address, String org_id, final double org_latitude, final double org_longitude) {
        mRxManager.add(mModel.updateOrgAddress(UserApiPram.updateOrgAddress(org_address, org_id, org_latitude, org_longitude))
                .subscribeWith(new BaseObserver<BaseEntry>(mContext, true) {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        mView.updateOrgAddressSuccess(org_address, org_latitude, org_longitude);
                    }

                    @Override
                    protected void onError(String message) {
                        mView.onError(message);
                    }
                }));
    }
}