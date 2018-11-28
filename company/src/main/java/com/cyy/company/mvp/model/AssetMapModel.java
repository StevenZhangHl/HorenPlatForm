package com.cyy.company.mvp.model;

import com.cyy.company.api.ApiCompany;
import com.cyy.company.bean.AssetMapListBean;
import com.cyy.company.bean.HomeOrgDetail;
import com.cyy.company.mvp.contract.AssetMapContract;
import com.horen.base.rx.RxHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/12:46
 * @description :资产分布
 * @github :https://github.com/chenyy0708
 */
public class AssetMapModel implements AssetMapContract.Model {

    /**
     * 坐标查询
     */
    @Override
    public Observable<AssetMapListBean> getOrgMapList(RequestBody body) {
        return ApiCompany.getInstance().getOrgMapList(body)
                .compose(RxHelper.<AssetMapListBean>getResult());
    }

    @Override
    public Observable<AssetMapListBean> getOrgDownMapList(RequestBody body) {
        return ApiCompany.getInstance().getOrgDownMapList(body)
                .compose(RxHelper.<AssetMapListBean>getResult());
    }

    /**
     * 根据百网ID,产新信息查询
     *
     * @param body
     */
    @Override
    public Observable<HomeOrgDetail> getOrgByIdsProType(RequestBody body) {
        return ApiCompany.getInstance().getOrgByIdsProType(body)
                .compose(RxHelper.<HomeOrgDetail>getResult());
    }
}
