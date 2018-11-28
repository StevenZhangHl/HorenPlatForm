package com.horen.user.ui.activity.accout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.LoginBean;
import com.horen.base.constant.EventBusCode;
import com.horen.base.constant.MsgEvent;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.EditTextHelper;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.RippleButton;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;

import org.simple.eventbus.EventBus;

/**
 * @author :ChenYangYi
 * @date :2018/09/05/09:51
 * @description :更改联系人
 * @github :https://github.com/chenyy0708
 */
public class ChangeContactsActivity extends BaseActivity implements EditTextHelper.EditTextInputListener {

    private EditText mEtContact;
    private RippleButton mRbtSave;

    /**
     * 更新姓名
     */
    public static final int NAME = 1;
    /**
     * 更新联系人
     */
    public static final int NAME_CONTACT = 2;
    private String name;
    private int type;
    private String org_id;

    public static void startActivity(Context context, String name, int type) {
        startActivity(context, name, type, "");
    }

    public static void startActivity(Context context, String name, int type, String org_id) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        intent.putExtra("org_id", org_id);
        intent.setClass(context, ChangeContactsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_change_contacts;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("更改联系人", R.color.white);
        getTitleBar().setBackgroundResource(R.color.white);
        mEtContact = (EditText) findViewById(R.id.et_contact);
        mRbtSave = (RippleButton) findViewById(R.id.rbt_save);
        name = getIntent().getStringExtra("name");
        org_id = getIntent().getStringExtra("org_id");
        type = getIntent().getIntExtra("type", NAME);
        new EditTextHelper.Builder()
                .addEditTexts(mEtContact)
                .addInputLimit(2)
                .addOnInputListener(this)
                .build();
        mEtContact.setText(name);
        mEtContact.setSelection(mEtContact.getText().toString().length());
        if (type == NAME) { // 更新姓名
            showWhiteTitle("姓名", R.color.white);
            mEtContact.setHint("请输入真实姓名");
        } else if (type == NAME_CONTACT) { // 业务范围更新联系人
            showWhiteTitle("更改联系人", R.color.white);
        }
        // 修改绑定联系人
        mRbtSave.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == NAME) {
                    updateNickName();
                } else { // 更改网点联系人
                    updateOrgContact();
                }
            }
        });
    }

    /**
     * 更新联系人
     */
    private void updateNickName() {
        mRxManager.add(ApiUser.getInstance().updateNickName(UserApiPram.updateNickName(mEtContact.getText().toString().trim()))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    public void onStart() {
                        mRbtSave.showLoadingButton();
                    }

                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        mRbtSave.showGreenButton();
                        // 保存新用户名更新本地
                        showToast("修改姓名成功");
                        finish();
                        LoginBean userInfo = UserHelper.getUserInfo();
                        userInfo.getLoginInfo().setUser_nickname(mEtContact.getText().toString().trim());
                        // 更新本地绑定号码
                        UserHelper.saveUserInfo(userInfo);
                        EventBus.getDefault().post(new MsgEvent(EventBusCode.REFERSH_USER_INFO));
                    }

                    @Override
                    protected void onError(String message) {
                        mRbtSave.showRedButton(message);
                    }
                }));
    }

    /**
     * 更新网点联系人
     */
    private void updateOrgContact() {
        final String org_contact = mEtContact.getText().toString();
        mRxManager.add(ApiUser.getInstance().updateOrgContact(UserApiPram.updateOrgContact(org_contact, org_id))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    public void onStart() {
                        mRbtSave.showLoadingButton();
                    }

                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        mRbtSave.showGreenButton();
                        // 保存新用户名更新本地
                        finish();
                        // 通知业务范围更新联系人
                        EventBus.getDefault().post(org_contact, EventBusCode.CHANGE_ORG_CONTACT);
                    }

                    @Override
                    protected void onError(String message) {
                        mRbtSave.showRedButton(message);
                    }
                }));
    }

    @Override
    public void onSuccess() {
        mRbtSave.showGreenButton();
    }

    @Override
    public void onError() {
        mRbtSave.showGrayButton();
    }
}
