package com.horen.user.ui.activity.accout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.widget.HRTitle;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.bean.CompanyInfo;

/**
 * @author :ChenYangYi
 * @date :2018/10/18/15:12
 * @description :所属企业信息
 * @github :https://github.com/chenyy0708
 */
public class CompanyInfoActivity extends BaseActivity {

    private HRTitle mToolBar;
    private TextView mTvCompanyName;
    private TextView mTvCompanyAddress;
    private TextView mTvCompanyContact;
    private TextView mTvCompanyMobile;
    private TextView mTvCompanyMail;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, CompanyInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_company_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mTvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mTvCompanyAddress = (TextView) findViewById(R.id.tv_company_address);
        mTvCompanyContact = (TextView) findViewById(R.id.tv_company_contact);
        mTvCompanyMobile = (TextView) findViewById(R.id.tv_company_mobile);
        mTvCompanyMail = (TextView) findViewById(R.id.tv_company_mail);
        mToolBar.bindActivity(this, R.color.white);
        // 获取企业信息
        getPCSysUserInfo();

    }

    private void getPCSysUserInfo() {
        mRxManager.add(ApiUser.getInstance().getPCSysUserInfo(UserApiPram.getDefaultPram())
                .compose(RxHelper.<CompanyInfo>getResult())
                .subscribeWith(new BaseObserver<CompanyInfo>() {
                    @Override
                    protected void onSuccess(CompanyInfo info) {
                        mTvCompanyName.setText(info.getPdList().getCompany_name());
                        mTvCompanyAddress.setText(info.getPdList().getCompany_address());
                        mTvCompanyContact.setText(info.getPdList().getCompany_contact());
                        mTvCompanyMobile.setText(info.getPdList().getCompany_tel());
                        mTvCompanyMail.setText(info.getPdList().getCompany_mail());
                    }

                    @Override
                    protected void onError(String message) {
                    }
                }));
    }
}
