package com.cyy.company.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/15:54
 * @description :首页大仓产品信息
 * @github :https://github.com/chenyy0708
 */
public class MapOkuraBean {
    private String type;

    private List<HomeOkuraDetail.PdListBean> mData;

    public MapOkuraBean(String type, List<HomeOkuraDetail.PdListBean> mData) {
        this.type = type;
        this.mData = mData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HomeOkuraDetail.PdListBean> getmData() {
        return mData;
    }

    public void setmData(List<HomeOkuraDetail.PdListBean> mData) {
        this.mData = mData;
    }
}
