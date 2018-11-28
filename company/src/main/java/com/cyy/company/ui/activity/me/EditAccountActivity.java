package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AccountManager;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.EditTextHelper;
import com.horen.base.util.FormatUtil;
import com.horen.base.widget.HRTitle;
import com.horen.base.widget.PWEditText;

import org.simple.eventbus.EventBus;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/15:56
 * @description :编辑账号
 * @github :https://github.com/chenyy0708
 */
public class EditAccountActivity extends BaseActivity implements EditTextHelper.EditTextInputListener {

    private HRTitle mToolBar;
    private PWEditText mEtName;
    private PWEditText mEtPhone;
    private PWEditText mEtAccount;
    private PWEditText mEtPwd;
    private AccountManager.CustomerUsersBean.ListBean account;

    /**
     * 是否输入完毕
     */
    private boolean isSuccess;

    public static void startAction(Context context, AccountManager.CustomerUsersBean.ListBean account) {
        Intent intent = new Intent();
        intent.putExtra("account", account);
        intent.setClass(context, EditAccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_account;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mEtName = (PWEditText) findViewById(R.id.et_name);
        mEtPhone = (PWEditText) findViewById(R.id.et_phone);
        mEtAccount = (PWEditText) findViewById(R.id.et_account);
        mEtPwd = (PWEditText) findViewById(R.id.et_pwd);
        mToolBar.bindActivity(this, R.color.white);
        account = (AccountManager.CustomerUsersBean.ListBean) getIntent().getSerializableExtra("account");
        new EditTextHelper.Builder()
                .addEditTexts(mEtName, mEtAccount, mEtPhone, mEtPwd)
                .addOnInputListener(this)
                .build();
        // 保存
        mToolBar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSuccess) {
                    showToast("请填写完整信息");
                    return;
                }
                // 手机校验
                if (!FormatUtil.isMobileNO(mEtPhone.getText().toString().trim())) {
                    showToast("请填写正确的手机号");
                    return;
                }
                // 判断密码不能包含*
                if (mEtPwd.getText().toString().trim().contains("*") && !mEtPwd.getText().toString().trim().equals("********")) {
                    showToast("请输入正确的密码");
                    return;
                }
                // 保存
                saveAccount();
            }
        });
        // 编辑
        if (account != null) {
            // 姓名
            mEtName.setText(account.getUser_nickname());
            mEtName.setSelection(mEtName.getText().length());
            // 联系电话
            mEtPhone.setText(account.getUser_mobile());
            mEtPhone.setSelection(mEtPhone.getText().length());
            // 登陆账号
            mEtAccount.setText(account.getUser_name());
            mEtAccount.setSelection(mEtAccount.getText().length());
            // 密码
            mEtPwd.setText("********");
            mEtPwd.setSelection(mEtPwd.getText().length());
        } else { // 新增账号密码显示
            mEtPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    /**
     * 保存/新增
     */
    private void saveAccount() {
        // 如果account对象为null，代表新增
        final boolean isAdd = account == null;
        String user_id = isAdd ? "" : account.getUser_id();
        String user_mail = isAdd ? "" : account.getUser_mail();
        String user_mobile = isAdd ? mEtPhone.getText().toString() : account.getUser_mobile();
        String user_name = isAdd ? mEtAccount.getText().toString() : account.getUser_name();
        String user_nickname = isAdd ? mEtName.getText().toString() : account.getUser_nickname();
        // 如果密码框里还是******** 说明没有修改密码，传空
        String user_password = mEtPwd.getText().toString().equals("********") ? "" : mEtPwd.getText().toString();
        mRxManager.add(ApiCompany.getInstance().saveUserCust(CompanyParams.saveUserCust(user_id, user_mail,
                user_mobile, user_name, user_nickname, user_password))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>(mContext, true) {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        if (isAdd) {
                            showToast("已新增");
                        } else {
                            showToast("已修改");
                        }
                        EventBus.getDefault().post(EventBusCode.REFRESH_ACCOUNT,
                                EventBusCode.REFRESH_ACCOUNT);
                        finish();
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    @Override
    public void onSuccess() {
        isSuccess = true;
    }

    @Override
    public void onError() {
        isSuccess = false;
    }
}
