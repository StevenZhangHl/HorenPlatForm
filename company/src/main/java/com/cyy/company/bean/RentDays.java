package com.cyy.company.bean;

// FIXME generate failure  field _$PdList350

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/16:16
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class RentDays {

    private List<PdListBean> pdList;

    public List<PdListBean> getPdList() {
        return pdList;
    }

    public void setPdList(List<PdListBean> pdList) {
        this.pdList = pdList;
    }

    public static class PdListBean {
        /**
         * set_rentdays : 30
         */

        private int set_rentdays;

        public int getSet_rentdays() {
            return set_rentdays;
        }

        public void setSet_rentdays(int set_rentdays) {
            this.set_rentdays = set_rentdays;
        }
    }
}
