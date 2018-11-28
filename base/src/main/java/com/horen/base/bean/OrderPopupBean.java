package com.horen.base.bean;

/**
 * Created by HOREN on 2017/11/1.
 */

public class OrderPopupBean {
    public String name;
    public String type;

    public OrderPopupBean(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
