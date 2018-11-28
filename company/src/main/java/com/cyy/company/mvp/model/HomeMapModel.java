package com.cyy.company.mvp.model;

import com.cyy.company.api.ApiCompany;
import com.cyy.company.bean.HomeCoor;
import com.cyy.company.bean.HomeOkuraDetail;
import com.cyy.company.bean.HomeOrgDetail;
import com.cyy.company.mvp.contract.HomeMapContract;
import com.horen.base.rx.RxHelper;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/12:46
 * @description :百网千驿
 * @github :https://github.com/chenyy0708
 */
public class HomeMapModel implements HomeMapContract.Model {

    /**
     * 百网千驿坐标查询
     */
    @Override
    public Observable<HomeCoor> getHomeCoorList(RequestBody body) {
        return ApiCompany.getInstance().getHomeCoorList(body)
                .compose(RxHelper.<HomeCoor>getResult());
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

    @Override
    public Observable<HomeOkuraDetail> getHomeOkuraList(RequestBody body) {
        return ApiCompany.getInstance().getHomeOkuraList(body)
                .compose(RxHelper.<HomeOkuraDetail>getResult());
    }
}
