package com.horen.partner.bean;

import com.horen.base.bean.BaseEntry;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/6 9:08
 * Description:This isApiResultHomeData
 */
public class ApiResultHomeData extends BaseEntry{
    public List<HomeBanner> solution_homebanners;//首页轮播图列表
    public List<Partner> solution_homecompanys;//首页盟友列表
    public List<PlanTypeList> solution_homehotsolutions;//首页热门方案列表
}
