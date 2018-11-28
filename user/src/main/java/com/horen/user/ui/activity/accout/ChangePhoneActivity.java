package com.horen.user.ui.activity.accout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.MatcherUtils;
import com.horen.base.widget.RippleButton;
import com.horen.user.R;

/**
 * @author :ChenYangYi
 * @date :2018/09/05/09:51
 * @description :更改联系电话
 * @github :https://github.com/chenyy0708
 */
public class ChangePhoneActivity extends BaseActivity {

    private TextView mEtPhone;
    private RippleButton mRbtSave;

    /**
     * 更换手机号
     */
    public static final int PHONE_NUMBER = 1;
    /**
     * 更换业务范围联系电话
     */
    public static final int PHONE_CONTACT = 2;
    private String phone;
    private int type;
    private String org_id;

    public static void startActivity(Context context, String phone, int type) {
        startActivity(context, phone, type, "");
    }

    public static void startActivity(Context context, String phone, int type, String org_id) {
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        intent.putExtra("type", type);
        intent.putExtra("org_id", org_id);
        intent.setClass(context, ChangePhoneActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_service_activity_change_phone;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("更改联系电话", R.color.white);
        getTitleBar().setBackgroundResource(R.color.white);
        mEtPhone = (TextView) findViewById(R.id.et_phone);
        mRbtSave = (RippleButton) findViewById(R.id.rbt_save);
        org_id = getIntent().getStringExtra("org_id");
        phone = getIntent().getStringExtra("phone");
        type = getIntent().getIntExtra("type", PHONE_NUMBER);
        mEtPhone.setText(FormatUtil.phoneSetMiddleGone(phone));
        mRbtSave.showGreenButton();
        mRbtSave.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MatcherUtils.isMobilePhone(phone)) {
                    mRbtSave.showRedButton("请输入正确的手机号");
                } else {
                    // 更换手机号
                    ModifyPhoneActivity.startAction(mContext, type,org_id);
                    finish();
                }
            }
        });
        if (type == PHONE_NUMBER) { // 手机号
            showWhiteTitle("手机号", R.color.white);
        } else if (type == PHONE_CONTACT) { // 业务范围联系电话
            showWhiteTitle("更改联系电话", R.color.white);
        }
    }
}
