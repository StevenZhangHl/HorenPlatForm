package com.horen.partner.bean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/17 13:16
 * Description:This isPropertyBean 租赁资产
 */
public class PropertyBean {

    /**
     * total_num : 27054
     * total_loss : 26500
     * total_normal : 55616
     * total_empty : 52775
     * ctnr_type : 测试内容px3u
     * total_full : 78318
     * ctnr_name : 测试内容ij7b
     */

    private int total_num;
    private int total_loss;
    private int total_normal;
    private int total_empty;
    private String ctnr_type;
    private int total_full;
    private String ctnr_name;

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getTotal_loss() {
        return total_loss;
    }

    public void setTotal_loss(int total_loss) {
        this.total_loss = total_loss;
    }

    public int getTotal_normal() {
        return total_normal;
    }

    public void setTotal_normal(int total_normal) {
        this.total_normal = total_normal;
    }

    public int getTotal_empty() {
        return total_empty;
    }

    public void setTotal_empty(int total_empty) {
        this.total_empty = total_empty;
    }

    public String getCtnr_type() {
        return ctnr_type;
    }

    public void setCtnr_type(String ctnr_type) {
        this.ctnr_type = ctnr_type;
    }

    public int getTotal_full() {
        return total_full;
    }

    public void setTotal_full(int total_full) {
        this.total_full = total_full;
    }

    public String getCtnr_name() {
        return ctnr_name;
    }

    public void setCtnr_name(String ctnr_name) {
        this.ctnr_name = ctnr_name;
    }
}
