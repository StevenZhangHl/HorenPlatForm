package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.AddressBook;
import com.cyy.company.enums.OrgType;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.FormatUtil;
import com.horen.base.widget.HRTitle;
import com.horen.base.widget.PWEditText;
import com.suke.widget.SwitchButton;

import org.simple.eventbus.EventBus;

/**
 * @author :ChenYangYi
 * @date :2018/10/15/18:00
 * @description :编辑地址
 * @github :https://github.com/chenyy0708
 */
public class AddressEditActivity extends BaseActivity {

    private HRTitle mToolBar;
    private PWEditText mEtName;
    private PWEditText mEtPhone;
    private TextView mTvOrgName;
    private TextView mTvAddress;
    private SwitchButton mSwitchButton;
    private AddressBook.PdListBean.ListBean addressBook;

    public static void startAction(Context context, AddressBook.PdListBean.ListBean addressBook, String org_type) {
        Intent intent = new Intent();
        intent.putExtra("org_type", org_type);
        intent.putExtra("addressBook", addressBook);
        intent.setClass(context, AddressEditActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_edit;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mEtName = (PWEditText) findViewById(R.id.et_name);
        mEtPhone = (PWEditText) findViewById(R.id.et_phone);
        mTvOrgName = (TextView) findViewById(R.id.tv_org_name);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mSwitchButton = (SwitchButton) findViewById(R.id.switch_button);
        mToolBar.bindActivity(this, R.color.color_f5);
        addressBook = (AddressBook.PdListBean.ListBean) getIntent().getSerializableExtra("addressBook");
        mToolBar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtName.getText()) || TextUtils.isEmpty(mEtPhone.getText())) {
                    showToast("姓名和联系电话不能为空");
                    return;
                }
                // 手机校验
                if (!FormatUtil.isMobileNO(mEtPhone.getText().toString().trim())) {
                    showToast("请填写正确的联系电话");
                    return;
                }
                upOrderConsignee();
            }
        });
        mEtName.setText(addressBook.getOrg_consignee());
        mEtName.setSelection(mEtName.getText().length());
        mEtPhone.setText(addressBook.getOrg_consigneetel());
        mEtPhone.setSelection(mEtName.getText().length());
        mTvOrgName.setText(addressBook.getOrg_name());
        mTvAddress.setText(addressBook.getOrg_address());
        // 是否默认
        if (addressBook.getFlag_defaultorg().equals("1")) {
            mSwitchButton.setChecked(true);
            mSwitchButton.setVisibility(View.GONE);
        } else {
            mSwitchButton.setChecked(false);
            mSwitchButton.setVisibility(View.VISIBLE);
        }
        // 下游隐藏默认
        if (getIntent().getStringExtra("org_type").equals(OrgType.TWO.getPosition())) {
            findViewById(R.id.ll_normal).setVisibility(View.GONE);
        }
    }

    /**
     * 网点地址联系人和电话修改
     */
    private void upOrderConsignee() {
        mRxManager.add(ApiCompany.getInstance().upOrderConsignee(CompanyParams.upOrderConsignee(addressBook.getOrg_id(),
                mSwitchButton.isChecked() ? "1" : "0", mEtName.getText().toString(), mEtPhone.getText().toString()))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>(mContext, true) {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        showToast("保存成功");
                        EventBus.getDefault().post(EventBusCode.REFRESH_ADDRESS,
                                EventBusCode.REFRESH_ADDRESS);
                        finish();
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }
}
