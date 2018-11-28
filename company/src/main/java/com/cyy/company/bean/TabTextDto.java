package com.cyy.company.bean;

import java.io.Serializable;

/**
 * Created by Zhao on 2017/11/8/008.
 */

public class TabTextDto implements Serializable {

    private String text;//全部
    private String position;//0

    public TabTextDto() {
    }

    public TabTextDto(String text, String position) {
        this.text = text;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "text='" + text + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

}
