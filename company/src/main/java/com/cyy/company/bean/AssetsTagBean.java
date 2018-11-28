package com.cyy.company.bean;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/14:26
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class AssetsTagBean {
    private String name;

    public AssetsTagBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
