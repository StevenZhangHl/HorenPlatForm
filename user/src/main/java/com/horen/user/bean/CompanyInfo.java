package com.horen.user.bean;

/**
 * @author :ChenYangYi
 * @date :2018/10/18/15:28
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class CompanyInfo {

    /**
     * pdList : {"company_address":"京东A京东A京东A","company_contact":"京东","company_mail":"dfjdkf@qq.com","company_name":"京东A","company_tel":"18112312222","consulting_mobile":"","consulting_name":"李磊的业务商","create_date":"2018-09-26 10:50:55","photo":"http://61.153.224.202:9106/upload/photo/2018-10-12-acfd2ef6-5d87-445e-b875-ed2df668ba1a.jpg","token":"3c50ca2d4905e043b5b8b3f57e47728f8295e352","update_date":"2018-10-18 12:47:52","user_id":"25749959802373022","user_mail":"dfjdkf@qq.com","user_mobile":"18175541587","user_name":"JDA","user_nickname":"京东","user_password":"3c50ca2d4905e043b5b8b3f57e47728f8295e352"}
     */

    private PdListBean pdList;

    public PdListBean getPdList() {
        return pdList;
    }

    public void setPdList(PdListBean pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * company_address : 京东A京东A京东A
         * company_contact : 京东
         * company_mail : dfjdkf@qq.com
         * company_name : 京东A
         * company_tel : 18112312222
         * consulting_mobile :
         * consulting_name : 李磊的业务商
         * create_date : 2018-09-26 10:50:55
         * photo : http://61.153.224.202:9106/upload/photo/2018-10-12-acfd2ef6-5d87-445e-b875-ed2df668ba1a.jpg
         * token : 3c50ca2d4905e043b5b8b3f57e47728f8295e352
         * update_date : 2018-10-18 12:47:52
         * user_id : 25749959802373022
         * user_mail : dfjdkf@qq.com
         * user_mobile : 18175541587
         * user_name : JDA
         * user_nickname : 京东
         * user_password : 3c50ca2d4905e043b5b8b3f57e47728f8295e352
         */

        private String company_address;
        private String company_contact;
        private String company_mail;
        private String company_name;
        private String company_tel;
        private String consulting_mobile;
        private String consulting_name;
        private String create_date;
        private String photo;
        private String token;
        private String update_date;
        private String user_id;
        private String user_mail;
        private String user_mobile;
        private String user_name;
        private String user_nickname;
        private String user_password;

        public String getCompany_address() {
            return company_address;
        }

        public void setCompany_address(String company_address) {
            this.company_address = company_address;
        }

        public String getCompany_contact() {
            return company_contact;
        }

        public void setCompany_contact(String company_contact) {
            this.company_contact = company_contact;
        }

        public String getCompany_mail() {
            return company_mail;
        }

        public void setCompany_mail(String company_mail) {
            this.company_mail = company_mail;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_tel() {
            return company_tel;
        }

        public void setCompany_tel(String company_tel) {
            this.company_tel = company_tel;
        }

        public String getConsulting_mobile() {
            return consulting_mobile;
        }

        public void setConsulting_mobile(String consulting_mobile) {
            this.consulting_mobile = consulting_mobile;
        }

        public String getConsulting_name() {
            return consulting_name;
        }

        public void setConsulting_name(String consulting_name) {
            this.consulting_name = consulting_name;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_mail() {
            return user_mail;
        }

        public void setUser_mail(String user_mail) {
            this.user_mail = user_mail;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }
    }
}
