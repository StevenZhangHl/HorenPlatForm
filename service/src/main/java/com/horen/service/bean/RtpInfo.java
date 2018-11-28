package com.horen.service.bean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/22/13:09
 * @description :箱子扫码信息
 * @github :https://github.com/chenyy0708
 */
public class RtpInfo {


    /**
     * positionList : [{"position":"111","position_id":"555","product_type":"OF330"},{"position":"222","position_id":"666","product_type":"OF330"},{"position":"333","position_id":"777","product_type":"OF330"}]
     * rtpInfo : {"ctnr_sn":"010102006 001173","product_id":"01.0102.006","product_name":"超立方OF330食品","product_photo":"http://61.153.224.202:9096/upload/photo/OF330-green.png","product_type":"OF330"}
     */

    private RtpInfoBean rtpInfo;
    private List<PositionListBean> positionList;

    public RtpInfoBean getRtpInfo() {
        return rtpInfo;
    }

    public void setRtpInfo(RtpInfoBean rtpInfo) {
        this.rtpInfo = rtpInfo;
    }

    public List<PositionListBean> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<PositionListBean> positionList) {
        this.positionList = positionList;
    }
}
