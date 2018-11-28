package com.horen.user.ui.activity.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.listener.ProvincesCitiesListener;
import com.horen.base.util.CityPickerViewHelper;
import com.horen.base.util.EditTextUtils;
import com.horen.base.widget.RippleButton;
import com.horen.user.R;

/**
 * @author :ChenYangYi
 * @date :2018/09/05/09:18
 * @description : 更改仓储地址
 * @github :https://github.com/chenyy0708
 */
public class StorageAddressActivity extends BaseActivity implements View.OnClickListener, ProvincesCitiesListener, EditTextUtils.EdittextInputLinstener {

    private LinearLayout mLlAddress;
    private TextView mTvAddress;
    private EditText mEtDetailedAddress;
    private RippleButton mRbtSave;
    private CityPickerViewHelper pickerViewHelper;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, StorageAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_storage_address;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("更改仓储地址", R.color.white);
        getTitleBar().setBackgroundResource(R.color.white);
        mLlAddress = (LinearLayout) findViewById(R.id.ll_address);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mEtDetailedAddress = (EditText) findViewById(R.id.et_detailed_address);
        mRbtSave = (RippleButton) findViewById(R.id.rbt_save);
        mLlAddress.setOnClickListener(this);
        pickerViewHelper = new CityPickerViewHelper(mContext, this);
        EditTextUtils editTextUtils = new EditTextUtils();
        editTextUtils.addEdittexts(mEtDetailedAddress);
        editTextUtils.addEdittextInputLinstener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_address) {
            pickerViewHelper.show();
        }
    }

    @Override
    public void onPickerCityListener(String province, String city, String area) {
        mTvAddress.setText(province + " " + city + " " + area);
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
