package com.horen.partner.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/14 14:41
 * Description:This isVisiteNoteBaseBean
 */
public class VisiteNoteBaseBean implements Serializable{

    /**
     * visit_name : 测试内容nbv2
     * user_id : 测试内容j0to
     * visit_tel : 测试内容v72n
     * visit_content : 测试内容74s4
     * visit_id : 测试内容2tfo
     * visit_date : 46330
     * visit_addr : 测试内容8n73
     * photosUrl : ["string1","string2","string3","string4","string5"]
     * customer_id : 测试内容ued2
     */

    private String visit_name;
    private String user_id;
    private String visit_tel;
    private String visit_content;
    private String visit_id;
    private String visit_date;
    private String visit_addr;
    private String customer_id;
    private List<String> photo_urls;

    public String getVisit_name() {
        return visit_name;
    }

    public void setVisit_name(String visit_name) {
        this.visit_name = visit_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVisit_tel() {
        return visit_tel;
    }

    public void setVisit_tel(String visit_tel) {
        this.visit_tel = visit_tel;
    }

    public String getVisit_content() {
        return visit_content;
    }

    public void setVisit_content(String visit_content) {
        this.visit_content = visit_content;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getVisit_addr() {
        return visit_addr;
    }

    public void setVisit_addr(String visit_addr) {
        this.visit_addr = visit_addr;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public List<String> getPhotosUrl() {
        return photo_urls;
    }

    public void setPhotosUrl(List<String> photosUrl) {
        this.photo_urls = photosUrl;
    }

}
