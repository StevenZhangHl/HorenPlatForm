package com.horen.service.ui.activity.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.bean.RadioSelectBean;
import com.horen.base.constant.EventBusCode;
import com.horen.base.listener.RadioSelectListener;
import com.horen.base.util.CollectionUtils;
import com.horen.base.widget.HRToolbar;
import com.horen.base.widget.MessageDialog;
import com.horen.base.widget.RadioSelectDialog;
import com.horen.base.widget.RippleButton;
import com.horen.service.R;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.mvp.contract.RepairEditContract;
import com.horen.service.mvp.model.RepairAddModel;
import com.horen.service.mvp.presenter.RepairEditPresenter;
import com.horen.service.ui.activity.adapter.PhotoPickerAdapter;
import com.horen.service.utils.OrderUtils;
import com.horen.base.util.PhotoPickerHelper;
import com.zhihu.matisse.Matisse;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/13:05
 * @description :维修编辑
 * @github :https://github.com/chenyy0708
 */
public class RepairEditActivity extends BaseActivity<RepairEditPresenter, RepairAddModel> implements PhotoPickerAdapter.OnPickerListener, TextWatcher, View.OnClickListener, RadioSelectListener, PhotoPickerAdapter.onDeleteListener, RepairEditContract.View {

    private HRToolbar mToolBar;
    private TextView mTvOrderNumber;
    private TextView mTvBoxNumber;
    private EditText mEtResponsibleParty;
    private TextView mTvReportDate;
    private EditText mEtRemake;
    private TextView mTvRemakeCount;
    private RecyclerView mRvPhotoPicker;
    private RippleButton mRbtSave;
    private PhotoPickerAdapter pickerAdapter;

    /**
     * 打开相册请求码
     */
    public static final int PHOTO_REQUEST_CODE = 100;
    private RepairDetailBean.ServiceListBean repairBean;
    private View viewPsersonLiable;

    public static void startActivity(Context context, RepairDetailBean.ServiceListBean repairBean) {
        Intent intent = new Intent();
        intent.putExtra("repairBean", repairBean);
        intent.setClass(context, RepairEditActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.service_activity_repair_edit;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRToolbar) findViewById(R.id.tool_bar);
        mTvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        mTvBoxNumber = (TextView) findViewById(R.id.tv_box_number);
        mEtResponsibleParty = (EditText) findViewById(R.id.et_responsible_party);
        viewPsersonLiable = (View) findViewById(R.id.view_person_liable);
        mTvReportDate = (TextView) findViewById(R.id.tv_report_date);
        mEtRemake = (EditText) findViewById(R.id.et_remake);
        mTvRemakeCount = (TextView) findViewById(R.id.tv_remake_count);
        mRvPhotoPicker = (RecyclerView) findViewById(R.id.rv_photo_picker);
        mRbtSave = (RippleButton) findViewById(R.id.rbt_save);
        viewPsersonLiable.setOnClickListener(this);
        initToolbar(mToolBar.getToolbar(), true);
        mToolBar.setRightText(getString(R.string.service_delete));
        // 删除
        mToolBar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialog(mContext)
                        .showTitle("删除")
                        .isShowContent(false)
                        .setButtonTexts("否", "是")
                        .setButtonTextsColors(ContextCompat.getColor(mContext, R.color.color_333), ContextCompat.getColor(mContext, R.color.service_color_EB4))
                        .setOnClickListene(new MessageDialog.OnClickListener() {
                            @Override
                            public void onLeftClick() {

                            }

                            @Override
                            public void onRightClick() {
                                // 删除
                                mRbtSave.showLoadingButton();
                                mPresenter.delRepair(repairBean.getService_id());
                            }
                        })
                        .show();
            }
        });
        // 保存
        mRbtSave.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRbtSave.showLoadingButton();
                mPresenter.addRtpRepair(OrderUtils.getPerson(mEtResponsibleParty.getText().toString().trim()), mEtRemake.getText().toString().trim(), pickerAdapter.getmImageUrlList(), repairBean);
            }
        });
        // 获取编辑信息
        repairBean = (RepairDetailBean.ServiceListBean) getIntent().getSerializableExtra("repairBean");
        initRecyclerView();
        initListener();
        // 初始化数据
        initData();
    }

    private void initData() {
        // 服务单号
        mTvOrderNumber.setText(repairBean.getService_id());
        // 箱号
        mTvBoxNumber.setText(repairBean.getCtnr_sn());
        // 责任方
        mEtResponsibleParty.setText(OrderUtils.checkPerson(repairBean.getIs_person()));
        // 报备日期
        mTvReportDate.setText(repairBean.getCreate_time());
        // 备注
        mEtRemake.setText(repairBean.getRemark());
        // 图片
        if (!CollectionUtils.isNullOrEmpty(repairBean.getPositionList()))
            pickerAdapter.setNewData(repairBean.getPositionList().get(0).getImgList());
        checkBtStatus();
    }

    private void initListener() {
        mEtRemake.addTextChangedListener(this);
        // 监听软键盘关闭打开
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            mRbtSave.setVisibility(View.GONE);
                            if (mTvRemakeCount != null) {
                                if (mEtRemake.isFocused()) {
                                    mTvRemakeCount.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mRbtSave.setVisibility(View.VISIBLE);
                                }
                            }, 100);
                            if (mTvRemakeCount != null) {
                                mTvRemakeCount.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    private void initRecyclerView() {
        mRvPhotoPicker.setLayoutManager(new GridLayoutManager(mContext, 4));
        pickerAdapter = new PhotoPickerAdapter(mContext, 9, getString(R.string.service_photo_upload));
        mRvPhotoPicker.setAdapter(pickerAdapter);
        pickerAdapter.setOnPickerListener(this);
        pickerAdapter.setOnDeleteListener(this);
    }

    @Override
    public void onPicker(int position) {
        PhotoPickerHelper.start(this, 9 - pickerAdapter.getmImageUrlList().size(), PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 从相册选择图片
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> photoPaths = Matisse.obtainPathResult(data);
            pickerAdapter.addData(photoPaths);
        }
        checkBtStatus();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mTvRemakeCount.setText(charSequence.length() + "/30");
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_person_liable) {
            // 弹框
            new RadioSelectDialog(mContext, OrderUtils.getPerson(), getString(R.string.service_person_liable),
                    Integer.valueOf(OrderUtils.getPerson(mEtResponsibleParty.getText().toString().trim()))).
                    setRadioSelectListener(this).show();
        }
    }

    @Override
    public void onSelected(RadioSelectBean bean,int position) {
        mEtResponsibleParty.setText(bean.getTabName());
    }

    /**
     * 检查保存按钮的状态，选择照片是否为空
     */
    private void checkBtStatus() {
        if (pickerAdapter.getmImageUrlList().size() > 0) {
            mRbtSave.showGreenButton();
        } else {
            mRbtSave.showGrayButton();
        }
    }

    @Override
    public void onDeletePhoto() {
        checkBtStatus();
    }

    @Override
    public void saveSuccess() {
        mRbtSave.showGreenButton();
        finish();
        // 通知页面刷新数据
        EventBus.getDefault().post(EventBusCode.REFRESH_REPAIR_LIST, EventBusCode.REFRESH_REPAIR_LIST);
    }

    @Override
    public void delRepairSuccess(String service_id) {
        mRbtSave.showGreenButton();
        finish();
        // 通知页面刷新数据
        EventBus.getDefault().post(EventBusCode.DELETE, EventBusCode.REFRESH_REPAIR_LIST);
    }

    @Override
    public void onError(String msg) {
        mRbtSave.showRedButton(msg);
    }
}
