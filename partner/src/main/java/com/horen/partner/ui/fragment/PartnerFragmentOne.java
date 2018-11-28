package com.horen.partner.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.horen.base.base.BaseFragment;
import com.horen.partner.R;

/**
 * Author:Steven
 * Time:2018/8/28 9:33
 * Description:This isPartnerFragmentOne
 */
public class PartnerFragmentOne extends BaseFragment {

    /**
     * 申请流程
     */
    public static final int APPLICATION_PROCESS = 0;
    /**
     * 条件及要求
     */
    public static final int CONDITIONS_REQUIREMENTS = 1;
    /**
     * 合作规则
     */
    public static final int COOPERATION_RULES = 2;

    private LinearLayout mLlApplicaitonProcess;
    private LinearLayout mLlConditionsRequirements;
    private LinearLayout mLlCooperationRules;

    public static PartnerFragmentOne newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        PartnerFragmentOne fragment = new PartnerFragmentOne();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_partner_one;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlApplicaitonProcess = (LinearLayout) rootView.findViewById(R.id.ll_applicaiton_process);
        mLlConditionsRequirements = (LinearLayout) rootView.findViewById(R.id.ll_conditions_requirements);
        mLlCooperationRules = (LinearLayout) rootView.findViewById(R.id.ll_cooperation_rules);
        int type = getArguments().getInt("type");
        switch (type) {
            case APPLICATION_PROCESS:
                mLlApplicaitonProcess.setVisibility(View.VISIBLE);
                break;
            case CONDITIONS_REQUIREMENTS:
                mLlConditionsRequirements.setVisibility(View.VISIBLE);
                break;
            case COOPERATION_RULES:
                mLlCooperationRules.setVisibility(View.VISIBLE);
                break;
        }
    }

}
