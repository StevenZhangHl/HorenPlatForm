package com.horen.partner.bean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/6 9:08
 * Description:This isPlan
 */
public class Plan {
    List<PlanBean> solutions; // 方案图片列表，个数为 1、2、3

    public List<PlanBean> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<PlanBean> solutions) {
        this.solutions = solutions;
    }
}
