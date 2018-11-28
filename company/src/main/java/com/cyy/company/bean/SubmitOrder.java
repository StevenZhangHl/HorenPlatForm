package com.cyy.company.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/10/23/13:06
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class SubmitOrder implements Serializable {

    /**
     * order_id : SCN10210118082502
     */

    private String order_id;
    private String order_idS;
    private String order_idZ;

    private ArrayList<String> order_list;

    public ArrayList<String> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(ArrayList<String> order_list) {
        this.order_list = order_list;
    }

    public String getOrder_idS() {
        return order_idS;
    }

    public void setOrder_idS(String order_idS) {
        this.order_idS = order_idS;
    }

    public String getOrder_idZ() {
        return order_idZ;
    }

    public void setOrder_idZ(String order_idZ) {
        this.order_idZ = order_idZ;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
