package com.horen.partner.bean;

import com.horen.base.bean.BaseEntry;

import java.io.Serializable;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/9 8:50
 * Description:This isCustomerBean
 */
public class CustomerBean  implements Serializable{

    /**
     * customer_id : 测试内容6dhq
     * customer_name : 测试内容26yc
     * requirements : 测试内容5g4i
     * create_date : 52562
     * city_id : 测试内容k2b2
     * customer_contact : 测试内容229d
     * state_id : 测试内容nh3b
     * county_id : 测试内容6fp4
     * customer_mail : 测试内容jn76
     * end_date : 26047
     * photo_urls : ["string1","string2","string3","string4","string5"]
     * street : 测试内容53pi
     * support_status : 测试内容ysrq
     * start_date : 61822
     * customer_address : 测试内容2sl0
     * country_id : 测试内容hyn3
     * customer_industry : 测试内容2f22
     * status : 测试内容cv1h
     */

    private String customer_id;
    private String customer_name;
    private String requirements;
    private String create_date;
    private String city_id;
    private String customer_contact;
    private String state_id;
    private String county_id;
    private String customer_mail;
    private String end_date;
    private String street;
    private String support_status;
    private String start_date;
    private String customer_address;
    private String country_id;
    private String customer_industry;
    private String status;
    private List<String> photo_urls;
    private String customer_tel;
    private String industry_name;

    public String getIndustry_name() {
        return industry_name;
    }

    public void setIndustry_name(String industry_name) {
        this.industry_name = industry_name;
    }

    public String getCustomer_tel() {
        return customer_tel;
    }

    public void setCustomer_tel(String customer_tel) {
        this.customer_tel = customer_tel;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCustomer_contact() {
        return customer_contact;
    }

    public void setCustomer_contact(String customer_contact) {
        this.customer_contact = customer_contact;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

    public String getCustomer_mail() {
        return customer_mail;
    }

    public void setCustomer_mail(String customer_mail) {
        this.customer_mail = customer_mail;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSupport_status() {
        return support_status;
    }

    public void setSupport_status(String support_status) {
        this.support_status = support_status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCustomer_industry() {
        return customer_industry;
    }

    public void setCustomer_industry(String customer_industry) {
        this.customer_industry = customer_industry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPhoto_urls() {
        return photo_urls;
    }

    public void setPhoto_urls(List<String> photo_urls) {
        this.photo_urls = photo_urls;
    }
}
