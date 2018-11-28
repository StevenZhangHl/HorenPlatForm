package com.horen.user.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.TypeBean;
import com.horen.base.listener.MultipleSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.EditTextUtils;
import com.horen.base.util.KeybordS;
import com.horen.base.util.MatcherUtils;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.HRToolbar;
import com.horen.base.widget.PWEditText;
import com.horen.base.widget.RippleButton;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.bean.PurposeBean;
import com.horen.user.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity implements EditTextUtils.EdittextInputLinstener, View.OnClickListener {

    /**
     * 请输入您的手机号
     */
    private PWEditText et_phone;
    /**
     * 请输入您的姓名
     */
    private PWEditText et_contanct_name;
    /**
     * 请输入公司或个人名称
     */
    private PWEditText et_company_name;
    /**
     * 请输入公司或个人地址
     */
    private PWEditText et_address;
    /**
     * 请选择合作意向
     */
    private PWEditText et_purpose;
    private RelativeLayout rl_selecet_purpose;
    private RippleButton bt_submit;
    private EditTextUtils editTextUtils;
    private String purposeType;
    private List<TypeBean> typeBeanList = new ArrayList<>();
    private HRToolbar toolbar;

    public static void startRegisterActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar = (HRToolbar) findViewById(R.id.tool_bar);
        initToolbar(toolbar.getToolbar(), true,R.color.white);
        et_phone = (PWEditText) findViewById(R.id.et_phone);
        et_contanct_name = (PWEditText) findViewById(R.id.et_contanct_name);
        et_company_name = (PWEditText) findViewById(R.id.et_company_name);
        et_address = (PWEditText) findViewById(R.id.et_address);
        et_purpose = (PWEditText) findViewById(R.id.et_purpose);
        rl_selecet_purpose = (RelativeLayout) findViewById(R.id.rl_selecet_purpose);
        bt_submit = (RippleButton) findViewById(R.id.bt_submit);
        //监听
        editTextUtils = new EditTextUtils();
        editTextUtils.addEdittexts(et_phone, et_contanct_name, et_company_name, et_address, et_purpose);
        editTextUtils.addEdittextInputLinstener(this);
        bt_submit.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeybordS.closeAllKeybord(RegisterActivity.this);
                submitRegister();
            }
        });
        rl_selecet_purpose.setOnClickListener(this);
        getPurposeData();
    }

    /**
     * 获取或作意向数据
     */
    private void getPurposeData() {
        mRxManager.add(ApiUser.getInstance().getPurposeData(UserApiPram.getPurposeData()).compose(RxHelper.<List<PurposeBean>>getResult()).subscribeWith(new BaseObserver<List<PurposeBean>>(this, true) {
            @Override
            protected void onSuccess(List<PurposeBean> purposeBeans) {
                typeBeanList.addAll(getTypeBeanList(purposeBeans));
            }

            @Override
            protected void onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }

    private void submitRegister() {
        String mobile = et_phone.getTrimmedString();
        String consult_uname = et_contanct_name.getTrimmedString();
        String company_name = et_company_name.getTrimmedString();
        String address = et_address.getTrimmedString();
        String user_type = purposeType;
        if (!MatcherUtils.isMobilePhone(mobile)) {
            ToastUitl.showShort("请输入正确的手机号");
            return;
        }
        bt_submit.showLoadingButton();
        mRxManager.add(ApiUser.getInstance().submitRegister(UserApiPram.submitRegister(mobile, consult_uname, company_name, address, user_type)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(this, false) {
            @Override
            protected void onSuccess(BaseEntry baseEntry) {
                ToastUitl.showShort("预约成功");
                finish();
            }

            @Override
            protected void onError(String message) {
                bt_submit.showRedButton(message);
            }
        }));
    }

    @Override
    public void onSuccess() {
        bt_submit.showGreenButton();
    }

    @Override
    public void onError() {
        bt_submit.showGrayButton();
    }

    @Override
    public void onClick(View view) {
        if (view == rl_selecet_purpose) {
            if (typeBeanList.size() == 0) {
                ToastUitl.showShort("获取合作意向数据失败，请检查您的网络设置");
                return;
            }
            KeybordS.closeAllKeybord(RegisterActivity.this);
            final CustomDialog dialog = new CustomDialog(this, typeBeanList, "合作意向");
            dialog.setSelectListener(new MultipleSelectListener() {
                @Override
                public void onMultipleSelect(List<Integer> mSelect) {
                    String result = "";
                    if (mSelect.size() == 0) {
                        purposeType = "";
                        et_purpose.getText().clear();
                        return;
                    }
                    for (int i = 0; i < mSelect.size(); i++) {
                        for (int j = 0; j < typeBeanList.size(); j++) {
                            if (Integer.parseInt(typeBeanList.get(j).getTypeId()) == mSelect.get(i)) {
                                if (i > 0) {
                                    result = result + " + " + typeBeanList.get(j).getTypeName();
                                } else {
                                    result = typeBeanList.get(j).getTypeName();
                                }
                            }
                        }
                    }
                    et_purpose.setText(result);
                    if (mSelect.size() == 2) {
                        purposeType = "5";
                    } else {
                        purposeType = mSelect.get(0) + "";
                    }
                }
            });
            dialog.show();
        }
    }

    /**
     * 转换数据
     *
     * @param purposeBeans
     * @return
     */
    private List<TypeBean> getTypeBeanList(List<PurposeBean> purposeBeans) {
        List<TypeBean> result = new ArrayList<>();
        for (int i = 0; i < purposeBeans.size(); i++) {
            TypeBean typeBean = new TypeBean(purposeBeans.get(i).getUser_type(), purposeBeans.get(i).getType_name(), false);
            result.add(typeBean);
        }
        return result;
    }
}
