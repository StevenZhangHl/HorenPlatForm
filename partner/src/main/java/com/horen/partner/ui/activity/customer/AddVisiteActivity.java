package com.horen.partner.ui.activity.customer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.UploadBean;
import com.horen.base.constant.Constants;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.EditTextUtils;
import com.horen.base.util.Glide4Engine;
import com.horen.base.util.KeybordS;
import com.horen.base.util.MatcherUtils;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.MyGridView;
import com.horen.base.widget.PWEditText;
import com.horen.base.widget.RippleButton;
import com.horen.base.widget.TranslateLoadingDialog;
import com.horen.partner.R;
import com.horen.partner.adapter.ShowPhotoAdapter;
import com.horen.partner.api.ApiPartner;
import com.horen.partner.api.ApiRequest;
import com.horen.partner.bean.VisiteNoteBaseBean;
import com.horen.partner.ui.activity.PlusImageActivity;
import com.xw.repo.XEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class AddVisiteActivity extends BaseActivity implements View.OnClickListener {
    private final int REQUEST_CODE_CHOOSE = 1;
    private final int REQUEST_CODE_EDIT_IMAGE = 2;
    public static final int RESULT_CODE_VIEW_IMG = 11; //查看大图页面的结果码
    /**
     * 拜访人:
     */
    private PWEditText mTvVisitePeople;
    /**
     * 拜访人电话:
     */
    private PWEditText mTvVisitePeopleTel;
    /**
     * 拜访地址
     */
    private PWEditText mTvVisiteAddress;

    /**
     * 请输入需求
     */
    private EditText mEtNeeds;
    /**
     * 提交
     */
    private RippleButton bt_add_visite;
    /**
     * 图片预览
     */
    private MyGridView mPhotoRecycleview;
    private ShowPhotoAdapter showPhotoAdapter;
    private List<String> imageBeanList = new ArrayList<>();
    private boolean hasAdd = true;
    private String visiteId;
    private TranslateLoadingDialog loadingDialog;
    private TextView tv_str_count;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_visite;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvVisitePeople = (PWEditText) findViewById(R.id.et_contact);
        mTvVisitePeopleTel = (PWEditText) findViewById(R.id.et_phone);
        mPhotoRecycleview = (MyGridView) findViewById(R.id.photo_recycleview);
        mEtNeeds = (EditText) findViewById(R.id.et_needs);
        mTvVisiteAddress = (PWEditText) findViewById(R.id.et_visite_address);
        bt_add_visite = (RippleButton) findViewById(R.id.bt_add_visite);
        tv_str_count = (TextView) findViewById(R.id.tv_str_count);
        showPhotoAdapter = new ShowPhotoAdapter(this, imageBeanList);
        mPhotoRecycleview.setAdapter(showPhotoAdapter);
        mPhotoRecycleview.setOnItemClickListener(new OnItemChildClickListener());
        showWhiteTitle("拜访记录");
        getIntentData();
        initEditTextChangeListener();
        bt_add_visite.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
        loadingDialog = new TranslateLoadingDialog();
    }

    private void getIntentData() {
        if (getIntent().getSerializableExtra(Constants.PARTNER_VISITE_INFO) != null) {
            VisiteNoteBaseBean info = (VisiteNoteBaseBean) getIntent().getSerializableExtra(Constants.PARTNER_VISITE_INFO);
            setViewData(info);
            hasAdd = false;
            visiteId = info.getVisit_id();
        }
    }

    /**
     * 编辑信息时设置默认数据
     *
     * @param info
     */
    private void setViewData(VisiteNoteBaseBean info) {
        mTvVisitePeople.setText(info.getVisit_name());
        mTvVisitePeopleTel.setText(info.getVisit_tel());
        mTvVisiteAddress.setText(info.getVisit_addr());
        mEtNeeds.setText(info.getVisit_content());
        if (info.getPhotosUrl() != null) {
            imageBeanList.addAll(info.getPhotosUrl());
            showPhotoAdapter.setNewData(info.getPhotosUrl());
        }
        updateContent();
    }

    private void initEditTextChangeListener() {
        mTvVisitePeople.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvVisiteAddress.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvVisitePeopleTel.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtNeeds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 30) {
                    tv_str_count.setText("30 / 30");
                    return;
                }
                tv_str_count.setText(mEtNeeds.getText().toString().length() + " / 30");
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEtNeeds.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    tv_str_count.setVisibility(View.INVISIBLE);
                } else {
                    tv_str_count.setVisibility(View.VISIBLE);
                    tv_str_count.setText(mEtNeeds.getText().toString().length() + " / 30");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
    }

    /**
     * 更新保存按钮状态
     */
    private void updateContent() {
        final String customer_address = mTvVisiteAddress.getText().toString().trim();
        final String customer_name = mTvVisitePeople.getText().toString().trim();
        final String customer_tel = mTvVisitePeopleTel.getText().toString().trim();
        final String requirements = mEtNeeds.getText().toString().trim();
        if (customer_address.length() == 0) {
            bt_add_visite.setEnabled(false);
            bt_add_visite.showGrayButton();
            return;
        }
        if (customer_name.length() == 0) {
            bt_add_visite.setEnabled(false);
            bt_add_visite.showGrayButton();
            return;
        }
        if (customer_tel.length() == 0) {
            bt_add_visite.setEnabled(false);
            bt_add_visite.showGrayButton();
            return;
        }
        if (requirements.length() == 0) {
            bt_add_visite.setEnabled(false);
            bt_add_visite.showGrayButton();
            return;
        }
        bt_add_visite.setEnabled(true);
        bt_add_visite.showGreenButton();
    }

    private void submitData() {
        final String customer_id = getIntent().getStringExtra(Constants.PARTNER_CUSTOMER_CUSTOMER_ID);
        final String customer_address = mTvVisiteAddress.getText().toString().trim();
        final String customer_name = mTvVisitePeople.getText().toString().trim();
        final String customer_tel = mTvVisitePeopleTel.getText().toString().trim();
        final String requirements = mEtNeeds.getText().toString().trim();
        if (customer_name.length() == 0) {
            ToastUitl.showShort("拜访人不能为空");
            return;
        }
        if (customer_tel.length() == 0) {
            ToastUitl.showShort("拜访人电话不能为空");
            return;
        }
        if (!MatcherUtils.isMobilePhone(customer_tel)) {
            ToastUitl.showShort("请输入正确的手机号");
            return;
        }
        if (customer_address.length() == 0) {
            ToastUitl.showShort("拜访地址不能为空");
            return;
        }
        if (requirements.length() == 0) {
            ToastUitl.showShort("需求信息不能为空");
            return;
        }
        loadingDialog.showDialogForLoading(this, false);
        bt_add_visite.showLoadingButton();
        imageBeanList = showPhotoAdapter.getBeanList();
        final List<File> files = new ArrayList<>();
        if (hasAdd) {
            if (imageBeanList.size() != 0) {
                mRxManager.add(Observable.just(imageBeanList).compose(RxHelper.uploadFile(files)).subscribeWith(new BaseObserver<UploadBean>(this, false) {
                    @Override
                    protected void onSuccess(UploadBean uploadBean) {
                        if (uploadBean.getCode().equals("000000")) {
                            List<String> resultImages = new ArrayList<>();
                            for (int i = 0; i < uploadBean.getData().size(); i++) {
                                resultImages.add(uploadBean.getData().get(i).getWatchUrl());
                            }
                            mRxManager.add(ApiPartner.getInstance().addVisiteNoteInfo(ApiRequest.addVisiteNoteInfo(customer_id, customer_address, requirements, customer_name, customer_tel, resultImages)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {

                                @Override
                                protected void onSuccess(BaseEntry o) {
                                    if (o.success()) {
                                        ToastUitl.showShort("拜访新增成功");
                                        setResult(RESULT_OK);
                                        loadingDialog.cancelDialogForLoading();
                                        finish();
                                    }
                                }

                                @Override
                                protected void onError(String message) {
                                    loadingDialog.cancelDialogForLoading();
                                    bt_add_visite.showRedButton(message);
                                }
                            }));
                        }
                        for (File file : files) {
                            file.delete();
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        loadingDialog.cancelDialogForLoading();
                        bt_add_visite.showRedButton(message);
                    }
                }));
            } else {
                mRxManager.add(ApiPartner.getInstance().addVisiteNoteInfo(ApiRequest.addVisiteNoteInfo(customer_id, customer_address, requirements, customer_name, customer_tel, imageBeanList)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {

                    @Override
                    protected void onSuccess(BaseEntry o) {
                        if (o.success()) {
                            ToastUitl.showShort("拜访新增成功");
                            setResult(RESULT_OK);
                            loadingDialog.cancelDialogForLoading();
                            finish();
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        bt_add_visite.showRedButton(message);
                        loadingDialog.cancelDialogForLoading();
                    }
                }));
            }
        } else {
            List<String> uploadFiles = new ArrayList<>();
            final List<String> serverFiles = new ArrayList<>();
            for (int i = 0; i < imageBeanList.size(); i++) {
                if (imageBeanList.get(i).startsWith("http://")) {
                    serverFiles.add(imageBeanList.get(i));
                } else {
                    uploadFiles.add(imageBeanList.get(i));
                }
            }
            if (uploadFiles.size() != 0) {
                mRxManager.add(Observable.just(imageBeanList).compose(RxHelper.uploadFile(files)).subscribeWith(new BaseObserver<UploadBean>(this, true) {
                    @Override
                    protected void onSuccess(UploadBean uploadBean) {
                        if (uploadBean.getCode().equals("000000")) {
                            List<String> resultImages = new ArrayList<>();
                            for (int i = 0; i < uploadBean.getData().size(); i++) {
                                resultImages.add(uploadBean.getData().get(i).getWatchUrl());
                            }
                            resultImages.addAll(serverFiles);
                            mRxManager.add(ApiPartner.getInstance().eidtVisiteNoteInfo(ApiRequest.editVisiteNoteInfo(visiteId, customer_address, requirements, customer_name, customer_tel, resultImages)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
                                @Override
                                protected void onSuccess(BaseEntry baseEntry) {
                                    ToastUitl.showShort("拜访修改成功");
                                    setResult(RESULT_OK);
                                    loadingDialog.cancelDialogForLoading();
                                    finish();
                                }

                                @Override
                                protected void onError(String message) {
                                    ToastUitl.showShort(message);
                                    bt_add_visite.showRedButton(message);
                                    loadingDialog.cancelDialogForLoading();
                                }
                            }));
                        }
                        for (File file : files) {
                            file.delete();
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        bt_add_visite.showRedButton(message);
                        loadingDialog.cancelDialogForLoading();
                    }
                }));
            } else {
                mRxManager.add(ApiPartner.getInstance().eidtVisiteNoteInfo(ApiRequest.editVisiteNoteInfo(visiteId, customer_address, requirements, customer_name, customer_tel, serverFiles)).compose(RxHelper.handleResult()).subscribeWith(new BaseObserver<BaseEntry>(mContext, false) {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        ToastUitl.showShort("拜访修改成功");
                        setResult(RESULT_OK);
                        loadingDialog.cancelDialogForLoading();
                        finish();
                    }

                    @Override
                    protected void onError(String message) {
                        ToastUitl.showShort(message);
                        loadingDialog.cancelDialogForLoading();
                    }
                }));
            }
        }

    }

    private class OnItemChildClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == adapterView.getChildCount() - 1) {
                if (imageBeanList.size() == 6) {
                    ToastUitl.showShort("最多选6张图");
                    viewPluImg(i);
                } else {
                    startSelectView(imageBeanList.size());
                }
            } else {
                viewPluImg(i);
            }
        }
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra("imageList", (ArrayList<String>) imageBeanList);
        intent.putExtra("imagePosition", position);
        startActivityForResult(intent, REQUEST_CODE_EDIT_IMAGE);
    }

    /**
     * 拉起图片选择和相机拍照
     */
    private void startSelectView(int size) {
        Matisse.from(AddVisiteActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(6 - size)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, HRConstant.FILE_PROVIDER))
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize())
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private List<String> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainPathResult(data);
            Log.i("Matisse", "mSelected: " + mSelected);
            imageBeanList.addAll(mSelected);
            showPhotoAdapter.setNewData(imageBeanList);
        }
        if (requestCode == REQUEST_CODE_EDIT_IMAGE) {
            imageBeanList = data.getStringArrayListExtra("imageList");
            showPhotoAdapter.setNewData(imageBeanList);
        }
    }
}
