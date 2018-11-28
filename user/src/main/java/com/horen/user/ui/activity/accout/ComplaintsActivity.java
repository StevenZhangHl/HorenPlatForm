package com.horen.user.ui.activity.accout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.RadioSelectBean;
import com.horen.base.listener.RadioSelectListener;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;
import com.horen.base.widget.RadioSelectDialog;
import com.horen.base.widget.RippleButton;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.bean.Complaints;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/12:59
 * @description :投诉建议
 * @github :https://github.com/chenyy0708
 */
public class ComplaintsActivity extends BaseActivity implements View.OnClickListener, RadioSelectListener, TextWatcher {

    private HRTitle mToolBar;
    private LinearLayout mLlComplaints;
    private EditText mEtNote;
    private TextView mTvCount;
    private RippleButton mSbtSubmit;
    private RadioSelectDialog dialog;

    private String complain_content;
    private String complain_typeid;

    private Complaints complaints;
    private TextView mTvComplaintsName;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ComplaintsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_complaints;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mLlComplaints = (LinearLayout) findViewById(R.id.ll_complaints);
        mEtNote = (EditText) findViewById(R.id.et_note);
        mTvComplaintsName = (TextView) findViewById(R.id.tv_complaints_name);
        mTvCount = (TextView) findViewById(R.id.tv_count);
        mSbtSubmit = (RippleButton) findViewById(R.id.sbt_submit);
        mToolBar.bindActivity(this, R.color.white, "设置");
        mLlComplaints.setOnClickListener(this);
        mEtNote.addTextChangedListener(this);
        // 获取投诉对象列表
        getComplainTypeList();
        // 提交
        mSbtSubmit.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSbtSubmit.showLoadingButton();
                if (TextUtils.isEmpty(complain_typeid)) {
                    showToast("请选择投诉对象");
                    return;
                }
                createComplain();
            }
        });
    }

    /**
     * 提交投诉建议
     */
    private void createComplain() {
        mRxManager.add(ApiUser.getInstance().createComplain(UserApiPram.createComplain(UserHelper.getUserInfo().getLoginInfo().getCompany_id(), complain_content, complain_typeid))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>() {
                    @Override
                    protected void onSuccess(BaseEntry entry) {
                        showToast("投诉建议已提交");
                        finish();
                    }

                    @Override
                    protected void onError(String message) {
                        mSbtSubmit.showRedButton(message);
                    }
                }));
    }

    /**
     * 获取投诉对象列表
     */
    private void getComplainTypeList() {
        mRxManager.add(ApiUser.getInstance().getComplainTypeList(UserApiPram.getDefaultPram())
                .compose(RxHelper.<Complaints>getResult())
                .subscribeWith(new BaseObserver<Complaints>() {
                    @Override
                    protected void onSuccess(Complaints complaints) {
                        ComplaintsActivity.this.complaints = complaints;
                        List<RadioSelectBean> mData = new ArrayList<>();
                        for (Complaints.ComplainTypeListBean complainTypeListBean : complaints.getComplainTypeList()) {
                            mData.add(new RadioSelectBean(complainTypeListBean.getComplain_type()));
                        }
                        dialog = new RadioSelectDialog(mContext, mData, "投诉/建议对象");
                        dialog.setRadioSelectListener(ComplaintsActivity.this);
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_complaints) { // 投诉建议
            if (dialog != null)
                dialog.show();
        }
    }

    @Override
    public void onSelected(RadioSelectBean bean, int position) {
        complain_typeid = complaints.getComplainTypeList().get(position).getComplain_typeid();
        mTvComplaintsName.setText(bean.getTabName());
        checkStatus();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mTvCount.setText(charSequence.length() + "/50");
        complain_content = charSequence.toString();
        checkStatus();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    private void checkStatus() {
        if (!TextUtils.isEmpty(complain_content) && !TextUtils.isEmpty(complain_typeid)) {
            mSbtSubmit.showGreenButton();
        } else {
            mSbtSubmit.showGrayButton();
        }
    }
}
