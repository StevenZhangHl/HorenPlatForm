package com.horen.base.bean;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/16:46
 * @description :多选Bean
 * @github :https://github.com/chenyy0708
 */
public class RadioSelectBean {

    public RadioSelectBean(String tabName) {
        this.tabName = tabName;
    }

    public RadioSelectBean(String tabName,String id) {
        this.tabName = tabName;
        this.id = id;
    }
    public RadioSelectBean(String tabName, boolean isSelect) {
        this.tabName = tabName;
        this.isSelect = isSelect;
    }

    /**
     * 属性名
     */
    private String tabName;
    private String id;
    /**
     * 是否选择
     */
    private boolean isSelect;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
